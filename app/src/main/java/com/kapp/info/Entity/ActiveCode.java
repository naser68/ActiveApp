package com.kapp.info.Entity;

/**
 * Created by Naser on 6/17/2017.
 */

public class ActiveCode {


    private int Id;
    private String KeyCode;
    private String ActiveCode;
    private String Name;
    private String MobileNumber;
    private String CreateDate;


    public ActiveCode() {

    }

    public ActiveCode(int id , String keyCode, String activeCode, String name, String mobileNumber , String createDate) {
        Id = id;
        KeyCode = keyCode;
        ActiveCode = activeCode;
        Name = name;
        MobileNumber = mobileNumber;
        CreateDate = createDate;
    }



    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getKeyCode() {
        return KeyCode;
    }

    public void setKeyCode(String keyCode) {
        KeyCode = keyCode;
    }

    public String getActiveCode() {
        return ActiveCode;
    }

    public void setActiveCode(String activeCode) {
        ActiveCode = activeCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

}
