package app.weconnect.gasappgasup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderSummary extends AppCompatActivity
{


    protected void onCreate(final Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_order_summary);
        //final String order_number = get_receipt();

        //Toast.makeText(getApplicationContext(),order_number,Toast.LENGTH_LONG).show();

        Bundle bundle = getIntent().getExtras();
        final String item = bundle.getString("item");
        final String str1 = bundle.getString("quantity");
        final String str2 = bundle.getString("refill_buy");
        final String str3 = bundle.getString("vendor_string");
        final String str6 = bundle.getString("vendor");
        final String str4 = bundle.getString("the_total");
        final String str5 = bundle.getString("location");
        final String order_no = bundle.getString("order_no");
        final String image_url = bundle.getString("image_string");


        final TextView type_text      = findViewById(R.id.type_text);
        TextView nature_txt     = (TextView) findViewById(R.id.refill_textView);
        TextView quantity_txt   = (TextView) findViewById(R.id.qty_textView);
        TextView vendor_txt     = (TextView) findViewById(R.id.vendor_textView);
        TextView location_txt   = (TextView) findViewById(R.id.location_textView);
        TextView total_txt      = (TextView) findViewById(R.id.total_textView);
        final Button confirm_btn = (Button) findViewById(R.id.confirm_bttn);

        type_text.setText(item);
        nature_txt.setText(str2);
        quantity_txt.setText(str1);
        vendor_txt.setText(str3);
        location_txt.setText(str5);
        total_txt.setText(str4);


        confirm_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View paramAnonymousView) {

                confirm_btn.setEnabled(false);

                final LinearLayout linearLayout = findViewById(R.id.linearProgress4);
                final ProgressBar progressBar   = findViewById(R.id.progress_bar4);
                linearLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                //((LinearLayout)OrderSummary.this.findViewById(2131230859)).setVisibility(0)

                String time_recorded = new SimpleDateFormat("HH:mm:ss dd-MMM-yyyy").format(new Date());
                String date_substring = time_recorded.substring(time_recorded.length()-11);
                String time_substring = time_recorded.substring(0,time_recorded.length()-12);
                final String month_year = date_substring.substring(date_substring.length()-8);


                final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                final Orders orders = new Orders(item, str2, str1, str3, str5, str4, image_url, time_recorded);

                final VendorClass vendorClass = new VendorClass(order_no,  "Customer", str2, FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(),str1, time_substring,str4,item,date_substring);

                firebaseDatabase.getReference("users").child(firebaseUser.getUid()).child("Orders").push().setValue(orders).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        //Toast.makeText(getApplicationContext(),get_receipt(),Toast.LENGTH_LONG).show();

                        firebaseDatabase.getReference("vendors").child(str6.substring(0,str6.length()-1)).child("Orders").child(month_year).push().setValue(vendorClass);

                        Toast.makeText(getApplicationContext(),"Oder was sent successfully.",Toast.LENGTH_LONG).show();


                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        linearLayout.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        finish();

                    }
                });
            }
        });

    }



}
