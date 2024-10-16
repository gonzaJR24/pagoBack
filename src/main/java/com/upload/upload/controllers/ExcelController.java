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
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ExcelController {

  @Autowired
  private DoctorReportService service;

  @PostMapping("/upload-excel")
  public ResponseEntity<List<String>> uploadExcel(@RequestParam("file") MultipartFile file,
                                                  @RequestParam int columnIndex) throws IOException {
    List<String> columnData = new ArrayList<>();

    Workbook workbook = new XSSFWorkbook(file.getInputStream());
      Sheet sheet = workbook.getSheetAt(0);
      for (Row row : sheet) {
        Cell cell = row.getCell(columnIndex);
        if (cell != null) {
          columnData.add(cell.getStringCellValue());
        }
      }
      for(String data:columnData){
        DoctorReportEntity entity=new DoctorReportEntity();

        if(!data.isEmpty()){
          String columnaInicio=columnData.get(0);
          switch (columnaInicio){
            case "ORDEN":
              entity.setNumero_orden(data);
              service.save(entity);
              break;
          }
        }
      }
      return ResponseEntity.ok(columnData);
  }
}


