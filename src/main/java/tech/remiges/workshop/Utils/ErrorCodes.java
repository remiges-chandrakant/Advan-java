package tech.remiges.workshop.Utils;

public enum ErrorCodes {

    SUCCESS("0", "Success"),
    INSUFFICIENTARG("-2", "Insufficient Arguments"),
    FAILS("-1", "Fails"),
    EMPLOYEENOTFOUND("-3", "Employee not found in DB");

    ErrorCodes(String code, String errormsg) {
        errorcode = code;
        errorMsg = errormsg;
    }

    public String errorcode;
    public String errorMsg;
}
