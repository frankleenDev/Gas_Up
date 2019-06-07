package app.weconnect.gasappgasup.mFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.weconnect.gasappgasup.Auth.SignUpVendor;
import app.weconnect.gasappgasup.R;
import app.weconnect.gasappgasup.SummaryClass;
import app.weconnect.gasappgasup.VendorActivity;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 Created by Oclemy on 9/18/2016.
 */
public class InterGalactic extends Fragment{

    private RecyclerView rv;
    LineChartView lineChartView;
    private static String[] spacecrafts={"Pioneer","Voyager","Casini","Spirit","Challenger"};
    private int janCount, febCount, marCount, aprCount, mayCount, junCount, julCount, augCount, sepCount, octCount, novCount, decCount;
    private int jan=-1, feb=-1, mar=-1, apr=-1, may=-1, jun=-1, jul=-1, aug=-1, sep=-1, oct=-1, nov=-1, dec=-1;


    public static InterGalactic newInstance()
    {
        return new InterGalactic();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView=inflater.inflate(R.layout.intergalactic, container,false);

        final RelativeLayout relativeLayout = rootView.findViewById(R.id.chartRelvativeLayout);

        String time_recorded = new SimpleDateFormat("HH:mm:ss dd-MMM-yyyy").format(new Date());
        final String date_substring = time_recorded.substring(time_recorded.length()-4).trim();

        final String auth;
        auth = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Toast.makeText(getActivity(),date_substring,Toast.LENGTH_LONG).show();

        lineChartView = rootView.findViewById(R.id.chart);
        final TextView clietz = rootView.findViewById(R.id.clients_text);
        final TextView produtz = rootView.findViewById(R.id.productz_text);
        final TextView deliveriz = rootView.findViewById(R.id.delivery_text);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("vendors");

        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NewApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //adapter = new MyAdapter(VendorActivity.this, list);
                //recyclerView.setAdapter(adapter);
                if (dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {

                    relativeLayout.invalidate();


                    jan = (int)  dataSnapshot.child(auth).child("Orders").child("Jan-" + date_substring).getChildrenCount();
                    feb = (int)  dataSnapshot.child(auth).child("Orders").child("Feb-" + date_substring).getChildrenCount();
                    mar = (int)  dataSnapshot.child(auth).child("Orders").child("Mar-" + date_substring).getChildrenCount();
                    apr = (int)  dataSnapshot.child(auth).child("Orders").child("Apr-" + date_substring).getChildrenCount();
                    may = (int)  dataSnapshot.child(auth).child("Orders").child("May-" + date_substring).getChildrenCount();
                    jun = (int)  dataSnapshot.child(auth).child("Orders").child("Jun-" + date_substring).getChildrenCount();
                    jul = (int)  dataSnapshot.child(auth).child("Orders").child("Jul-" + date_substring).getChildrenCount();
                    aug = (int)  dataSnapshot.child(auth).child("Orders").child("Aug-" + date_substring).getChildrenCount();
                    sep = (int)  dataSnapshot.child(auth).child("Orders").child("Sep-" + date_substring).getChildrenCount();
                    oct = (int)  dataSnapshot.child(auth).child("Orders").child("Oct-" + date_substring).getChildrenCount();
                    nov = (int)  dataSnapshot.child(auth).child("Orders").child("Nov-" + date_substring).getChildrenCount();
                    dec = (int)  dataSnapshot.child(auth).child("Orders").child("Dec-" + date_substring).getChildrenCount();

                    //Toast.makeText(getActivity(), auth, Toast.LENGTH_LONG).show();

                    //checkData();
                    final SummaryClass summaryClass1 = dataSnapshot.child(auth).child("Summary").getValue(SummaryClass.class);


                    String clients_no = String.valueOf( summaryClass1.getClients());
                    String products_no = String.valueOf( summaryClass1.getProducts());
                    String deliveries_no = String.valueOf( summaryClass1.getDeliveries());

                    clietz.setText(clients_no);
                    produtz.setText(products_no);
                    deliveriz.setText(deliveries_no);
                            //produtz.setText(summaryClass1.getProducts().toString());



                    String[] axisData = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};

        int[] yAxisData = {jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec};

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


        //REFERENCE
        //rv= (RecyclerView) rootView.findViewById(R.id.intergalactic_RV);

        //LAYOUT MANAGER
        //rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        //ADAPTER
        //rv.setAdapter(new MyAdapter(getActivity(),spacecrafts));

                }
                else{
                    //Toast.makeText(getActivity(), "Error retrieving data...", Toast.LENGTH_LONG).show();

                    //startActivity(new Intent(getActivity(), MainActivity.class));
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
                //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                //drawer.addDrawerListener(toggle);
                //toggle.syncState();

                //REFERNCE NAV VIEW AND ATTACH ITS ITEM SELECTION LISTENER
                //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                //navigationView.setNavigationItemSelectedListener(SplashScreen.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return rootView;
    }

    @Override
    public String toString() {
        return "InterGalactic";
    }





}
