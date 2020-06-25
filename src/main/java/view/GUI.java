package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Category;
import model.Product;
import model.log.Log;
import model.users.Customer;
import model.users.Manager;
import model.users.User;

import java.util.Date;
import java.util.HashMap;

public class GUI extends Application {

    public GUI(){

    }

    public static void setMenu(){

    }

    public enum Menu{
        ACCOUNTS_MENU("/layouts/accounts-menu.fxml"),
        PRODUCTS_MENU("/layouts/products-menu.fxml"),
        MAIN_MENU("/layouts/main-menu.fxml"),
        OFFS_MENU("/layouts/offs_menu.fxml");
        Parent root;
        String file;
        boolean isPreLoaded;

        Menu(Parent root){
            this.root = root;
            isPreLoaded = true;
        }

        Menu(String file){
            this.file = file;
            isPreLoaded = false;
        }

        public String getFile(){
            return file;
        }

        public boolean isPreLoaded(){
            return isPreLoaded;
        }

        public Parent getRoot(){
            return root;
        }
    }

    public static void initialize(){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Some cool stuffs to test!!
//
//        Customer c1 = new Customer("matind", "1234", "Matin", "Daghyani","m@mail.com","09121234567", 1000);
//        Customer c2 = new Customer("alid", "1234", "Ali", "Davoodi","a@mail.com","09121111111", 1030);
//        Manager m1 = new Manager("saraK", "12ss3", "sara", "kamali", "s@mail.com", "444222");
//        User.addUser(c1);
//        User.addUser(c2);
//        User.setLoggedInUser(c1);
//        Category category = new Category("Fruit", null);
//        Product p1 = new Product("Apple", "Tarebar", 10, 20, "FaridD", "Fruit");
//        Product p2 = new Product("Orange", "Tarebar", 12, 40, "FaridD", "Fruit");
//        Product.addProduct(p1);
//        Product.addProduct(p2);
//        category.addProduct(p1.getProductId());
//        category.addProduct(p2.getProductId());
//        Category.addCategory(category);
//        HashMap<String, Integer> products = new HashMap<>();
//        products.put(p1.getProductId(), 1);
//        products.put(p2.getProductId(), 1);
//        Log log = new Log(new Date(), 1000, 1000, products, "matin", "Niavaran", false);
//        c1.addLog(log);
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1100, 700));
        primaryStage.show();
    }
}
