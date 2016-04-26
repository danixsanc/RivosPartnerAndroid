package com.yozzibeens.rivostaxipartner.solicitud;

public class SolicitudRegistrarGcmId {

    public SolicitudRegistrarGcmId(String cabbie_Id, String gcmId) {
        Cabbie_Id = cabbie_Id;
        GcmId = gcmId;
    }

    public SolicitudRegistrarGcmId() {

    }

    public String getCabbie_Id() {
        return Cabbie_Id;
    }

    public void setCabbie_Id(String cabbie_Id) {
        Cabbie_Id = cabbie_Id;
    }

    public String getGcmId() {
        return GcmId;
    }

    public void setGcmId(String gcmId) {
        GcmId = gcmId;
    }

    private String Cabbie_Id;
    private String GcmId;


}
