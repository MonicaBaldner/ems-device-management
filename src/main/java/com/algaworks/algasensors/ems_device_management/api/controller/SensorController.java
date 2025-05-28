package com.algaworks.algasensors.ems_device_management.api.controller;

import com.algaworks.algasensors.ems_device_management.api.client.SensorMonitoringClient;
import com.algaworks.algasensors.ems_device_management.api.model.SensorDetailOutput;
import com.algaworks.algasensors.ems_device_management.api.model.SensorInput;
import com.algaworks.algasensors.ems_device_management.api.model.SensorMonitoringOutput;
import com.algaworks.algasensors.ems_device_management.api.model.SensorOutput;
import com.algaworks.algasensors.ems_device_management.common.IDGenerator;
import com.algaworks.algasensors.ems_device_management.domain.model.Sensor;
import com.algaworks.algasensors.ems_device_management.domain.model.SensorId;
import com.algaworks.algasensors.ems_device_management.domain.repository.SensorRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorRepository sensorRepository; //10.04
    private final SensorMonitoringClient sensorMonitoringClient; //11.02

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    // public Sensor create(@RequestBody SensorInput sensorInput){ Substituido pelo SensorOutput na 10.05
    public SensorOutput create(@RequestBody SensorInput sensorInput){
        //  return Sensor.builder() substituido pela declaração de sensor na 10.04
        Sensor sensor = Sensor.builder()
                //  .id(IdGenerator.generateTSID()) substituido pela abaixo na 10.04
                .id(new SensorId(IDGenerator.generateTSID()))
                .name(sensorInput.getName())
                .ip(sensorInput.getIp())
                .location(sensorInput.getLocation())
                .protocol(sensorInput.getProtocol())
                .model(sensorInput.getModel())
                .enabled(false)
                .build();
        //  return sensorRepository.saveAndFlush(sensor);
        sensor = sensorRepository.saveAndFlush(sensor);
        //  return SensorOutput.builder().build(); substituido pelo abaixo na 10.05
        return convertToModel(sensor);
    }

    //aula 10.06
    @GetMapping("{sensorId}")
    public SensorOutput getOne(@PathVariable TSID sensorId){
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return convertToModel(sensor);
    }
// Fim aula 10.06

    //aula 10.07
    @GetMapping
    public Page<SensorOutput> search(@PageableDefault Pageable pageable){
        Page<Sensor> sensors = sensorRepository.findAll(pageable);
        // return sensors.map(sensor -> convertToModel(sensor));
        return sensors.map(this::convertToModel);
    }
//Fim aula 10.07

//aula 10.09
@PutMapping("/{sensorId}")
public SensorOutput update(@PathVariable TSID sensorId,
                           @RequestBody SensorInput input) {
    Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    sensor.setName(input.getName());
    sensor.setLocation(input.getLocation());
    sensor.setIp(input.getIp());
    sensor.setModel(input.getModel());
    sensor.setProtocol(input.getProtocol());

    sensor = sensorRepository.save(sensor);

    return convertToModel(sensor);
}

    @DeleteMapping("/{sensorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        sensorRepository.delete(sensor);
        sensorMonitoringClient.disableMonitoring(sensorId); //11.02
    }
//fim aula 10.09

    //Aula 10.10
    @PutMapping("/{sensorId}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enable(@PathVariable TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        sensor.setEnabled(true);
        sensorRepository.save(sensor);
        sensorMonitoringClient.enableMonitoring(sensorId); //11.02
    }

    @DeleteMapping("/{sensorId}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disable(@PathVariable TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        sensor.setEnabled(false);
        sensorRepository.save(sensor);
        sensorMonitoringClient.disableMonitoring(sensorId); //11.02
    }
//Fim aula 10.10

    //11.06
    @GetMapping("{sensorId}/detail")
    public SensorDetailOutput getOneWithDetail(@PathVariable TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        SensorMonitoringOutput monitoringOuput = sensorMonitoringClient.getDetail(sensorId);
        SensorOutput sensorOutput = convertToModel(sensor);

        return SensorDetailOutput.builder()
                .monitoring(monitoringOuput)
                .sensor(sensorOutput)
                .build();
    }
//Fim 11.06



    private SensorOutput convertToModel(Sensor sensor) {
        return SensorOutput.builder()
                .id(sensor.getId().getValue())
                .name(sensor.getName())
                .ip(sensor.getIp())
                .location(sensor.getLocation())
                .protocol(sensor.getProtocol())
                .model(sensor.getModel())
                .enabled(sensor.getEnabled())
                .build();
    }

}
