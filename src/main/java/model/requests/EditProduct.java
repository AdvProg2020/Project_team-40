package model.requests;

import model.Product;

import java.util.Map;

public class EditProduct extends Request{
    private Map<String, String> toEdit;
    private String oldProductId;

    public EditProduct(Map<String, String> toEdit, String oldProductId) {
        super("Edit Product");
        this.toEdit = toEdit;
        this.oldProductId = oldProductId;
    }

    @Override
    public void action() {
       //TODO
    }

    @Override
    public String toString() {
        return super.toString() + "Product: " + oldProductId +"\n"
                + "Changes: " + toEdit;
    }
}
