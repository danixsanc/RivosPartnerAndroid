package com.yozzibeens.rivostaxipartner.solicitud;

public class SolicitudCambiarEstatus {

    public SolicitudCambiarEstatus() {
    }

    public String getCabbie_Id() {
        return Cabbie_Id;
    }

    public void setCabbie_Id(String cabbie_Id) {
        Cabbie_Id = cabbie_Id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public SolicitudCambiarEstatus(String cabbie_Id, String status) {
        Cabbie_Id = cabbie_Id;
        Status = status;
    }

    private String Cabbie_Id;
    private String Status;


}
