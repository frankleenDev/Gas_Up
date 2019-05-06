package app.weconnect.gasappgasup.mRecycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import app.weconnect.gasappgasup.PredefinedItems;
import app.weconnect.gasappgasup.R;

import java.util.ArrayList;


/**
 * Created by Oclemy on 9/24/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class SetItemsAdapter extends RecyclerView.Adapter<SetItemsAdapter.MyViewHolder> {

    Context context;
    ArrayList<PredefinedItems> orders;

    public SetItemsAdapter(Context c , ArrayList<PredefinedItems> o)
    {
        context = c;
        orders = o;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.add_item_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.title.setText(orders.get(position).getTitle());
        holder.refill_price.setText(orders.get(position).getRefill());
        holder.buying_price.setText(orders.get(position).getDesc());
        holder.check_available.setText(orders.get(position).getCheck_box());
        //holder.total.setText(orders.get(position).getTotal());
        Picasso.get().load(orders.get(position).getImage()).into(holder.image_view);
        /*if(profiles.get(position).getPermission()) {
            holder.btn.setVisibility(View.VISIBLE);
            holder.onClick(position);
        }*/

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView title,refill_price, buying_price, check_available;
        ImageView image_view;

        public MyViewHolder(View itemView) {

            super(itemView);
            title              =  itemView.findViewById(R.id.title_text);
            refill_price       =  itemView.findViewById(R.id.refill_price);
            buying_price       =  itemView.findViewById(R.id.buying_price);
            image_view         =  itemView.findViewById(R.id.image_view);
            check_available    =  itemView.findViewById(R.id.check_available);

        }
        public void onClick(final int position)
        {
            //image_view.setOnClickListener(new View.OnClickListener() {
            // @Override
            // public void onClick(View v) {
            // }
            //});
        }
    }
}
