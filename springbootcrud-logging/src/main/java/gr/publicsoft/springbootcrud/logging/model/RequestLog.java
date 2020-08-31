package gr.publicsoft.springbootcrud.logging.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * A simple POJO to represent a request log entry
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RequestLog {

    private String method;
    private String uri;
    private String headers;
    private String parameters;
    private String body;
}
