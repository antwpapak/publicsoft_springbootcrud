package gr.publicsoft.springbootcrud.logging.api;


import gr.publicsoft.springbootcrud.logging.model.ResponseLog;
import org.slf4j.Logger;

import static java.util.Objects.nonNull;

public interface ResponseLogger {

    Logger getLogger();

    default void logResponse(ResponseLog responseLog) {
        getLogger().info(String.format("[RESPONSE] %s%s%s",
                responseLog.getStatusCode(),
                (nonNull(responseLog.getHeaders()) && !responseLog.getHeaders().isEmpty()) ? String.format(" | HEADERS: {%s}", responseLog.getHeaders()) : "",
                (nonNull(responseLog.getBody()) && !responseLog.getBody().isEmpty()) ? String.format(" | BODY: %s", responseLog.getBody()) : ""
        ).replaceAll("\\s{2,}", " "));
    }
}
