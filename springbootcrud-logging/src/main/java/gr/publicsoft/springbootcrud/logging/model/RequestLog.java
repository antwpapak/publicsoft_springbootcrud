package gr.publicsoft.springbootcrud.logging.model;

/**
 * A simple POJO to represent a request log entry
 */
public class RequestLog {

    private String method;
    private String uri;
    private String headers;
    private String parameters;
    private String body;
}
