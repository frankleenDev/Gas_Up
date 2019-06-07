package app.weconnect.gasappgasup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import app.weconnect.gasappgasup.Auth.NumberAuthActivity;
import app.weconnect.gasappgasup.mRecycler.MyAdapter;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreen extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private String clients, products, deliveries;
    private long jan=-1, feb=-1, mar=-1, apr=-1, may=-1, jun=-1, jul=-1, aug=-1, sep=-1, oct=-1, nov=-1, dec=-1;
    private String date_substring, auth;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            /*mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);*/
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        FirebaseUser auth1 = FirebaseAuth.getInstance().getCurrentUser();

        if(auth1==null){

            startActivity(new Intent(getApplicationContext(), NumberAuthActivity.class));

        }

        if(auth1!=null){

            getCount();

        }

        String time_recorded = new SimpleDateFormat("HH:mm:ss dd-MMM-yyyy").format(new Date());
        date_substring = time_recorded.substring(time_recorded.length()-4).trim();

        setContentView(R.layout.activity_splash_screen);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        //mContentView = findViewById(R.id.fullscreen_content);

        //testLong();
        if (auth1!=null) {
            //checkData();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    public void getCount(){

        auth = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("vendors");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                //adapter = new MyAdapter(VendorActivity.this, list);
                //recyclerView.setAdapter(adapter);
                if (dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {


                    jan = dataSnapshot.child(auth).child("Orders").child("Jan-" + date_substring).getChildrenCount();
                    feb = dataSnapshot.child(auth).child("Orders").child("Feb-" + date_substring).getChildrenCount();
                    mar = dataSnapshot.child(auth).child("Orders").child("Mar-" + date_substring).getChildrenCount();
                    apr = dataSnapshot.child(auth).child("Orders").child("Apr-" + date_substring).getChildrenCount();
                    may = dataSnapshot.child(auth).child("Orders").child("May-" + date_substring).getChildrenCount();
                    jun = dataSnapshot.child(auth).child("Orders").child("Jun-" + date_substring).getChildrenCount();
                    jul = dataSnapshot.child(auth).child("Orders").child("Jul-" + date_substring).getChildrenCount();
                    aug = dataSnapshot.child(auth).child("Orders").child("Aug-" + date_substring).getChildrenCount();
                    sep = dataSnapshot.child(auth).child("Orders").child("Sep-" + date_substring).getChildrenCount();
                    oct = dataSnapshot.child(auth).child("Orders").child("Oct-" + date_substring).getChildrenCount();
                    nov = dataSnapshot.child(auth).child("Orders").child("Nov-" + date_substring).getChildrenCount();
                    dec = dataSnapshot.child(auth).child("Orders").child("Dec-" + date_substring).getChildrenCount();

                    //Toast.makeText(getApplicationContext(), auth, Toast.LENGTH_LONG).show();

                    //checkData();

                     FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                     FirebaseAuth mauth = FirebaseAuth.getInstance();


                            String token_id = FirebaseInstanceId.getInstance().getToken();
                            String current_id = mauth.getCurrentUser().getUid();

                            String vendor_name = (String) dataSnapshot.child(current_id).child("Profile").child("agents_name").getValue();

                            Map<String, Object> tokenMap = new HashMap<>();
                            tokenMap.put("token_id", token_id);
                            tokenMap.put("venodr_name", vendor_name);


                            firestore.collection("vendors").document(current_id).set(tokenMap);


                    Intent intent = new Intent(getApplicationContext(), VendorActivity.class);
                    intent.putExtra("jan", jan);
                    intent.putExtra("feb", feb);
                    intent.putExtra("mar", mar);
                    intent.putExtra("apr", apr);
                    intent.putExtra("may", may);
                    intent.putExtra("oct", oct);
                    intent.putExtra("jun", jun);
                    intent.putExtra("jul", jul);
                    intent.putExtra("aug", aug);
                    intent.putExtra("sep", sep);
                    intent.putExtra("nov", nov);
                    intent.putExtra("dec", dec);

                    //Toast.makeText(getApplicationContext(),String.valueOf(jan),Toast.LENGTH_LONG).show();

                    //Toast.makeText(getApplicationContext(),"Vendor...",Toast.LENGTH_LONG).show();
                    startActivity(intent);

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

    }


    public void checkData(){

        DatabaseReference postRef = FirebaseDatabase.getInstance().getReference();

        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    if (data.child("vendors").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()) {
                        //do ur stuff

                        if(jan!=-1) {

                            Intent intent = new Intent(getApplicationContext(), VendorActivity.class);
                            intent.putExtra("jan", jan);
                            intent.putExtra("feb", feb);
                            intent.putExtra("mar", mar);
                            intent.putExtra("apr", apr);
                            intent.putExtra("may", may);
                            intent.putExtra("oct", oct);
                            intent.putExtra("jun", jun);
                            intent.putExtra("jul", jul);
                            intent.putExtra("aug", aug);
                            intent.putExtra("sep", sep);
                            intent.putExtra("nov", nov);
                            intent.putExtra("dec", dec);

                            //Toast.makeText(getApplicationContext(),String.valueOf(jan),Toast.LENGTH_LONG).show();


                            startActivity(intent);
                        }
                        else {

                            //getCount();
                            //testLong();
                            Toast.makeText(getApplicationContext(),"Nothing...!",Toast.LENGTH_LONG).show();

                        }

                    } else {
                        //do something if not exists

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

    }

    public void testLong(){

        //checkData();
    }

}
