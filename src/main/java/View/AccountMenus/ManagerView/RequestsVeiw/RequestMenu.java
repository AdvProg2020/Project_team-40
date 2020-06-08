package View.AccountMenus.ManagerView.RequestsVeiw;

import Controller.Accounts.ManagerAccountController;
import View.Menu;
import exceptions.AccountsException;
import model.requests.Request;

import java.util.HashMap;

public class RequestMenu extends Menu {
    ManagerAccountController managerAccountController;

    public RequestMenu(Menu parentMenu) {
        super("Manage Requests Menu", parentMenu);
        managerAccountController = ManagerAccountController.getInstance();
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getShowAllRequests());
        submenus.put(2, getRequestDetails());
        submenus.put(3, getAccept());
        submenus.put(4, getDecline());
        setSubMenus(submenus);
    }


    public Menu getShowAllRequests(){
        return new Menu("View all requests", this) {
            @Override
            public void show() {
                for (Request request : managerAccountController.getALlRequests()) {
                    System.out.println(request);
                    System.out.println("---------------------------------");
                }
                System.out.println("Enter anything to return");
            }

            @Override
            public void execute() {
                scanner.nextLine();
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }

    public Menu getRequestDetails() {
        return new Menu("Request details", this) {
            @Override
            public void show() {
                System.out.println("Enter a requestId or back to return: ");
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("Back")) {
                    this.parentMenu.show();
                    this.parentMenu.execute();
                } else {
                    try {
                        System.out.println(managerAccountController.getRequest(input));
                    } catch (AccountsException e) {
                        System.out.println(e.getMessage());
                    }
                }
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }

    public Menu getAccept(){
        return new Menu("Accept request", this) {
            @Override
            public void show() {
                System.out.println("Enter a requestId to accept or back to return: ");
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("Back")) {
                    this.parentMenu.show();
                    this.parentMenu.execute();
                } else {
                    try {
                        managerAccountController.acceptRequest(input);
                    } catch (AccountsException e) {
                        System.out.println(e.getMessage());
                    }
                }
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }

    public Menu getDecline(){
        return new Menu("Decline request", this) {
                @Override
                public void show() {
                    System.out.println("Enter a requestId to decline or back to return: ");
                }

                @Override
                public void execute() {
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("Back")) {
                        this.parentMenu.show();
                        this.parentMenu.execute();
                    } else {
                        try {
                            managerAccountController.declineRequest(input);
                        } catch (AccountsException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    this.parentMenu.show();
                    this.parentMenu.execute();
                }
            };

    }
}
