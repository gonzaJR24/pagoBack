package com.upload.upload.controllers;

import com.upload.upload.entities.HrReportEntity;
import com.upload.upload.services.HrReportService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class HrController {

  @Autowired
  private HrReportService reportService;

  @PostMapping("/hr_upload")
  public ResponseEntity<List<HrReportEntity>> uploadExcel(@RequestParam("file") MultipartFile file) throws IOException {
    List<HrReportEntity> entities = new ArrayList<>();

    Workbook workbook = new XSSFWorkbook(file.getInputStream());
    Sheet sheet = workbook.getSheetAt(0);

    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
      Row row = sheet.getRow(i);
      if (row == null) {
        continue;
      }

      HrReportEntity entity = new HrReportEntity();

      Cell orderCell = row.getCell(0);
      Cell fechaFacturaCell = row.getCell(2);
      Cell hcuCell = row.getCell(10);
      Cell descripcionCell = row.getCell(19);
      Cell atendidoCell = row.getCell(20);
      Cell costoCell = row.getCell(22);
      Cell sucursalCell = row.getCell(52);
      Cell proveedorCell = row.getCell(54);


      if (orderCell != null) {
        if (orderCell.getCellType() == CellType.NUMERIC) {
          entity.setNumero_orden((int) orderCell.getNumericCellValue());
        }
      }

      if (fechaFacturaCell != null) {
        if (fechaFacturaCell.getCellType() == CellType.STRING) {
          String dateString = fechaFacturaCell.getStringCellValue().trim();
          try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Asegúrate de que el formato coincide
            Date date = sdf.parse(dateString);
            entity.setFecha_facturacion(new java.sql.Date(date.getTime()));
          } catch (ParseException e) {
            System.err.println("Error al parsear la fecha: " + e.getMessage());
          }
        } else if (fechaFacturaCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(fechaFacturaCell)) {
          Date date = fechaFacturaCell.getDateCellValue();
          entity.setFecha_facturacion(new java.sql.Date(date.getTime()));
        }
      }

      if (hcuCell != null) {
        if (hcuCell.getCellType() == CellType.NUMERIC) {
          entity.setHcu((int) hcuCell.getNumericCellValue());
        }
      }

      if (descripcionCell != null && descripcionCell.getCellType() == CellType.STRING) {
        entity.setDescripcion(descripcionCell.getStringCellValue());
      }

      if (atendidoCell != null && atendidoCell.getCellType() == CellType.STRING) {
        entity.setStatus(atendidoCell.getStringCellValue());
      }

      if (costoCell != null) {
        if (costoCell.getCellType() == CellType.NUMERIC) {
          entity.setCosto((int) costoCell.getNumericCellValue());
        }
      }

      if (sucursalCell != null && sucursalCell.getCellType() == CellType.STRING) {
        entity.setSucursal(sucursalCell.getStringCellValue());
      }


      if (proveedorCell != null && proveedorCell.getCellType() == CellType.STRING) {
        entity.setProveedor(proveedorCell.getStringCellValue());
      }

      if(entity.getProveedor()==null){
        continue;
      }else{
      reportService.save(entity);
      }
    }

    return ResponseEntity.ok(reportService.list());
  }

}
