package model.requests;

import model.Off;

public class AddOff extends Request{
    private Off off;

    public AddOff(Off off) {
        this.off = off;
    }

    public Off getOff() {
        return off;
    }

    @Override
    public void action() {

    }

    @Override
    public String toString() {
        return null;
    }
}
