package com.algaworks.algasensors.ems_device_management.domain.repository;

import com.algaworks.algasensors.ems_device_management.domain.model.Sensor;
import com.algaworks.algasensors.ems_device_management.domain.model.SensorId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, SensorId> {
}

