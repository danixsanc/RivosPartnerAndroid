package com.yozzibeens.rivostaxipartner.solicitud;

public class SolicitudLogin {


    public SolicitudLogin() {
    }

    public SolicitudLogin(String email, String password, String gcm_Id, String user_Type) {
        Email = email;
        Password = password;
        Gcm_Id = gcm_Id;
        User_Type = user_Type;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getGcm_Id() {
        return Gcm_Id;
    }

    public void setGcm_Id(String gcm_Id) {
        Gcm_Id = gcm_Id;
    }

    public String getUser_Type() {
        return User_Type;
    }

    public void setUser_Type(String user_Type) {
        User_Type = user_Type;
    }

    private String Email;
    private String Password;
    private String Gcm_Id;
    private String User_Type;


}
