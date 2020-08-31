package gr.publicsoft.springbootcrud.logging.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A simple POJO to represent a response log entry
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ResponseLog {

    private int statusCode;
    private String headers;
    private String body;
}
