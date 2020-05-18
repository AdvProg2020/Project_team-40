package model;

import exceptions.DataException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Score implements Serializable{

    private static HashMap<String, Score> allScores = new HashMap<>();
    private static final String PATH = "src/main/resources/scores/";

    private String username;
    private int score;
    private String productID;

    public Score(String username, int score, String productID) {
        this.username = username;
        this.score = score;
        this.productID = productID;
    }

    public String getProductID(){
        return productID;
    }

    public String getUserName(){
        return username;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public static void addScore(Score score){
        allScores.put(score.getProductID(), score);
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
                Score score = (Score) inputStream.readObject();
                allScores.put(score.getProductID(), score);
                file.close();
                inputStream.close();
                new File(PATH + path).delete();
            } catch (Exception e) {
                throw new DataException("Loading scores failed.");
            }

        }

        for(Map.Entry<String, Score> entry : allScores.entrySet()) {
            Product.getProductById(entry.getKey()).addScore(entry.getValue());
        }
    }

    public static void saveData() throws DataException {
        File directory = new File(PATH);
        if (!directory.exists())
            if (!directory.mkdir())
                throw new DataException("Saving scores failed.");

        for (Score score : allScores.values()) {
            try {
                FileOutputStream file = new FileOutputStream(PATH + score.getProductID());
                ObjectOutputStream outputStream = new ObjectOutputStream(file);
                outputStream.writeObject(score);
                file.close();
                outputStream.close();
            } catch (Exception e) {
                throw new DataException("Saving scores failed.");
            }
        }
    }

}
