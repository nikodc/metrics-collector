package com.nikodc.metricscollector

class MetricCollector {

    String name

    static mapping = {
        tablePerHierarchy false
    }

    static constraints = {
        name nullable: false, blank: false, maxSize: 200
    }

    Object pull() {
        // must be implemented subclasses
        null
    }

}
