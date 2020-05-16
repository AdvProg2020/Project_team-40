package model.requests;


import model.Off;
import model.enumerations.SetUpStatus;
import model.enumerations.Status;

public class EditOff extends Request {
    private Off off;
    private String field;
    private String newField;

    public EditOff(String offID, String field, String newField) {
        super("Edit Off");
        this.off = Off.getOffByID(offID);
        this.field = field;
        this.newField = newField;
    }

    @Override
    public void action() {
        if(status == Status.Confirmed) {
            editOffAfterManagersAcceptance();
        }
    }

    public void editOffAfterManagersAcceptance() {
        if(field.equals("discount percentage")) {
            off.setDiscountPercentage(Double.parseDouble(newField));
        } else if(field.equals("start date")) {
            off.setStartDate(newField);
        } else if(field.equals("end date")) {
            off.setEndDate(newField);
        } else if(field.equals("status")) {
            if(newField.equals("creating")) {
                off.setStatus(SetUpStatus.Creating);
            } else if(newField.equals("editing")) {
                off.setStatus(SetUpStatus.Editing);
            } else if(newField.equals("confirmed")) {
                off.setStatus(SetUpStatus.Confirmed);
            }
        }
    }

    @Override
    public String toString() {
        return super.toString() + "Edited Off: " + off + "\n" +
                "Edited field: " + field + "\n" +
                "Field changed to:" + newField;
    }
}
