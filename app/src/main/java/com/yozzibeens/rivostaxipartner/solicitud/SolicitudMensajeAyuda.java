package com.yozzibeens.rivostaxipartner.solicitud;

public class SolicitudMensajeAyuda {

    private String Cabbie_Id;
    private String Subject;
    private String Message;

    public SolicitudMensajeAyuda(String cabbie_Id, String subject, String message) {
        Cabbie_Id = cabbie_Id;
        Subject = subject;
        Message = message;
    }

    public SolicitudMensajeAyuda() {
    }

    public String getCabbie_Id() {
        return Cabbie_Id;
    }

    public void setCabbie_Id(String cabbie_Id) {
        Cabbie_Id = cabbie_Id;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }





}
