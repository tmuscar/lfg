package edu.fsu.cs.mysql_test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    Context mContext;
    List<Events>mData;
    Dialog myDialog;

    public EventsAdapter(Context mContext, List<Events> mData){
        this.mContext = mContext;
        this.mData = mData;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.event_item, parent,false);
        final MyViewHolder viewHolder = new MyViewHolder(view);

        viewHolder.event_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication g = MyApplication.getInstance();
                String request_id = mData.get(viewHolder.getAdapterPosition()).getRequestid();
                g.setRequestid(request_id);
            }
        });




        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.game_name.setText(mData.get(position).getGame());
        holder.quest_name.setText(mData.get(position).getQuest());
        holder.imageView.setImageResource(mData.get(position).getPicture());
        holder.requester.setText(mData.get(position).getRequester());
        holder.time.setText(mData.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout event_contact;
        private TextView game_name, quest_name, time, requester, players_commited;
        private ImageView imageView;
        private Button decision;

        public MyViewHolder(View itemView){
            super(itemView);

            event_contact = (LinearLayout) itemView.findViewById(R.id.event_item_id);
            game_name = (TextView) itemView.findViewById(R.id.game_name);
            quest_name = (TextView) itemView.findViewById(R.id.quest_name);
            imageView = (ImageView) itemView.findViewById(R.id.img_game_logo);
            //decision = (Button) itemView.findViewById(R.id.submit_id);
            requester = (TextView) itemView.findViewById(R.id.requester_id);
            time = (TextView) itemView.findViewById(R.id.time_id);
        }
    }


}

