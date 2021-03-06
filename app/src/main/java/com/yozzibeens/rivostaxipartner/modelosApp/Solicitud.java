package com.yozzibeens.rivostaxipartner.modelosApp;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table CABBIE.
 */
public class Solicitud {



    public Solicitud() {
    }


    public Solicitud(String request_Id, String date, String latitude_In, String longitude_In, String latitude_Fn, String longitude_Fn, String inicio, String destino, String ref, String price, String client_Id, String firstName, String cabbie_Id, String lastName, String phone, String gcm_Id, String user_Type_Id) {

        Request_Id = request_Id;
        Date = date;
        Latitude_In = latitude_In;
        Longitude_In = longitude_In;
        Latitude_Fn = latitude_Fn;
        Longitude_Fn = longitude_Fn;
        Inicio = inicio;
        Destino = destino;
        Ref = ref;
        Price = price;
        Client_Id = client_Id;
        FirstName = firstName;
        Cabbie_Id = cabbie_Id;
        LastName = lastName;
        Phone = phone;
        Gcm_Id = gcm_Id;
        User_Type_Id = user_Type_Id;
    }

    private String Request_Id;
    private String Date;
    private String Latitude_In;
    private String Longitude_In;
    private String Latitude_Fn;
    private String Longitude_Fn;
    private String Inicio;
    private String Destino;
    private String Ref;
    private String Price;
    private String Client_Id;
    private String FirstName;
    private String Cabbie_Id;
    private String LastName;
    private String Phone;
    private String Gcm_Id;
    private String User_Type_Id;

    public String getRequest_Id() {
        return Request_Id;
    }

    public void setRequest_Id(String request_Id) {
        Request_Id = request_Id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getLatitude_In() {
        return Latitude_In;
    }

    public void setLatitude_In(String latitude_In) {
        Latitude_In = latitude_In;
    }

    public String getLongitude_In() {
        return Longitude_In;
    }

    public void setLongitude_In(String longitude_In) {
        Longitude_In = longitude_In;
    }

    public String getLatitude_Fn() {
        return Latitude_Fn;
    }

    public void setLatitude_Fn(String latitude_Fn) {
        Latitude_Fn = latitude_Fn;
    }

    public String getLongitude_Fn() {
        return Longitude_Fn;
    }

    public void setLongitude_Fn(String longitude_Fn) {
        Longitude_Fn = longitude_Fn;
    }

    public String getInicio() {
        return Inicio;
    }

    public void setInicio(String inicio) {
        Inicio = inicio;
    }

    public String getDestino() {
        return Destino;
    }

    public void setDestino(String destino) {
        Destino = destino;
    }

    public String getRef() {
        return Ref;
    }

    public void setRef(String ref) {
        Ref = ref;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getClient_Id() {
        return Client_Id;
    }

    public void setClient_Id(String client_Id) {
        Client_Id = client_Id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getCabbie_Id() {
        return Cabbie_Id;
    }

    public void setCabbie_Id(String cabbie_Id) {
        Cabbie_Id = cabbie_Id;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getGcm_Id() {
        return Gcm_Id;
    }

    public void setGcm_Id(String gcm_Id) {
        Gcm_Id = gcm_Id;
    }

    public String getUser_Type_Id() {
        return User_Type_Id;
    }

    public void setUser_Type_Id(String user_Type_Id) {
        User_Type_Id = user_Type_Id;
    }
}
