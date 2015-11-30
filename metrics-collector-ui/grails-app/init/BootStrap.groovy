import com.nikodc.metricscollector.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class BootStrap {

    static Logger logger = LoggerFactory.getLogger('com.nikodc.metricscollector')

    def grailsApplication

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

        if (grailsApplication.config.metricscollector.engine.trigger) {
            logger.info("Initializing EngineExecutionJob with cron " +
                    "trigger: once every ${grailsApplication.config.metricscollector.engine.trigger} ms")
            EngineExecutionJob.schedule(Long.valueOf(grailsApplication.config.metricscollector.engine.trigger))
            logger.info("EngineExecutionJob scheduled!")
        }

    }

    def destroy = {
    }
}
