package app.weconnect.gasappgasup;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import app.weconnect.gasappgasup.mRecycler.SearchAdapter;
import app.weconnect.gasappgasup.mRecycler.SetItemsAdapter;
import app.weconnect.gasappgasup.mRecycler.VendorAdapter;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private EditText search_txt;
    private ImageView search_btn;
    private String user_key;
    private DatabaseReference reference;

    ArrayList<User> userz;
    SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        search_txt = findViewById(R.id.search_field);
        search_btn = findViewById(R.id.search_btn);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String search_no = search_txt.getText().toString().trim();

                if(search_no.isEmpty()){

                    Toast.makeText(getApplicationContext(),"Enter a phone number...",Toast.LENGTH_SHORT).show();
                }
                else{

                    DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("users");
                    ref.orderByChild("Phone").equalTo(search_no).addValueEventListener(new ValueEventListener(){
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot datas : dataSnapshot.getChildren()) {

                                if(datas.exists()){

                                    user_key = datas.getKey();

                                    get_user();

                                    //Toast.makeText(getApplicationContext(),get_number,Toast.LENGTH_LONG).show();
                                }
                                if(!(datas.exists())){
                                    Toast.makeText(getApplicationContext(),"Data not found...",Toast.LENGTH_LONG).show();

                                }

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }});

                }


            }
        });


    }


    private void get_user(){

        //Adapter setter

        reference = FirebaseDatabase.getInstance().getReference("users").child(user_key).child("userMetaData");

        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userz = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    User p = dataSnapshot1.getValue(User.class);
                    userz.add(p);

                    //Toast.makeText(getApplicationContext(),p.getUid(),Toast.LENGTH_SHORT).show();
                }

                recyclerView = findViewById(R.id.list_view);

                searchAdapter = new SearchAdapter(SearchActivity.this, userz);
                recyclerView.setAdapter(searchAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
