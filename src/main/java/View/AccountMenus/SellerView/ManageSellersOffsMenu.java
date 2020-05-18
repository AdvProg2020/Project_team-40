package View.AccountMenus.SellerView;

import Controller.Accounts.SellerAccountController;
import View.ConsoleCommand;
import View.Menu;
import exceptions.AccountsException;
import model.Off;
import model.Product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class ManageSellersOffsMenu extends Menu {
    SellerAccountController sellerAccountController;

    public ManageSellersOffsMenu(Menu parentMenu) {
        super("Manage Seller's Offs Menu", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getViewOffs());
        submenus.put(2, getViewOff());
        submenus.put(3, getAddOff());
        submenus.put(4, getEditOff());
        setSubMenus(submenus);
        sellerAccountController = SellerAccountController.getInstance();
    }

    private Menu getViewOffs() {
        return new Menu("List of Offs", this) {
            @Override
            public void show() {
                System.out.println("Offs:");
                HashMap<String, Off> offs = sellerAccountController.getAllOffs();
                int offNumber = 1;
                for(Off off: offs.values()) {
                    System.out.println(offNumber + ". " + off.getId());
                }
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    private Menu getViewOff() {
        return new Menu("Off Information", this) {
            ArrayList<Off> offs;

            @Override
            public void show() {
                offs = showAndGetListOfOffs();
            }

            @Override
            public void execute() {
                int chosenOffNumber = getNumberOfNextMenu(offs.size());
                System.out.println(offs.get(chosenOffNumber - 1));
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    private Menu getEditOff(){
        return new Menu("Edit Off", this) {ArrayList<Off> offs;
            @Override
            public void show() {
                offs = showAndGetListOfOffs();
            }

            @Override
            public void execute() {
                int chosenOffNumber = getNumberOfNextMenu(offs.size());
                Off off = offs.get(chosenOffNumber - 1);
                System.out.println(off);
                try {
                    editOff(off);
                } catch (AccountsException e) {
                    System.out.println(e.getMessage());
                }
                parentMenu.show();
                parentMenu.execute();
            }

            private void editOff(Off off) throws AccountsException {
                printOptionsForEditing();
                int chosenFieldNumber = getNumberOfNextMenu(4);
                String field;
                String newInformation;
                if(chosenFieldNumber == 1) {
                    field = "discount percentage";
                    newInformation = getNewDiscountPercentage();
                } else if(chosenFieldNumber == 2) {
                    field = "start date";
                    newInformation = getNewDate(true, off);
                } else if(chosenFieldNumber == 3) {
                    field = "end date";
                    newInformation = getNewDate(false, off);
                } else {
                    field = "status";
                    newInformation = getNewStatus();
                }
                sellerAccountController.editOff(off.getId(), field, newInformation);
            }

            private void printOptionsForEditing() {
                System.out.println("Choose a field:\n" +
                        "1. discount percentage\n" +
                        "2. start date\n" +
                        "3. end date\n" +
                        "4. status");
            }

            private String getNewDate(boolean isStartDate, Off off) {
                //TODO: TEST THIS
                String errorMessage = "Enter a valid date in this format:\n" +
                        "dd/MM/yy HH:mm:ss\n" +
                        "For example: 12/01/10 9:55:34";
                String date = null;
                try {
                    if(isStartDate) {
                       date = getStartDate(errorMessage, off);
                    } else {
                        date = getEndDate(errorMessage, off);
                    }
                } catch (ParseException e) {
                    System.out.println(e.getMessage());
                }
                return date;
            }

            private String getStartDate(String errorMessage, Off off) throws ParseException {
                String dateInString = getValidInput(ConsoleCommand.DATE, errorMessage);
                Date date = new SimpleDateFormat("dd/MM/yy HH:mm:ss").
                        parse(dateInString);
                while(new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse(off.getEndDate())
                        .compareTo(date) <= 0 || new Date().compareTo(date) >= 0) {
                    System.out.println("Invalid date, this date is either after expiration or before now.");
                    dateInString = getValidInput(ConsoleCommand.DATE, errorMessage);
                    date = new SimpleDateFormat().parse(dateInString);
                }
                return dateInString;
            }

            private String getEndDate(String errorMessage, Off off) throws ParseException {
                //TODO:HANDLE THE CASE THAT USER ENTERS 25 AS AN HOUR
                String dateInString = getValidInput(ConsoleCommand.DATE, errorMessage);
                Date date = new SimpleDateFormat("dd/MM/yy HH:mm:ss").
                        parse(dateInString);
                while(new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse(off.getStartDate())
                        .compareTo(date) >= 0 || new Date().compareTo(date) >= 0) {
                    System.out.println("Invalid date, this date is either after expiration or before now.");
                    dateInString = getValidInput(ConsoleCommand.DATE, errorMessage);
                    date = new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse(dateInString);
                }
                return dateInString;
            }

            private String getNewStatus() {
                System.out.println("Enter the new Status:\n" +
                        "1. Creating\n" +
                        "2. Editing\n" +
                        "3. confirmed");
                int status = getNumberOfNextMenu(3);
                if(status == 1) {
                    return "creating";
                } else if(status == 2) {
                    return"editing";
                } else {
                    return "confirmed";
                }
            }
        };
    }

    //TODO:TEST IT
    private Menu getAddOff(){
        return new Menu("Add Off", this) {
            ArrayList<String> productsIds;
            String errorMessage = "Enter a valid date in this format:\n" +
                    "dd/MM/yy HH:mm:ss\n" +
                    "For example: 12/01/10 09:55:34";

            @Override
            public void show() {
                productsIds = sellerAccountController.getSellerProductIDs();
                System.out.println("your Products:");
                int productNumber = 1;
                for(String productId: productsIds) {
                    try {
                        System.out.println(productNumber + ". " +
                                        sellerAccountController.getProductDetails(productId).getName() +
                                "\n" + productId);
                    } catch (AccountsException e) {
                        System.out.println(e.getMessage());
                    }
                    productNumber++;
                }
                System.out.println("Enter the number of the products you want to include in your off," +
                        " Enter end to finish:");
            }

            @Override
            public void execute() {
                ArrayList<String> productsInOff = getProductIds();
                try {
                    String startDate = getStartDate();
                    String endDate = getEndDate(startDate);
                    double percentage = Double.parseDouble(getNewDiscountPercentage());
                    sellerAccountController.addOffToSeller(productsIds, startDate, endDate, percentage);
                } catch (ParseException | AccountsException e) {
                    System.out.println(e.getMessage());
                }
                parentMenu.show();
                parentMenu.execute();
            }

            private ArrayList<String> getProductIds () {
                ArrayList<String> productsInOff = new ArrayList<>();
                String input = "";
                while(!(input.equalsIgnoreCase("end"))) {
                    input = scanner.nextLine();
                    if(ConsoleCommand.INTEGER.getStringMatcher(input.trim()).matches()) {
                        int chosenProductNumber = Integer.parseInt(input);
                        try {
                            productsInOff.add(sellerAccountController.
                                    getProductDetails(productsIds.get(chosenProductNumber - 1)).getProductId());
                        } catch (AccountsException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                return productsInOff;
            }

            private String getStartDate() throws ParseException {
                System.out.println("Enter start date in this format --> dd/MM/yy HH:mm:ss:");
                String dateInString = getValidInput(ConsoleCommand.DATE, errorMessage);
                Date date = new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse(dateInString);
                while(new Date().compareTo(date) >= 0) {
                    System.out.println("Invalid date, this date is before current date.");
                    dateInString = getValidInput(ConsoleCommand.DATE, errorMessage);
                    date = new SimpleDateFormat().parse(dateInString);
                }
                return dateInString;
            }

            private String getEndDate(String startDate) throws ParseException {
                System.out.println("Enter end date in this format --> dd/MM/yy HH:mm:ss:");
                String dateInString = getValidInput(ConsoleCommand.DATE, errorMessage);
                Date date = new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse(dateInString);
                while(new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse(startDate).compareTo(date) >= 0 ||
                        new Date().compareTo(date) >= 0) {
                    System.out.println("Invalid date, this date is either after expiration or before now.");
                    dateInString = getValidInput(ConsoleCommand.DATE, errorMessage);
                    date = new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse(dateInString);
                }
                return dateInString;
            }
        };
    }

    private ArrayList<Off> showAndGetListOfOffs() {
        ArrayList<Off> offs = new ArrayList<>(sellerAccountController.getAllOffs().values());
        int offNumber = 1;
        for(Off off: offs) {
            System.out.println(offNumber + ". " + off.getId());
            offNumber++;
        }
        System.out.println("Choose one of the offs.");
        return offs;
    }

    private String getNewDiscountPercentage() {
        System.out.println("Enter the percentage(A valid number between 0 and 100): ");
        double discountPercentage = Double.parseDouble(getValidInput(ConsoleCommand.DOUBLE,
                "INVALID input.Enter a valid number."));
        while(discountPercentage > 100 || discountPercentage < 0) {
            System.out.println("INVALID input.Enter a valid number between 0 and 100.");
            discountPercentage = Double.parseDouble(getValidInput(ConsoleCommand.DOUBLE,
                    "INVALID input. Enter a valid number."));
        }
        return Double.toString(discountPercentage);
    }
}
