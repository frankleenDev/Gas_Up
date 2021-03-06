package app.weconnect.gasappgasup;

import android.annotation.SuppressLint;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import app.weconnect.gasappgasup.Auth.NumberAuthActivity;
import app.weconnect.gasappgasup.Auth.SignUpVendor;
import app.weconnect.gasappgasup.mFragments.InterGalactic;
import app.weconnect.gasappgasup.mFragments.InterPlanetary;
import app.weconnect.gasappgasup.mFragments.InterStellar;
import app.weconnect.gasappgasup.mFragments.InterUniverse;
import app.weconnect.gasappgasup.mRecycler.DashBoardAdapter;
import app.weconnect.gasappgasup.mRecycler.MyAdapter;
import app.weconnect.gasappgasup.mRecycler.MyOrders;
import app.weconnect.gasappgasup.mRecycler.SetItemsAdapter;
import app.weconnect.gasappgasup.mRecycler.VendorAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VendorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseReference reference, reference3, reference2;
    RecyclerView recyclerView;
    ArrayList<VendorClass> list3;
    ArrayList<PredefinedItems> list4;
    ArrayList<SummaryClass> summaryClasses;
    VendorAdapter vendorAdapter;
    DashBoardAdapter dashBoardAdapter;
    SetItemsAdapter setItemsAdapter;
    DatabaseReference databaseReference;

    private int janCount, febCount, marCount, aprCount, mayCount, junCount, julCount, augCount, sepCount, octCount, novCount, decCount;



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

    public void signOut(){

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view2);
        View headerView = navigationView.getHeaderView(0);
        TextView phone_number = (TextView) headerView.findViewById(R.id.my_number);
        //navUsername.setText("Your Text Here");

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

        if(getIntent()!=null){

            phone_number.setText(getIntent().getStringExtra("phone"));

        }

        //setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar == null)
        {
            //actionBar.setDisplayHomeAsUpEnabled(true);
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
        reference3 = FirebaseDatabase.getInstance().getReference().child("News");

        //VendorActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.containerID, InterGalactic.newInstance()).commit();


        //VendorOrderz();
        dashBoard();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //recyclerView3 = (RecyclerView) findViewById(R.id.intergalactic_RV);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Shop");

    }

    //CLOSE DRAWER WHEN BACK BTN IS CLICKED,IF OPEN
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

            dashBoard();

        } else if (id == R.id.orderz) {

            VendorOrderz();
            //VendorActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.containerID, InterStellar.newInstance()).commit();

        }  else if (id == R.id.sign_out2) {

            FirebaseFirestore fr = FirebaseFirestore.getInstance();
            FirebaseAuth fa = FirebaseAuth.getInstance();
            String current_id = fa.getCurrentUser().getUid();

            Map<String, Object> remove_id = new HashMap<>();
            remove_id.put("token_id", "");

            //Toast.makeText(getApplicationContext(),current_id,Toast.LENGTH_SHORT).show();

            fr.collection("vendors").document(current_id).update(remove_id).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    signOut();

                }
            });


        } else if (id == R.id.productz) {

            setItemz();
            VendorActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.containerID, InterStellar.newInstance()).commit();


        }else if (id == R.id.shop) {

            Shop();


        } else if (id == R.id.add_vendor) {

            //startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            startActivity(new Intent(getApplicationContext(), SearchActivity.class));
        }

        //REFERENCE AND CLOSE DRAWER LAYOUT
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setItemz(){

        reference3.addValueEventListener(new ValueEventListener() {
            @SuppressLint("RestrictedApi")
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

                fab.setVisibility(View.GONE);

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
                        //startActivity(new Intent(getApplicationContext(), NumberAuthActivity.class));
                    }
                });

                //REFERENCE DRAWER,TOGGLE ITS INDICATOR
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                //drawer.addDrawerListener(toggle);
                //toggle.syncState();

                //REFERNCE NAV VIEW AND ATTACH ITS ITEM SELECTION LISTENER
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view2);
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


        final String firebaseAuth = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Toast.makeText(getApplicationContext(),firebaseAuth,Toast.LENGTH_LONG).show();
        reference2 = FirebaseDatabase.getInstance().getReference("vendors").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Orders").child(month_year);

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
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view2);
                navigationView.setNavigationItemSelectedListener(VendorActivity.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void dashBoard(){


        final String firebaseAuth = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference reference4 = FirebaseDatabase.getInstance().getReference("vendors").child(firebaseAuth).child("Summary");

        reference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot3) {
                summaryClasses = new ArrayList<SummaryClass>();
                for (DataSnapshot dataSnapshot5 : dataSnapshot3.getChildren()) {
                    SummaryClass x = dataSnapshot5.getValue(SummaryClass.class);
                    summaryClasses.add(x);

                    //Toast.makeText(getApplicationContext(),firebaseAuth,Toast.LENGTH_LONG).show();

                    dashBoardAdapter = new DashBoardAdapter(VendorActivity.this, summaryClasses);
                    recyclerView.setAdapter(dashBoardAdapter);



                }

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
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view2);
                navigationView.setNavigationItemSelectedListener(VendorActivity.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void Shop(){

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               ArrayList<Products> list = new ArrayList<Products>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Products p = dataSnapshot1.getValue(Products.class);
                    list.add(p);
                }
                MyAdapter adapter = new MyAdapter(VendorActivity.this, list);
                recyclerView.setAdapter(adapter);

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


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
