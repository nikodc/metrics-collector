package com.nikodc.metricscollector

import javax.management.MBeanServerConnection
import javax.management.ObjectName
import javax.management.remote.JMXConnector
import javax.management.remote.JMXConnectorFactory
import javax.management.remote.JMXServiceURL

class JmxService {

    public Object getAttributeValue(String serviceUrl, String objectName, String attribute) {
        String attributeNamePart = attributeNamePart(attribute)
        String attributePropertyPart = attributePropertyPathPart(attribute)

        Object value = mbeanServerConnection(serviceUrl).getAttribute(new ObjectName(objectName), attributeNamePart)

        if (attributePropertyPart != null) {
            value = value[attributePropertyPart]
        }

        value
    }

    private String attributeNamePart(String attribute) {
        String result = attribute
        int i = attribute.indexOf('.')
        if (i > 0) {
            result = attribute.substring(0, i)
        }
        result
    }

    private String attributePropertyPathPart(String attribute) {
        String result = null
        int i = attribute.indexOf('.')
        if (i > 0) {
            result = attribute.substring(i + 1)
        }
        result
    }

    private MBeanServerConnection mbeanServerConnection(String serviceUrl) {
        JMXConnector jmxConnector = JMXConnectorFactory.connect(new JMXServiceURL(serviceUrl))
        MBeanServerConnection mbeanServerConnection = jmxConnector.getMBeanServerConnection()

        mbeanServerConnection
    }

}
