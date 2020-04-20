package model.requests;

import model.users.Seller;

public class SellingPermission extends Request{
    private Seller seller;

    public SellingPermission(Seller seller) {
        this.seller = seller;
    }

    @Override
    protected void generateId() {
        this.requestId = "sp";
    }

    @Override
    public void action() {
        if(isAccepted)
            seller.setManagerPermission(true);
    }

    @Override
    public String toString() {
        return super.toString() + " Seller: " + seller;
    }
}
