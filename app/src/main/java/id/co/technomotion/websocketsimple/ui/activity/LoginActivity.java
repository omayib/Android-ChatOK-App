package id.co.technomotion.websocketsimple.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.greenrobot.event.EventBus;
import id.co.technomotion.websocketsimple.Application.ChatApplication;
import id.co.technomotion.websocketsimple.R;
import id.co.technomotion.websocketsimple.model.event.OnLoginSucceeded;

/**
 * Created by omayib on 7/7/15.
 */
public class LoginActivity extends Activity {

    private EditText editTextUsername;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EventBus.getDefault().register(this);

        editTextUsername= (EditText) findViewById(R.id.editTextUsername);
        btnLogin= (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editTextUsername.getText().toString().isEmpty())
                    return;


                ChatApplication app= (ChatApplication) getApplication();
                app.getChat().init();
                app.getChat().login(editTextUsername.getText().toString());
//                app.getChat().toHeroku(editTextUsername.getText().toString());

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(OnLoginSucceeded e){
        startActivity(new Intent (getApplicationContext(),MainActivity.class));
        finish();
    }
}
