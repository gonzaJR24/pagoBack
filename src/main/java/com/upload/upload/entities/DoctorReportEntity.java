package com.upload.upload.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "reporte_doctor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorReportEntity {

  @Id
  private String numero_orden;

  private Date fecha_orden;

  private Date fecha_facturacion;

  private int hcu;

  private int cantidad;

  private int costo;
}
