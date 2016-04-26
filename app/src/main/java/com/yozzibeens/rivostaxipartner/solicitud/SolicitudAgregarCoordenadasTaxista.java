package com.yozzibeens.rivostaxipartner.solicitud;

public class SolicitudAgregarCoordenadasTaxista {


    public SolicitudAgregarCoordenadasTaxista(String cabbie_Id, String latitude, String longitude) {
        Cabbie_Id = cabbie_Id;
        Latitude = latitude;
        Longitude = longitude;
    }

    public SolicitudAgregarCoordenadasTaxista() {
    }

    private String Cabbie_Id;
    private String Latitude;
    private String Longitude;

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getCabbie_Id() {
        return Cabbie_Id;
    }

    public void setCabbie_Id(String cabbie_Id) {
        Cabbie_Id = cabbie_Id;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }




}
