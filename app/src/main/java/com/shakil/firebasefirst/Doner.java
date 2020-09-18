package com.shakil.firebasefirst;

import java.util.Date;

public class Doner {
    private  String name;
    private  String phone;
    private String bloodGroup;
    private  String area;
    private String imagepath;
    private String date;
    private int number_of_donation;
    public  Doner()
    {

    }

    public Doner(String name, String phone, String bloodGroup,String area,String imagepath,String date,int number_of_donation) {
        this.name = name;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
        this.area=area;
        this.imagepath=imagepath;
        this.date=date;
        this.number_of_donation=number_of_donation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getArea(){return area;}
    public  void  setArea(){this.area=area;}

    public String getImagepath(){return imagepath;}
    public  void  setImagepath(){this.imagepath=imagepath;}

    public int getNumber_of_donation() {
        return number_of_donation;
    }

    public void setNumber_of_donation(int number_of_donation) {
        this.number_of_donation = number_of_donation;
    }
}
