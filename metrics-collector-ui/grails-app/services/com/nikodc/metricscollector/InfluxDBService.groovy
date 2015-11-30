package com.nikodc.metricscollector

import org.apache.commons.codec.binary.Base64
import org.springframework.http.*
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

import java.math.RoundingMode
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

class InfluxDBService {

    static final int READ_TIMEOUT = 30 * 1000
    static final int CONNECT_TIMEOUT = 10 * 1000

    void post(Measurement measurement, Config config) {
        ResponseEntity<Object> response = null

        try {
            String measurementPayload = measurement.linealize()
            HttpEntity<String> httpEntity = new HttpEntity<String>(measurementPayload, httpHeaders(config))
            response = restTemplate().exchange(
                    "${config.connectionUrl}/write?db=${config.database}",
                    HttpMethod.POST, httpEntity, Object.class)

        } catch (Exception e) {
            throw new InfluxDBServiceException("Error invocando InfluxDB rest api", e)
        }

        if (response.statusCode != HttpStatus.NO_CONTENT) {
            throw new InfluxDBServiceException("Respuesta incorrecta invocando InfluxDB rest api, " +
                    "http status = " + response.statusCode)
        }
    }

    private HttpHeaders httpHeaders(Config config) {
        HttpHeaders headers = new HttpHeaders()
        if (config.user != null && config.password != null) {
            String plainCreds = "${config.user}:${config.password}"
            byte[] plainCredsBytes = plainCreds.bytes
            byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes)
            String base64Creds = new String(base64CredsBytes, Charset.forName("UTF-8"))
            headers.add("Authorization", "Basic " + base64Creds)
        }
        return headers;
    }

    private RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory()
        requestFactory.setReadTimeout(READ_TIMEOUT)
        requestFactory.setConnectTimeout(CONNECT_TIMEOUT)
        new RestTemplate(requestFactory)
    }

    static class Config {

        String connectionUrl
        String database
        String user
        String password
    }

    static class Measurement {

        String name
        Long time
        Map<String, String> tags
        Map<String, Object> fields

        String linealize() {

            // name
            String result = "${name}"

            // tags
            tags.each { String key, String value ->
                result += ",${key}=${value}"
            }

            result += ' '

            // fields
            int fieldsSize = fields.size();
            fields.eachWithIndex { String key, Object value, int i ->
                if (value instanceof String) {
                    result += "${key}=\"${value}\""
                } else if (value instanceof Double) {
                    BigDecimal bigDecimalValue = BigDecimal.valueOf((Double) value).setScale(6, RoundingMode.HALF_UP);
                    result += "${key}=${bigDecimalValue}"
                } else {
                    result += "${key}=${value}"
                }

                if ((i + 1) < fieldsSize) {
                    result += ','
                }
            }

            // time
            if (time == null) {
                time = Long.valueOf(System.currentTimeMillis())
            }
            long timeInNanos = TimeUnit.NANOSECONDS.convert(this.time.longValue(), TimeUnit.MILLISECONDS)
            result += " ${timeInNanos}"

            result
        }
    }

    static class InfluxDBServiceException extends RuntimeException {

        InfluxDBServiceException(String message) {
            super(message)
        }

        InfluxDBServiceException(String message, Throwable t) {
            super(message, t)
        }
    }

}
