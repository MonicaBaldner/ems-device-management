package com.algaworks.algasensors.ems_device_management.api.client;

import com.algaworks.algasensors.ems_device_management.api.model.SensorMonitoringOutput;
import io.hypersistence.tsid.TSID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;

@HttpExchange("/api/sensors/{sensorId}/monitoring") //11.07
public interface SensorMonitoringClient {

    @PutExchange("/enable") //11.07
    void enableMonitoring(@PathVariable TSID sensorId);

    @DeleteExchange("/enable")  //11.07
    void disableMonitoring(@PathVariable TSID sensorId);

    @GetExchange
        //11.07
    //11.06
    SensorMonitoringOutput getDetail(@PathVariable TSID sensorId);
    //11.06

}
