package com.upload.upload.services;

import com.upload.upload.Repository.ReporteHrRepository;
import com.upload.upload.entities.HrReportEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HrReportService {

  @Autowired
  private ReporteHrRepository repository;

  public void save(HrReportEntity entity){
    repository.save(entity);
  }

  public List<HrReportEntity> list(){
    return repository.findAll();
  }
}
