package com.yozzibeens.rivostaxipartner.respuesta;

/**
 * Created by Antonio on 04/10/2016.
 */

public class ResultadoObtenerSolicitudPorId
{
    public ResultadoObtenerSolicitudPorId(){

    }

    public ResultadoObtenerSolicitudPorId(String request_Id) {
        Request_Id = request_Id;
    }

    public String getRequest_Id() {
        return Request_Id;
    }

    public void setRequest_Id(String request_Id) {
        Request_Id = request_Id;
    }

    private String Request_Id;
}
