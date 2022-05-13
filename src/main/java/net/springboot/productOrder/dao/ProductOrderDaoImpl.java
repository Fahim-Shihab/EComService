package net.springboot.productOrder.dao;

import lombok.var;
import net.springboot.common.base.Defs;
import net.springboot.common.base.ServiceResponse;
import net.springboot.common.enums.OrderStatus;
import net.springboot.common.enums.Status;
import net.springboot.common.repository.BaseRepository;
import net.springboot.common.util.SearchUtil;
import net.springboot.common.util.Utils;
import net.springboot.product.model.Product;
import net.springboot.productOrder.model.OrderInfo;
import net.springboot.productOrder.model.ProductOrder;
import net.springboot.productOrder.payload.GetOrderInfoRequest;
import net.springboot.productOrder.payload.GetOrderInfoResponse;
import net.springboot.productOrder.payload.SaveOrderDetailRequest;
import net.springboot.productOrder.payload.OrderInfoRequest;
import net.springboot.security.model.LoggedInUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class ProductOrderDaoImpl implements ProductOrderDao {
    @PersistenceContext
    EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductOrderDaoImpl.class);

    private final BaseRepository repository;

    public ProductOrderDaoImpl(BaseRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public ServiceResponse SaveProductOrder(SaveOrderDetailRequest request) {

        try{
            boolean isOrderInfoUpdate = false;
            OrderInfo orderInfo = new OrderInfo();

            if (request == null || request.getOrderInfo() == null || request.getProductOrderList() == null){
                return new ServiceResponse(false,"Order cannot be empty");
            }

            if (Utils.isOk(request.getOrderInfo().getOrderId())) {
                String sql = "SELECT * FROM order_info WHERE order_id=:Id ";
                Map<String, Object> params = new HashMap<>();
                params.clear();
                params.put("Id", request.getOrderInfo().getOrderId());
                orderInfo = repository.findSingleResultByNativeQuery(sql, OrderInfo.class, params);

                if (orderInfo != null) {
                    isOrderInfoUpdate = true;
                    orderInfo.setOrderId(request.getOrderInfo().getOrderId());
                } else {
                    orderInfo = new OrderInfo();
                    orderInfo.setOrderId(UUID.randomUUID().toString().replace("-","").substring(0,10));
                }
            }
            else{
                orderInfo = new OrderInfo();
                orderInfo.setOrderId(UUID.randomUUID().toString().replace("-","").substring(0,10));
            }

            Timestamp timestamp = Utils.getCurrentTimeStamp();

            LoggedInUser user = (LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            orderInfo.setOrderBy(user.getId());
            orderInfo.setOrderDate(request.getOrderInfo().getOrderDate());
            orderInfo.setTotalPrice(request.getOrderInfo().getTotalPrice());
            orderInfo.setCustomerComments(request.getOrderInfo().getCustomerComments());
            orderInfo.setAdminComments(request.getOrderInfo().getAdminComments());
            orderInfo.setDeliveryAddress(request.getOrderInfo().getDeliveryAddress());
            orderInfo.setExpectedDeliveryDate(request.getOrderInfo().getExpectedDeliveryDate());
            orderInfo.setCompletedDeliveryDate(request.getOrderInfo().getCompletedDeliveryDate());
            orderInfo.setOrderStatus(request.getOrderInfo().getOrderStatus().getCode());
            orderInfo.setStatus(request.getOrderInfo().getStatus().getCode());

            if(!isOrderInfoUpdate) {
                orderInfo.setEntryDate(timestamp);
                orderInfo.setEntryById(user.getId());

                repository.persist(orderInfo);
            }
            else if(isOrderInfoUpdate) {
                orderInfo.setUpdDate(timestamp);
                orderInfo.setUpdById(user.getId());

                repository.merge(orderInfo);
            }

            for (var obj: request.getProductOrderList()) {

                Product product = new Product();

                if (Utils.isOk(obj.getProductId())) {
                    String sql = "SELECT * FROM product WHERE id=:Id ";
                    Map<String, Object> params = new HashMap<>();
                    params.clear();
                    params.put("Id", obj.getProductId());
                    product = repository.findSingleResultByNativeQuery(sql, Product.class, params);

                    if (product == null) {
                        return new ServiceResponse(false, "Wrong product id");
                    }
                }
                else{
                    return new ServiceResponse(false, "Product Id is required");
                }

                boolean isOrderUpdate = false;
                ProductOrder productOrder = new ProductOrder();

                if (Utils.isOk(obj.getId())){
                    String sql = "SELECT * FROM product_order WHERE id=:Id ";
                    Map<String, Object> params = new HashMap<>();
                    params.clear();
                    params.put("Id", obj.getId());
                    productOrder = repository.findSingleResultByNativeQuery(sql, ProductOrder.class, params);

                    if (productOrder != null) {
                        isOrderUpdate = true;
                        productOrder.setId(obj.getId());
                    } else {
                        productOrder = new ProductOrder();
                        productOrder.setId(UUID.randomUUID().toString().replace("-","").substring(0,20));
                    }
                }
                else {
                    productOrder = new ProductOrder();
                    productOrder.setId(UUID.randomUUID().toString().replace("-","").substring(0,20));
                }

                productOrder.setOrderId(orderInfo.getOrderId());
                productOrder.setAdminComments(obj.getAdminComments());
                productOrder.setAmount(obj.getAmount());
                productOrder.setCustomerComments(obj.getCustomerComments());
                productOrder.setDiscountCode(obj.getDiscountCode());
                productOrder.setOrderStatus(obj.getOrderStatus().getCode());
                productOrder.setStatus(obj.getStatus().getCode());
                productOrder.setPrice(obj.getPrice());
                productOrder.setProductId(product);
                productOrder.setReturnDate(obj.getReturnDate());

                if(!isOrderUpdate) {
                    productOrder.setEntryDate(timestamp);
                    productOrder.setEntryById(user.getId());

                    repository.persist(productOrder);
                }
                else if(isOrderUpdate) {
                    productOrder.setUpdDate(timestamp);
                    productOrder.setUpdById(user.getId());

                    repository.merge(productOrder);
                }
            }

        }catch (Throwable t) {
            LOGGER.error("ORDER SAVE ERROR:", t);
            return new ServiceResponse(false, "Internal server error. Please contact with admin");
        }
        return new ServiceResponse(true, "Order has been placed successfully");
    }

    @Override
    @Transactional
    public GetOrderInfoResponse GetProductOrder(GetOrderInfoRequest request)
    {
        SearchUtil util = new SearchUtil();

        try{

            GetOrderInfoResponse response = new GetOrderInfoResponse();

            String sql = "SELECT * FROM order_info WHERE 1=1 ";
            String sqlCount = "SELECT COUNT(*) FROM order_info WHERE 1=1 ";
            Map<String, Object> params = new HashMap<>();

            String whereClause = "";

            if (Utils.isOk(request.getOrderId())) {
                whereClause += " AND order_id = '"+request.getOrderId()+"'";
            }
            if (Utils.isOk(request.getOrderBy())){
                whereClause += " AND order_by = '"+request.getOrderBy()+"'";
            }
            if (Utils.isOk(request.getOrderDate())){
                whereClause += " AND order_date = '"+request.getOrderDate()+"'";
            }
            if (Utils.isOk(request.getOrderStatus())){
                whereClause += " AND order_status = '"+request.getOrderStatus().getCode()+"'";
            }
            if (Utils.isOk(request.getStatus())){
                whereClause += " AND status = '"+request.getStatus().getCode()+"'";
            }
            if (request.getPage() - 1 <= 0) {
                request.setPage(0);
            } else {
                request.setPage(request.getPage() - 1);
            }
            if (request.getSize() == 0) {
                request.setSize(Defs.DEFAULT_LIMIT);
            }

            sql += whereClause;
            sqlCount += whereClause;

            Query qCount = em.createNativeQuery(sqlCount);

            qCount = util.setQueryParameter(qCount, request);
            BigInteger count = (BigInteger) qCount.getSingleResult();

            if (count.longValueExact() <= 0 || (count.longValueExact() < (request.getPage()*request.getSize()))){
                return new GetOrderInfoResponse("Could not find product order");
            }

            Query q = em.createNativeQuery(sql, OrderInfo.class);
            q.setFirstResult(request.getPage()*request.getSize());
            q.setMaxResults(request.getSize());
            q = util.setQueryParameter(q,request);

            List<OrderInfo> entitylist = q.getResultList();

            if (entitylist != null) {

                List<OrderInfoRequest> list = new ArrayList<>();
                entitylist.forEach(orderInfo -> {
                    OrderInfoRequest obj = new OrderInfoRequest();
                    obj.setAdminComments(orderInfo.getAdminComments());
                    obj.setCompletedDeliveryDate(orderInfo.getCompletedDeliveryDate());
                    obj.setCustomerComments(orderInfo.getCustomerComments());
                    obj.setDeliveryAddress(orderInfo.getDeliveryAddress());
                    obj.setExpectedDeliveryDate(orderInfo.getExpectedDeliveryDate());
                    obj.setOrderBy(orderInfo.getOrderBy());
                    obj.setOrderDate(orderInfo.getOrderDate());
                    obj.setOrderId(orderInfo.getOrderId());
                    obj.setOrderStatus(OrderStatus.getByCode(orderInfo.getOrderStatus()));
                    obj.setStatus(Status.getByCode(orderInfo.getStatus()));
                    obj.setTotalPrice(orderInfo.getTotalPrice());

                    list.add(obj);
                });
                response.setOrderInfoDetailsList(list);
                response.setTotal(count);

                return response;
            }

        }catch (Throwable t) {
            LOGGER.error("Product order fetch ERROR:", t);
            return new GetOrderInfoResponse("Internal server error. Please contact with admin");
        }

        return new GetOrderInfoResponse("Error fetching order");
    }
}
