package com.yozzibeens.rivostaxipartner.solicitud;

public class SolicitudObtenerDatosSolicitud {

    public String getCabbie_Id() {
        return Cabbie_Id;
    }

    public void setCabbie_Id(String cabbie_Id) {
        Cabbie_Id = cabbie_Id;
    }

    public String getRequest_Id() {
        return Request_Id;
    }

    public void setRequest_Id(String request_Id) {
        Request_Id = request_Id;
    }

    public SolicitudObtenerDatosSolicitud(String cabbie_Id, String client_Id, String request_Id) {
        Cabbie_Id = cabbie_Id;
        Request_Id = request_Id;
    }

    public SolicitudObtenerDatosSolicitud() {
    }

    private String Cabbie_Id;
    private String Request_Id;


}
