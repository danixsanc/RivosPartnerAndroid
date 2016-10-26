package com.yozzibeens.rivostaxipartner.modelo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table CABBIE.
 */
public class Cabbie {

    private Long id;
    private String Cabbie_Id;
    private String Name;
    private String Email;
    private String Phone;
    private String Image;

    public Cabbie() {
    }

    public Cabbie(Long id) {
        this.id = id;
    }

    public Cabbie(Long id, String Cabbie_Id, String Name, String Email, String Phone, String Image) {
        this.id = id;
        this.Cabbie_Id = Cabbie_Id;
        this.Name = Name;
        this.Email = Email;
        this.Phone = Phone;
        this.Image = Image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCabbie_Id() {
        return Cabbie_Id;
    }

    public void setCabbie_Id(String Cabbie_Id) {
        this.Cabbie_Id = Cabbie_Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

}
