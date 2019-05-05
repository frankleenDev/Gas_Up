package app.weconnect.gasappgasup;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import app.weconnect.gasappgasup.location.LocationView;
import app.weconnect.gasappgasup.location.PermissionImpl;
import butterknife.ButterKnife;

public class PlaceOrder extends AppCompatActivity implements LocationView {


    private TextView address, item, grand_total, price, qty, subtotal, total, vat;
    private Button cancel, order_bttn;
    private ProgressBar progressBar;
    String swap, last_count, order_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        ButterKnife.bind(this);
        order_no = get_receipt();

        this.progressBar = ((ProgressBar)findViewById(R.id.progress_bar));
        new Handler().postDelayed(new Runnable()
        {
            public void run()
            {
                //progressBar.setVisibility(View.GONE);

            }
        }, 2000L);
        Bundle bundle = getIntent().getExtras();
        final String product = bundle.getString("item");
        String str3 = bundle.getString("quantity");
        final String str4 = bundle.getString("vendor");
        String get_price = bundle.getString("item_price");
        final String str1 = bundle.getString("refill_buy");
        final String str2 = bundle.getString("chosen_vendor");
        final String imageString = bundle.getString("image_string");


        item        = findViewById(R.id.title);
        qty         = findViewById(R.id.qty);
        price       = findViewById(R.id.price);
        total       = findViewById(R.id.total);
        subtotal    = findViewById(R.id.sub_total);
        vat         = findViewById(R.id.vat);

        grand_total = findViewById(R.id.grand_total);
        address     = findViewById(R.id.delivery_address);
        cancel      = findViewById(R.id.cancel_bttn);
        order_bttn  = findViewById(R.id.place_order_bttn);


        item.setText(product);
        qty.setText(str3);
        price.setText(get_price);
        swap = String.valueOf(Integer.parseInt(str3) * Integer.parseInt(get_price) * 1.0);
        total.setText(swap);
        subtotal.setText(swap);

        float d1 = Float.parseFloat(swap);
        //Double.isNaN(d1);
        vat.setText(String.valueOf(d1 * 0.18));
        //localObject2 = this.grand_total;

        //Double.isNaN(d2);
        //Double.isNaN(d1);

        grand_total.setText(String.valueOf(d1 + (d1 * 0.18)));
        this.address.setText("Zanzibar, Mjini Magharibi");
        this.cancel.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
               startActivity(new Intent(getApplicationContext(),MainActivity.class));
               finish();
            }
        });
        this.order_bttn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                Intent intent = new Intent(PlaceOrder.this.getApplicationContext(), OrderSummary.class);
                intent.putExtra("item", product);
                intent.putExtra("refill_buy", str1);
                intent.putExtra("quantity", qty.getText().toString());
                intent.putExtra("vendor_string", str2);
                intent.putExtra("vendor", str4);
                intent.putExtra("order_no", order_no);
                intent.putExtra("the_total", grand_total.getText().toString());
                intent.putExtra("location", address.getText().toString());
                intent.putExtra("image_string", imageString);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        new PermissionImpl(PlaceOrder.this, PlaceOrder.this).giveMeCurrentLocation();
    }

    @Override
    public void next(Location location) {
        //textLocation.setText(location.getLatitude()+"\n"+location.getLongitude());

        address.setText(getAddress(this,location.getLatitude(),location.getLongitude()));

        Log.v("Here",location.getProvider());

    }

    public String getAddress(Context context, double lat, double lng) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);

            String add = obj.getAddressLine(0);
            //add = add + "\n" + obj.getCountryName();
            //add = add + "\n" + obj.getCountryCode();
            //add = add + "\n" + obj.getAdminArea();
            //add = add + "\n" + obj.getPostalCode();
            //add = add + "\n" + obj.getSubAdminArea();
            //add = add + "\n" + obj.getLocality();
            //add = add + "\n" + obj.getSubThoroughfare();

            return add;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    @Override
    public void error() {
        Log.v("Here","error");
    }

    public String get_receipt(){

        DatabaseReference demoRef = FirebaseDatabase.getInstance().getReference("receipt_no");

        demoRef.child("count").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                //demoValue.setText(value);
                last_count = dataSnapshot.getValue().toString();

                order_no = last_count;

                //Toast.makeText(getApplicationContext(),last_count,Toast.LENGTH_LONG).show();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("receipt_no");
                databaseReference.child("count").setValue(String.valueOf(Integer.parseInt(last_count)+1));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return last_count;

    }

    }

