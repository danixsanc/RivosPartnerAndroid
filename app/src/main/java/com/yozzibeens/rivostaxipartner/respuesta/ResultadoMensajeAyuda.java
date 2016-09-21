package com.yozzibeens.rivostaxipartner.respuesta;


public class ResultadoMensajeAyuda {



    private boolean Data;
    private boolean IsError;
    private String Message;

    public boolean isData() {
        return Data;
    }

    public void setData(boolean data) {
        Data = data;
    }

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
