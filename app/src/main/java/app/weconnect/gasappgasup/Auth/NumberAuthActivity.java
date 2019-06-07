package app.weconnect.gasappgasup.Auth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import app.weconnect.gasappgasup.MainActivity;
import app.weconnect.gasappgasup.R;
import app.weconnect.gasappgasup.SplashScreen;
import app.weconnect.gasappgasup.User;

public class NumberAuthActivity extends AppCompatActivity {

    private final int REQUEST_LOGIN = 100;

    private FirebaseAuth auth;
    private String authNumber;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_auth);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {

            if (!auth.getCurrentUser().getPhoneNumber().isEmpty()) {

                startActivity(new Intent(getApplicationContext(), SplashScreen.class)
                        .putExtra("phone", auth.getCurrentUser().getPhoneNumber())
                );
                finish();

            }
        }else {

                //authNumber = FirebaseAuth.getInstance().getCurrentUser().getUid();

                startActivityForResult(AuthUI.getInstance()
                        .createSignInIntentBuilder().setAvailableProviders(
                                Arrays.asList(
                                        new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()
                                )).build(), REQUEST_LOGIN);
            }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_LOGIN){


            IdpResponse response = IdpResponse.fromResultIntent(data);

            //Successful Signed In
            if(resultCode == RESULT_OK)
            {
                authNumber = FirebaseAuth.getInstance().getCurrentUser().getUid();

                if(!FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().isEmpty()){

                    String mobile_phone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

                    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

                    User user = new User(authNumber,mobile_phone);

                    database.child("users").child(authNumber).child("Profile/Phone").setValue(mobile_phone);
                    database.child("users").child(authNumber).child("userMetaData").child("root").setValue(user);

                    String token_id = FirebaseInstanceId.getInstance().getToken();


                    Map<String, Object> uzer = new HashMap<>();
                    uzer.put("user_id", authNumber);
                    uzer.put("phone_number", mobile_phone);
                    uzer.put("token_id", token_id);

                    db.collection("Users").document(authNumber).set(uzer);

                    startActivity(new Intent(this, SplashScreen.class)
                            .putExtra("phone", FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()));
                    finish();
                    return;

                }

                else { //sign in failed

                    if(response == null){

                        Toast.makeText(getApplicationContext(),"Cancelled", Toast.LENGTH_LONG).show();
                        return;

                    }
                    if(response.getErrorCode() == ErrorCodes.NO_NETWORK){

                        Toast.makeText(getApplicationContext(),"No Internet", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if(response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {

                        Toast.makeText(getApplicationContext(), "Unknow Error..!", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                Toast.makeText(getApplicationContext(),"Unknow Sign in Error..!", Toast.LENGTH_LONG).show();


            }

        }


    }

}

