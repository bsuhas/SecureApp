package com.vs.kook.view.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.vs.kook.R;
import com.vs.kook.utils.DialogUtils;
import com.vs.kook.utils.UserSharedPreferences;
import com.vs.kook.view.activity.LoginScreenActivity;
import com.vs.kook.view.activity.MainActivity;
import com.vs.kook.view.models.LoginReqModel;
import com.vs.kook.view.models.LoginResponseModel;
import com.vs.kook.view.models.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by user on 18/4/16.
 */
public class LogoutNetworking extends AbstractNetworking {


    private static final String URL = "http://192.168.0.115/kook/signOut.php";
    private static final int TYPE = Request.Method.POST;

    private final Handler handler;

    /*
    handler is used to take actions after response...
    ex. call next activity or call next API... etc
    handler actions are handled in activity
     */
    public LogoutNetworking(boolean isForeground, Context context , Handler handler) {
        super(isForeground, context , TYPE , URL);
        this.handler = handler;
    }



    @Override
    protected void setParams(Object obj) throws JSONException {
        JSONObject param = new JSONObject();
        UserInfo userInfo = UserSharedPreferences.getInstance(context).getUserPreferences();
      /*  param.put("email_id", userInfo.getUserEmail());
        param.put("user_id",userInfo.getUseId());*/
        param.put("email_id", "Bsuhas@gmail.com");
        param.put("user_id","23344");
        this.params = param;
        Log.e("logout" , "logout params = " + params);
    }


    @Override
    public void onResponse(String response) {
        super.onResponse(response);
        Log.e("logout","logout response = "+ response);

        if(!hasError) {
            try {
                handler.sendEmptyMessage(MainActivity.START_NEXT_ACTIVITY);
            } catch (Exception e) {
                e.printStackTrace();
                if (isForeground)
                    DialogUtils.showDialog(context, context.getString(R.string.error), e.getLocalizedMessage(),
                            context.getString(R.string.ok), context.getString(R.string.cancel), null);
            }
        }else{
            handler.sendEmptyMessage(MainActivity.EROOR);
        }
    }


    @Override
    protected void parseJsonAndInsert(String response) throws Exception {
        Gson gson = new Gson();
        LoginResponseModel loginResponseModel = gson.fromJson(response, LoginResponseModel.class);
        if (loginResponseModel.getError_code()==000) {
            UserSharedPreferences.getInstance(context).clearUserPreferences();
        } else {
            DialogUtils.showAlertDialog(context, context.getString(R.string.error), loginResponseModel.getErrorMsg());
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        super.onErrorResponse(error);
    }
}
