package model.requests;

import model.Product;
import model.enumerations.SetUpStatus;
import model.enumerations.Status;
import java.util.HashMap;

public class EditProduct extends Request{
    private static final long serialVersionUID = 2760300356766841575L;
    private String productId;
    private String field;
    private String newField;
    private HashMap<String, Double> extraValueProperties;
    private HashMap<String, String> extraStringProperties;

    public EditProduct(String productId, String field, String newField, HashMap<String, Double> extraValueProperties,
                       HashMap<String, String> extraStringProperties) {
        super("Edit Product");
        this.productId = productId;
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
        Product product = Product.getProductById(productId);
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
        Product product = Product.getProductById(productId);
        if(newField.equalsIgnoreCase("creating")) {
            product.setStatus(SetUpStatus.Creating);
        } else if(newField.equalsIgnoreCase("editing")) {
            product.setStatus(SetUpStatus.Editing);
        } else if(newField.equalsIgnoreCase("confirmed")) {
            product.setStatus(SetUpStatus.Confirmed);
        }
    }

    private void resetProperties() {
        Product product = Product.getProductById(productId);
        product.setCategory(newField);
        product.setExtraStringProperties(this.extraStringProperties);
        product.setExtraValueProperties(this.extraValueProperties);
    }
    private StringBuilder getPropertiesToEdit(){
        StringBuilder propertiesToEdit = new StringBuilder();
        String line = "==============================\n";
        propertiesToEdit.append(line).append("Field: ").append(field).append('\n').append("New field: ").append(newField).append('\n').append(line);
        return propertiesToEdit;
    }

    public Product getProduct() {
        return Product.getProductById(productId);
    }

    public String getField() {
        return field;
    }

    public String getNewField() {
        return newField;
    }

    @Override
    public String toString() {
        return super.toString() + "Edited product: " + Product.getProductById(productId) + '\n' + getPropertiesToEdit();
    }
}
