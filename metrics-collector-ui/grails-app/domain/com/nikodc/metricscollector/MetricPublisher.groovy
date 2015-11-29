package com.nikodc.metricscollector

abstract class MetricPublisher {

    String name

    static mapping = {
        tablePerHierarchy false
    }

    static constraints = {
        name nullable: false, blank: false, maxSize: 200
    }

    abstract void publish(Object value);
}
