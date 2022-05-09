package net.springboot.productOrder.repository;

import net.springboot.common.base.ServiceResponse;
import net.springboot.common.repository.BaseRepository;
import net.springboot.productOrder.payload.ProductOrderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
    public ServiceResponse SaveProductOrder(List<ProductOrderRequest> request) {

        return new ServiceResponse(false,"M");
    }
}
