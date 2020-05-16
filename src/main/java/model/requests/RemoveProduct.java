package model.requests;

import model.Product;
import model.enumerations.Status;

public class RemoveProduct extends Request{
    private Product product;

    public RemoveProduct(Product product) {
        super("Remove Product");
        this.product = product;
    }

    @Override
    public void action() {
        if(status == Status.Confirmed) {
            Product.removeProduct(product.getProductId());
        }
    }

    @Override
    public String toString() {
        return super.toString() + "Product: " + product;
    }
}
