package com.upload.upload.Repository;

import com.upload.upload.entities.DoctorReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReporteDoctorRepository extends JpaRepository<DoctorReportEntity, Integer> {
}
