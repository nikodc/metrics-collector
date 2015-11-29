import com.nikodc.metricscollector.*

class BootStrap {

    def init = { servletContext ->

        environments {
            development {
                def collector = new JmxMetricCollector(name:'HeapMemoryUsage',
                        host:'localhost', port:9090,
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
