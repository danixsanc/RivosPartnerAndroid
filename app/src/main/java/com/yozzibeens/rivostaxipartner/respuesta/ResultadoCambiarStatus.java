package com.yozzibeens.rivostaxipartner.respuesta;


public class ResultadoCambiarStatus {

    private boolean Error;
    private String Message;

    public boolean isError() {
        return Error;
    }

    public void setError(boolean error) {
        Error = error;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

}
