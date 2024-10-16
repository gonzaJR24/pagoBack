package com.upload.upload.services;

import com.upload.upload.Repository.ReporteDoctorRepository;
import com.upload.upload.entities.DoctorReportEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorReportService {

  @Autowired
  private ReporteDoctorRepository repository;

  public void save(DoctorReportEntity entity){
    repository.save(entity);
  }
}
