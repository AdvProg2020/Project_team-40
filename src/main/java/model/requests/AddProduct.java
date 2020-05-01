package model.requests;

import model.Product;

public class AddProduct extends Request{
    private Product product;

    public AddProduct(Product product) {
        super("Add Product");
        this.product = product;
    }

    @Override
    public void action() {
        if(isAccepted) {
            product.getSeller().addProduct(product);
            Product.addProduct(product);
        }
    }

    @Override
    public String toString() {
        return super.toString() + "Added product: " + product;
    }
}
