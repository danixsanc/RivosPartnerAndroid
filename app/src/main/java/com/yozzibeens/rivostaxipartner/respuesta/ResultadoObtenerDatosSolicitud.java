package com.yozzibeens.rivostaxipartner.respuesta;


import com.yozzibeens.rivostaxipartner.modelosApp.DatosSolicitud;

import java.util.ArrayList;

public class ResultadoObtenerDatosSolicitud {

    private ArrayList<DatosSolicitud> Data;
    private boolean Error;
    private String Message;

    public ArrayList<DatosSolicitud> getData() {
        return Data;
    }

    public void setData(ArrayList<DatosSolicitud> data) {
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
