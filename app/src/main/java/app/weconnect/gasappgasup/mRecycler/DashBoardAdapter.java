package app.weconnect.gasappgasup.mRecycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import app.weconnect.gasappgasup.R;
import app.weconnect.gasappgasup.SummaryClass;

import java.util.ArrayList;



public class DashBoardAdapter extends RecyclerView.Adapter<DashBoardAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<SummaryClass> orders;

    public DashBoardAdapter(Context c , ArrayList<SummaryClass> o)
    {
        context = c;
        orders = o;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.dashboard,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

            holder.clients_text.setText(orders.get(position).getClients());
            holder.productz_text.setText(orders.get(position).getProducts());
            holder.delivery_text.setText(orders.get(position).getDeliveries());

    }

    @Override
    public int getItemCount() {

        return orders.size();

    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView clients_text, productz_text, delivery_text;
        //ImageView image_view;

        MyViewHolder(View itemView) {
            super(itemView);

            clients_text   =  itemView.findViewById(R.id.clients_text);
            productz_text  =  itemView.findViewById(R.id.productz_text);
            delivery_text  =  itemView.findViewById(R.id.delivery_text);

        }

    }
}
