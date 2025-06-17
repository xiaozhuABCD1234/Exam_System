package com.examsystem.backend.pojo;

public class ResponseMessage<T> {
    private int code; // 状态码
    private String message; // 响应消息
    private T data; // 响应数据

    public ResponseMessage(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ResponseMessage<T> success(T data) {
        return new ResponseMessage<>(200, "success", data);
    }

    public static <T> ResponseMessage<T> error(int code, String message) {
        return new ResponseMessage<>(code, message, null);
    }

    public static <T> ResponseMessage<T> notFound(String message) {
        return new ResponseMessage<>(404, message, null);
    }

    public static <T> ResponseMessage<T> unauthorized(String message) {
        return new ResponseMessage<>(401, message, null);
    }

    public static <T> ResponseMessage<T> badRequest(String message) {
        return new ResponseMessage<>(400, message, null);
    }

}
