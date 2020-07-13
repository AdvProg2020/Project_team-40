package server.model.requests;

import server.model.Off;
import server.model.enumerations.Status;

public class AddOff extends Request{
    private static final long serialVersionUID = 6700410384833798993L;
    private Off off;

    public AddOff(){}

    public AddOff(Off off) {
        super("Add Off");
        this.off = off;
    }

    public Off getOff() {
        return off;
    }

    @Override
    public void action() {
        if(status.equals(Status.Confirmed)) {
            Off.addOff(off);
            off.getSeller().addOff(off);
        }
    }

    @Override
    public String toString() {
        return super.toString() + "Off: " + off;
    }
}
