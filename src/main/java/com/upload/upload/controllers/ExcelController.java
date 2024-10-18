package com.upload.upload.controllers;

import com.upload.upload.entities.DoctorReportEntity;
import com.upload.upload.services.DoctorReportService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ExcelController {

  @Autowired
  private DoctorReportService service;

  @PostMapping("/upload-excel")
  public ResponseEntity<List<DoctorReportEntity>> uploadExcel(@RequestParam("file") MultipartFile file) throws IOException {
    List<DoctorReportEntity> entities = new ArrayList<>();

    Workbook workbook = new XSSFWorkbook(file.getInputStream());
    Sheet sheet = workbook.getSheetAt(0);

    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
      Row row = sheet.getRow(i);
      if (row == null) {
        continue;
      }

      DoctorReportEntity entity = new DoctorReportEntity();

      Cell orderCell = row.getCell(3);
      Cell nameCell = row.getCell(2);
      Cell hcuCell = row.getCell(1);
      Cell dateCell = row.getCell(10);
      Cell descripcionCell = row.getCell(4);
      Cell costoCell = row.getCell(12);


      if (orderCell != null) {
        if (orderCell.getCellType() == CellType.NUMERIC) {
          entity.setNumero_orden((int) orderCell.getNumericCellValue());
        }
      }


      if (nameCell != null && nameCell.getCellType() == CellType.STRING) {
        entity.setNombre_paciente(nameCell.getStringCellValue());
      }

      if (hcuCell != null) {
        if (hcuCell.getCellType() == CellType.NUMERIC) {
          entity.setHcu((int) hcuCell.getNumericCellValue());
        }
      }


      if (dateCell != null) {
        if (dateCell.getCellType() == CellType.STRING) {
          String dateString = dateCell.getStringCellValue().trim();
          try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Asegúrate de que el formato coincide
            Date date = sdf.parse(dateString);
            entity.setFecha_atencion(new java.sql.Date(date.getTime()));
          } catch (ParseException e) {
            System.err.println("Error al parsear la fecha: " + e.getMessage());
          }
        } else if (dateCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(dateCell)) {
          Date date = dateCell.getDateCellValue();
          entity.setFecha_atencion(new java.sql.Date(date.getTime()));
        }
      }


      if (descripcionCell != null && descripcionCell.getCellType() == CellType.STRING) {
        entity.setDescripcion(descripcionCell.getStringCellValue());
      }

      if (costoCell != null) {
        if (costoCell.getCellType() == CellType.NUMERIC) {
          entity.setCosto((int) costoCell.getNumericCellValue());
        }
      }

      entities.add(entity);
      service.save(entity);
    }

    return ResponseEntity.ok(entities);
  }

}


