package com.yozzibeens.rivostaxipartner.respuesta;

public class ResultadoNotificacion {

    public long getMulticast_id() {
        return multicast_id;
    }

    public void setMulticast_id(long multicast_id) {
        this.multicast_id = multicast_id;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }

    public ResultadoNotificacion(long multicast_id, int success, int failure) {
        this.multicast_id = multicast_id;
        this.success = success;
        this.failure = failure;
    }

    private long multicast_id;
    private int success;
    private int failure;

    public ResultadoNotificacion() {
    }
}
