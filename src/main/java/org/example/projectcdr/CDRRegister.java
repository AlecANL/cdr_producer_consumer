package org.example.projectcdr;

public class CDRRegister {
    private String accountNumber;
    private String phone;
    private String timestamp;
    private String duration;

    public CDRRegister(String accountNumber, String phone, String timestamp, String duration) {
        this.accountNumber = accountNumber;
        this.phone = phone;
        this.timestamp = timestamp;
        this.duration = duration;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public String getDuration() {
        return this.duration;
    }
}
