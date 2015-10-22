package com.ereader.common.exception;

public class ErrorMessage implements java.io.Serializable {

    private static final long serialVersionUID = 7714918001204577L;
    /**
     * 异常类型
     */
    private String code;

    /**
     * 异常名称
     */
    private String name;

    /**
     * 异常信息
     */
    private String message;


    /**
     * 详细信息
     */
    private String details;


    public ErrorMessage(String code, String name, String mesg, String details) {
        this.code = code;
        this.name = name;
        this.message = mesg;
        this.details = details;
    }

    public ErrorMessage(String message) {
        this.message = message;
    }

    public ErrorMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorMessage() {

    }


    public String getDetails() {
        return details;
    }


    public void setDetails(String details) {
        this.details = details;
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


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getSimpleString() {
        return null;
    }
}
