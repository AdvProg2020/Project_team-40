package model.requests;

import java.util.Map;

public class EditOff extends Request {
    private String oldOffId;
    private Map<String, String> toEdit;

    public EditOff(String oldOffId, Map<String, String> toEdit) {
        super("Edit Off");
        this.oldOffId = oldOffId;
        this.toEdit = toEdit;
    }

    @Override
    public void action() {
        //TODO
    }

    @Override
    public String toString() {
        return super.toString() + "Off: " + oldOffId +"\n"
                + "Changes: " + toEdit;
    }
}
