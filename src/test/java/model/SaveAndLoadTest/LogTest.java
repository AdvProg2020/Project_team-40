package model.SaveAndLoadTest;

import model.log.Log;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class LogTest {

    @Test
    public void saveLog() throws IOException {
        ArrayList<String> productsName = new ArrayList<>();
        productsName.add("aaaaa");
        Date date = new Date();
        Log log = new Log(date, 100, 20, productsName, "Amirali",
                "Bagher", true);
        String idBeforeSaving = log.getId();
        Log.saveData();
        Log.getLogs().remove(idBeforeSaving);
        Log.loadData();
        Log logAfterSaving = Log.getLogs().get(idBeforeSaving);
        Assert.assertEquals(log, logAfterSaving);
    }
}
