package com.java.microservice.controller;

import com.java.microservice.entity.User;
import com.java.microservice.service.CacheInspectionService;
import com.java.microservice.service.MainService;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.awt.desktop.SystemEventListener;
import java.util.List;
import java.util.Map;

@RestController
public class GetController {

    @Autowired
    MainService mainService;

    @GetMapping(value = "/getUsers")
    public ResponseEntity<?>  getAllUsers(){

        try {
            List<User> userResponse = mainService.getAllUsers();
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "/getUser")
    public ResponseEntity<?>  getUser(@RequestParam Map<String,String> requestParams){

        try {
            List<User> userResponse = mainService.getUser(requestParams);
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }

    }

}
