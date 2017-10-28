package com.soento.framework.core.lang;

import com.soento.framework.core.consts.BaseMessageCode;

public class RestResponse<T> extends BaseObject {

    private String code;
    private String message;
    private T data;

    public RestResponse() {
        this.code = BaseMessageCode.E9998;
    }

    public void success() {
        this.code = BaseMessageCode.SUCCESS;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
}
