package client.controller;

import java.util.HashMap;

public class Client {
    private boolean isLoggedIn;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private Integer bankAccount;
    private String role;
    private String company;
    private static String latestToken;
    private static Client client = new Client();

    private Client(){}


    public boolean isLoggedIn(){
        return isLoggedIn;
    }

    public String getUsername(){
        if (username != null)
            return username;
        return null;
    }

    public String getPassword(){
        if (password != null)
            return password;
        return null;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public Integer getBankAccount() {
        return bankAccount;
    }

    public String getRole() {
        return role;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setBankAccount(Integer bankAccount) {
        this.bankAccount = bankAccount;
    }

    public  void setLatestToken(String latestToken) {
        Client.latestToken = latestToken;
    }

    public void logout(){
        isLoggedIn = false;
        username = null;
        password = null;
        firstName = null;
        lastName = null;
        email = null;
        phoneNo = null;
        role = null;
        bankAccount = null;
        company = null;
    }


    public void  setParameters(HashMap<?, ?> values){
        this.isLoggedIn = true;
        this.username =(String) values.get("username");
        this.password = (String) values.get("password");
        this.bankAccount = Integer.parseInt((String) values.get("bankAccount"));
        this.firstName = (String) values.get("firstName");
        this.lastName = (String) values.get("lastName");
        this.role = (String) values.get("role");
        this.email = (String) values.get("email");
        this.phoneNo = (String) values.get("phoneNumber");
        if (role.equals("Seller")){
            this.company = (String) values.get("company");
        }
    }

    public static Client getInstance() {
        return client;
    }







}
