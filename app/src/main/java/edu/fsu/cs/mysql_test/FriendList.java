package edu.fsu.cs.mysql_test;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FriendList extends Fragment{

    private RecyclerView recyclerView;
    private List<Friends> list;

    public FriendList(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friend_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.friend_recycleview);
        FriendAdapter friendAdapter = new FriendAdapter(getContext(),list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(friendAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        MyApplication g = MyApplication.getInstance();
        String[] data = g.getFriends();
        int size = (data.length/4);
        String[] name = new String[size];
        String[] game = new String[size];

        //ArrayAdapter<String[]> adapter = new ArrayAdapter<String[]>(this,android.R.layout.simple_list_item_1,data);
        list = new ArrayList<>();
        int i = 0;
        for (i = 0; i < data.length-3; i = i+3){
            list.add(new Friends(data[i],data[i+1], R.drawable.user,data[i+2]));
        }



        /*list = new ArrayList<>();
        list.add(new Friends("Name1","Username1",R.drawable.user));
        list.add(new Friends("Name2","Username2",R.drawable.user));
        list.add(new Friends("Name3","Username3",R.drawable.user));
        list.add(new Friends("Name4","Username4",R.drawable.user));
        list.add(new Friends("Name5","Username5",R.drawable.user));
        list.add(new Friends("Name6","Username6",R.drawable.user));*/

    }
}
