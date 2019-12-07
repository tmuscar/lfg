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

public class InviteAdapter extends RecyclerView.Adapter<InviteAdapter.MyViewHolder> {

    Context mContext;
    List<Invite>mData;
    Dialog myDialog;

    public InviteAdapter(Context mContext, List<Invite> mData){
        this.mContext = mContext;
        this.mData = mData;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.invite_item, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);

        final View rootView = LayoutInflater.from(mContext).inflate(R.layout.confirm_dialog, null);

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext, R.style.MyDialogTheme);

        alertDialog.setMessage("Invite to this Event")
                .setView(rootView);

        viewHolder.invite_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyApplication g = MyApplication.getInstance();
                        String request_id = mData.get(viewHolder.getAdapterPosition()).getRequestid();
                        g.setRequestid(request_id);
                        String user_id = g.getUserid();
                        String friend_id = g.getFriendid();
                        CreateInvite event = new CreateInvite(mContext);
                        event.execute(request_id, user_id, friend_id);
                    }

                });
                alertDialog.setNegativeButton("Change Selection", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(mContext, InviteList.class);
                        mContext.startActivity(intent);
                    }
                });
                if (rootView.getParent() != null){
                    ((ViewGroup) rootView.getParent()).removeView(rootView);
                }
                alertDialog.show();
            }
        });




        return viewHolder;
    }
    private class CreateInvite extends AsyncTask<String, Void, String> {

        Context context;
        AlertDialog.Builder alertDialog;

        CreateInvite (Context ctx){
            context = ctx;
        }
        @Override
        protected String doInBackground(String... params) {
            String request_id = params[0];
            String user_id = params[1];
            String friend_id = params[2];
            String[] result = new String[50];

            /**
             * Stores address of php file to access database and receive results from the SQL call
             */
            String register_url = "http://10.0.2.2/create_invite.php";
            try {
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("request_id", "UTF-8")+"="+URLEncoder.encode(request_id, "UTF-8") + "&" +
                        URLEncoder.encode("user_id", "UTF-8")+"="+URLEncoder.encode(user_id, "UTF-8") + "&" +
                        URLEncoder.encode("friend_id", "UTF-8")+"="+URLEncoder.encode(friend_id, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                //result[] = "";
                String line = "";
                int i =0;
                while ((line = bufferedReader.readLine()) != null) {
                    result[i] = "";
                    result[i] = line;
                    i++;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                MyApplication x = MyApplication.getInstance();
                x.setInvite(result, i);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent(mContext,Tabs.class);
            mContext.startActivity(intent);
        }
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

        private LinearLayout invite_contact;
        private TextView game_name, quest_name, time, requester;
        private ImageView imageView;
        private Button invite;

        public MyViewHolder(View itemView){
            super(itemView);

            invite_contact = (LinearLayout) itemView.findViewById(R.id.invite_item_id);
            game_name = (TextView) itemView.findViewById(R.id.game_name);
            quest_name = (TextView) itemView.findViewById(R.id.quest_name);
            imageView = (ImageView) itemView.findViewById(R.id.img_game_logo);
            //invite = (Button) itemView.findViewById(R.id.submit_id);
            requester = (TextView) itemView.findViewById(R.id.requester_id);
            time = (TextView) itemView.findViewById(R.id.time_id);
        }


    }

}
