package server.model.requests;

import exceptions.DataException;
import server.model.Utility;
import server.model.enumerations.Status;
import server.model.users.Manager;

import java.io.*;

public abstract class Request implements Serializable {
    private static final long serialVersionUID = -4762922681383129456L;
    protected String type;
    protected String requestId;
    protected Status status;

    public Request(){}

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

    public String getStatus(){
        return status.getStr();
    }

    public String getType(){
        return type;
    }

    @Override
    public String toString() {
        return "Request ID :" + requestId  + "\n"
                + "Type : " + type +"\n"
                + "Permission status: " + getStatus() + "\n";
    }

    public static void loadData() throws DataException {
        String directoryPath = "src/main/resources/requests/";
        File directory = new File(directoryPath);
        String[] pathNames = directory.list();
        if (pathNames == null)
            return;

        for (String path: pathNames) {
            try {
                FileInputStream file = new FileInputStream(directoryPath + path);
                ObjectInputStream inputStream = new ObjectInputStream(file);
                Request request = (Request) inputStream.readObject();

                if (request instanceof AddProduct)
                    ((AddProduct) request).getProduct().makeNewArrayList();

                Manager.getRequests().add(request);
                file.close();
                inputStream.close();
                new File(directoryPath + path).delete();
            } catch (Exception e) {
                throw new DataException("Loading requests failed.");
            }
        }
    }

    public static void saveData() throws DataException {
        String path = "src/main/resources/requests/";
        File directory = new File(path);
        if (!directory.exists())
            if (!directory.mkdir())
                throw new DataException("Saving requests failed.");
        for (Request request: Manager.getRequests()) {
            try {
                FileOutputStream file = new FileOutputStream(path + request.requestId);
                ObjectOutputStream outputStream = new ObjectOutputStream(file);
                outputStream.writeObject(request);
                file.close();
                outputStream.close();

            } catch (Exception e) {
                throw new DataException("Saving requests failed.");
            }
        }
    }
}
