package com.yozzibeens.rivostaxipartner.solicitud;

public class SolicitudConsultarReferencia {


    public SolicitudConsultarReferencia(String referencia) {
        Referencia = referencia;
    }

    public SolicitudConsultarReferencia() {
    }

    private String Referencia;

    public String getReferencia() {
        return Referencia;
    }

    public void setReferencia(String referencia) {
        Referencia = referencia;
    }
}
