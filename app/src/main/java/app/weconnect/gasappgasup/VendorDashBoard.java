package app.weconnect.gasappgasup;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import app.weconnect.gasappgasup.Auth.NumberAuthActivity;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class VendorDashBoard extends AppCompatActivity {


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    private ActionBar toolbar;
    private FirebaseAuth auth;
    private FirebaseUser auth1 = FirebaseAuth.getInstance().getCurrentUser();
    private int janCount, febCount, marCount, aprCount, mayCount, junCount, julCount, augCount, sepCount, octCount, novCount, decCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_dash_board);

        Bundle b = getIntent().getExtras();
        if (getIntent().getExtras()!=null) {

            janCount = (int) b.getLong("jan");
            febCount = (int) b.getLong("feb");
            marCount = (int) b.getLong("mar");
            aprCount = (int) b.getLong("apr");
            mayCount = (int) b.getLong("may");
            junCount = (int) b.getLong("jun");
            julCount = (int) b.getLong("jul");
            augCount = (int) b.getLong("aug");
            sepCount = (int) b.getLong("sep");
            octCount = (int) b.getLong("oct");
            novCount = (int) b.getLong("nov");
            decCount = (int) b.getLong("dec");

        }

        //Toast.makeText(getApplicationContext(),String.valueOf(janCount),Toast.LENGTH_LONG).show();

        NavigationView navigation =  findViewById(R.id.navigation);
        navigation.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //if(janCount==-1){

        //this.recreate();
        //Toast.makeText(getApplicationContext(),"zero",Toast.LENGTH_SHORT).show();
        //}

        //Toast.makeText(getApplicationContext(),String.valueOf(janCount),Toast.LENGTH_SHORT).show();

        //getMonthCount();

        final TextView clietz = findViewById(R.id.clients_text);
        final TextView produtz = findViewById(R.id.productz_text);
        final TextView deliveriz = findViewById(R.id.delivery_text);
        LinearLayout linearLayout = findViewById(R.id.lineardashboard);
        ProgressBar progressBar = findViewById(R.id.progress_bardashboard);

        progressBar.setVisibility(View.VISIBLE);

        FirebaseDatabase.getInstance().getReference()
                .child("vendors").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()){

                            //SummaryClass summaryClass = new SummaryClass();

                            final SummaryClass summaryClass1 = dataSnapshot.child("Summary").getValue(SummaryClass.class);

                            runOnUiThread(new Runnable() {
                                public void run() {

                                    clietz.setText(summaryClass1.getClients().toString());
                                    produtz.setText(summaryClass1.getProducts().toString());
                                    deliveriz.setText(summaryClass1.getDeliveries().toString());

                                }
                            });

                            //Toast.makeText(getApplicationContext(),client_sum.toString(),Toast.LENGTH_SHORT).show();

                        } else {

                            //mProgressDialog.dismiss();
                            //Snackbar.make(view, "datasnapshot is null", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        //progressBar.setVisibility(View.GONE);
                    }
                });

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //startActivity(new Intent(getApplicationContext(),SetItemsAdapter.class));

            }
        });

        auth = FirebaseAuth.getInstance();

        drawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //toolbar = getSupportActionBar();


        LineChartView lineChartView = findViewById(R.id.chart);

        String[] axisData = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};

        int[] yAxisData = {janCount, febCount, marCount, aprCount, mayCount, junCount, julCount, augCount, sepCount, octCount, novCount, decCount};

        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();

        Line line = new Line(yAxisValues);

        for(int i = 0; i < axisData.length; i++){
            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
        }

        for (int i = 0; i < yAxisData.length; i++){
            yAxisValues.add(new PointValue(i, yAxisData[i]));
        }

        List lines = new ArrayList();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        lineChartView.setLineChartData(data);

        Axis axis = new Axis();
        axis.setValues(axisValues);
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        data.setAxisYLeft(yAxis);

        Line line2 = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));

        axis.setTextSize(13);

        axis.setTextColor(Color.parseColor("#808080"));

        yAxis.setTextColor(Color.parseColor("#808080"));
        yAxis.setTextSize(13);

        yAxis.setName("Quantity of products sold");

        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top =110;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);

        linearLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        if (janCount==0){

            //startActivity(new Intent(getApplicationContext(),SplashScreen.class));
        }

    }

    private void setSupportActionBar(Toolbar toolbar) {
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new NavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {

            switch (item.getItemId()) {
                case R.id.dashboard:
                    //toolbar.setTitle("Dashboard");
                    startActivity(new Intent(getApplicationContext(),VendorDashBoard.class));
                    return true;
                case R.id.orderz:
                    //toolbar.setTitle("Orders");
                    startActivity(new Intent(getApplicationContext(),VendorActivity.class));
                    return true;
                case R.id.order_number:
                    //toolbar.setTitle("Vendor");
                    startActivity(new Intent(getApplicationContext(),VendorActivity.class));
                    return true;
                case R.id.sign_out2:
                    //toolbar.setTitle("Settings");
                    AuthUI.getInstance().signOut(VendorDashBoard.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            startActivity(new Intent(getApplicationContext(), NumberAuthActivity.class));

                        }
                    });
                    return true;
                case R.id.sign_out:
                    toolbar.setTitle("GasApp");
                    auth.signOut();
                    startActivity(new Intent(getApplicationContext(), VendorActivity.class));

                    return true;
            }

            return false;
        }
    };



}
