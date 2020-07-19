package server.model.requests;

import server.model.enumerations.Status;
import server.model.users.Seller;
import server.model.users.User;

public class SellingPermission extends Request{
    private static final long serialVersionUID = 2469735397956364303L;
    private String sellerUsername;
    private transient Seller seller;

    public SellingPermission(){}

    public SellingPermission(String sellerUsername) {
        super("Selling Permission");
        seller = (Seller) User.getUserByUsername(sellerUsername);
        this.sellerUsername = sellerUsername;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public Seller getSeller(){
        return seller;
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
