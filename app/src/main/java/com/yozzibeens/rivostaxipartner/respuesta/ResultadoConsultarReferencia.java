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

    private ArrayList<Referencia> Data;
    private boolean Error;
    private String Message;


}
