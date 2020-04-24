package model.requests;

import model.Product;

public class RemoveProduct extends Request{
    private Product product;

    public RemoveProduct(Product product) {
        this.product = product;
    }

    @Override
    protected void generateId() {
        this.requestId = "rp";
    }

    @Override
    public void action() {
        if(isAccepted) {
            product.getSeller().deleteProduct(product);
            Product.removeProduct(product.getProductId());
        }
    }

    @Override
    public String toString() {
        return super.toString() + "Product: " + product;
    }
}
