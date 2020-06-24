package Main;

import controller.accounts.ManagerAccountController;
import exceptions.DataException;
import model.Loader;
import model.Product;
import model.enumerations.PropertyType;
import model.users.Seller;
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
        Seller seller = new Seller("a", "a", "a", "a", "a@.a", "0", 1000, "a");
        seller.setManagerPermission(true);
        Product product1 = new Product("b", "a", 10, 10, "a", "mobile");
        Product product2 = new Product("c", "a", 10, 10, "a", "mobile");
        Product product3 = new Product("d", "a", 10, 10, "a", "mobile");
        Product product4 = new Product("e", "a", 1000, 10, "a", "mobile");
        Product product5 = new Product("f", "a", 1000, 10, "a", "pc");
        Product product6 = new Product("g", "a", 1000, 10, "a", "pc");
        Product product7 = new Product("h", "a", 1000, 10, "a", "pc");
        Product product8 = new Product("i", "a", 1000, 10, "a", "pc");
        seller.addProduct(product1);
        seller.addProduct(product2);
        seller.addProduct(product3);
        seller.addProduct(product4);
        seller.addProduct(product5);
        seller.addProduct(product6);
        seller.addProduct(product7);
        seller.addProduct(product8);
        Product.addProduct(product1);
        Product.addProduct(product2);
        Product.addProduct(product3);
        Product.addProduct(product4);
        Product.addProduct(product5);
        Product.addProduct(product6);
        Product.addProduct(product7);
        Product.addProduct(product8);

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