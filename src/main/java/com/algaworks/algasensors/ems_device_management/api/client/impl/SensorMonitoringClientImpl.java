package com.algaworks.algasensors.ems_device_management.api.client.impl;

import com.algaworks.algasensors.ems_device_management.api.client.RestClientFactory;
import com.algaworks.algasensors.ems_device_management.api.client.SensorMonitoringClient;
import com.algaworks.algasensors.ems_device_management.api.client.SensorMonitoringClientBadGatewayException;
import com.algaworks.algasensors.ems_device_management.api.model.SensorMonitoringOutput;
import io.hypersistence.tsid.TSID;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;

//@Component Essa classe foi substituida pelas SensorMonitoringClient e RestClientConfig na 11.07
public class SensorMonitoringClientImpl implements SensorMonitoringClient {

    private final RestClient restClient;

    /* substituido pelo abaixo na 11.05
    public SensorMonitoringClientImpl(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:8082")
                //aula 11.04
                .requestFactory(generateClientHttpRequestFactory())
                //fim aula 11.04
                //aula 11.03
                .defaultStatusHandler(HttpStatusCode::isError, (request,response) ->{
                    throw new SensorMonitoringClientBadGatewayException();
                })
                //fim aula 11.03
                .build();
    }

    //aula 11.04
    private ClientHttpRequestFactory generateClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        factory.setReadTimeout(Duration.ofSeconds(5));
        factory.setConnectTimeout(Duration.ofSeconds(3));

        return factory;
    }
    //fim aula 11.04
*/

//11.05
    public SensorMonitoringClientImpl(RestClientFactory factory) {
        this.restClient = factory.temperatureMonitoringRestClient();
    }
    //11.05

    @Override
    public void enableMonitoring(TSID sensorId) {
        restClient.put()
                .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public void disableMonitoring(TSID sensorId) {
        restClient.delete()
                .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
                .retrieve()
                .toBodilessEntity();
    }

    //aula 11.06
    @Override
    public SensorMonitoringOutput getDetail(TSID sensorId) {
        return restClient.get()
                .uri("/api/sensors/{sensorId}/monitoring", sensorId)
                .retrieve()
                .body(SensorMonitoringOutput.class);

    }
    //fim aula 11.06

}

