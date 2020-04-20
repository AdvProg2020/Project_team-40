package model.requests;

import model.Off;

public class AddOff extends Request{
    private Off off;

    public AddOff(Off off) {
        this.off = off;
    }

    @Override
    protected void generateId() {
        this.requestId = "af";
    }

    @Override
    public void action() {
        if(isAccepted){
            off.getSeller().deleteOff(off);
            Off.addOff(off);
        }
    }

    @Override
    public String toString() {
        return super.toString() + "Off: " + off;
    }
}
