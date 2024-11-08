package com.mcsv.order_service.Config.Exception;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ExceptionResponse {
    private String status;
    private String fallo_en;
    private Date timestamp;
    private String mensaje;
    private String requestId;
}