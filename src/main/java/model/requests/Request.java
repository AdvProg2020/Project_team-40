package model.requests;

public abstract class Request {
    protected String requestId;
    protected boolean isAccepted;

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    protected abstract void generateId();

    public abstract void action();

    @Override
    public  String toString(){
        String status = "Declined";
        if (isAccepted)
            status = "Accepted";
        return "requestId : " + requestId + "\n"
                + "Status :" + status + "\n";
    }
}
