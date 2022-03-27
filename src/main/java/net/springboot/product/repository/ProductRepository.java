package net.springboot.product.repository;

import net.springboot.common.base.ServiceResponse;
import net.springboot.common.util.Utils;
import net.springboot.lookup.repository.BaseRepository;
import net.springboot.product.model.Product;
import net.springboot.product.model.ProductType;
import net.springboot.product.payload.product.SaveProductRequest;
import net.springboot.security.model.LoggedInUser;
import net.springboot.vendor.model.Vendor;
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
public class ProductRepository {

    @PersistenceContext
    EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductTypeRepository.class);

    private final BaseRepository repository;

    public ProductRepository(BaseRepository repository){
        this.repository = repository;
    }

    @Transactional
    public ServiceResponse SaveProduct(SaveProductRequest request){
        try{

            boolean isUpdate = false;
            Product product = new Product();

            if (Utils.isOk(request.getId())) {
                String sql = "SELECT * FROM product WHERE id=:Id ";
                Map<String, Object> params = new HashMap<>();
                params.clear();
                params.put("Id", request.getId());
                product = repository.findSingleResultByNativeQuery(sql, Product.class, params);

                if (product != null) {
                    isUpdate = true;
                    product.setId(request.getId());
                } else {
                    product = new Product();
                    product.setId(UUID.randomUUID().toString().substring(0,10));
                }
            }
            else{
                product = new Product();
                product.setId(UUID.randomUUID().toString().substring(0,10));
            }

            if (request.getVendorId() > 0) {
                String sql = "SELECT * FROM vendor WHERE id=:vendorId ";
                Map<String, Object> params = new HashMap<>();
                params.clear();
                params.put("vendorId", request.getVendorId());
                Vendor vendor = repository.findSingleResultByNativeQuery(sql, Vendor.class, params);

                if (vendor == null) {
                    return new ServiceResponse(false, "Wrong vendor id");
                } else {
                    product.setVendorId(vendor);
                }
            }
            else{
                return new ServiceResponse(false, "Vendor is required");
            }

            if (request.getProductType() > 0) {
                String sql = "SELECT * FROM product_type WHERE id=:queryId ";
                Map<String, Object> params = new HashMap<>();
                params.clear();
                params.put("queryId", request.getVendorId());
                ProductType productType = repository.findSingleResultByNativeQuery(sql, ProductType.class, params);

                if (productType == null) {
                    return new ServiceResponse(false, "Wrong product type");
                } else {
                    product.setProductType(productType);
                }
            }
            else{
                return new ServiceResponse(false,"Product Type is required");
            }

            product.setAmount(request.getAmount());
            product.setName(request.getName());
            product.setPurchaseCost(request.getPurchaseCost());
            product.setUnitPrice(request.getUnitPrice());
            product.setStatus(request.getStatus().getCode());
            product.setExpiryDate(Utils.getDate(request.getExpiryDate(), "dd/MM/yyyy"));
            product.setManufactureDate(Utils.getDate(request.getManufactureDate(),"dd/MM/yyyy"));
            product.setPurchaseDate(Utils.getDate(request.getPurchaseDate(),"dd/MM/yyyy"));

            Timestamp timestamp = Utils.getCurrentTimeStamp();

            LoggedInUser user = (LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if(!isUpdate) {
                product.setEntryDate(timestamp);
                product.setEntryById(user.getId());

                repository.persist(product);
            }
            else if(isUpdate) {
                product.setUpdDate(timestamp);
                product.setUpdById(user.getId());

                repository.merge(product);
            }

        }catch (Throwable t) {
            LOGGER.error("PRODUCT SAVE ERROR:", t);
            return new ServiceResponse(false, "Internal server error. Please contact with admin");
        }
        return new ServiceResponse(true, "PRODUCT has been saved successfully");
    }
}
