package net.simplifiedlearning.firebaseauth.chat_people;

import java.io.Serializable;

public class ChatPeople implements Serializable {

    private String linkAvatar;
    private String nickName;
    private String email;

    public ChatPeople() {
    }

    public ChatPeople(String linkAvatar, String nickName, String email) {
        this.linkAvatar = linkAvatar;
        this.nickName = nickName;
        this.email = email;
    }

    public String getLinkAvatar() {
        return linkAvatar;
    }

    public void setLinkAvatar(String linkAvatar) {
        this.linkAvatar = linkAvatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
