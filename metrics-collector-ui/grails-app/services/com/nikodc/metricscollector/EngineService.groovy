package com.nikodc.metricscollector

import grails.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Transactional
class EngineService {

    static Logger logger = LoggerFactory.getLogger(EngineService)

    def execute() {
        logger.info("Executing...")

        Route.list().each {
            try {
                it.transfer()
            } catch (Exception e) {
                logger.warn("Exception thrown while processing route: ${it.id}", e)
            }
        }

        logger.info("End!")
    }
}
