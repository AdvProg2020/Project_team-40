package model.requests;

public abstract class Request {
    protected String type;
    protected String requestId;
    protected boolean isAccepted;

    public Request(String type) {
        this.type = type;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public abstract void action();

    @Override
    public  String toString(){
        String status = "Declined";
        if (isAccepted)
            status = "Accepted";
        return "requestId : " + requestId + "\n"
                + "Status :" + status + "\n"
                + "Type : " + type + "\n";
    }
}
