package model.requests;

import model.Product;
import model.enumerations.Status;

public class RemoveProduct extends Request{
    private String productId;

    public RemoveProduct(String productId) {
        super("Remove Product");
        this.productId = productId;
    }

    @Override
    public void action() {
        if(status == Status.Confirmed) {
            Product.removeProduct(productId);
        }
    }

    @Override
    public String toString() {
        return super.toString() + "Product: " + Product.getProductById(productId);
    }
}
