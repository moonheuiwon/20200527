package com.example.a20200527;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.a20200527.databinding.ActivitySignUPBinding;
import com.example.a20200527.utils.ServerUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUPActivity extends BaseActivity {

    ActivitySignUPBinding binding;

    boolean idCheckOk = false;
    boolean nickNameCheckOk = false;

//    응용 문제
//    비번은 타이핑 할 때마다 길이 검사
//    => 0글자 : 비밀번호를 입력해주세요.
//    => 8글자 미만 : 비밀번호가 너무 짧습니다.
//    그이상 : 사용해도 좋은 비밀 번호입니다.

//    비번 확인도 타이핑 할 때마다 검사.
//    => 0글자 : 비밀번호 확인을 입력해주세요.
//    => 비번과 같다 : 비밀번호 재입력이 확인 되었습니다.
//    => 다르다 : 비밀번호가 서로 다릅니다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_u_p);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        binding.nickNameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nickNameCheckOk = false;
                binding.nickNameCheckResultTxt.setText("중복검사를 해주세요.");

                checkSignUpEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        응용문제
//        닉네임 중복확인 버튼 => 서버에 중복확인 요청 (문서 참조)
//        => 성공일 경우 "사용해도 좋습니다." 토스트
//        => 실패일경우 "중복된 닉네임입니다." 토스트

        binding.emailEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

//                email을 변경하면 무조건 중복검사를 실패로 변경 => 제검사 요구
                idCheckOk = false;
                binding.idCheckResultTxt.setText("중복 검사를 진행해주세요.");

//                버튼을 비활성화로 체크
                checkSignUpEnable();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        binding.nickNameCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputNick = binding.nickNameEdt.getText().toString();

                ServerUtil.getRequestDuplicatedCheck(mContext, inputNick, "NICKNAME", new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {
                        Log.d("로그확인", json.toString());

                        try {
                            int code = json.getInt("code");

                            if (code == 200) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        binding.nickNameCheckResultTxt.setText("사용해도 좋은 닉네임입니다.");
                                        nickNameCheckOk = true;

                                    }
                                });
                            }
                            else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        binding.nickNameCheckResultTxt.setText("중복된 닉네임입니다.");

                                    }
                                });
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    checkSignUpEnable();
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        binding.idCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail = binding.emailEdt.getText().toString();

                ServerUtil.getRequestDuplicatedCheck(mContext, inputEmail, "EMAIL", new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {
                        Log.d("중복응답확인", json.toString());

                        try {
                            int code = json.getInt("code");

                            if (code == 200) {
//                                중복검사 통과
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, "사용해도 좋은 아이디입니다.", Toast.LENGTH_SHORT).show();
                                        binding.idCheckResultTxt.setText("사용해도 좋은 아이디입니다.");

                                        idCheckOk = true;
                                    }
                                });

                            }
                            else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
//                                      중복 혹은 문제가있어서 통과가 안됨 Ex. 400으로 나옴 (404 - not found / 403 - 권한 없음)
                                        Toast.makeText(mContext, "중복되는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                                        binding.idCheckResultTxt.setText("중복되는 아이디 입니다.");
                                    }
                                });

                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    checkSignUpEnable();
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        binding.pwEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                checkSignUpEnable();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.pwRepeatEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                checkSignUpEnable();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    boolean checkPasswords() {

        boolean isPwOk = false;

        String pw = binding.pwEdt.getText().toString();

        if (pw.length() == 0) {
            binding.pwCheckResultEdt.setText("비밀번호를 입력해주세요");
        }
        else if (pw.length() < 8) {
            binding.pwCheckResultEdt.setText("비밀번호가 너무 짧습니다.");
        }
        else  {
            binding.pwCheckResultEdt.setText("사용해도 좋은 비밀번호입니다.");
            isPwOk = true;
        }

        boolean isPwRepeatOk = false;
        String pwRepeat = binding.pwRepeatEdt.getText().toString();

        if (pwRepeat.length() == 0 ) {
            binding.pwRepeatCheckResultEdt.setText("비밀번호 확인을 입력해주세요.");
        }
        else if (pwRepeat.equals(pw)) {
            binding.pwRepeatCheckResultEdt.setText("비밀번호 재입력이 확인되었습니다.");
            isPwRepeatOk = true;
        }
        else {
            binding.pwRepeatCheckResultEdt.setText("비밀번호가 서로 다릅니다.");
        }
        return  isPwOk && isPwRepeatOk;
    }

    void checkSignUpEnable() {

        boolean isAllPassWordOk = checkPasswords();

//        boolean isIdDupCheckOk = idCheckOk;

        binding.signUpBtn.setEnabled(isAllPassWordOk && idCheckOk && nickNameCheckOk);

    }

    @Override
    public void setValues() {

    }
}
