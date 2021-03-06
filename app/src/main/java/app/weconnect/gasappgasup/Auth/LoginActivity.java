package app.weconnect.gasappgasup.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.google.firebase.database.ValueEventListener;

import app.weconnect.gasappgasup.MainActivity;
import app.weconnect.gasappgasup.R;
import app.weconnect.gasappgasup.VendorActivity;
import app.weconnect.gasappgasup.VendorDashBoard;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        if (auth.getCurrentUser() != null) {


            FirebaseDatabase.getInstance().getReference()
                    .child("vendors").child(currentFirebaseUser.getUid()).child("Profile").child("userID")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()){

                                //do something
                                Toast.makeText(getApplicationContext(),dataSnapshot.getValue().toString(),Toast.LENGTH_LONG).show();

                                startActivity(new Intent(getApplicationContext(), VendorActivity.class));

                            } else {

                                startActivity(new Intent(LoginActivity.this, VendorDashBoard.class));
                                finish();

                                //mProgressDialog.dismiss();
                                //Snackbar.make(view, "datasnapshot is null", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                            progressBar.setVisibility(View.GONE);
                        }
                    });

        }

        // set the view now
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        inputEmail    = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar   = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup     = (Button) findViewById(R.id.btn_signup);
        btnLogin      = (Button) findViewById(R.id.btn_login);
        btnReset      = (Button) findViewById(R.id.btn_reset_password);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpVendor.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {

                                    FirebaseDatabase.getInstance().getReference()
                                            .child("vendors").child(auth.getCurrentUser().getUid()).child("Profile").child("userID")
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {

                                                    if (dataSnapshot.exists()){

                                                        //do something
                                                        Toast.makeText(getApplicationContext(),dataSnapshot.getValue().toString(),Toast.LENGTH_LONG).show();
                                                        startActivity(new Intent(getApplicationContext(),VendorDashBoard.class));

                                                    } else {

                                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                                        //mProgressDialog.dismiss();
                                                        //Snackbar.make(view, "datasnapshot is null", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                    progressBar.setVisibility(View.GONE);
                                                }
                                            });

                                    //Intent intent = new Intent(LoginActivity.this, NewsActivity.class);
                                    //startActivity(intent);
                                    //finish();
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(getApplicationContext(),SignUpVendor.class));

        super.onBackPressed();
    }
}

