package com.nikodc.metricscollector

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class InfluxDBMetricPublisher extends MetricPublisher {

    static Logger logger = LoggerFactory.getLogger(InfluxDBMetricPublisher)

    def influxDBService

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
        influxDBService.post(measurement(value), config())
    }

    private InfluxDBService.Measurement measurement(Object value) {
        new InfluxDBService.Measurement(name: name, tags: [(tagName):tagValue], fields: [value:value])
    }

    private InfluxDBService.Config config() {
        new InfluxDBService.Config(connectionUrl: connectionUrl, database: database, user: user, password: password)
    }

}
