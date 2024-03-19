package tech.remiges.workshop.controller;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.remiges.workshop.Request.WorkshopRequest;
import tech.remiges.workshop.Utils.CommonUtils;
import tech.remiges.workshop.Utils.ErrorCodes;
import tech.remiges.workshop.WorkshopResponse.WorkshopResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UtilityController {

    @Autowired
    private Environment env;

    @GetMapping("/hello")
    public WorkshopResponse GetHelloMessage() {

        WorkshopResponse response = new WorkshopResponse();
        response.setStatus(CommonUtils.SUCCESS);
        HashMap<String, Object> data = new HashMap<>();
        data.put("msg", "Hello World");
        response.setData(data);
        return response;
    }

    @PostMapping("/hello")
    public WorkshopResponse getHellocustmize(@RequestBody WorkshopRequest request) {
        WorkshopResponse response = new WorkshopResponse();
        Map<String, Object> data = request.getData();

        if (data.isEmpty()) {
            response.setStatus(CommonUtils.FAIL);
            response.setStatus_Msg("provide data");
        } else {
            response.setStatus(CommonUtils.SUCCESS);
            HashMap<String, Object> resdata = new HashMap<>();
            String name = data.get("name").toString();
            resdata.put("msg", "Hello "
                    + name);
            response.setData(resdata);
        }
        return response;
    }

    @PostMapping("/mySum")
    public WorkshopResponse getOperationResult(@RequestBody WorkshopRequest request) {
        if (request.getData().size() < 3) {
            WorkshopResponse response = new WorkshopResponse(CommonUtils.FAIL, ErrorCodes.INSUFFICIENTARG.errorcode,
                    ErrorCodes.INSUFFICIENTARG.errorMsg, Map.of("", ""),
                    request.getReqId());
            response.set_serverTs(Instant.now());

            return response;

        }

        String operation = (String) request.getData().get("operation").toString().toLowerCase();
        Number num1Obj = (Number) request.getData().get("num1");
        Number num2Obj = (Number) request.getData().get("num2");

        // Convert num1Obj and num2Obj to double
        float num1 = num1Obj.floatValue();
        float num2 = num2Obj.floatValue();

        // Perform operation
        double result;
        switch (operation) {
            case "add":
                result = num1 + num2;
                break;
            case "subtract":
                result = num1 - num2;
                break;
            case "multiply":
                result = num1 * num2;
                break;
            case "divide":
                if (num2 == 0) {
                    throw new IllegalArgumentException("Division by zero is not allowed.");
                }
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("operation not supported: " + operation);
        }

        WorkshopResponse response = new WorkshopResponse(CommonUtils.SUCCESS, "", "", Map.of("result", result),
                request.getReqId());
        response.set_serverTs(Instant.now());

        return response;
    }

    @PostMapping("/mycalc")
    public WorkshopResponse getMyCalcResults(@RequestBody WorkshopRequest request) {

        if (request.getData().size() < 2) {
            WorkshopResponse response = new WorkshopResponse(CommonUtils.FAIL, ErrorCodes.INSUFFICIENTARG.errorcode,
                    ErrorCodes.INSUFFICIENTARG.errorMsg, Map.of("", ""),
                    request.getReqId());
            response.set_serverTs(Instant.now());

            return response;

        }

        String operation = (String) request.getData().get("operation");
        List<Integer> numbers = ((List<Integer>) request.getData().get("numbers"));

        double result = 0;
        String opExecuted = "";

        switch (operation) {
            case "mean":
                result = CommonUtils.calculateMean(numbers);
                opExecuted = "Mean value Operation Executed";
                break;
            case "min":
                result = Collections.min(numbers);
                opExecuted = "Minimum value Operation Executed";
                break;
            case "max":
                result = Collections.max(numbers);
                opExecuted = "Maximum value Operation Executed";
                break;
            default:
                throw new IllegalArgumentException("operation not supported " + operation);
        }
        WorkshopResponse response = new WorkshopResponse(CommonUtils.SUCCESS, "", "",
                Map.of(opExecuted, result), request.getReqId());

        return response;
    }

    @PostMapping("/myproperties")
    public WorkshopResponse getPropertiesValues(@RequestBody WorkshopRequest request) {

        if (request.getData().size() < 1) {
            WorkshopResponse response = new WorkshopResponse(CommonUtils.FAIL, ErrorCodes.INSUFFICIENTARG.errorcode,
                    ErrorCodes.INSUFFICIENTARG.errorMsg, Map.of("", ""),
                    request.getReqId());
            response.set_serverTs(Instant.now());

            return response;

        }
        List<String> propertylist = (List<String>) request.getData().get("properties");
        Map<String, Object> result = new HashMap<>();

        propertylist.forEach(identifier -> {

            String propertyValue = env.getProperty(identifier, "NULL");
            result.put(identifier, propertyValue);

        });

        WorkshopResponse response = new WorkshopResponse(CommonUtils.SUCCESS, "", "", result,
                request.getReqId());
        response.set_serverTs(Instant.now());

        return response;

    }
}