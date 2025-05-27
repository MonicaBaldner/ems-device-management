package com.algaworks.algasensors.ems_device_management.common;

import io.hypersistence.tsid.TSID;

import java.util.Optional;

public class IDGenerator {

    private static final TSID.Factory tsidFactory;

    static {
        Optional.ofNullable(System.getenv("tsid.node"))
                .ifPresent(tsidNode -> System.setProperty("tsid.node", tsidNode));

        Optional.ofNullable(System.getenv("tsid.node.count"))
                .ifPresent(tsidNodeCount -> System.setProperty("tsid.node.count", tsidNodeCount));

        tsidFactory = TSID.Factory.builder().build();
    }

    private IDGenerator() {

    }

    public static TSID generateTSID() {
        return tsidFactory.generate();
    }

}


