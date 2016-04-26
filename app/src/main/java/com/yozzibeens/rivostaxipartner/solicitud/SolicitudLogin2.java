package com.yozzibeens.rivostaxipartner.solicitud;

/**
 * Created by danixsanc on 21/04/2016.
 */
public class SolicitudLogin2 {

    public SolicitudLogin2(String email, String password) {
        Email = email;
        Password = password;
    }

    public SolicitudLogin2() {
    }

    private String Email;

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    private String Password;


}
