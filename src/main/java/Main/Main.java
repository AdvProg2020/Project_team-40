package Main;

import controller.accounts.ManagerAccountController;
import exceptions.DataException;
import model.Loader;
import model.Product;
import model.enumerations.PropertyType;
import view.GUI;

import java.io.File;
import java.util.HashMap;

public class Main {
    private static final String PATH = "src/main/resources";

    public static void main(String[] args) {
        initializeLoading();
        addTempVariables();
        GUI.initialize();
    }

    private static void addTempVariables(){
        HashMap<String, PropertyType> properties = new HashMap<>();
        properties.put("size", PropertyType.STRING);
        properties.put("wifi", PropertyType.STRING);
        properties.put("number of usb jacks", PropertyType.RANGE);
        properties.put("storage space", PropertyType.RANGE);

        HashMap<String, PropertyType> subProperties = new HashMap<>();
        properties.put("display", PropertyType.STRING);
        properties.put("OS", PropertyType.STRING);
        properties.put("ram", PropertyType.RANGE);
        properties.put("camera quality", PropertyType.RANGE);

        HashMap<String, PropertyType> subProperties2 = new HashMap<>();
        properties.put("ram", PropertyType.STRING);
        properties.put("cpu", PropertyType.STRING);
        properties.put("graphics", PropertyType.RANGE);
        properties.put("ssd", PropertyType.RANGE);

        try {
            ManagerAccountController.getInstance().createCategory("electronics", null, properties);
        }catch(Exception e){
            e.printStackTrace();
        }
        try {
            ManagerAccountController.getInstance().createCategory("mobile", "electronics", subProperties);
        }catch(Exception e){
            e.printStackTrace();
        }
        try {
            ManagerAccountController.getInstance().createCategory("pc", "electronics", subProperties2);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void resourcesInitialization() throws DataException {
        File resourcesDirectory = new File(PATH);
        if (!resourcesDirectory.exists())
            if (!resourcesDirectory.mkdir())
                throw new DataException("System loading failed.");
    }

    private static void initializeLoading(){
        try {
            resourcesInitialization();
        } catch (DataException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        try {
            Loader.getLoader().loadData();
        } catch (DataException e) {
            System.err.println(e.getMessage());
        }
    }
}