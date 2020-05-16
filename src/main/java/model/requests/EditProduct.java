package model.requests;

import model.Product;
import model.enumerations.SetUpStatus;
import model.enumerations.Status;

public class EditProduct extends Request{
    private Product product;
    private String field;
    private String newField;

    public EditProduct(String productId, String field, String newField) {
        super("Edit Product");
        this.product = Product.getProductById(productId);
        this.field = field;
        this.newField = newField;
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
            product.setCategory(newField);
        } else if(field.equalsIgnoreCase("status")) {
            if(newField.equalsIgnoreCase("creating")) {
                product.setStatus(SetUpStatus.Creating);
            } else if(newField.equalsIgnoreCase("editing")) {
                product.setStatus(SetUpStatus.Editing);
            } else if(newField.equalsIgnoreCase("confirmed")) {
                product.setStatus(SetUpStatus.Confirmed);
            }
        }
    }

    @Override
    public String toString() {
        return super.toString() + "Edited product: " + product;
    }
}
