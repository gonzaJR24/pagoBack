package com.upload.upload.services;

import com.upload.upload.Repository.UserRepository;
import com.upload.upload.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
  @Autowired
  private UserRepository repository;

  public void save(UserEntity userEntity){
    repository.save(userEntity);
  }

  public List<UserEntity> list(){
    return repository.findAll();
  }
}
