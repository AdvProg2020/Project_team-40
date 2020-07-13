package server.model.requests;

import server.model.Product;
import server.model.enumerations.Status;

public class RemoveProduct extends Request{
    private static final long serialVersionUID = 4475874827989857321L;
    private String productId;

    public RemoveProduct(){}

    public RemoveProduct(String productId) {
        super("Remove Product");
        this.productId = productId;
    }

    public Product getProduct() {
        return Product.getProductById(productId);
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
