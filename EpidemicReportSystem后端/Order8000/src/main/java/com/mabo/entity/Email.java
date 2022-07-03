package com.mabo.entity;

public class Email {
    public String address;
    public String subject;
    public String content;
    public Email() {
    }

    public Email(String address, String subject, String content) {
        this.address = address;
        this.subject = subject;
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
