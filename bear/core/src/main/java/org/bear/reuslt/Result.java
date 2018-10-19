package org.bear.reuslt;

public class Result<T> {
    /**
     * 状态   1.success 2.fail
     */
    private String status;
    /**
     * 失败信息
     */
    private String message;
    /**
     * 失败对应的错误码
     */
    private Integer code;
    /**
     * 返回的数据
     */
    private T data;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
