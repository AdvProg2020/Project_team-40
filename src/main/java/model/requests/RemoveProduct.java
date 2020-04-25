package model.requests;

import model.Product;

public class RemoveProduct extends Request{
    private Product product;

    public RemoveProduct(Product product) {
        super("Remove Product");
        this.product = product;
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
