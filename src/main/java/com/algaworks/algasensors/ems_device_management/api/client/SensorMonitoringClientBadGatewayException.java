package com.algaworks.algasensors.ems_device_management.api.client;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.BAD_GATEWAY) substituido pelo ExceptionHandler
public class SensorMonitoringClientBadGatewayException extends RuntimeException{
}

