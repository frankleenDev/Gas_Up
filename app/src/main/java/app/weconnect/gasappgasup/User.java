package app.weconnect.gasappgasup;

public class User {

    String uid;
    String number;


    public User() {
    }

    public User(String uid, String number) {
        this.uid = uid;
        this.number = number;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
