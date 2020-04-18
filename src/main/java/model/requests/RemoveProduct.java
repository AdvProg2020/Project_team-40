package model.requests;

import model.Product;

public class RemoveProduct extends Request{
    private Product product;

    public RemoveProduct(Product product) {
        this.product = product;
    }

    @Override
    public void action() {

    }

    @Override
    public String toString() {
        return null;
    }
}
