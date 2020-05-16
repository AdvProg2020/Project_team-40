package model.requests;

import model.Product;
import model.enumerations.SetUpStatus;
import model.enumerations.Status;

import java.util.ArrayList;
import java.util.HashMap;

public class EditProduct extends Request{
    private Product product;
    private String field;
    private String newField;
    private HashMap<String, Double> extraValueProperties;
    private HashMap<String, String> extraStringProperties;

    public EditProduct(String productId, String field, String newField, HashMap<String, Double> extraValueProperties,
                       HashMap<String, String> extraStringProperties) {
        //TODO: ADD THE EXTRA_PROPERTY THING....
        super("Edit Product");
        this.product = Product.getProductById(productId);
        this.field = field;
        this.newField = newField;
        this.extraStringProperties = extraStringProperties;
        this.extraValueProperties = extraValueProperties;
    }

    @Override
    public void action() {
        if(status == Status.Confirmed) {
            editProductAfterManagersAcceptance();
        }
    }

    public void editProductAfterManagersAcceptance(){
        if(field.equals("name")) {
            product.setName(newField);
        } else if(field.equalsIgnoreCase("company")) {
            product.setCompany(newField);
        } else if(field.equalsIgnoreCase("price")) {
            product.setPrice(Double.parseDouble(newField));
        } else if(field.equalsIgnoreCase("count")) {
            product.setCount(Integer.parseInt(newField));
        } else if(field.equalsIgnoreCase("category")) {
            resetProperties();
        } else if(field.equalsIgnoreCase("status")) {
            resetStatus();
        }
    }

    private void resetStatus() {
        if(newField.equalsIgnoreCase("creating")) {
            product.setStatus(SetUpStatus.Creating);
        } else if(newField.equalsIgnoreCase("editing")) {
            product.setStatus(SetUpStatus.Editing);
        } else if(newField.equalsIgnoreCase("confirmed")) {
            product.setStatus(SetUpStatus.Confirmed);
        }
    }

    private void resetProperties() {
        product.setCategory(newField);
        product.setExtraStringProperties(this.extraStringProperties);
        product.setExtraValueProperties(this.extraValueProperties);
    }

    @Override
    public String toString() {
        return super.toString() + "Edited product: " + product;
    }
}
