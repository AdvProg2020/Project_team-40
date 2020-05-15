package Controller.Accounts;

import exceptions.AccountsException;
import model.Category;
import model.DiscountCode;
import model.Product;
import model.requests.Request;
import model.users.Customer;
import model.users.Manager;
import model.users.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public void removeProduct(String productID) throws AccountsException {
        Product product = Product.getProductById(productID);
        if(product == null)
            throw new AccountsException("Product not found.");
        Product.removeProduct(productID);
    }

    private void addCustomers(DiscountCode discountCode,ArrayList<String> customers) throws AccountsException {
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
        DiscountCode discountCode = new DiscountCode(start, end, percentage, maxDiscount, countForEachUser);
        if(discountCode.isExpired())
            throw new AccountsException("Invalid date");
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
        if (!discountCode.isStartDateValid(newStart))
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
        if (!discountCode.isEndDateValid(newEnd))
            throw new AccountsException("Invalid date.");
        discountCode.setEndDate(newEnd);
    }

    public void editDiscount(String code, String field, String newValue) throws AccountsException {
        DiscountCode discountCode = DiscountCode.getDiscountCodeByCode(code);
        if (discountCode == null)
            throw new AccountsException("Discount code not found.");
        switch (field){
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
                discountCode.setMaxAmount(Integer.parseInt(newValue));
                break;
            default:
                throw new AccountsException("Field not found.");

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
        request.setAccepted(true);
        request.action();
    }

    public void declineRequest(String requestID) throws AccountsException {
        Request request = Manager.getRequestById(requestID);
        if (request == null)
            throw new AccountsException("Request not found.");
        request.setAccepted(false);
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

    public void editCategory(String categoryName, String field, String newField) throws AccountsException {
        Category category = Category.getCategoryByName(categoryName);
        if (category == null)
            throw new AccountsException("Category not found.");
        if (field.equalsIgnoreCase("name")){
            if (Category.getCategoryByName(newField) != null)
                throw new AccountsException("A category exists with this name.");
            category.setName(newField);
            handleProductsAfterEdit(field, newField, category);
        }
        else if (!category.getExtraProperties().contains(field))
            throw new AccountsException("There is no field with this name.");
        else {
            int fieldIndex = category.getExtraProperties().indexOf(field);
            category.getExtraProperties().set(fieldIndex, newField);
            handleProductsAfterEdit(field, newField, category);
        }


    }

    public void createCategory(String categoryName, String parentCategory, ArrayList<String> properties) throws AccountsException {
        if (Category.getCategoryByName(categoryName) != null)
            throw new AccountsException("Category exists with this name");
        if (parentCategory != null && Category.getCategoryByName(parentCategory) == null)
            throw new AccountsException("Parent category not found.");
        Category category = new Category(categoryName, parentCategory);
        Category.addCategory(category);
        for (String property : properties) {
            category.addProperty(property);
        }

    }

    public void removeCategory(String categoryName) throws AccountsException {
        if (Category.getCategoryByName(categoryName) == null)
            throw new AccountsException("Category not found.");
        Category.removeCategory(Category.getCategoryByName(categoryName));
    }
}
