package net.springboot.orderInformation.repository;

import net.springboot.common.base.ServiceResponse;
import net.springboot.common.repository.BaseRepository;
import net.springboot.orderInformation.payload.OrderInfoRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class OrderInfoRepository {
    @PersistenceContext
    EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderInfoRepository.class);

    private final BaseRepository repository;

    public OrderInfoRepository(BaseRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ServiceResponse SaveOrderInfo(OrderInfoRequest request) {

        return new ServiceResponse(false,"M");
    }
}
