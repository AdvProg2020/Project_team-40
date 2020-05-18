package model.requests;

import model.Off;
import model.enumerations.Status;

public class AddOff extends Request{
    private Off off;

    public AddOff(Off off) {
        super("Add Off");
        this.off = off;
    }

    @Override
    public void action() {
        if(status.equals(Status.Confirmed)) {
            off.getSeller().addOff(off);
            Off.addOff(off);
        }
    }

    @Override
    public String toString() {
        return super.toString() + "Off: " + off;
    }
}
