package com.vs.kook.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.vs.kook.R;

import org.json.JSONObject;

/**
 * Created by SUHAS on 06/05/2016.
 */
public class LoginScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginScreenActivity";
    private EditText mEdtUserName;
    private EditText mEdtPassword;
    private Button mBtnLogin;
    private String mUsername;
    private String mPassword;
    private boolean isKeepMeLogin = false;
    private CheckBox mChkKeepLogin;

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
                break;
        }
    }

    private void validateAndLogin() {
        if (validateInputFields()) {
//            String auth = Utils.getBase64EncodedString(mUsername + ":" + mPassword);
            String auth = "";
            JSONObject obj = new JSONObject();
            try {

                obj.put("username", mUsername);
                obj.put("password", mPassword);

            } catch (Exception e) {
                e.printStackTrace();
            }
//TODO API Call
//            AsyncTaskManager loginTask = new AsyncTaskManager(LoginScreenActivity.this, Constants.REQUEST_ID_AUTHENTICATE, auth,obj.toString());
//            loginTask.execute(Constants.URL_AUTHENTICATE);
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
        if (mChkKeepLogin.isChecked()) {
            isKeepMeLogin = true;
        } else {
            isKeepMeLogin = false;
        }
        return isValid;
    }
}
