package Main;

import client.view.GUI;

public class Main {
    public static void main(String[] args) {
        GUI.initialize();
    }

//    private static void addTempVariables() {
//        HashMap<String, PropertyType> properties = new HashMap<>();
//        properties.put("size", PropertyType.STRING);
//        properties.put("wifi", PropertyType.STRING);
//        properties.put("number of usb jacks", PropertyType.RANGE);
//        properties.put("storage space", PropertyType.RANGE);
//
//        HashMap<String, PropertyType> subProperties = new HashMap<>();
//        subProperties.put("display", PropertyType.STRING);
//        subProperties.put("OS", PropertyType.STRING);
//        subProperties.put("ram", PropertyType.RANGE);
//        subProperties.put("camera quality", PropertyType.RANGE);
//
//        HashMap<String, PropertyType> subProperties2 = new HashMap<>();
//        subProperties2.put("ram", PropertyType.STRING);
//        subProperties2.put("cpu", PropertyType.STRING);
//        subProperties2.put("graphics", PropertyType.RANGE);
//        subProperties2.put("ssd", PropertyType.RANGE);
//
//        try {
//            ManagerAccountController.getInstance().createCategory("electronics", null, properties);
//            ManagerAccountController.getInstance().createCategory("mobile", "electronics", subProperties);
//            ManagerAccountController.getInstance().createCategory("pc", "electronics", subProperties2);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        Seller seller = new Seller("a", "a", "a", "a", "a@.a", "0", 1000, "a");
//        seller.setManagerPermission(true);
//        Product product1 = new Product("b", "a", 10, 10, "a", "mobile");
//        Product product2 = new Product("c", "a", 10, 10, "a", "mobile");
//        Product product3 = new Product("d", "a", 10, 10, "a", "mobile");
//        Product product4 = new Product("e", "a", 1000, 10, "a", "mobile");
//        Product product5 = new Product("f", "a", 1000, 10, "a", "pc");
//        Product product6 = new Product("g", "a", 1000, 10, "a", "pc");
//        Product product7 = new Product("h", "a", 1000, 10, "a", "pc");
//        Product product8 = new Product("i", "a", 1000, 10, "a", "pc");
//        product1.setExplanation("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum commodo lacinia augue eu rhoncus. Sed non erat in felis rutrum faucibus. Integer laoreet bibendum purus, id interdum augue faucibus volutpat. Suspendisse efficitur tincidunt ipsum id ultrices. Praesent sem enim, ultricies sit amet tempor eu, aliquet at ipsum. Donec dapibus elit vitae tortor vulputate faucibus. Mauris purus urna, pellentesque maximus augue quis, blandit vestibulum urna.\n" +
//                "\n" +
//                "Mauris fermentum maximus ligula, sed vehicula lacus laoreet quis. Aliquam erat volutpat. Donec a convallis diam, nec condimentum felis. Cras orci ante, porta vitae tortor in, dictum egestas nibh. Etiam consequat dignissim iaculis. Nulla in suscipit augue. Proin imperdiet ligula sit amet ipsum malesuada, sit amet finibus enim varius. Donec semper a enim molestie vehicula. Aliquam erat volutpat.\n" +
//                "\n" +
//                "Mauris nunc eros, ultrices sed convallis fermentum, iaculis sed lacus. Aliquam placerat, sapien in tempus placerat, erat leo venenatis lorem, vitae feugiat leo orci sed nisl. Aenean eu euismod tortor, ac feugiat arcu. Suspendisse sed odio id nunc dictum tempus lobortis non dolor. Maecenas in orci facilisis, commodo ligula non, aliquet justo. Nulla lacus purus, iaculis sed placerat eu, sodales feugiat eros. Curabitur volutpat volutpat felis sed viverra. Ut faucibus tortor ac urna fermentum, eget pretium sem eleifend. Vestibulum nec suscipit ipsum. Pellentesque dictum eleifend mauris, et tincidunt est commodo nec.\n" +
//                "\n" +
//                "Quisque odio libero, tempor eget luctus vitae, porta pulvinar sapien. Vivamus vel aliquet tellus, quis tincidunt turpis. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Integer venenatis arcu imperdiet nisl placerat tincidunt. Donec in purus dictum, tristique ipsum a, dignissim est. Praesent suscipit felis quis nunc viverra, sed pellentesque massa pulvinar. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In quis dui sit amet ex aliquam pellentesque vel at libero. Praesent sed magna ac arcu fermentum fermentum. Vestibulum vestibulum nec odio id sollicitudin. Ut enim ipsum, porttitor in ex varius, varius dignissim ipsum.\n" +
//                "\n" +
//                "Curabitur ligula nisi, facilisis eget enim id, porttitor facilisis metus. Praesent eget maximus neque. Morbi congue orci non pulvinar feugiat. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Cras vehicula orci nec mauris posuere, quis scelerisque massa congue. Suspendisse hendrerit elit eu rutrum dignissim. Aenean elementum tellus vitae massa lobortis efficitur. Proin vel mauris eget mauris luctus laoreet sed vel est. Sed arcu odio, pellentesque sit amet lacinia et, elementum sit amet lectus. Proin vel facilisis erat, non pellentesque turpis. Phasellus ultricies tincidunt ullamcorper. Pellentesque dignissim hendrerit orci non tempus. Vivamus sollicitudin nisi in tortor auctor, eget pretium sem dapibus. Vestibulum egestas tellus id condimentum facilisis. Quisque ac luctus nulla.");
//        seller.addProduct(product1);
//        seller.addProduct(product2);
//        seller.addProduct(product3);
//        seller.addProduct(product4);
//        seller.addProduct(product5);
//        seller.addProduct(product6);
//        seller.addProduct(product7);
//        seller.addProduct(product8);
//        Product.addProduct(product1);
//        Product.addProduct(product2);
//        Product.addProduct(product3);
//        Product.addProduct(product4);
//        Product.addProduct(product5);
//        Product.addProduct(product6);
//        Product.addProduct(product7);
//        Product.addProduct(product8);
//
//    }


}