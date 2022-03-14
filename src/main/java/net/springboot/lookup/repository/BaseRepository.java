package net.springboot.lookup.repository;

import net.springboot.common.base.Defs;
import net.springboot.common.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.*;

@Repository
public class BaseRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseRepository.class);
    @Autowired
    private EntityManager em;

    @Transactional
    public <T> T persist(T entity) {
        em.persist(entity);
        return entity;
    }

    @Transactional
    public <T> T merge(T entity) {
        em.merge(entity);
        return entity;
    }

    @Transactional
    public int deleteByIdIn(Class entity, List<Integer> ids) {
        Query query = em.createQuery("delete from " + entity.getSimpleName() + " o where o.id in(" + Utils.listToString(ids) + ")");
        return query.executeUpdate();
    }

    public <T> T findByFieldName(Class entity, Object value, String fieldName) {
        if (!Utils.isOk(value) || !Utils.isOk(fieldName)) {
            return null;
        }
        String sql = "select o from " + entity.getSimpleName() + " o where o." + fieldName + "=:fieldName ";
        Query query = em.createQuery(sql, entity).setParameter("fieldName", value);
        try {
            List<T> list = query.getResultList();
            if (list.size() >= 1) {
                return list.get(0);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    @Transactional
    public int updateByNativeQuery(String sql, Map<String, Object> params) {
        if (!Utils.isOk(sql)) {
            return 0;
        }

        printSQL(sql, params);
        Query query = em.createNativeQuery(sql);
        LOGGER.info("==QUERY:{}", sql);
        long start = System.currentTimeMillis();
        if (params != null && !params.isEmpty()) {
            params.forEach((k, v) -> query.setParameter(k, v));
        }
        LOGGER.info("==PREPARE TOOK:{}", (System.currentTimeMillis() - start));
        try {
            start = System.currentTimeMillis();
            int value = query.executeUpdate();
            LOGGER.info("==QUERY TOOK:{}", (System.currentTimeMillis() - start));
            return value;
        } catch (Throwable t) {
            t.printStackTrace();
            return 0;
        }
    }

    public <T> List<T> findByNativeQuery(String sql, Class<T> entity, Map<String, Object> params, Integer startIndex, Integer limit) {
        if (!Utils.isOk(sql)) {
            return new ArrayList<T>();
        }

        if (startIndex == null || startIndex < 0) {
            startIndex = 0;
        }

        if (limit == null || limit < 0) {
            limit = 100;
        }

        printSQL(sql, params);
        Query query = em.createNativeQuery(sql, entity).setFirstResult(startIndex).setMaxResults(limit);
        if (params != null && params.size() > 0) {
            params.forEach((k, v) -> {
                query.setParameter(k, v);
            });
        }

        return query.getResultList();
    }

    public <T> List<T> findByNativeQuery(String sql, Map<String, Object> params, Integer startIndex, Integer limit) {
        if (!Utils.isOk(sql)) {
            return new ArrayList<T>();
        }

        if (startIndex == null || startIndex < 0) {
            startIndex = 0;
        }

        if (limit == null || limit < 0) {
            limit = 100;
        }

        printSQL(sql, params);
        Query query = em.createNativeQuery(sql).setFirstResult(startIndex).setMaxResults(limit);
        if (params != null && params.size() > 0) {
            params.forEach((k, v) -> {
                query.setParameter(k, v);
            });
        }

        return query.getResultList();
    }

    public <T> List<T> findByNativeQuery(String sql, Map<String, Object> params) {
        if (!Utils.isOk(sql)) {
            return new ArrayList<>();
        }
        printSQL(sql, params);
        Query query = em.createNativeQuery(sql);
        if (params != null && params.size() > 0) {
            params.forEach((k, v) -> query.setParameter(k, v));
        }

        return query.getResultList();
    }


    public List<Map<String, Object>> findByNativeQueryWithColumnName(String sql, Map<String, Object> params) {
        if (!Utils.isOk(sql)) {
            return new ArrayList<>();
        }

        printSQL(sql, params);

        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(((EntityManagerFactoryInfo) em.getEntityManagerFactory()).getDataSource());
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        params.forEach((k, v) -> paramSource.addValue(k, v));
        return jdbcTemplate.queryForList(sql, paramSource);

        /*
        Query query = em.createNativeQuery(sql);
        if(params != null && params.size() >0) {
            params.forEach((k,v)->query.setParameter(k,v));
        }
        org.hibernate.query.internal.NativeQueryImpl hibernateQuery = ((org.hibernate.query.internal.NativeQueryImpl) query);
        hibernateQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        List<Map<String,Object>> res = hibernateQuery.list();
        return res;

         */
    }

    public <T> List<T> findByNativeQuery(String sql, Class<T> entity, Map<String, Object> params) {
        if (!Utils.isOk(sql)) {
            return new ArrayList<>();
        }
        printSQL(sql, params);
        Query query = em.createNativeQuery(sql, entity);
        if (params != null && params.size() > 0) {
            params.forEach((k, v) -> {
                query.setParameter(k, v);
            });
        }

        return query.getResultList();
    }


    public <T> T findSingleResultByNativeQuery(String sql, Map<String, Object> params) {
        if (!Utils.isOk(sql)) {
            return null;
        }
        printSQL(sql, params);
        Query query = em.createNativeQuery(sql);
        if (params != null && params.size() > 0) {
            params.forEach((k, v) -> {
                query.setParameter(k, v);
            });
        }
        query.setFirstResult(0).setMaxResults(1);
        try {
            return (T) query.getSingleResult();
        } catch (NoResultException ne) {
            return null;
        }
    }

    public <T> T findSingleResultByNativeQuery(String sql, Class<T> entity, Map<String, Object> params) {
        if (!Utils.isOk(sql)) {
            return null;
        }
        printSQL(sql, params);
        Query query = em.createNativeQuery(sql, entity);
        if (params != null && params.size() > 0) {
            params.forEach((k, v) -> query.setParameter(k, v));
        }
        query.setFirstResult(0).setMaxResults(1);
        try {
            List<T> list = query.getResultList();
            if (list.size() >= 1) {
                return list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        catch (NoResultException ne) {
//            return null;
//        }
        return null;
    }

    public Integer findCountByNativeQuery(String sql, Map<String, Object> params) {
        if (!Utils.isOk(sql)) {
            return 0;
        }
        printSQL(sql, params);
        Query query = em.createNativeQuery(sql);
        if (params != null && params.size() > 0) {
            params.forEach((k, v) -> query.setParameter(k, v));
        }

        return Utils.getIntegerFromObject(query.getSingleResult());
    }

    public <T> List<T> findByJpql(String sql, Class<T> entity, Map<String, Object> params, Integer startIndex, Integer limit) {
        if (startIndex == null || startIndex < 0) {
            startIndex = 0;
        }
        if (limit == null || limit < 0) {
            limit = 100;
        }
        printSQL(sql, params);
        Query query = em.createQuery(sql, entity);
        if (params != null && params.size() > 0) {
            params.forEach((k, v) -> query.setParameter(k, v));
        }
        query.setFirstResult(startIndex).setMaxResults(limit);
        return query.getResultList();
    }

    public <T> List<T> findByJpql(String sql, Class<T> entity, Map<String, Object> params) {
        printSQL(sql, params);
        Query query = em.createQuery(sql, entity);
        if (params != null && params.size() > 0) {
            params.forEach((k, v) -> query.setParameter(k, v));
        }
        return query.getResultList();
    }

    public <T> T findSingleResultByJpql(String sql, Class<T> entity, Map<String, Object> params) {
        Query query = em.createQuery(sql, entity);
        if (params != null && params.size() > 0) {
            params.forEach((k, v) -> query.setParameter(k, v));
        }
        try {
            query.setFirstResult(0).setMaxResults(1);
            return (T) query.getResultList().get(0);
        } catch (Throwable t) {
            return null;
        }
    }

    public Integer findCountByJpql(String sql, Map<String, Object> params) {
        Query query = em.createQuery(sql);
        if (params != null && !params.isEmpty()) {
            params.forEach((k, v) -> query.setParameter(k, v));
        }
        return Utils.getIntegerFromObject(query.getSingleResult());
    }

    private void printSQL(String sql, Map<String, Object> params) {
        if (Defs.WRITE_SQL) {
            LOGGER.info("===RAW SQL:{}", sql);
            if (params != null && params.size() > 0) {
                LOGGER.info("==PARAM START==");
                params.forEach((k, v) -> LOGGER.info("==KEY:{},VALUE:{}", k, v));
                LOGGER.info("==PARAM END==");
            }
        }
    }
}
