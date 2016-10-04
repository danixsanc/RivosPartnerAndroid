package com.yozzibeens.rivostaxipartner.respuesta;

import com.yozzibeens.rivostaxipartner.modelo.Cabbie;
import com.yozzibeens.rivostaxipartner.modelosApp.Solicitud;

import java.util.ArrayList;


public class ResultadoObtenerSolicitudes {

    private ArrayList<Solicitud> Data;
    private boolean IsError;
    private String Message;




    public ArrayList<Solicitud> getData() {
        return Data;
    }

    public void setData(ArrayList<Solicitud> data) {
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
