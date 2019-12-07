/*package edu.fsu.cs.mysql_test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Events  extends Fragment {

    public Events(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.event_list, container, false);
        return rootView;
    }
}*/

package edu.fsu.cs.mysql_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Events {

    private int Picture;
    private String Game;
    private String Quest;
    private String Requester;
    private String Time;
    private String Requestid;

    public Events(int picture, String game, String quest, String time, String requester, String request_id){
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