package model.users;

import exceptions.DataException;
import model.BankAccount;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class  User implements Serializable {
    private static final long serialVersionUID = -178828746063914640L;
    protected static HashMap<String, User> allUsers = new HashMap<>();
    private static User loggedInUser;
    protected String username;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNo;
    protected BankAccount bankAccount;

    public User(String username, String password, String firstName,
                String lastName, String email, String phoneNo) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.bankAccount = null;
        allUsers.put(this.username, this);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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

    public abstract String getRole();

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean checkPassword(String password){
        return password.equals(this.password);
    }

    @Override
    public abstract String toString();

    public static void addUser(User user) {
        allUsers.put(user.getUsername(), user);
    }

    public static User getUserByUsername(String username){
        return allUsers.get(username);
    }

    public static void deleteUser(User user){
        allUsers.remove(user.getUsername());
    }

    public static boolean doesUserExist(String username){
        return allUsers.get(username) != null;
    }

    public static Set<String> getAllUsernames(){
        return allUsers.keySet();
    }

    public static boolean doesManagerExist(){
        for (String user : allUsers.keySet())
            if (allUsers.get(user) instanceof Manager)
                return true;

        return false;
    }

    public static void setLoggedInUser(User loggedInUser) {
        User.loggedInUser = loggedInUser;
    }

    public static boolean isUserLoggedIn(){
        return User.loggedInUser != null;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void loadData() throws DataException {
        String usersDirectoryPath = "src/main/resources/users/";
        File usersDirectory = new File(usersDirectoryPath);
        String[] pathNames = usersDirectory.list();
        if (pathNames == null)
            return;
        for(String path: pathNames) {
            try {
                FileInputStream file = new FileInputStream(usersDirectoryPath + path);
                ObjectInputStream inputStream = new ObjectInputStream(file);
                User user = (User)inputStream.readObject();
                allUsers.put(user.getUsername(), user);
                file.close();
                inputStream.close();
                new File(usersDirectoryPath + path).delete();
            } catch (Exception e) {
                throw new DataException("Loading users failed.");
            }

        }
    }

    public  static void saveData() throws DataException {
        String usersDirectoryPath = "src/main/resources/users/";
        File directory = new File(usersDirectoryPath);
        if (!directory.exists())
            if (!directory.mkdir())
                throw new DataException("Saving users failed.");
        for(Map.Entry<String, User> entry: allUsers.entrySet()) {
            try {
                User user = entry.getValue();
                FileOutputStream file = new
                        FileOutputStream(usersDirectoryPath + user.username);
                ObjectOutputStream outputStream = new ObjectOutputStream(file);
                outputStream.writeObject(user);
                file.close();
                outputStream.close();
            } catch (Exception exception) {
                throw new DataException("Saving users failed.");
            }
        }
    }
}
