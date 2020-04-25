package model.users;

import model.DiscountCode;
import model.Loader;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

public abstract class  User implements Serializable {
    private static HashMap<String, User> allUsers = new HashMap<>();
    private static User loggedInUser;
    protected String username;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNo;

    public User(String username, String password, String firstName,
                String lastName, String email, String phoneNo) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
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

    public boolean checkPassword(String password){
        if(password.equals(this.password)){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public abstract String toString();

    public static User getUserByUsername(String username){
        return allUsers.get(username);
    }

    public static void deleteUser(User user){
        allUsers.remove(user.getUsername());
    }

    public static void setLoggedInUser(User loggedInUser) {
        User.loggedInUser = loggedInUser;
    }

    public static boolean isUserLoggedIn(){
        if(User.loggedInUser == null){
            return false;
        } else {
            return true;
        }
    }

    public static void loadData() throws IOException{
        String usersDirectoryPath = "src/main/resources/users/";
        File usersDirectory = new File(usersDirectoryPath);
        String[] pathNames = usersDirectory.list();
        assert pathNames != null;
        for(String path: pathNames) {
            FileInputStream file = new FileInputStream(usersDirectoryPath + path);
            ObjectInputStream inputStream = new ObjectInputStream(file);
            try {
                User user = (User)inputStream.readObject();
                allUsers.put(user.getUsername(), user);
                file.close();
                inputStream.close();
                new File(usersDirectoryPath + path).delete();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public  static void saveData() {
        String usersDirectoryPath = "src/main/resources/users/";
        for(Map.Entry<String, User> entry: allUsers.entrySet()) {
            try {
                User user = entry.getValue();
                FileOutputStream file = new
                        FileOutputStream(usersDirectoryPath + user.getFirstName() + " " + user.getLastName());
                ObjectOutputStream outputStream = new ObjectOutputStream(file);
                outputStream.writeObject(user);
                file.close();
                outputStream.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
