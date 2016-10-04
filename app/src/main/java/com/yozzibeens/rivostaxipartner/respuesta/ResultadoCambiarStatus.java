package com.yozzibeens.rivostaxipartner.respuesta;


public class ResultadoCambiarStatus {

    private boolean IsError;
    private String Message;

    public boolean isError() {
        return IsError;
    }

    public void setError(boolean error) {
        IsError = error;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

}
