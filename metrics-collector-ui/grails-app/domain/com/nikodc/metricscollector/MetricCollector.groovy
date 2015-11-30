package com.nikodc.metricscollector

class MetricCollector {

    String name

    static mapping = {
        tablePerHierarchy false
    }

    static constraints = {
        name nullable: false, blank: false, maxSize: 200,  unique: true
    }

    @Override
    String toString() {
        return name
    }

    Object pull() {
        // must be implemented subclasses
        null
    }

}
