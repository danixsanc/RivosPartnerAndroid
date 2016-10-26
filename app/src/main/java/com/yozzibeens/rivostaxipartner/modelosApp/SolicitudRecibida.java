package com.yozzibeens.rivostaxipartner.modelosApp;

import java.io.Serializable;

/**
 * Created by Antonio on 26/10/2016.
 */

public class SolicitudRecibida implements Serializable {


    public SolicitudRecibida() {
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

    public String getGcm_Id() {
        return Gcm_Id;
    }

    public void setGcm_Id(String gcm_Id) {
        Gcm_Id = gcm_Id;
    }

    public int[] getList_Client() {
        return List_Client;
    }

    public void setList_Client(int[] list_Client) {
        List_Client = list_Client;
    }

    public int[] getList_Request() {
        return List_Request;
    }

    public void setList_Request(int[] list_Request) {
        List_Request = list_Request;
    }

    public String[] getList_gcm_id() {
        return List_gcm_id;
    }

    public void setList_gcm_id(String[] list_gcm_id) {
        List_gcm_id = list_gcm_id;
    }

    public SolicitudRecibida(String client_Id, String request_Id, String gcm_Id, int[] list_Client, int[] list_Request, String[] list_gcm_id) {
        Client_Id = client_Id;
        Request_Id = request_Id;
        Gcm_Id = gcm_Id;
        List_Client = list_Client;
        List_Request = list_Request;
        List_gcm_id = list_gcm_id;
    }

    private String Client_Id;
    private String Request_Id;
    private String Gcm_Id;
    private int[] List_Client;
    private int[] List_Request;
    private String[] List_gcm_id;



}
