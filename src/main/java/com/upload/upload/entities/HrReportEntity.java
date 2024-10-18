package com.upload.upload.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "REPORTE_PRODUCCION")
@Getter
@Setter
@AllArgsConstructor
public class HrReportEntity {
  @Id
  private int numero_orden;

  @Column
  @Temporal(TemporalType.DATE)
  private Date fecha_facturacion;

  private int hcu;

  private String descripcion;

  private String status;

  private int costo;

  private String sucursal;

  private String proveedor;

  public HrReportEntity() {
  }
}
