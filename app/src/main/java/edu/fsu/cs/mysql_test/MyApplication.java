package edu.fsu.cs.mysql_test;

import android.app.Application;

public class MyApplication extends Application {
    private String userid;
    private String game;
    private String quest;
    private String[] friends;
    private String[] events;
    static MyApplication myAppInstance;
    private String friendid;
    private String requestid;
    private String[] invite;

    public MyApplication() {
        myAppInstance = this;
    }

    public static MyApplication getInstance(){
        return myAppInstance;
    }


    /**
     * Functions to retrieve and set global variables for quest, game, and userID
     * @return
     */

    public String[] getFriends(){ return friends;}

    public void setFriends(String[] temp, int count){
        int i = 0;
        friends = new String[count+1];
        for(i = 0; i < count; i++ )
            this.friends[i] = temp[i];
    }

    public String[] getEvents(){ return events;}

    public void setEvents(String[] temp, int count){
        int i = 0;
        events = new String[count+1];
        for(i = 0; i < count; i++ )
            this.events[i] = temp[i];
    }

    public String getQuest() {
        return quest;
    }

    public void setQuest(String quest) {
        this.quest = quest;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    public String getFriendid() {
        return friendid;
    }

    public void setFriendid(String friendid) {
        this.friendid = friendid;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getFriendUsername() {
        return requestid;
    }

    public void setFriendUsername(String requestid) {
        this.requestid = requestid;
    }


    public String[] getInvite(){ return invite;}

    public void setInvite(String[] temp, int count){
        int i = 0;
        invite = new String[count+1];
        for(i = 0; i < count; i++ )
            this.invite[i] = temp[i];
    }





}
