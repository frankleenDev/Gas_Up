package app.weconnect.gasappgasup;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PlaceOrder extends AppCompatActivity {


    private TextView address, item, grand_total, price, qty, subtotal, total, vat;
    private Button cancel, order_bttn;
    private ProgressBar progressBar;
    String swap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

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
                intent.putExtra("the_total", grand_total.getText().toString());
                intent.putExtra("location", "Zanzibar, Maisara");
                intent.putExtra("image_string", imageString);
                startActivity(intent);
            }
        });
    }

    }

