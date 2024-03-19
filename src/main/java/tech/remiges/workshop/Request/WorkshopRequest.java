package tech.remiges.workshop.Request;

import java.time.Instant;
import java.util.Map;

import lombok.Data;

@Data
public class WorkshopRequest {

    private String token;

    private Map<String, Object> data;
    private String reqId;
    private Instant client_ts;
    private String client_type;

    public WorkshopRequest(String token, Map<String, Object> data, String reqId, String client_ts, String client_type) {
        this.token = token;
        this.data = data;
        this.reqId = reqId;
        this.client_ts = Instant.now();
        this.client_type = client_type;
    }

}
