package app.weconnect.gasappgasup.mRecycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import app.weconnect.gasappgasup.Orders;
import app.weconnect.gasappgasup.R;

import java.util.ArrayList;


/**
 * Created by Oclemy on 9/24/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class MyOrders extends RecyclerView.Adapter<MyOrders.MyViewHolder> {

    Context context;
    ArrayList<Orders> orders;

    public MyOrders(Context c , ArrayList<Orders> o)
    {
        context = c;
        orders = o;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.myorders,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.type.setText(orders.get(position).getType());
        holder.nature.setText(orders.get(position).getNature());
        holder.total.setText(orders.get(position).getTotal());
        holder.date.setText(orders.get(position).getTime_ordered());
        holder.quality.setText(orders.get(position).getQuantity());
        holder.loc.setText(orders.get(position).getLocation());
        Picasso.get().load(orders.get(position).getImage_url()).into(holder.image_view);
        /*if(profiles.get(position).getPermission()) {
            holder.btn.setVisibility(View.VISIBLE);
            holder.onClick(position);
        }*/

        holder.image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, position+" is clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView type,nature, quality, loc, total, date;
        ImageView image_view;

        public MyViewHolder(View itemView) {
            super(itemView);
            type        =  itemView.findViewById(R.id.type_txt);
            nature      =  itemView.findViewById(R.id.nature_txt);
            quality     =  itemView.findViewById(R.id.quantity_txt);
            loc         =  itemView.findViewById(R.id.loc_txt);
            total       =  itemView.findViewById(R.id.total_txt);
            date        =  itemView.findViewById(R.id.date_txt);
            image_view  =  itemView.findViewById(R.id.image_view);
        }
        public void onClick(final int position)
        {
            image_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }
}
