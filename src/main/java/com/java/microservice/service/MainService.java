package com.java.microservice.service;

import com.java.microservice.dto.UserDTO;
import com.java.microservice.entity.User;
import com.java.microservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class MainService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HelperService helperService;

    public List<User> getAllUsers(){

        List<User> userList = userRepository.getAllUsers();
        return userList;
    }

    @CachePut(value = "userDetails",
            key = "T(org.springframework.util.DigestUtils).md5DigestAsHex(#requestParams.toString().bytes)")
    @Transactional
    public List<User> saveUsers(List<UserDTO> userReqPayload) throws Exception{

            List<User> userSavePayloadList = new ArrayList<>();

            for (UserDTO userdto : userReqPayload) {

                if (userdto.getUuid() != null) {
                    Optional<User> existingUserFromDB = userRepository.findByUuid(userdto.getUuid());
                    if(existingUserFromDB.isPresent())
                    {
                        User existingUser = existingUserFromDB.get();
                        helperService.copyNonNullFieldsFromDto(userdto, existingUser);
                        userSavePayloadList.add(existingUser);
                    }else {
                        throw new RuntimeException("UUID does not exist: "+userdto.getUuid());
                    }
                } else {
                    User user = new User();
                    user.setUuid(UUID.randomUUID().toString());
                    helperService.copyNonNullFieldsFromDto(userdto, user);
                    userSavePayloadList.add(user);
                }
            }
            userRepository.saveAll(userSavePayloadList);
            return userSavePayloadList;


    }

    @Cacheable(value = "userDetails",
            key = "T(org.springframework.util.DigestUtils).md5DigestAsHex(#requestParams.toString().bytes)")
    public List<User> getUser(Map<String,String> requestParams) throws Exception{
        return helperService.userSearchByCriteriaQuery(requestParams);
    }



}
