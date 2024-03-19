package tech.remiges.workshop.WorkshopResponse;

import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class WorkshopResponse {

     private String status;
    private String status_Code;
    private String status_Msg;
    private Map<String, Object> data;
    private String _reqId;
    private Instant _serverTs;


    // constructor with parameter
    public WorkshopResponse(String status, String statusCode, String statusMsg, Map<String, Object> data, String reqId) {
        this.status = status;
        this.status_Code = statusCode;
        this.status_Msg = statusMsg;
        this.data = data;
        this._reqId = reqId;
        this._serverTs = Instant.now();
    }

    public WorkshopResponse() {
        this.status = "";
        this.status_Code = "";
        this.status_Msg = "";
        
    }

    

    

}
