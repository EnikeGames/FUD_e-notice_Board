package com.example.e_noticeboard;

public class StudentInfo {

    private String name;
    private String regno;
    private String password;
    private String email;
    private String phone;
    private String department;
    private String dash;

    public StudentInfo() {
    }

    public StudentInfo(String name, String regno, String password, String email, String phone, String department, String dash) {
        this.name = name;
        this.regno = regno;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.dash = dash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDash() {
        return dash;
    }

    public void setDash(String dash) {
        this.dash = dash;
    }
}
