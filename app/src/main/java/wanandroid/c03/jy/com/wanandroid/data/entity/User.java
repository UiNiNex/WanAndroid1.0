package wanandroid.c03.jy.com.wanandroid.data.entity;

import java.util.Arrays;

/*
 * created by taofu on 2018/11/27
 **/
public class User {


    private String username;
    private String id;

    private String collectIds[];

    private String email;
    private String icon;

    private String password;
    private String token;
    private int type;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getCollectIds() {
        return collectIds;
    }

    public void setCollectIds(String[] collectIds) {
        this.collectIds = collectIds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", id='" + id + '\'' +
                ", collectIds=" + Arrays.toString(collectIds) +
                ", email='" + email + '\'' +
                ", icon='" + icon + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", type=" + type +
                '}';
    }
}
