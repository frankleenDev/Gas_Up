package app.weconnect.gasappgasup;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BuyOrRefill extends AppCompatActivity {

    private Products newz = new Products();
    private ProgressBar progressBar;
    private TextView qty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_or_refill);

        final LinearLayout linear = (LinearLayout) findViewById(R.id.linearLayout);
        this.progressBar = ((ProgressBar) findViewById(R.id.progress_bar2));

        new Handler().postDelayed(new Runnable() {
            public void run() {
                progressBar.setVisibility(View.GONE);
                //linear.setVisibility(View.GONE);
            }
        }, 2000);

        String product_id = getIntent().getExtras().getString("prod_id");

        final TextView cylinder = (TextView) findViewById(R.id.cylinder_type);
        final TextView vendor = (TextView) findViewById(R.id.vendor);
        qty = ((TextView) findViewById(R.id.qty));
        Button add_btn = (Button) findViewById(R.id.add_btn);
        Button minus_button = (Button) findViewById(R.id.minus_btn);
        final Button refill_btn = (Button) findViewById(R.id.refill_btn);
        final Button buy_btn = (Button) findViewById(R.id.buy_btn);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        this.qty.setText("1");

        FirebaseDatabase.getInstance().getReference().child("Shop").child(product_id).addValueEventListener(new ValueEventListener() {
            public void onCancelled(@NonNull DatabaseError paramAnonymousDatabaseError) {
            }

            public void onDataChange(@NonNull DataSnapshot paramAnonymousDataSnapshot) {
                String prod_id = paramAnonymousDataSnapshot.child("prod_id").getValue().toString();
                String title = paramAnonymousDataSnapshot.child("title").getValue().toString();
                String str1 = paramAnonymousDataSnapshot.child("image").getValue().toString();
                String str2 = paramAnonymousDataSnapshot.child("vendor_name").getValue().toString();
                String buying_price = paramAnonymousDataSnapshot.child("desc").getValue().toString();
                String refill_price = paramAnonymousDataSnapshot.child("refill").getValue().toString();
                BuyOrRefill.this.newz.setDesc(buying_price);
                BuyOrRefill.this.newz.setImage(str1);
                BuyOrRefill.this.newz.setTitle(title);
                BuyOrRefill.this.newz.setProd_id(prod_id);
                BuyOrRefill.this.newz.setRefill(refill_price);
                BuyOrRefill.this.newz.setVendor(str2);

                progressBar.setVisibility(View.GONE);
                //linear.setVisibility(View.GONE);

                cylinder.setText(newz.getTitle());
                vendor.setText(BuyOrRefill.this.newz.getVendor());
                Picasso.get().load(newz.getImage()).into(imageView);


                refill_btn.setText("Refill " + newz.getRefill());

                buy_btn.setText("Buy " + newz.getDesc());

            }
        });
        add_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                int i = Integer.parseInt(qty.getText().toString());
                qty.setText(String.valueOf(Integer.valueOf(i + 1)));
            }
        });
        minus_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                int i = Integer.parseInt(qty.getText().toString());
                if (i != 1) {
                    //int i = paramAnonymousView.intValue();
                    qty.setText(String.valueOf(Integer.valueOf(i - 1)));
                    //return;
                }
                Toast.makeText(BuyOrRefill.this.getApplicationContext(), "1 is the minimum quantity", Toast.LENGTH_SHORT).show();
            }
        });
        refill_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {

                String send_price = BuyOrRefill.this.newz.getRefill();
                BuyOrRefill.this.sendOrder("Refill", send_price);
            }
        });
        buy_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {

                String send_price = BuyOrRefill.this.newz.getDesc();
                BuyOrRefill.this.sendOrder("New Cylinder", send_price);
            }
        });
    }

    private void sendOrder(String paramString1, String paramString2)
    {
        Intent localIntent = new Intent(getApplicationContext(), PlaceOrder.class);
        localIntent.putExtra("item", this.newz.getTitle());
        localIntent.putExtra("quantity", this.qty.getText().toString());
        localIntent.putExtra("item_price", paramString2);
        localIntent.putExtra("refill_buy", paramString1);
        localIntent.putExtra("chosen_vendor", this.newz.getVendor());
        localIntent.putExtra("image_string", this.newz.getImage());
        startActivity(localIntent);
    }

}
