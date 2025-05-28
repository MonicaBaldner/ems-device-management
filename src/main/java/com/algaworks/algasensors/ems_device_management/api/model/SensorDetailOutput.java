package com.algaworks.algasensors.ems_device_management.api.model;

import lombok.Builder;
import lombok.Data;

//11.06
@Data
@Builder
public class SensorDetailOutput {
    private SensorOutput sensor;
    private SensorMonitoringOutput monitoring;
}
//11.06

