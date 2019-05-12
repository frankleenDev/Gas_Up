package app.weconnect.gasappgasup.mRecycler;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import app.weconnect.gasappgasup.BuyOrRefill;
import app.weconnect.gasappgasup.R;
import app.weconnect.gasappgasup.Products;

import java.util.ArrayList;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Products> profiles;

    public MyAdapter(Context c , ArrayList<Products> p)
    {
        context = c;
        profiles = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.model,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.name.setText(profiles.get(position).getDesc());
        holder.title.setText(profiles.get(position).getTitle());
        holder.email.setText(profiles.get(position).getRefill());
        holder.vendor.setText(profiles.get(position).getVendor_name());
        Picasso.get().load(profiles.get(position).getImage()).into(holder.profilePic);
        /*if(profiles.get(position).getPermission()) {
            holder.btn.setVisibility(View.VISIBLE);
            holder.onClick(position);
        }*/



        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, position+" is clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, BuyOrRefill.class);
                intent.putExtra("prod_id", profiles.get(position).getVendor());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,email, vendor, title;
        ImageView profilePic;
        LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            title      =  itemView.findViewById(R.id.title);
            name       =  itemView.findViewById(R.id.buying_price);
            email      =  itemView.findViewById(R.id.refill_price);
            profilePic =  itemView.findViewById(R.id.image_view);
            vendor     =  itemView.findViewById(R.id.vendor_txt);
            linearLayout     =  itemView.findViewById(R.id.user_linearLayout);
        }
        public void onClick(final int position)
        {
            profilePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }
}
