package model;

import exceptions.DataException;
import model.enumerations.Status;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Comment implements Serializable{

    private static HashMap<String, Comment> allComments = new HashMap<>();
    private static final String PATH = "src/main/resources/comments/";

    private String username;
    private String productID;
    private String title;
    private String content;
    private Status status = Status.Waiting;
    private Date lastUpdate;
    private int numberOfUpdates = 0;

    public Comment(String username, String productID, String title, String content) {
        this.username = username;
        this.productID = productID;
        this.title = title;
        this.content = content;
        this.lastUpdate = new Date();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public String getProductID(){
        return productID;
    }

    public String getUsername(){
        return username;
    }

    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }

    public boolean hasBought (){

        //TODO : this part
        if(true);

        return false;
    }

    public Date getLastUpdate(){
        return lastUpdate;
    }

    public void updateText(String title, String content){
        numberOfUpdates++;
        lastUpdate = new Date();
        content = content + "\nEdit " + numberOfUpdates + " : \n" + "title : " + title + "\n" +
        "------------------------" + "\n" +
        content ;
    }

    @Override
    public String toString(){
        String bought = this.hasBought() ? "This user has purchased the product." : "This user has not purchased this product" ;
        return  "username : " + username + "\n" +
                bought + "\n" +
                "title : " + title + "\n" +
                "------------------------" + "\n" +
                "content : " + content + "\n" +
                "last update : " + lastUpdate;
    }

    public static void addComment(Comment comment){
        allComments.put(comment.getProductID(), comment);
    }

    public static void loadData() throws DataException{
        File directory = new File(PATH);
        String[] pathNames = directory.list();
        if (pathNames == null)
            return;
        for (String path: pathNames) {
            try {
                FileInputStream file = new FileInputStream(PATH + path);
                ObjectInputStream inputStream = new ObjectInputStream(file);
                Comment comment = (Comment) inputStream.readObject();
                allComments.put(comment.getProductID(), comment);
                file.close();
                inputStream.close();
                new File(PATH + path).delete();
            } catch (Exception e) {
                throw new DataException("Loading comments failed.");
            }

        }

        for(Map.Entry<String, Comment> entry : allComments.entrySet()) {
            Product.getProductById(entry.getKey()).addComment(entry.getValue());
        }
    }

    public static void saveData() throws DataException {
        File directory = new File(PATH);
        if (!directory.exists())
            if (!directory.mkdir())
                throw new DataException("Saving comments failed.");

        for (Comment comment : allComments.values()) {
            try {
                FileOutputStream file = new FileOutputStream(PATH + comment.getProductID());
                ObjectOutputStream outputStream = new ObjectOutputStream(file);
                outputStream.writeObject(comment);
                file.close();
                outputStream.close();
            } catch (Exception e) {
                throw new DataException("Saving comments failed.");
            }
        }
    }

}
