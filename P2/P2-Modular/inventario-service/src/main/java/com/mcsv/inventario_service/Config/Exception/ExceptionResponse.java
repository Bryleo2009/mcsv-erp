package com.mcsv.inventario_service.Config.Exception;

import java.util.Date;

public class ExceptionResponse {
    private String status;
    private String fallo_en;
    private Date timestamp;
    private String mensaje;


    public ExceptionResponse(Date timestamp, String mensaje, String fallo_en,String status) {
        this.timestamp = timestamp;
        this.mensaje = mensaje;
        this.fallo_en = fallo_en;
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFallo_en() {
        return fallo_en;
    }

    public void setFallo_en(String fallo_en) {
        this.fallo_en = fallo_en;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }


}