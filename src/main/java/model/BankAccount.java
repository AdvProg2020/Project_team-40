package model;

public class BankAccount {
    private int accountNumber;
    private String username;
    private String password;

    public BankAccount(int accountNumber, String username, String password) {
        this.accountNumber = accountNumber;
        this.username = username;
        this.password = password;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
