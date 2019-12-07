package edu.fsu.cs.mysql_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Invite {

    private int Picture;
    private String Game;
    private String Quest;
    private String Requester;
    private String Time;
    private String Requestid;

    public Invite(int picture, String game, String quest, String time, String requester, String request_id){
        Picture = picture;
        Game = game;
        Quest = quest;
        Requester = requester;
        Time = time;
        Requestid = request_id;
    }

    public int getPicture(){
        return Picture;
    }

    public String getGame(){
        return Game;
    }

    public String getQuest(){
        return Quest;
    }

    public String getRequester(){
        return Requester;
    }

    public String getTime(){
        return Time;
    }

    public String getRequestid() { return Requestid; }
}