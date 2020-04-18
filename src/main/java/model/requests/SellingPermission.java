package model.requests;

import model.users.Seller;

public class SellingPermission extends Request{
    private Seller seller;

    public SellingPermission(Seller seller) {
        this.seller = seller;
    }

    @Override
    public void action() {

    }

    @Override
    public String toString() {
        return null;
    }
}
