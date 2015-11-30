package com.nikodc.metricscollector

class Route {

    MetricCollector collector
    MetricPublisher publisher

    static constraints = {
        collector nullable: false
        publisher nullable: false
    }

    def transfer() {
        def value = collector.pull()
        publisher.publish(value)
    }
}
