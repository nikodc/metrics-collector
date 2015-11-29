package com.nikodc.metricscollector

class JmxMetricCollector extends MetricCollector {

    String host
    int port
    String objectName
    String attribute

    static constraints = {
        host nullable: false, blank: false
        port nullable: false, min: 0
        objectName nullable: false, blank: false
        attribute nullable: false, blank: false
    }

    @Override
    Object collect() {
        // TODO to be implemented!
        return 1
    }
}
