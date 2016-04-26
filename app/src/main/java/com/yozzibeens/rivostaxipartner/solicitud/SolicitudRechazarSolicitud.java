package com.yozzibeens.rivostaxipartner.solicitud;

public class SolicitudRechazarSolicitud {

    public String getCabbie_Id() {
        return Cabbie_Id;
    }

    public void setCabbie_Id(String cabbie_Id) {
        Cabbie_Id = cabbie_Id;
    }

    public String getClient_Id() {
        return Client_Id;
    }

    public void setClient_Id(String client_Id) {
        Client_Id = client_Id;
    }

    public String getRequest_Id() {
        return Request_Id;
    }

    public void setRequest_Id(String request_Id) {
        Request_Id = request_Id;
    }

    public SolicitudRechazarSolicitud(String cabbie_Id, String client_Id, String request_Id) {
        Cabbie_Id = cabbie_Id;
        Client_Id = client_Id;
        Request_Id = request_Id;
    }

    public SolicitudRechazarSolicitud() {
    }

    private String Cabbie_Id;
    private String Client_Id;
    private String Request_Id;


}
