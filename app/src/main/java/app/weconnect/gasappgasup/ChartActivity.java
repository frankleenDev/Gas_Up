package app.weconnect.gasappgasup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChartActivity extends AppCompatActivity {

    private LineChart chart;
    private int jan=-1, feb=-1, mar=-1, apr=-1, may=-1, jun=-1, jul=-1, aug=-1, sep=-1, oct=-1, nov=-1, dec=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        String time_recorded = new SimpleDateFormat("HH:mm:ss dd-MMM-yyyy").format(new Date());
        final String date_substring = time_recorded.substring(time_recorded.length()-4).trim();

        Toast.makeText(getApplicationContext(),date_substring,Toast.LENGTH_LONG).show();

        final String auth = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("vendors");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //adapter = new MyAdapter(VendorActivity.this, list);
                //recyclerView.setAdapter(adapter);
                if (dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {


                    jan = (int) dataSnapshot.child(auth).child("Orders").child("Jan-" + date_substring).getChildrenCount();
                    feb = (int) dataSnapshot.child(auth).child("Orders").child("Feb-" + date_substring).getChildrenCount();
                    mar = (int) dataSnapshot.child(auth).child("Orders").child("Mar-" + date_substring).getChildrenCount();
                    apr = (int) dataSnapshot.child(auth).child("Orders").child("Apr-" + date_substring).getChildrenCount();
                    may = (int) dataSnapshot.child(auth).child("Orders").child("May-" + date_substring).getChildrenCount();
                    jun = (int) dataSnapshot.child(auth).child("Orders").child("Jun-" + date_substring).getChildrenCount();
                    jul = (int) dataSnapshot.child(auth).child("Orders").child("Jul-" + date_substring).getChildrenCount();
                    aug = (int) dataSnapshot.child(auth).child("Orders").child("Aug-" + date_substring).getChildrenCount();
                    sep = (int) dataSnapshot.child(auth).child("Orders").child("Sep-" + date_substring).getChildrenCount();
                    oct = (int) dataSnapshot.child(auth).child("Orders").child("Oct-" + date_substring).getChildrenCount();
                    nov = (int) dataSnapshot.child(auth).child("Orders").child("Nov-" + date_substring).getChildrenCount();
                    dec = (int) dataSnapshot.child(auth).child("Orders").child("Dec-" + date_substring).getChildrenCount();


                }
                else{
                    //Toast.makeText(getApplicationContext(), auth, Toast.LENGTH_LONG).show();

                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }

                /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
                        startActivity(new Intent(getApplicationContext(), NumberAuthActivity.class));
                    }
                });*/

                //REFERENCE DRAWER,TOGGLE ITS INDICATOR
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                //drawer.addDrawerListener(toggle);
                //toggle.syncState();

                //REFERNCE NAV VIEW AND ATTACH ITS ITEM SELECTION LISTENER
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                //navigationView.setNavigationItemSelectedListener(SplashScreen.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        chart = findViewById(R.id.chart);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, jan));
        entries.add(new Entry(1, feb));
        entries.add(new Entry(2, mar));
        entries.add(new Entry(3, apr));
        entries.add(new Entry(4, may));
        entries.add(new Entry(5, jun));
        entries.add(new Entry(6, jul));
        entries.add(new Entry(7, aug));
        entries.add(new Entry(8, sep));
        entries.add(new Entry(9, oct));
        entries.add(new Entry(10, nov));
        entries.add(new Entry(11, dec));

        LineDataSet dataSet = new LineDataSet(entries, "Customized values");
        dataSet.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        dataSet.setValueTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        //****
        // Controlling X axis
        XAxis xAxis = chart.getXAxis();
        // Set the xAxis position to bottom. Default is top
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //Customizing x axis value
        final String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun","Jul","Aug","Sept","Oct","Nov","Dec"};

        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return months[(int) value];
            }
        };
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);

        //***
        // Controlling right side of y axis
        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);

        //***
        // Controlling left side of y axis
        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setGranularity(1f);

        // Setting Data
        LineData data = new LineData(dataSet);
        chart.setData(data);
        chart.animateX(2500);
        //refresh
        chart.invalidate();



    }
}
