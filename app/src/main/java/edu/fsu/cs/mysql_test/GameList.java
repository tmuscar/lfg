package edu.fsu.cs.mysql_test;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GameList extends Fragment {

    private RecyclerView myRecylce;
    private List<Games> mList;

    public GameList(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_list, container, false);
        myRecylce = (RecyclerView) view.findViewById(R.id.rv_list);
        GameAdapter gameAdapter = new GameAdapter(getContext(),mList);
        myRecylce.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecylce.setAdapter(gameAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        mList = new ArrayList<>();

        mList.add(new Games( R.drawable.fortnite_logo,"Fortnite"));
        mList.add(new Games(R.drawable.logo_ow,"Overwatch"));
        mList.add(new Games(R.drawable.pubg_logo,"PUBG"));
        mList.add(new Games(R.drawable.destiny_logo,"Destiny"));
        mList.add(new Games(R.drawable.bo4_logo, "Black Ops 4"));
        mList.add(new Games(R.drawable.minecraft_logo,"Minecraft"));
        mList.add(new Games(R.drawable.lol_logo, "LoL"));
        mList.add(new Games(R.drawable.chess_logo, "Chess"));


    }
}
