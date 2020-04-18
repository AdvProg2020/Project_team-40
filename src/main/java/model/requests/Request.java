package model.requests;

public abstract class Request {
    private String requestId;
    private boolean isAccepted;

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    private void generateId(){}

    public abstract void action();

    @Override
    public abstract String toString();
}
