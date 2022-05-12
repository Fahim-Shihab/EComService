package net.springboot.security.repository;

import net.springboot.common.base.Defs;
import net.springboot.common.base.ServiceResponse;
import net.springboot.common.util.SearchUtil;
import net.springboot.common.util.Utils;
import net.springboot.common.repository.BaseRepository;
import net.springboot.security.dao.SecurityDaoImpl;
import net.springboot.security.model.UserInfo;
import net.springboot.security.payload.RegisterUserRequest;
import net.springboot.security.payload.SingleUserInfo;
import net.springboot.security.payload.UserInfoRequest;
import net.springboot.security.payload.UserInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SecurityRepositoryImpl implements SecurityRepository {

    @Autowired
    SecurityDaoImpl securityDaoImpl;

    @Override
    public ServiceResponse register(RegisterUserRequest request) {

        ServiceResponse response = securityDaoImpl.register(request);

        return response;
    }

    @Override
    public UserInfoResponse GetUserInfo(UserInfoRequest request){

        UserInfoResponse response = securityDaoImpl.GetUserInfo(request);

        return response;
    }

}
