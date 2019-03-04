package net.broomes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="profiles")
public class Profile {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "avatar")
    private byte[] avatar;

    public Profile() {
    }

    public Profile(String username, byte[] avatar) {
        this.username = username;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}
