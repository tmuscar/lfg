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

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.MyViewHolder> {

    Context mContext;
    List<Friends>mData;
    Dialog myDialog;

    public FriendAdapter(Context mContext, List<Friends> mData){
        this.mContext = mContext;
        this.mData = mData;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(mContext).inflate(R.layout.friend_item, parent,false);
        final MyViewHolder viewHolder = new MyViewHolder(view);

        myDialog= new Dialog(mContext);
        myDialog.setContentView(R.layout.contact_info);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        viewHolder.item_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialog_name_tv =(TextView) myDialog.findViewById(R.id.contact_name_id);
                final TextView dialog_username_tv = (TextView) myDialog.findViewById(R.id.user_name_id);
                ImageView dialog_contact_img = (ImageView) myDialog.findViewById(R.id.logo);
                dialog_name_tv.setText(mData.get(viewHolder.getAdapterPosition()).getName());
                dialog_username_tv.setText(mData.get(viewHolder.getAdapterPosition()).getUsername());
                final String username = mData.get(viewHolder.getAdapterPosition()).getUsername();
                dialog_contact_img.setImageResource(mData.get(viewHolder.getAdapterPosition()).getPhoto());
                MyApplication g = MyApplication.getInstance();
                String user_id = mData.get(viewHolder.getAdapterPosition()).getUserid();
                g.setFriendid(user_id);
                //Toast.makeText(mContext, "Test Click" + String.valueOf(viewHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();

                //myDialog.show();

                Button dialog_invite_btn = (Button) myDialog.findViewById(R.id.invite_btn);
                //Button dialog_info_btn = (Button) myDialog.findViewById(R.id.info_btn);
                Button dialog_delete_btn = (Button) myDialog.findViewById(R.id.delete_btn);

                dialog_invite_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext,InviteList.class);
                        mContext.startActivity(intent);
                        Toast.makeText(mContext, "Invite" + String.valueOf(viewHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    }
                });

                /*dialog_info_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext,Information.class);
                        mContext.startActivity(intent);
                        Toast.makeText(mContext, "Info" + String.valueOf(viewHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    }
                });*/

                dialog_delete_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Delete Contact from Database
                        Delete delete = new Delete(mContext);
                        delete.execute(username);
                        Intent intent = new Intent(mContext,Tabs.class);
                        mContext.startActivity(intent);
                        //Toast.makeText(mContext, "Deleted " + String.valueOf(mData.get(viewHolder.getAdapterPosition()).getUsername()), Toast.LENGTH_SHORT).show();
                    }
                });

                if (view.getParent() != null){
                    ((ViewGroup) view.getParent()).removeView(view);
                }
                myDialog.show();


            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.text_Name.setText(mData.get(position).getName());
        holder.text_Username.setText(mData.get(position).getUsername());
        holder.imageView.setImageResource(mData.get(position).getPhoto());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout item_contact;
        private TextView text_Name, text_Username;
        private ImageView imageView;
        private Button invite_btn;

        public MyViewHolder(View itemView){
            super(itemView);

            invite_btn = (Button) itemView.findViewById(R.id.invite_btn);
            item_contact = (LinearLayout) itemView.findViewById(R.id.contact_item_id);
            text_Name = (TextView) itemView.findViewById(R.id.name_friend);
            text_Username = (TextView) itemView.findViewById(R.id.username_friend);
            imageView = (ImageView) itemView.findViewById(R.id.img_friend);

        }
    }

    private class Delete extends AsyncTask<String, Void, String> {


        Context context;
        AlertDialog.Builder alertDialog;

        Delete(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String result = null;
            MyApplication k = MyApplication.getInstance();
            String userid = k.getUserid();

            /**
             * Stores address of php file to access database
             */
            String login_url = "http://10.0.2.2/delete_friend.php";
            try {
                /**
                 * Accesses database with HttpURLConnection by passes in the two user
                 * entered arguments and stores the returned values from the database
                 */
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8") + "&"
                        + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                result = "";
                String line = "";

                /**
                 * Stores the results
                 */
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            String[] result2 = new String[50];
            String friends_url = "http://10.0.2.2/friends.php";
            try {
                URL url = new URL(friends_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                //result = "";
                String line = "";
                int i = 0;
                int count = 0;
                while ((line = bufferedReader.readLine()) != null) {
                    result2[i] = "";
                    result2[i] = line;
                    i++;
                }
                count = i;
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                MyApplication f = MyApplication.getInstance();
                f.setFriends(result2, count);
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
}
