package com.nikodc.metricscollector

class InfluxDBMetricPublisher extends MetricPublisher {

    String connectionUrl
    String database
    String tagName
    String tagValue
    String user
    String password

    static constraints = {
        connectionUrl nullable: false, blank: false
        database nullable: false, blank: false
        tagName nullable: true
        tagValue nullable: false
        user nullable: true
        password nullable: true
    }

    @Override
    void publish(Object value) {
        // TODO to be implemented!
    }
}
