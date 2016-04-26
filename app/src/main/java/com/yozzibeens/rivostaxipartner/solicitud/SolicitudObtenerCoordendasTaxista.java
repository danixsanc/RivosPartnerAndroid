package com.yozzibeens.rivostaxipartner.solicitud;

public class SolicitudObtenerCoordendasTaxista {

    public SolicitudObtenerCoordendasTaxista(String cabbie_Id) {
        Cabbie_Id = cabbie_Id;
    }

    public SolicitudObtenerCoordendasTaxista() {
    }

    public String getCabbie_Id() {
        return Cabbie_Id;
    }

    public void setCabbie_Id(String cabbie_Id) {
        Cabbie_Id = cabbie_Id;
    }


    private String Cabbie_Id;



}
