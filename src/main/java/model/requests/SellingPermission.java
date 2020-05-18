package model.requests;

import model.enumerations.Status;
import model.users.Seller;

public class SellingPermission extends Request{
    private String sellerUsername;

    public SellingPermission(String sellerUsername) {
        super("Selling Permission");
        this.sellerUsername = sellerUsername;
    }

    @Override
    public void action() {
        if(status == Status.Confirmed) {
            Seller seller = (Seller)Seller.getUserByUsername(sellerUsername);
            seller.setManagerPermission(true);
        }
    }

    @Override
    public String toString() {
        return super.toString() + " Seller: " + Seller.getUserByUsername(sellerUsername);
    }
}
