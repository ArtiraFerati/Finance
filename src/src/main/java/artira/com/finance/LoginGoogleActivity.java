package artira.com.finance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;

public class LoginGoogleActivity extends AppCompatActivity {

    static final int GOOGLE_SIGN = 123;
    FirebaseAuth mAuth;
    Button btn_login, btn_logout;
    TextView text;
    ImageView image;
    ProgressBar progressBar;
    GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_google);

        btn_login = findViewById(R.id.login);
        btn_logout = findViewById(R.id.logout);
        text = findViewById(R.id.text);
        image = findViewById(R.id.image);
        progressBar = findViewById(R.id.progress_circular);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);


        btn_login.setOnClickListener(v -> SignInGoogle());
        btn_logout.setOnClickListener(v -> Logout());

        if (mAuth.getCurrentUser() != null) {
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);
        }

    }

    void SignInGoogle() {

        progressBar.setVisibility(View.VISIBLE);
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, GOOGLE_SIGN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);

                if (account != null) firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.INVISIBLE);

                        Log.d("TAG", "signin success");

                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);

                    } else {
                        progressBar.setVisibility(View.INVISIBLE);

                        Log.w("TAG", "signin failure", task.getException());

                        Toast.makeText(this, "SignIn failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();


            text.append("Info : \n");
            text.append(name + "\n");
            text.append(email);


            btn_login.setVisibility(View.INVISIBLE);
            btn_logout.setVisibility(View.VISIBLE);

        } else {

            text.setText(getString(R.string.login));

            btn_login.setVisibility(View.VISIBLE);
            btn_logout.setVisibility(View.INVISIBLE);
        }
    }

    void Logout(){

        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                task -> updateUI(null));
    }
}
