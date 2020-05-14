package model.requests;

import model.Utility;
public abstract class Request {
    protected String type;
    protected String requestId;
    protected boolean isAccepted;

    public Request(String type) {
        this.type = type;
        requestId = Utility.generateId();
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public abstract void action();

    public String getRequestId() {
        return requestId;
    }

    private String getStatus(){
        if (isAccepted)
            return "Accepted";
        return "Waiting";
    }

    @Override
    public String toString() {
        return "Request ID :" + requestId  + "\n"
                + "Type : " + type +"\n"
                + "Permission status: " + getStatus() + "\n";
    }
}
