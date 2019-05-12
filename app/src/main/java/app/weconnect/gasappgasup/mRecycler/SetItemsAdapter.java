package app.weconnect.gasappgasup.mRecycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import app.weconnect.gasappgasup.PredefinedItems;
import app.weconnect.gasappgasup.Products;
import app.weconnect.gasappgasup.R;

import java.util.ArrayList;

import static java.lang.String.valueOf;


/**
 * Created by Oclemy on 9/24/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class SetItemsAdapter extends RecyclerView.Adapter<SetItemsAdapter.MyViewHolder> {

    Context context;
    ArrayList<PredefinedItems> orders;
    String vendor_name;


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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

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

        holder.check_available.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();
                final String vendorUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("vendors").child(vendorUID+position).child("Profile").child("agents_name");

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //adapter = new MyAdapter(VendorActivity.this, list);
                        //recyclerView.setAdapter(adapter);

                        vendor_name = (String) dataSnapshot.getValue();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                if (isChecked) {

                    //EditText editText = findViewById(R.id.refill_price);

                    String buying_prize = holder.buying_price.getText().toString();
                    String refill_prize = holder.refill_price.getText().toString();

                    Products newz = new Products(orders.get(position).getTitle(),buying_prize,orders.get(position).getImage(),String.valueOf(position),"url",refill_prize,vendorUID,vendor_name);

                    firebaseDatabase.child("Shop").child(vendorUID+position).setValue(newz);

                    //checkedStatus[holder.getAdapterPosition()] = true;
                    //performCheckedActions(); //your logic here
                } else {

                    firebaseDatabase.child("Shop").child(vendorUID+position).removeValue();


                    Toast.makeText(context,orders.get(position).getTitle()+" has been removed.",Toast.LENGTH_SHORT).show();


                    //checkedStatus[holder.getAdapterPosition()] = false;
                    //performUncheckedActions(); //your logic here
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView title,refill_price, buying_price;
        CheckBox check_available;
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
