package com.yozzibeens.rivostaxipartner.respuesta;

import com.yozzibeens.rivostaxipartner.modelo.Cabbie;

import java.util.ArrayList;


public class ResultadoRechazarSolicitud {

    private ArrayList<Cabbie> Data;
    private boolean Error;
    private String Message;



    public ArrayList<Cabbie> getData() {
        return Data;
    }

    public void setData(ArrayList<Cabbie> data) {
        Data = data;
    }

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
