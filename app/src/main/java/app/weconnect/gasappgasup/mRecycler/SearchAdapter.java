package app.weconnect.gasappgasup.mRecycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import app.weconnect.gasappgasup.R;
import app.weconnect.gasappgasup.User;

import java.util.ArrayList;



public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<User> profiles;

    public SearchAdapter(Context c , ArrayList<User> p)
    {
        context = c;
        profiles = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,final int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.user,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.user_id.setText(profiles.get(position).getUid());
        holder.phone_no.setText(profiles.get(position).getNumber());

        //Picasso.get().load(profiles.get(position).getImage()).into(holder.profilePic);
        /*if(profiles.get(position).getPermission()) {
            holder.btn.setVisibility(View.VISIBLE);
            holder.onClick(position);
        }*/



        holder.user_linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Toast.makeText(context, position+" is clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, BuyOrRefill.class);
                //intent.putExtra("prod_id", profiles.get(position).getUID());
                context.startActivity(intent);*/

                Toast.makeText(context,position,Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView user_id, phone_no;

        LinearLayout user_linearLayout;

        private MyViewHolder(View itemView) {
            super(itemView);
            user_id             =  itemView.findViewById(R.id.buying_price);
            phone_no            =  itemView.findViewById(R.id.refill_price);
            user_linearLayout   =  itemView.findViewById(R.id.user_linearLayout);

        }

    }
}
