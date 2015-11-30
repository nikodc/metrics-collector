package com.nikodc.metricscollector

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class JmxMetricCollector extends MetricCollector {

    static Logger logger = LoggerFactory.getLogger(JmxMetricCollector)

    def jmxService

    String serviceUrl
    String objectName
    String attribute

    static constraints = {
        serviceUrl nullable: false, blank: false
        objectName nullable: false, blank: false
        attribute nullable: false, blank: false
    }

    @Override
    Object pull() {
        jmxService.getAttributeValue(serviceUrl, objectName, attribute)
    }
}
