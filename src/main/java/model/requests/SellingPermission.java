package model.requests;

import model.enumerations.Status;
import model.users.Seller;

public class SellingPermission extends Request{
    private Seller seller;

    public SellingPermission(Seller seller) {
        super("Selling Permission");
        this.seller = seller;
    }

    @Override
    public void action() {
        if(status == Status.Confirmed)
            seller.setManagerPermission(true);
    }

    @Override
    public String toString() {
        return super.toString() + " Seller: " + seller;
    }
}
