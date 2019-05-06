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
import app.weconnect.gasappgasup.VendorClass;

import java.util.ArrayList;


/**
 * Created by Oclemy on 9/24/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class VendorAdapter extends RecyclerView.Adapter<VendorAdapter.MyViewHolder> {

    Context context;
    ArrayList<VendorClass> orders;

    public VendorAdapter(Context c , ArrayList<VendorClass> o)
    {
        context = c;
        orders = o;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.vendor_orders,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.order_number.setText("#" + orders.get(position).getOrder_no());
        holder.customer_name.setText(orders.get(position).getCustomer());
        holder.nature.setText(orders.get(position).getNature());
        holder.phone.setText(orders.get(position).getPhone_no());
        holder.product.setText(orders.get(position).getProduct());
        holder.quantity.setText(orders.get(position).getQuantity());
        holder.total.setText(orders.get(position).getTotal());
        holder.time_ordered.setText(orders.get(position).getTime());
        holder.time_ordered.setText(orders.get(position).getTime());

        String this_year = orders.get(position).getRecorded_date();

        holder.year.setText(orders.get(position).getRecorded_date().substring(this_year.length()-4));
        holder.month_text.setText(orders.get(position).getRecorded_date().substring(this_year.indexOf("-")+1,this_year.lastIndexOf("-")));
        holder.date_txt.setText(orders.get(position).getRecorded_date().substring(0,this_year.length()-9));
        //Picasso.get().load(orders.get(position).getImage_url()).into(holder.image_view);
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
        TextView order_number,customer_name, nature, phone, product, quantity, total, time_ordered, year, date_txt, month_text;
        //ImageView image_view;

        public MyViewHolder(View itemView) {
            super(itemView);
            order_number        =  itemView.findViewById(R.id.order_number);
            customer_name      =  itemView.findViewById(R.id.customer_name);
            nature     =  itemView.findViewById(R.id.nature);
            phone         =  itemView.findViewById(R.id.phone);
            product       =  itemView.findViewById(R.id.product);
            quantity        =  itemView.findViewById(R.id.quantity);
            total        =  itemView.findViewById(R.id.total);
            time_ordered        =  itemView.findViewById(R.id.time_ordered);
            year        =  itemView.findViewById(R.id.year);
            date_txt        =  itemView.findViewById(R.id.date_text);
            month_text        =  itemView.findViewById(R.id.month_text);
            //image_view  =  itemView.findViewById(R.id.image_view);
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
