package com.yozzibeens.rivostaxipartner.respuesta;


import com.yozzibeens.rivostaxipartner.modelosApp.Referencia;

import java.util.ArrayList;

public class ResultadoConsultarReferencia {

    public ArrayList<Referencia> getData() {
        return Data;
    }

    public void setData(ArrayList<Referencia> data) {
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

    private ArrayList<Referencia> Data;
    private boolean IsError;
    private String Message;


}
