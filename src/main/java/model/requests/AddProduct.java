package model.requests;

import model.Product;

public class AddProduct extends Request{
    private Product product;

    public AddProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public void action() {

    }

    @Override
    public String toString() {
        return null;
    }
}
