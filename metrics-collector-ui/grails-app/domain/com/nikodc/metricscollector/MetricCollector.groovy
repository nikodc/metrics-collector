package com.nikodc.metricscollector

abstract class MetricCollector {

    String name

    static mapping = {
        tablePerHierarchy false
    }

    static constraints = {
        name nullable: false, blank: false, maxSize: 200
    }

    abstract Object collect()

}
