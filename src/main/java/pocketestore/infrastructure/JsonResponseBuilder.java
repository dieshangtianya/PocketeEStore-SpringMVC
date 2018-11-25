package pocketestore.infrastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonResponseBuilder {

    public static Map<String, Object> buildSuccessResponse() {
        JsonResponseBuilder builder = new JsonResponseBuilder();
        builder.setSuccessData(null);
        return builder.buildResponse();
    }

    public static Map<String, Object> buildSuccessResponse(Object data) {
        JsonResponseBuilder builder = new JsonResponseBuilder();
        builder.setSuccessData(data);
        return builder.buildResponse();
    }

    public static <T> Map<String, Object> buildSuccessResponse(int currentPage, int pageSize, long total, List<T> data) {
        JsonResponseBuilder builder = new JsonResponseBuilder();
        builder.setSuccessData(currentPage, pageSize, total, (List<Object>) data);
        return builder.buildResponse();
    }

    public static Map<String, Object> buildErrorResponse(String errorMsg) {
        JsonResponseBuilder builder = new JsonResponseBuilder();
        builder.setError(errorMsg);
        return builder.buildResponse();
    }

    public static Map<String, Object> buildErrorResponse(int statusCode, String errorMsg) {
        JsonResponseBuilder builder = new JsonResponseBuilder();
        builder.setError(statusCode, errorMsg);
        return builder.buildResponse();
    }

    private Map<String, Object> responseMap;

    public JsonResponseBuilder() {
        responseMap = new HashMap();
    }

    public void setSuccessData(Object object) {
        responseMap.put("status", 0);
        Map<String, Object> result = new HashMap<>();
        if (object != null) {
            result.put("data", object);
        }
        responseMap.put("result", result);
    }

    public void setSuccessData(int currentPage, int pageSize, long total, List<Object> data) {
        responseMap.put("status", 0);
        Map<String, Object> result = new HashMap<>();
        result.put("pageSize", pageSize);
        result.put("currentPage", currentPage);
        result.put("total", total);
        result.put("data", data);
        responseMap.put("result", result);
    }

    public void setError(String errorMsg) {
        responseMap.put("status", -1);
        responseMap.put("error", errorMsg);
    }

    public void setError(int status, String errorMsg) {
        responseMap.put("status", status);
        responseMap.put("error", errorMsg);

    }

    public Map<String, Object> buildResponse() {
        return responseMap;
    }
}

