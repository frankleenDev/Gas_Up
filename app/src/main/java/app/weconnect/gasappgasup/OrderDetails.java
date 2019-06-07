package app.weconnect.gasappgasup;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class OrderDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        final TextView product, quantity, total, location, date, customer_number;
        final Button confirm, cancel;

        product         = findViewById(R.id.product);
        quantity        = findViewById(R.id.quantity);
        total           = findViewById(R.id.total);
        location        = findViewById(R.id.location);
        date            = findViewById(R.id.date);
        customer_number = findViewById(R.id.customer_number);

        confirm        = findViewById(R.id.confirm_button);
        cancel         = findViewById(R.id.cancel_button);

        String order_id = getIntent().getStringExtra("overhead");
        String uid      = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //final String quantity = db.collection("vendors/"+uid+"/Orders/"+order_id+"/quantity").get().toString();

        final DocumentReference user = db.collection("vendors").document(uid).collection("Orders").document(order_id);
        user.get().addOnCompleteListener(new OnCompleteListener < DocumentSnapshot > () {
            @Override
            public void onComplete(@NonNull Task < DocumentSnapshot > task) {
                if (task.isSuccessful()) {


                        DocumentSnapshot doc = task.getResult();

                    if(doc.exists()) {


                        VendorClass vendorClass = doc.toObject(VendorClass.class);

                        //product.setText(vendorClass.getProduct());
                        product.setText(Html.fromHtml(vendorClass.getProduct()+"<sup><small>"+vendorClass.getNature()+"</small></sup>"));
                        quantity.setText(vendorClass.getQuantity());
                        total.setText(vendorClass.getTotal());
                        location.setText(vendorClass.getLocation());
                        date.setText(vendorClass.getRecorded_date());
                        customer_number.setText(vendorClass.getPhone_no());


                    }
                    else {

                        Toast.makeText(getApplicationContext(), "Error accessing data...",Toast.LENGTH_SHORT).show();
                    }


                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                confirm.setEnabled(false);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseAuth auth = FirebaseAuth.getInstance();

                //db.collection("Users/"+auth.getCurrentUser().getUid()+"/Orders").add(orders);



            }
        });


    }

}
