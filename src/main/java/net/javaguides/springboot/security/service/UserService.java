package net.javaguides.springboot.security.service;

import net.javaguides.springboot.lookup.repository.BaseRepository;
import net.javaguides.springboot.security.model.LoggedInUser;
import net.javaguides.springboot.security.model.UserInfo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    private final BaseRepository repository;

    public UserService(BaseRepository repository) {
        this.repository = repository;
    }
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        String sql = "SELECT * FROM user_info WHERE user_id =:userId AND user_status=:status ";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId.trim().toUpperCase());
        params.put("status", "A");
        UserInfo userInfo = repository.findSingleResultByNativeQuery(sql, UserInfo.class, params);
        if(userInfo == null) {
            throw new UsernameNotFoundException("User Not Found with username:"+userId);
        }
        return new LoggedInUser(userInfo);
    }

    public UserInfo getUserInfo(String userName) throws UsernameNotFoundException {

        String sql = "SELECT * FROM USER_INFO WHERE USER_ID =:userId";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userName.trim().toUpperCase());
        UserInfo userInfo = repository.findSingleResultByNativeQuery(sql, UserInfo.class, params);
        if(userInfo == null) {
            throw new UsernameNotFoundException("User Not Found with username:"+userName);
        }
        return userInfo;
    }
}