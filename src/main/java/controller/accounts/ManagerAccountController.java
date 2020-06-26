package controller.accounts;

import exceptions.AccountsException;
import model.Category;
import model.DiscountCode;
import model.Product;
import model.enumerations.PropertyType;
import model.enumerations.Status;
import model.requests.Request;
import model.users.Customer;
import model.users.Manager;
import model.users.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class ManagerAccountController extends AccountController{
    private static ManagerAccountController managerAccountController;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    private ManagerAccountController(){}

    public static ManagerAccountController getInstance(){
        if(managerAccountController == null)
            managerAccountController = new ManagerAccountController();

        return managerAccountController;
    }

    public void createManagerAccount(String username, String password, String firstName, String lastName,
                                     String email, String phoneNumber) {
        new Manager(username, password, firstName, lastName, email, phoneNumber);
    }

    public ArrayList<String> getAllUserNames(){
        return new ArrayList<>(User.getAllUsernames());
    }

    public User getUser(String username) throws AccountsException {
        User user = User.getUserByUsername(username);
        if(user == null)
            throw new AccountsException("User not found");
        return user;

    }

    public void deleteUser(String username) throws AccountsException {
        User user = User.getUserByUsername(username);
        if(user == null)
            throw new AccountsException("User not found.");
        User.deleteUser(user);
    }

    public ArrayList<String> getAllProducts(){
        return new ArrayList<>(Product.getAllProductIds());
    }

    public void removeProduct(String productID) throws AccountsException {
        Product product = Product.getProductById(productID);
        if(product == null)
            throw new AccountsException("Product not found.");
        Product.removeProduct(productID);
    }

    private void addCustomers(DiscountCode discountCode,ArrayList<String> customers) throws AccountsException {
        if (customers.size() == 0)
            throw new AccountsException("No user has been selected!");
        for (String customer : customers) {
            User user = User.getUserByUsername(customer);
            if (user == null)
                throw new AccountsException("User " + customer + " not found.");
            if(!(user instanceof Customer))
                throw new AccountsException("You can just add customers. " + "User " + customer + " is not a customer!");
            discountCode.addCostumer((Customer)user);
        }
    }

    public void createDiscount(String startDate, String endDate, int percentage, double maxDiscount,
                               int countForEachUser, ArrayList<String> listOfUsernames) throws AccountsException {
        Date start;
        Date end;
        try {
            start = DATE_FORMAT.parse(startDate);
            end = DATE_FORMAT.parse(endDate);
        }
        catch (Exception e){
            throw new AccountsException("Invalid date format");
        }
        if(DiscountCode.isValid(start, end)) {
            throw new AccountsException("Invalid date");
        }
        DiscountCode discountCode = new DiscountCode(start, end, percentage, maxDiscount, countForEachUser);
        addCustomers(discountCode, listOfUsernames);
    }

    public ArrayList<DiscountCode> getAllDiscountCodes(){
        return DiscountCode.getAllDiscountCodes();
    }

    public DiscountCode getDiscount(String code) throws AccountsException {
        DiscountCode discountCode = DiscountCode.getDiscountCodeByCode(code);
        if(discountCode == null)
            throw new AccountsException("Discount code not found.");
        return discountCode;
    }

    public void removeDiscount(String code) throws AccountsException{
        DiscountCode discountCode = DiscountCode.getDiscountCodeByCode(code);
        if (discountCode == null)
            throw new AccountsException("Discount code not found.");
        DiscountCode.removeDiscountCode(discountCode);
    }

    private void setNewStartDate(String newValue, DiscountCode discountCode) throws AccountsException {
        Date newStart;
        try {
            newStart = DATE_FORMAT.parse(newValue);
        }
        catch (Exception e){
            throw new AccountsException("Date format is not valid.");
        }
        if (!discountCode.isStartDateValid(newStart, discountCode.getEndDate()))
            throw new AccountsException("Invalid date.");
        discountCode.setStartDate(newStart);
    }

    private void setNewEndDate(String newValue, DiscountCode discountCode) throws AccountsException {
        Date newEnd;
        try {
            newEnd = DATE_FORMAT.parse(newValue);
        }
        catch (Exception e){
            throw new AccountsException("Date format is not valid.");
        }
        if (!discountCode.isEndDateValid(newEnd, discountCode.getStartDate()))
            throw new AccountsException("Invalid date.");
        discountCode.setEndDate(newEnd);
    }

    public void editDiscount(String code, HashMap<String, String> toEdit) throws AccountsException {
        DiscountCode discountCode = DiscountCode.getDiscountCodeByCode(code);
        if (discountCode == null)
            throw new AccountsException("Discount code not found.");
        for (String field : toEdit.keySet()) {
            String newValue = toEdit.get(field);switch (field){
                case "Percentage":
                    discountCode.setPercentage(Integer.parseInt(newValue));
                    break;
                case "Start date":
                    setNewStartDate(newValue, discountCode);
                    break;
                case "End date":
                    setNewEndDate(newValue, discountCode);
                case "Maximum amount":
                    discountCode.setMaxAmount(Double.parseDouble(newValue));
                    break;
                case "Count per user":
                    discountCode.setCountPerUser(Integer.parseInt(newValue));
                    break;
                default:
                    throw new AccountsException("Field not found.");
        }

        }
    }

    public ArrayList<Request> getALlRequests(){
        return Manager.getRequests();
    }

    public Request getRequest(String requestID) throws AccountsException {
        Request request = Manager.getRequestById(requestID);
        if (request == null)
            throw new AccountsException("Request not found.");
        return request;
    }

    public void acceptRequest(String requestID) throws AccountsException {
        Request request = Manager.getRequestById(requestID);
        if (request == null)
            throw new AccountsException("Request not found.");
        if (!request.getStatus().equalsIgnoreCase(Status.Confirmed.getStr())) {
            request.setStatus(Status.Confirmed);
            request.action();
        }
    }

    public void declineRequest(String requestID) throws AccountsException {
        Request request = Manager.getRequestById(requestID);
        if (request == null)
            throw new AccountsException("Request not found.");
        request.setStatus(Status.Declined);
    }

    public Set<String> getAllCategories(){
        return Category.getAllCategories().keySet();
    }

    private void handleProductsAfterEdit(String field, String newField, Category category) {
        for (String productID : category.getProductIDs()) {
            Product product = Product.getProductById(productID);
            assert product != null;
            product.resetExtraProperty(field, newField);
        }
    }

    public void editCategory(String categoryName, HashMap<String, String> toEdit) throws AccountsException {
        Category category = Category.getCategoryByName(categoryName);
        if (category == null)
            throw new AccountsException("Category not found.");
        for (String field : toEdit.keySet()) {
            String newField = toEdit.get(field);
            if (field.equalsIgnoreCase("name")) {
                if (Category.getCategoryByName(newField) != null)
                    throw new AccountsException("A category exists with this name.");
                category.resetName(newField);
                handleProductsAfterEdit(field, newField, category);
            } else if (!category.getExtraProperties().containsKey(field))
                throw new AccountsException("There is no field with this name.");
            else {
                PropertyType type = category.getExtraProperties().get(field);
                category.addProperty(newField, type);
                category.getExtraProperties().remove(field);
                handleProductsAfterEdit(field, newField, category);
            }
        }
    }

    public void createCategory(String categoryName, String parentCategory, HashMap<String, PropertyType> properties) throws AccountsException {
        if (Category.getCategoryByName(categoryName) != null)
            throw new AccountsException("Category exists with this name");
        Category parent = Category.getCategoryByName(parentCategory);
        if (parentCategory != null && parent == null)
            throw new AccountsException("Parent category not found.");

        Category category = new Category(categoryName, parentCategory);
        category.getExtraProperties().putAll(properties);
        Category.addCategory(category);
    }

    public void removeCategory(String categoryName) throws AccountsException {
        if (Category.getCategoryByName(categoryName) == null)
            throw new AccountsException("Category not found.");
        Category.removeCategory(Category.getCategoryByName(categoryName));
    }
}
