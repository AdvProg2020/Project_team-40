package model.requests;

import model.enumerations.Status;
import model.users.Seller;

public class SellingPermission extends Request{
    private static final long serialVersionUID = 2469735397956364303L;
    private String sellerUsername;

    public SellingPermission(String sellerUsername) {
        super("Selling Permission");
        this.sellerUsername = sellerUsername;
    }

    public String getSellerUsername() {
        return sellerUsername;
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
