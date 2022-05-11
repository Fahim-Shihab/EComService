package net.springboot.productOrder.repository;

import lombok.var;
import net.springboot.common.base.ServiceResponse;
import net.springboot.common.repository.BaseRepository;
import net.springboot.common.util.Utils;
import net.springboot.product.model.Product;
import net.springboot.productOrder.model.OrderInfo;
import net.springboot.productOrder.model.ProductOrder;
import net.springboot.productOrder.payload.OrderDetailSaveRequest;
import net.springboot.security.model.LoggedInUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class ProductOrderRepository {
    @PersistenceContext
    EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductOrderRepository.class);

    private final BaseRepository repository;

    public ProductOrderRepository(BaseRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ServiceResponse SaveProductOrder(OrderDetailSaveRequest request) {

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
                    orderInfo.setOrderId(UUID.randomUUID().toString().substring(0,10));
                }
            }
            else{
                orderInfo = new OrderInfo();
                orderInfo.setOrderId(UUID.randomUUID().toString().substring(0,10));
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
                        productOrder.setId(UUID.randomUUID().toString().substring(0,20));
                    }
                }
                else {
                    productOrder = new ProductOrder();
                    productOrder.setId(UUID.randomUUID().toString().substring(0,20));
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
}
