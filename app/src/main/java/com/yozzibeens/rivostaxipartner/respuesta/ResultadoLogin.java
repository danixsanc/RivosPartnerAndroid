package com.yozzibeens.rivostaxipartner.respuesta;

import com.yozzibeens.rivostaxipartner.modelo.Cabbie;

import java.util.ArrayList;


public class ResultadoLogin {

    private Cabbie Data;
    private boolean IsError;
    private String Message;



    public Cabbie getData() {
        return Data;
    }

    public void setData(Cabbie data) {
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
