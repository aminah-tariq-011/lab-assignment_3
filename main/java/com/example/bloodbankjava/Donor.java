package com.example.bloodbankjava;
import java.time.LocalDate;
public class Donor extends User {
    private int age;
    private String gender;
    private String bloodType;
    private String contact;
    private String lastDonationDate;

    public Donor(int id, String name, String role, String email, String password, int age, String gender, String bloodType, String contact, String lastDonationDate) {
        super(id, name, role, email, password);
        this.age = age;
        this.gender = gender;
        this.bloodType = bloodType;
        this.contact = contact;
        this.lastDonationDate = lastDonationDate;
    }

    public Donor(String part, int i, String part1, String part2, String part3, String part4) {
        super(i, part, part1, part2, part3);
        this.lastDonationDate= part4;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getContact() {
        return contact;
    }

    public String getLastDonationDate() {
        return lastDonationDate;
    }

    public void setLastDonationDate(String lastDonationDate) {
        this.lastDonationDate = lastDonationDate;
    }

    public void donateBlood(String donationDate) {
        setLastDonationDate(donationDate);
        System.out.println("Blood donation successful! Last donation date updated.");
    }
    @Override
    public String toString() {
        return super.toString() + "," + age + "," + gender + "," + bloodType + "," + contact + "," + lastDonationDate;
    }
}
