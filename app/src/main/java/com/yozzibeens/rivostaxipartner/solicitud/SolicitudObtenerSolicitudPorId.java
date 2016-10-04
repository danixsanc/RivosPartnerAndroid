package com.yozzibeens.rivostaxipartner.solicitud;

/**
 * Created by Antonio on 04/10/2016.
 */

public class SolicitudObtenerSolicitudPorId {

    private boolean Data; //PENDIENTE
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
