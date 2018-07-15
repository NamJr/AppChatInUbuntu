package net.simplifiedlearning.firebaseauth;

public class Message {

    private String message;
    private String idSender;
    private String createdAt;

    public Message() {
    }

    public Message(String message, String idSender, String createdAt) {
        this.message = message;
        this.idSender = idSender;
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
