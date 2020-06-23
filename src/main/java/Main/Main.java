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

    private static void addTempVariables() {
        HashMap<String, PropertyType> properties = new HashMap<>();
        properties.put("size", PropertyType.STRING);
        properties.put("wifi", PropertyType.STRING);
        properties.put("number of usb jacks", PropertyType.RANGE);
        properties.put("storage space", PropertyType.RANGE);

        HashMap<String, PropertyType> subProperties = new HashMap<>();
        subProperties.put("display", PropertyType.STRING);
        subProperties.put("OS", PropertyType.STRING);
        subProperties.put("ram", PropertyType.RANGE);
        subProperties.put("camera quality", PropertyType.RANGE);

        HashMap<String, PropertyType> subProperties2 = new HashMap<>();
        subProperties2.put("ram", PropertyType.STRING);
        subProperties2.put("cpu", PropertyType.STRING);
        subProperties2.put("graphics", PropertyType.RANGE);
        subProperties2.put("ssd", PropertyType.RANGE);

        try {
            ManagerAccountController.getInstance().createCategory("electronics", null, properties);
            ManagerAccountController.getInstance().createCategory("mobile", "electronics", subProperties);
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