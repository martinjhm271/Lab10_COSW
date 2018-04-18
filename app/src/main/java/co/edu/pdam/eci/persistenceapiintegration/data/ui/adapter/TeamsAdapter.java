package co.edu.pdam.eci.persistenceapiintegration.data.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.edu.pdam.eci.persistenceapiintegration.R;
import co.edu.pdam.eci.persistenceapiintegration.data.entity.Team;



public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.ViewHolder> {

    List<Team> teams;


    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView name,shortname;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.logo);
            name = (TextView) itemView.findViewById(R.id.name);
            shortname = (TextView) itemView.findViewById(R.id.shortName);
        }
    }

    public TeamsAdapter(List<Team> teams) {
        this.teams = teams;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_card,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(teams.get(position).getName());
        holder.shortname.setText(teams.get(position).getShortName());
            Bitmap image = null;
            try{
                InputStream in = new java.net.URL(teams.get(position).getImageUrl()).openStream();
                image = BitmapFactory.decodeStream(in);
            }catch(Exception ex){
                System.out.println("Foto no encontrada!!");
            }

        Picasso.get().load(teams.get(position).getImageUrl()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }
}
