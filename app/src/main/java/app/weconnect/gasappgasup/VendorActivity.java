package app.weconnect.gasappgasup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import app.weconnect.gasappgasup.Auth.NumberAuthActivity;
import app.weconnect.gasappgasup.mFragments.InterGalactic;
import app.weconnect.gasappgasup.mFragments.InterPlanetary;
import app.weconnect.gasappgasup.mFragments.InterStellar;
import app.weconnect.gasappgasup.mFragments.InterUniverse;
import app.weconnect.gasappgasup.mRecycler.MyAdapter;
import app.weconnect.gasappgasup.mRecycler.MyOrders;
import app.weconnect.gasappgasup.mRecycler.SetItemsAdapter;
import app.weconnect.gasappgasup.mRecycler.VendorAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class VendorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseReference reference, reference2;
    RecyclerView recyclerView;
    ArrayList<VendorClass> list3;
    ArrayList<PredefinedItems> list4;
    VendorAdapter vendorAdapter;
    SetItemsAdapter setItemsAdapter;
    DatabaseReference databaseReference;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.vendor_main_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.signOut)

            signOut();

        return true;
    }

    private void signOut(){

        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                startActivity(new Intent(getApplicationContext(), NumberAuthActivity.class));

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_orders);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle;

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView phone_number = (TextView) headerView.findViewById(R.id.my_number);
        //navUsername.setText("Your Text Here");

        if(getIntent()!=null){

            phone_number.setText(getIntent().getStringExtra("phone"));

        }

        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer)
            {

                public void onDrawerClosed(View view)
                {
                    supportInvalidateOptionsMenu();
                    //drawerOpened = false;
                }

                public void onDrawerOpened(View drawerView)
                {
                    supportInvalidateOptionsMenu();
                    //drawerOpened = true;
                }
            };
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            drawerLayout.setDrawerListener(mDrawerToggle);
            mDrawerToggle.syncState();
        }

        //FirebaseApp.initializeApp(getApplicationContext());
        //FirebaseDatabase databaseReference = FirebaseDatabase.getInstance(); // THIS LINE IS CRASHING
        //myRef = database.getReference("remoteControl");
        reference = FirebaseDatabase.getInstance().getReference().child("Shop");

        VendorOrderz();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Shop");

    }

    //CLOSE DRAWER WHEN BACK BTN IS CLICKED,IF OPEN
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //RAISED WHEN NAV VIEW ITEM IS SELECTED
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //OPEN APPROPRIATE FRAGMENT WHEN NAV ITEM IS SELECTED
        if (id == R.id.dashboard) {
            //PERFORM TRANSACTION TO REPLACE CONTAINER WITH FRAGMENT
            //Shop();
            VendorActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.containerID, InterPlanetary.newInstance()).commit();

        } else if (id == R.id.orderz) {

            setItemz();
            VendorActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.containerID, InterStellar.newInstance()).commit();

        } else if (id == R.id.intergalactic) {
            VendorActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.containerID, InterGalactic.newInstance()).commit();

        } else if (id == R.id.interuniverse) {
            VendorActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.containerID, InterUniverse.newInstance()).commit();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        //REFERENCE AND CLOSE DRAWER LAYOUT
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setItemz(){

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list4 = new ArrayList<PredefinedItems>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    PredefinedItems p = dataSnapshot1.getValue(PredefinedItems.class);
                    list4.add(p);
                }
                setItemsAdapter = new SetItemsAdapter(VendorActivity.this, list4);
                recyclerView.setAdapter(setItemsAdapter);

                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
                        startActivity(new Intent(getApplicationContext(), NumberAuthActivity.class));
                    }
                });

                //REFERENCE DRAWER,TOGGLE ITS INDICATOR
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                //drawer.addDrawerListener(toggle);
                //toggle.syncState();

                //REFERNCE NAV VIEW AND ATTACH ITS ITEM SELECTION LISTENER
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                navigationView.setNavigationItemSelectedListener(VendorActivity.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void VendorOrderz(){

        String time_recorded = new SimpleDateFormat("HH:mm:ss dd-MMM-yyyy").format(new Date());
        String date_substring = time_recorded.substring(time_recorded.length()-11);
        final String month_year = date_substring.substring(date_substring.length()-8);

        reference2 = FirebaseDatabase.getInstance().getReference("vendors").child("t5Ay6Hs59mO1bMdJL0EJsVofeoA2").child("Orders").child(month_year);

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot3) {
                list3 = new ArrayList<VendorClass>();
                for (DataSnapshot dataSnapshot2 : dataSnapshot3.getChildren()) {
                    VendorClass o = dataSnapshot2.getValue(VendorClass.class);
                    list3.add(o);

                    // Toast.makeText(getApplicationContext(),o.getType(),Toast.LENGTH_LONG).show();
                }
                vendorAdapter = new VendorAdapter(VendorActivity.this, list3);
                recyclerView.setAdapter(vendorAdapter);

                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();

                        startActivity(new Intent(getApplicationContext(), NumberAuthActivity.class));
                    }
                });

                //REFERENCE DRAWER,TOGGLE ITS INDICATOR
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                //drawer.addDrawerListener(toggle);
                //toggle.syncState();

                //REFERNCE NAV VIEW AND ATTACH ITS ITEM SELECTION LISTENER
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                navigationView.setNavigationItemSelectedListener(VendorActivity.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
