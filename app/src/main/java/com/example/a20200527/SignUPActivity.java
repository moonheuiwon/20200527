package com.example.a20200527;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.a20200527.databinding.ActivitySignUPBinding;

public class SignUPActivity extends BaseActivity {

    ActivitySignUPBinding binding;

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

        boolean isIdDupCheckOk = true;

        binding.signUpBtn.setEnabled(isAllPassWordOk && isIdDupCheckOk);

    }

    @Override
    public void setValues() {

    }
}
