package com.upload.upload.controllers;

import com.upload.upload.entities.UserEntity;
import com.upload.upload.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  private UserService service;

  @GetMapping
  public List<UserEntity> list(){
    return service.list();
  }
}
