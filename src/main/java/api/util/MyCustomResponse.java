package api.util;

import java.util.Map;

public class MyCustomResponse {

    private Boolean success;
    private String message;
    private Map<String, Object> data;
    private Map<String, String> errorsDescription;

    public MyCustomResponse(Boolean success, String message, Map<String, Object> data, Map<String, String> errorsDescription) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.errorsDescription = errorsDescription;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Map<String, String> getErrorsDescription() {
        return errorsDescription;
    }

    public void setErrorsDescription(Map<String, String> errorsDescription) {
        this.errorsDescription = errorsDescription;
    }
}
