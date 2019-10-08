package com.example.e_noticeboard;

public class AdminsOnly {
    private String password;
    private String dash;

    public AdminsOnly() {
    }

    public AdminsOnly(String password, String dash) {
        this.password = password;
        this.dash = dash;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDash() {
        return dash;
    }

    public void setDash(String dash) {
        this.dash = dash;
    }
}
