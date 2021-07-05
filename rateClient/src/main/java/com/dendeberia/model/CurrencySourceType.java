package com.dendeberia.model;

public enum CurrencySourceType {
    CBR("cbr");

    String serviceName;

    CurrencySourceType(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}
