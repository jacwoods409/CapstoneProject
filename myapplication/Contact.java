package com.example.myapplication;


public class Contact {

    private String name, address, contactID, email, phoneNumber;

    public Contact(String contactID) {
        name = "Unknown";
        address = "Unknown";
        phoneNumber = "0000000000";
        email = "0000000000000";

        if (!(setContactID(contactID))) {
            throw new IllegalArgumentException("The Contact ID entered in for this contact does not meet requirements");
        }
    }  // if contact ID length is invalid per requirement

    public Contact(String contactID, String name, String phoneNumber, String address, String email) {
        Boolean[] state = new Boolean[5];
        state[0] = setContactID(contactID);
        state[1] = setPhoneNumber(phoneNumber);
        state[2] = setName(name);
        state[3] = setAddress(address);
        state[4] = setEmail(email);

        for (Boolean boo : state) {  // if one of the contact set method's variable is invalid, an illegalArgumentException will be thrown
            if (boo == false) {
                throw new IllegalArgumentException("A variable entered in for this contact does not meet requirements");
            }
        }

    }

    private boolean setContactID(String contactID) {    // method is only accessible through this class
        if (contactID != null && contactID.length() < 5) {
            this.contactID = contactID;
            return true;
        } else {
            return false;
        }

    }

    public String getContactID() {
        String c = this.contactID;  // gets contactID
        return c;
    }

    public boolean setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && phoneNumber.length() == 10&& !phoneNumber.contains("=") &&!phoneNumber.contains(".")  &&!phoneNumber.contains("-")) { // sets phone number and checks length requirements
            this.phoneNumber = phoneNumber;
            return true;

        } else {
            return false;
        }
    }

    public String getPhoneNumber() { // gets phone number
        String pN = this.phoneNumber;
        return pN;
    }

    public boolean setName(String name) { // sets first name and checks length requirements
        if (name != null && name.length() <= 10 && !name.contains("=") && !name.contains("SELECT")) {
            this.name = name;
            return true;
        } else
            return false;
    }

    public String getName() { // gets first name variable
        String fN = this.name;
        return fN;
    }


    public boolean setEmail(String email) {  // sets address
        if ((email != null && email.length() <= 30)&&(!email.contains("=")&& !email.contains("SELECT"))) { // equal sign is an indicator of an sql injection attack
            if(email.contains("@")&&(email.contains(".net") ||email.contains(".com")||email.contains(".gov")||email.contains(".edu")))
            {
                this.email = email;
                return true;
            }
            else
            {
                return false;
            }

        } else
            return false;
    }

    public String getEmail() {
        String ad = this.email; // gets address variable
        return ad;
    }



    public boolean setAddress(String address) {  // sets address
        if (address != null && address.length() <= 30 && !address.contains("=") && !address.contains("SELECT")) {
            this.address = address;
            return true;
        } else
            return false;
    }

    public String getAddress() {
        String ad = this.address; // gets address variable
        return ad;
    }

    @Override
    public String toString() {  // overrides toString method for display contact method
        String contact = "Contact ID: " + this.contactID + "\n"
                + "Name: " + this.name + "\n"
                + "Phone Number: " + "(" + this.phoneNumber.substring(0, 3) + ")"
                + this.phoneNumber.substring(3, 6) + "-" + this.phoneNumber.substring(6, 10) + "\n"
                + "Address: " + this.address + "\n"
                + "Email: " + this.email + "\n";
        return contact;
    }

    public String displayContact() {  // uses toString method to display contact
        return this.toString();
    }
}



