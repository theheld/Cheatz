package com.cheatz;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class MainpageRecyclerAdapter extends RecyclerView.Adapter<MainpageRecyclerAdapter.ViewHolder>  {

    public List<Mainpagemodel> mainList;
    public Context context;

    public MainpageRecyclerAdapter(List<Mainpagemodel> notifList) {
        this.mainList=notifList;
    }


    @NonNull
    @Override
    public MainpageRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainpagelayout, parent, false);
        context = parent.getContext();
        return new MainpageRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainpageRecyclerAdapter.ViewHolder holder, int position) {
        String imageurl=mainList.get(position).getImageurl();
        Picasso.get().load(imageurl).into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(context, Zoomimage.class);
                Intent.putExtra("imageurl",imageurl);
                context.startActivity(Intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;

            image=mView.findViewById(R.id.image);

        }
    }
}
