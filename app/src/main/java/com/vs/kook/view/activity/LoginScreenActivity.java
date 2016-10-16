package com.vs.kook.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vs.kook.R;
import com.vs.kook.utils.AppUtils;
import com.vs.kook.view.models.LoginReqModel;
import com.vs.kook.view.network.LoginNetworking;

import org.json.JSONException;

/**
 * Created by SUHAS on 06/05/2016.
 */
public class LoginScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginScreenActivity";
    private static final int START_NEXT_ACTIVITY = 100;
    private static final int EROOR = 101;
    private EditText mEdtUserName;
    private EditText mEdtPassword;
    private Button mBtnLogin;
    private String mUsername;
    private String mPassword;
    private boolean isKeepMeLogin = false;



    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START_NEXT_ACTIVITY:
                    break;
                case EROOR:
                    break;

            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen_layout);
        init();
    }

    private void init() {

        mEdtUserName = (EditText) findViewById(R.id.login_et_username);
        mEdtPassword = (EditText) findViewById(R.id.login_et_password);
        mBtnLogin = (Button) findViewById(R.id.login_button_login);

        mBtnLogin.setOnClickListener(this);

        mEdtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    validateAndLogin();
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button_login:
//                validateAndLogin();
                Intent intent = new Intent(LoginScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void validateAndLogin() {
        if (validateInputFields()) {
            LoginReqModel model = new LoginReqModel();
            model.setEmailId(mUsername);
            model.setPassword(mPassword);
            model.setDeviceId(AppUtils.getIMEI(this));

            LoginNetworking loginNetworking = new LoginNetworking(true,this,handler);
            try {
                loginNetworking.makeRequestAndInsert(model);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateInputFields() {
        boolean isValid = true;
        mUsername = mEdtUserName.getText().toString();
        mPassword = mEdtPassword.getText().toString();
        if (mUsername == null || mUsername.equals("")) {
            mEdtUserName.setError(getString(R.string.username_validation_msg));
            isValid = false;
        }
        if (mPassword == null || mPassword.equals("") || mPassword.length() < 6) {
            mEdtPassword.setError(getString(R.string.password_validation_msg));
            isValid = false;
        }

        return isValid;
    }
}
