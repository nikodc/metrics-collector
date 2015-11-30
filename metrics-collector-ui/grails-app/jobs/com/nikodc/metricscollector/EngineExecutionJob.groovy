package com.nikodc.metricscollector

class EngineExecutionJob {

    def engineService

    static triggers = {}

    def execute() {
        engineService.execute()
    }

}
