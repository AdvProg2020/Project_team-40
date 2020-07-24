package Main;

import client.controller.RequestHandler;
import client.view.GUI;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MainClient {
    public static void main(String[] args) throws IOException {
        setConfigurations();
        GUI.initialize();
    }

    private static void setConfigurations() throws IOException {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream("src/main/java/config.properties");
            prop = new Properties();
            prop.load(fis);
            String domain = prop.getProperty("domain");
            int port = Integer.parseInt(prop.getProperty("shop-port"));
            RequestHandler.setEndpoint(domain + ":" + port);
        } catch(IOException fnfe) {
            fnfe.printStackTrace();
        }
        if (fis != null)
            fis.close();
    }
}