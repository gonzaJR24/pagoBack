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
  private int numero_orden;

  private String nombre_paciente;
  private int hcu;

  @Column(name = "fecha_atencion")
  @Temporal(TemporalType.DATE)
  private Date fecha_atencion;

  private String descripcion;

  private int costo;

  private String proveedor;
}
