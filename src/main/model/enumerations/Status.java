package model.enumerations;

public enum Status {
    Waiting("Waiting"), Confirmed("Confirmed"), Declined("Declined");
    private String str;
    Status(String str){
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
