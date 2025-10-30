package com.ecommerce.http;

// HTTP接口统一响应实体
public class ApiResponse {
    private int code;    // 状态码：200=成功，400=业务错误，500=系统错误
    private String msg;  // 提示信息
    private Object data; // 响应数据（成功时返回商品信息，失败时为null）

    // 成功响应（带数据）
    public static ApiResponse success(Object data) {
        ApiResponse response = new ApiResponse();
        response.setCode(200);
        response.setMsg("操作成功");
        response.setData(data);
        return response;
    }

    // 失败响应（带错误信息）
    public static ApiResponse fail(String msg, int code) {
        ApiResponse response = new ApiResponse();
        response.setCode(code);
        response.setMsg(msg);
        response.setData(null);
        return response;
    }

    // Getter和Setter（必须，否则JSON序列化失败）
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
}