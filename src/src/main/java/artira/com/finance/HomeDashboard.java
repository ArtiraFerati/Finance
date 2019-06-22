package artira.com.finance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class HomeDashboard extends AppCompatActivity implements View.OnClickListener {

    private CardView phoneLogin, googleLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_dashboard);

        phoneLogin = (CardView) findViewById(R.id.loginPhone);
        googleLogin = (CardView) findViewById(R.id.loginGoogle);

        phoneLogin.setOnClickListener(this);
        googleLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent i;

        switch (v.getId()){

            case R.id.loginPhone : i = new Intent(this, LoginActivity.class); startActivity(i); break;
            case R.id.loginGoogle : i = new Intent(this, LoginGoogleActivity.class); startActivity(i); break;
            default: break;
        }

    }
}
