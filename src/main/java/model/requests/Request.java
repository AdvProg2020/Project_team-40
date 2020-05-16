package model.requests;

import model.Utility;
import model.enumerations.Status;

public abstract class Request {
    protected String type;
    protected String requestId;
    protected Status status;

    public Request(String type) {
        this.type = type;
        this.status = Status.Waiting;
        requestId = Utility.generateId();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public abstract void action();

    public String getRequestId() {
        return requestId;
    }

    private String getStatus(){
        return status.getStr();
    }

    @Override
    public String toString() {
        return "Request ID :" + requestId  + "\n"
                + "Type : " + type +"\n"
                + "Permission status: " + getStatus() + "\n";
    }
}
