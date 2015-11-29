package com.nikodc.metricscollector

import static org.springframework.http.HttpStatus.*

class EngineController {

    def engineService

    def show() {
        try {
            engineService.execute()

            render status: NO_CONTENT

        } catch (Exception e) {

            render status: INTERNAL_SERVER_ERROR
        }
    }
}
