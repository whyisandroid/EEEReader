package com.ereader.common.exception;


public class CommonException extends Exception {
    /**
     * 错误信息
     */
    private ErrorMessage errorMessage;


    private Exception backupException;

    private static final long serialVersionUID = -2019803409178990L;

    public CommonException() {

    }


    public CommonException(ErrorMessage em, Exception backExcep) {
        this.errorMessage = em;
        this.backupException = backExcep;
    }

    public CommonException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorMessage.getCode();
    }

    public Exception getBackupException() {
        return backupException;
    }

    public void setBackupException(Exception backupException) {
        this.backupException = backupException;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

}
