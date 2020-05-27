package com.example.a20200527;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.a20200527.databinding.ActivityLoginBinding;
import com.example.a20200527.databinding.ActivityLoginBinding ;
import com.example.a20200527.utils.ServerUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class loginActivity extends BaseActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = binding.emailEdt.getText().toString();
                String password = binding.pwEdt.getText().toString();

                ServerUtil.postRequestLogin(mContext, email, password, new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {

                        Log.d("JSON확인", json.toString());

                        try {
                            int code = json.getInt("code");
                            if (code == 200) {
                                Log.d("분석결과", "로그인 성공");
                            }
                            else  {
                                Log.d("분석결과", " 로그인 실패");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });

    }

    @Override
    public void setValues() {

    }
}
