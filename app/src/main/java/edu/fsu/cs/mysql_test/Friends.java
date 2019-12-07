package edu.fsu.cs.mysql_test;

public class Friends {

    private String Name;
    private String Username;
    private int Photo;
    private String Userid;

    public Friends(String name, String username, int photo, String userid){

        Name = name;
        Username = username;
        Photo = photo;
        Userid = userid;

    }

    public String getName() {
        return Name;
    }

    public String getUsername() {
        return Username;
    }

    public int getPhoto() {
        return Photo;
    }

    public String getUserid() { return Userid; }

    /*public void setName(String name) {
        Name = name;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setPhoto(int photo) {
        Photo = photo;
    }*/
}
