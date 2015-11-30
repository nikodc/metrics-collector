import com.nikodc.metricscollector.*

class BootStrap {

    def init = { servletContext ->

        environments {
            development {
                def collector = new JmxMetricCollector(name:'HeapMemoryUsage',
                        serviceUrl:'service:jmx:rmi://localhost/jndi/rmi://localhost:9090/jmxrmi',
                        objectName:'java.lang:type=Memory', attribute: 'HeapMemoryUsage.used').save()
                def publisher = new InfluxDBMetricPublisher(name:'heapMemoryUsage',
                        connectionUrl:'http://localhost:8086', database:'jvmstats',
                        tagName:'server', tagValue:'localhost',
                        user:'admin', password:'admin').save()
                new Route(collector:collector, publisher:publisher).save()
            }
        }
    }

    def destroy = {
    }
}
