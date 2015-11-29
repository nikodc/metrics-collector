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
            it.transfer()
        }

        logger.info("End!")
    }
}
