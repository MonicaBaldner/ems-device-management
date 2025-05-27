package com.algaworks.algasensors.ems_device_management;

import com.algaworks.algasensors.ems_device_management.common.IDGenerator;
import io.hypersistence.tsid.TSID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class TSIDTest {

    @Test
    public void shouldGenerateTSID(){
    /*  //  TSID tsid = TSID.fast();
        TSID tsid = TSID.Factory.getTsid();
        System.out.println(tsid);
        System.out.println(tsid.toLong());

     */

        /*System.setProperty("tsid.node","2");
        System.setProperty("tsid.node.count", "32");
        TSID.Factory factory = TSID.Factory.builder().build();
        TSID tsid = factory.generate();
        System.out.println("O TSID gerado foi " + tsid);
        System.out.println("O TSID gerado no formato Long " + tsid.toLong());
        System.out.println("O instante no qual o TSID foi gerado " + tsid.getInstant());*/


            TSID tsid = IDGenerator.generateTSID();
            Assertions.assertThat(tsid.getInstant())
                    .isCloseTo(Instant.now(), Assertions.within(1, ChronoUnit.MINUTES));
    }



}
