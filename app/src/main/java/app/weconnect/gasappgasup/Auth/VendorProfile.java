package app.weconnect.gasappgasup.Auth;

public class VendorProfile {

    String agents_name;
    String branch;
    String company;
    String mobile_number;
    String email;
    String userID;

    public VendorProfile() {
    }

    public VendorProfile(String agents_name, String branch, String company, String mobile_number, String email, String userID) {
        this.agents_name = agents_name;
        this.branch = branch;
        this.company = company;
        this.mobile_number = mobile_number;
        this.email = email;
        this.userID = userID;
    }

    public String getAgents_name() {
        return agents_name;
    }

    public void setAgents_name(String agents_name) {
        this.agents_name = agents_name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
