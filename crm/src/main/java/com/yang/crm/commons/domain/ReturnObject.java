package com.yang.crm.commons.domain;

/**
 * @author liushiqiang05
 * @date 2022/9/18 2:34 PM
 */
public class ReturnObject {
    private String code;
    private String message;
    private Object obj;

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

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
