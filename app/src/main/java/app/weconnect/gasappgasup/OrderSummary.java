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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderSummary extends AppCompatActivity
{
    protected void onCreate(final Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_order_summary);

        Bundle bundle = getIntent().getExtras();
        final String item = bundle.getString("item");
        final String str1 = bundle.getString("quantity");
        final String str2 = bundle.getString("refill_buy");
        final String str3 = bundle.getString("vendor_string");
        final String str4 = bundle.getString("the_total");
        final String str5 = bundle.getString("location");
        final String image_url = bundle.getString("image_string");


        TextView type_text = findViewById(R.id.type_text);
        TextView nature_txt = (TextView) findViewById(R.id.refill_textView);
        TextView quantity_txt = (TextView) findViewById(R.id.qty_textView);
        TextView vendor_txt = (TextView) findViewById(R.id.vendor_textView);
        TextView location_txt = (TextView) findViewById(R.id.location_textView);
        TextView total_txt = (TextView) findViewById(R.id.total_textView);
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
                //((LinearLayout)OrderSummary.this.findViewById(2131230859)).setVisibility(0);
                final ProgressBar progressBar = OrderSummary.this.findViewById(R.id.progress_bar4);
                progressBar.setVisibility(View.VISIBLE);
                String time_recorded = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new Date());
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                Orders orders = new Orders(item, str2, str1, str3, str5, str4, image_url, time_recorded);
                firebaseDatabase.getReference("users").child(firebaseUser.getUid()).child("Orders").push().setValue(orders).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    }
                });
            }
        });

    }}
