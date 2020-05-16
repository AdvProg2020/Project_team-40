package model.requests;

import model.Category;
import model.Product;
import model.enumerations.Status;

public class AddProduct extends Request{
    private Product product;

    public AddProduct(Product product) {
        super("Add Product");
        this.product = product;
    }

    @Override
    public void action() {
        if(status == Status.Confirmed) {
            Category.getCategoryByName(product.getCategory()).addProduct(product.getProductId());
            product.getSeller().addProduct(product);
            Product.addProduct(product);
        }
    }

    @Override
    public String toString() {
        return super.toString() + "Added product: " + product;
    }
}
