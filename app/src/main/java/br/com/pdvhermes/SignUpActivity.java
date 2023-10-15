package br.com.pdvhermes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    private EditText mEditTextName, mEditTextEmail, mEditTextPassword, mEditTextPassword2;
    private Button mButtonSignUp;
    private TextView mTextViewAlreadyLogin;
    private ProgressBar mProgressBar;

    private String mStringName, mStringEmail, mStringPassword, mStringPassword2 , mStringNivelAcesso;

    private boolean isRequired(){
        if(TextUtils.isEmpty(mEditTextName.getText())          ||
                TextUtils.isEmpty(mEditTextEmail.getText())    ||
                TextUtils.isEmpty(mEditTextPassword.getText()) ||
                TextUtils.isEmpty(mEditTextPassword2.getText()) ){
            return true;
        } else {
            return false;
        }
    }

    private boolean isSamePassword(){
        String mPass1 = mEditTextPassword.getText().toString().trim();
        String mPass2 = mEditTextPassword2.getText().toString().trim();
        return mPass1.equals(mPass2);
    }

    private void performActivityLogin(){
        Intent mIntent = new Intent(getApplicationContext() , LoginActivity.class);
        startActivity(mIntent);
        finish();
    }

    private void postData(){
        if(isRequired()){
            String mTextMessage = getString(R.string.text_error_all_fields_required);
            Toast.makeText(getApplicationContext(), mTextMessage , Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isSamePassword()){
            String mTextMessage = getString(R.string.text_password_are_not_same);
            Toast.makeText(getApplicationContext(), mTextMessage , Toast.LENGTH_SHORT).show();
            return;
        }

        mStringName = String.valueOf(mEditTextName.getText());
        mStringEmail = String.valueOf(mEditTextEmail.getText()).toLowerCase(Locale.ROOT);
        mStringPassword = String.valueOf(mEditTextPassword.getText()).toString().trim();
        mStringPassword2 = String.valueOf(mEditTextPassword2.getText()).trim();

        mProgressBar.setVisibility(View.VISIBLE);

        User mUser = new User(mStringName , mStringEmail , mStringPassword , mStringNivelAcesso);

        int vResult = UserDao.insertUser(mUser , getApplicationContext());

        String mTextMessage;

        mProgressBar.setVisibility(View.GONE);

        if (vResult <= 0){
            mTextMessage = getString(R.string.text_insert_error);

        } else {
            mTextMessage = getString(R.string.text_insert_success);
        }

        Intent mIntent = new Intent(getApplicationContext() , LoginActivity.class);
        startActivity(mIntent);
        finish();

    }

    public class ClickMyButtonSignUp implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            postData();
        }
    }

    public class ClickMyViewAlreadyLogin implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            performActivityLogin();
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEditTextEmail = findViewById(R.id.editText_email);
        mEditTextName = findViewById(R.id.editText_name);
        mEditTextPassword = findViewById(R.id.editText_password_sign_up);
        mEditTextPassword2 = findViewById(R.id.editText_password_sign_up_2);

        mTextViewAlreadyLogin = findViewById(R.id.button_have_account);
        mTextViewAlreadyLogin.setOnClickListener(new ClickMyViewAlreadyLogin());

        mProgressBar = findViewById(R.id.progressBarSignUp);

        mButtonSignUp = findViewById(R.id.button_sign_up);
        mButtonSignUp.setOnClickListener(new ClickMyButtonSignUp());

    }
}
