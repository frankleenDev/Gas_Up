package app.weconnect.gasappgasup.Auth;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import app.weconnect.gasappgasup.R;
import app.weconnect.gasappgasup.SplashScreen;
import app.weconnect.gasappgasup.SummaryClass;

public class SignUpVendor extends AppCompatActivity {

    private EditText agents_name, inputPassword, branch, company, agents_email, mobilenumber;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private DatabaseReference dataRef;
    private FirebaseDatabase firebaseDatabase;

    private String agent_name, lname, branch_name, mobile, email, company_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_vendor);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        agents_name = (EditText) findViewById(R.id.agents_name);
        branch = (EditText) findViewById(R.id.branch_name);
        company = (EditText) findViewById(R.id.company);
        mobilenumber = (EditText) findViewById(R.id.mobile_number);
        agents_email = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpVendor.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                agent_name = agents_name.getText().toString().trim();
                //lname = branch.getText().toString().trim();
                branch_name = branch.getText().toString().trim();
                mobile = mobilenumber.getText().toString().trim();
                email = agents_email.getText().toString().trim();
                company_name = company.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(agent_name)) {
                    Toast.makeText(getApplicationContext(), "Enter agent's name..!", Toast.LENGTH_SHORT).show();
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);

                DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("users");
                ref.orderByChild("Phone").equalTo(agent_name).addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot datas : dataSnapshot.getChildren()) {
                            String keys = datas.getKey();

                            Toast.makeText(getApplicationContext(),keys,Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }});


                /*
                Query dateQuery = FirebaseDatabase.getInstance().getReference("users").orderByChild("Phone").equalTo(agent_name);

                dateQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            //ExistD();

                            String found = (String) dataSnapshot.getKey();

                            Toast.makeText(getApplicationContext(),found,Toast.LENGTH_LONG).show();


                        } else {

                            Toast.makeText(getApplicationContext(),"Data not found..",Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                    // put rest of your code here
                });
                */


            }
        });




    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(getApplicationContext(), LoginActivity.class));

        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}