package my.headbana.fyp.drong3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder>{

    private ArrayList<Profile> mDataset;
    private Context context;

    public ProfileAdapter(ArrayList<Profile> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View profileView = inflater.inflate(R.layout.rvi_profile, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(profileView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // Get the data model based on position
        Profile profile = mDataset.get(position);

        // Set item views based on your views and data model
        TextView profileName = holder.profileName;
        profileName.setText(mDataset.get(position).getName());

        TextView profileAddress = holder.profileAddress;
        profileAddress.setText(mDataset.get(position).getAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int profile_id = mDataset.get(position).getId();
                Intent intent = new Intent(context, DisplayProfile.class);
                intent.putExtra("profile_id", profile_id);
                context.startActivity(intent);
//                Toast.makeText(context, "Clicked on userID : "+profile_id, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public TextView profileName, profileAddress;
        private Context context;

        public ViewHolder(View v) {
            super(v);
            context = itemView.getContext();

            profileName = (TextView) itemView.findViewById(R.id.profile_name);
            profileAddress = (TextView) itemView.findViewById(R.id.profile_address);
        }

//        @Override
//        public void onClick(View view) {
//            Log.d("Adapter Click Listener","User triggered listener");
//            int position = getAdapterPosition(); // gets item position
//            Intent intent = new Intent(context, DisplayProfile.class);
//            Bundle extras = intent.getExtras();
//            extras.putInt("profile_id", position);
//            Toast.makeText(context, "Clicked position:"+position+", data:"+position+"!", Toast.LENGTH_SHORT).show();
//        }
    }
}