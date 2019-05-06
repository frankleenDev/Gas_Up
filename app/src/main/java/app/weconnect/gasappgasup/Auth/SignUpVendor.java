package app.weconnect.gasappgasup.Auth;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

                if (TextUtils.isEmpty(branch_name)) {
                    Toast.makeText(getApplicationContext(), "Enter a branch name..!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(getApplicationContext(), "Enter mobile number address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(company_name)) {
                    Toast.makeText(getApplicationContext(), "Enter Company name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpVendor.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignUpVendor.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    firebaseDatabase = FirebaseDatabase.getInstance();

                                    // get reference to 'users' node
                                    //dataRef = firebaseDatabase.getReference("users");

                                    //String userId = firebaseDatabase.getReference("users").push().getKey();
                                    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

                                    // store app title to 'app_title' node
                                    firebaseDatabase.getReference("app_title").setValue("Gas App Gas Up");

                                    VendorProfile userProfile = new VendorProfile(agent_name, branch_name, company_name, mobile, email, currentFirebaseUser.getUid());
                                    SummaryClass summaryClass = new SummaryClass(Long.valueOf(0),Long.valueOf(0),Long.valueOf(0));

                                    firebaseDatabase.getReference("vendors").child(currentFirebaseUser.getUid()).child("Profile").setValue(userProfile);
                                    firebaseDatabase.getReference("vendors").child(currentFirebaseUser.getUid()).child("Summary").setValue(summaryClass);

                                    startActivity(new Intent(getApplicationContext(), SplashScreen.class));
                                    finish();
                                }
                            }
                        });



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