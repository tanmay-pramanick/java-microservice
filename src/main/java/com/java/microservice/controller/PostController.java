package com.java.microservice.controller;

import com.java.microservice.dto.UserDTO;
import com.java.microservice.entity.User;
import com.java.microservice.service.CacheInspectionService;
import com.java.microservice.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class PostController {

    @Autowired
    MainService mainService;

    @Autowired
    CacheInspectionService cacheInspectionService;

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody List<UserDTO> userReqPayload){

        try {
            List<User> userResponse = mainService.saveUsers(userReqPayload);
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/cache/{name}")
    public void  getCache(@PathVariable String name){

        try {
            cacheInspectionService.printCacheContents(name);
        } catch (Exception e) {
            e.printStackTrace();
            new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }



}
