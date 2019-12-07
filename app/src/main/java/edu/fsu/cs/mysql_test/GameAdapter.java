package edu.fsu.cs.mysql_test;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.myViewHolder> {

    Context mContext;
    List<Games> mData;

    public GameAdapter(Context mContext, List<Games> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    Context context = mContext;

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.game_item,parent,false);
        final myViewHolder viewHolder = new myViewHolder(view);

        /*viewHolder.game_item.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View v){
              Intent intent = new Intent(mContext,Request.class);
              mContext.startActivity(intent);
          }
        });*/

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {

        holder.game_logo.setImageResource(mData.get(position).getGameLogo());
        holder.game_name.setText(mData.get(position).getGameName());

        holder.game_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                final String GameName = mData.get(position).getGameName();
                MyApplication g = MyApplication.getInstance();
                g.setGame(GameName);

                intent = new Intent(mContext,Request.class);
                mContext.startActivity(intent);
                /*switch(GameName)
                {
                    case "Fortnite":
                        intent = new Intent(mContext,FortniteSchedule.class);
                        mContext.startActivity(intent);
                        break;
                    case "OVERWATCH":
                        intent = new Intent(mContext,OverwatchSchedule.class);
                        mContext.startActivity(intent);
                        break;
                    case "PUBG":
                        intent = new Intent(mContext,PUBGSchedule.class);
                        mContext.startActivity(intent);
                        break;
                    case "Destiny 2":
                        intent = new Intent(mContext,DestinyTwoSchedule.class);
                        mContext.startActivity(intent);
                        break;
                    case "Call of Duty: Black Ops 4":
                        intent = new Intent(mContext,COD4Schedule.class);
                        mContext.startActivity(intent);
                        break;
                    case "Minecraft":
                        intent = new Intent(mContext,MinecraftSchedule.class);
                        mContext.startActivity(intent);
                        break;
                    case "League of Legends":
                        intent = new Intent(mContext,LoLSchedule.class);
                        mContext.startActivity(intent);
                        break;
                    case "Chess":
                        intent = new Intent(mContext,ChessSchedule.class);
                        mContext.startActivity(intent);
                        break;
                }*/
                /*if(mData.get(position).getTitle() == "Fortnite") {
                    intent = new Intent(mContext,FortniteSchedule.class);
                    mContext.startActivity(intent);
                    //Toast.makeText(mContext, "Test Click" + String.valueOf(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                }
                else if(){
                    Toast.makeText(mContext, "Not Fornite GO FUCK YOURSELF" + String.valueOf(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }

    @Override
    public int getItemCount() {

        return mData.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout game_item;
        private ImageView game_logo;
        private TextView game_name;

        public myViewHolder(View itemView) {
            super(itemView);

            game_item = (LinearLayout) itemView.findViewById(R.id.game_item_id);
            game_logo = (ImageView) itemView.findViewById(R.id.game_logo);
            game_name = (TextView) itemView.findViewById(R.id.game_name);

        }
    }
}
