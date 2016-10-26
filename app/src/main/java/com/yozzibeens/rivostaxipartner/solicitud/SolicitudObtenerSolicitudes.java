package com.yozzibeens.rivostaxipartner.solicitud;

public class SolicitudObtenerSolicitudes {


    public SolicitudObtenerSolicitudes() {
    }

    public String getCabbie_Id() {
        return Cabbie_Id;
    }

    public void setCabbie_Id(String cabbie_Id) {
        Cabbie_Id = cabbie_Id;
    }

    public String getOnProcess() {
        return OnProcess;
    }

    public void setOnProcess(String onProcess) {
        OnProcess = onProcess;
    }

    public SolicitudObtenerSolicitudes(String cabbie_Id, String onProcess) {
        Cabbie_Id = cabbie_Id;
        OnProcess = onProcess;
    }

    private String Cabbie_Id;
    private String OnProcess;



}
