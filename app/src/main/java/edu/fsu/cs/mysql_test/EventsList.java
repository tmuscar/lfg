package edu.fsu.cs.mysql_test;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class EventsList extends Fragment{

    private RecyclerView recyclerView;
    private List<Events> list;

    public EventsList(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.event_recycleview);
        EventsAdapter friendAdapter = new EventsAdapter(getContext(),list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(friendAdapter);
        return view;
    }

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        list = new ArrayList<>();
        int Logos;
        /*list.add(new Events(R.drawable.lol_logo, "League of Legends", "Ranked Duo", "2018-03-22 ", "Thomas","1"));
        list.add(new Events(R.drawable.bo4_logo, "COD BO4", "TDM", "2018-10-2", "Fabio", "4"));
        list.add(new Events(R.drawable.destiny_logo, "Destiny 2", "Crucible", "2018-08-16", "Corn Boi", "2"));
        list.add(new Events(R.drawable.logo_ow, "OVERWATCH", "Anything", "2018-07-30", "Rock boi", "5"));
        list.add(new Events(R.drawable.chess_logo, "Chess", "Blitz", "2018-12-25", "Georgo", "1"));
        list.add(new Events(R.drawable.pubg_logo, "PUBG", "Duo", "2018-01-7", "Pizza", "3"));
    */
        MyApplication g = MyApplication.getInstance();
        String[] data = g.getEvents();
        //int size = (data.length/3);
        // String[] name = new String[size];
        // String[] game = new String[size];

        //ArrayAdapter<String[]> adapter = new ArrayAdapter<String[]>(this,android.R.layout.simple_list_item_1,data);

        int i = 0;
        for (i = 0; i < data.length-5; i = i+5){

            switch (data[i])
            {
                case "Fortnite":
                    Logos = R.drawable.fortnite_logo;
                    break;
                case "Minecraft":
                    Logos = R.drawable.minecraft_logo;
                    break;
                case "LoL":
                    Logos = R.drawable.lol_logo;
                    break;
                case "Black Ops 4":
                    Logos = R.drawable.bo4_logo;
                    break;
                case "Destiny":
                    Logos = R.drawable.destiny_logo;
                    break;
                case "Overwatch":
                    Logos = R.drawable.logo_ow;
                    break;
                case "Chess":
                    Logos = R.drawable.chess_logo;
                    break;
                case "PUBG":
                    Logos = R.drawable.pubg_logo;
                    break;
                default:
                    Logos = R.drawable.user;

            }
            list.add(new Events(Logos,data[i],data[i+1],data[i+2],data[i+3],data[i+4]));
        }


        //EventsAdapter adapter = new EventsAdapter(this, list);

        // recyclerView.setAdapter(adapter);
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}
