package com.nikodc.metricscollector

class MetricPublisher {

    String name

    static mapping = {
        tablePerHierarchy false
    }

    static constraints = {
        name nullable: false, blank: false, maxSize: 200
    }

    void publish(Object value) {
        // must be implemented subclasses
    }
}
