package com.yozzibeens.rivostaxipartner.solicitud;

public class SolicitudNotificacion {


    public SolicitudNotificacion(String gcm_Id, String message, String type) {
        Gcm_Id = gcm_Id;
        Message = message;
        Type = type;
    }
    public SolicitudNotificacion() {
    }

    public String getGcm_Id() {
        return Gcm_Id;
    }

    public void setGcm_Id(String gcm_Id) {
        Gcm_Id = gcm_Id;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    private String Gcm_Id;
    private String Message;
    private String Type;



}
