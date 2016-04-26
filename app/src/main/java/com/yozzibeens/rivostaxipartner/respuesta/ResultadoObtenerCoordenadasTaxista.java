package com.yozzibeens.rivostaxipartner.respuesta;

import com.yozzibeens.rivostaxipartner.modelo.Cabbie;
import com.yozzibeens.rivostaxipartner.modelosApp.Coordenadas;

import java.util.ArrayList;


public class ResultadoObtenerCoordenadasTaxista {

    private ArrayList<Coordenadas> Data;
    private boolean Error;
    private String Message;


    public ArrayList<Coordenadas> getData() {
        return Data;
    }

    public void setData(ArrayList<Coordenadas> data) {
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
