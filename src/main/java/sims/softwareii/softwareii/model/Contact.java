package sims.softwareii.softwareii.model;

public class Contact {
    private int id;
    private String contactName;
    private String email;

    public Contact(int id, String contactName, String email) {
        this.id = id;
        this.contactName = contactName;
        this.email = email;
    }

    // NOTE:  No argument constructor in case needed
    public Contact(){

    }

    public int getId() {
        return id;
    }

    public String getContactName() {
        return contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
