package br.com.pdvhermes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "Login Activity";

    TextView mButtonNewUser, mButtonForgotPassword;
    Button mButtonLogin;
    EditText mEditTextEmail, mEditTextPassword;
    ProgressBar mProgressBarLogin;
    String mStringUser, mStringPassword , mStringEmail , mNivelAcesso;
    SharedPreferences mSharedPreferences;

    private boolean isRequiredPassword(){
        return TextUtils.isEmpty(mEditTextPassword.getText());
    }

    private boolean isValidEmail(String mStringEmail){
        if(mStringEmail == null || mStringEmail.isEmpty()){
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(mStringEmail).matches();
    }

    private void showMain(){
        Intent mIntent = new Intent(getApplicationContext() , MainActivity.class);
        startActivity(mIntent);
        finish();
    }

    private void verifyLogged(){
        if(mSharedPreferences.getString("Logged", "False").equals("true")){
            showMain();
        }
    }

    private void postData(){
        mStringEmail = String.valueOf(mEditTextEmail.getText()).toLowerCase(Locale.ROOT);
        mStringPassword = String.valueOf(mEditTextPassword.getText());

        if (!isValidEmail(mStringEmail)){
            String mTextMessage = getString(R.string.text_email_not_valid);
            Toast.makeText(getApplicationContext() , mTextMessage , Toast.LENGTH_SHORT).show();
            return;
        }

        if (isRequiredPassword()){
            String mTextMessage = getString(R.string.text_error_fill_mandatory_information);
            Toast.makeText(getApplicationContext() , mTextMessage , Toast.LENGTH_SHORT).show();
            return;
        }

        mProgressBarLogin.setVisibility(View.VISIBLE);

        User mUser = new User(mStringUser , mStringEmail , mStringPassword, mNivelAcesso);

        String mResult = UserDao.authenticateUser(mUser , getApplicationContext());


        if(mResult.isEmpty() || mResult.equals("") || mResult.equals("Exception")){
            String mTextMessage;
            mTextMessage = getString(R.string.text_email_or_password_incorrect);
            if(mResult.equals("Exception")){
                mTextMessage = getString(R.string.text_connection_error);
            }
            Toast.makeText(getApplicationContext(), mTextMessage, Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString("Logged" , "true");
        mEditor.putString("email" , mStringEmail);
        mEditor.putString("name" , mResult);
        mEditor.apply();

        Intent mIntent = new Intent(getApplicationContext() , MainActivity.class);
        mIntent.putExtra("EXTRA_NAME" , mResult);
        startActivity(mIntent);
        finish();

    }

    public class ClickMyButtonLogin implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            postData();
        }
    }

    private void showSingUp(){
        Intent mIntent = new Intent(getApplicationContext() , SignUpActivity.class);
        startActivity(mIntent);
        finish();
    }

    public class ClickMyNewUser implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            showSingUp();
        }
    }

//    private void showForgotPassword(){
//        Intent mIntent = new Intent(getApplicationContext() , ResetPasswordActivity.class);
//        startActivity(mIntent);
//        finish();
//    }
//
//    public class ClickMyForgotPassword implements View.OnClickListener{
//        @Override
//        public void onClick(View view) {
//            showForgotPassword();
//        }
//    }

    public class EditMyTextAction implements TextView.OnEditorActionListener{
        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
            if(actionId == EditorInfo.IME_ACTION_DONE){
                postData();
            }

            return false;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditTextEmail = findViewById(R.id.editText_email_login);

        mEditTextPassword = findViewById(R.id.editText_password_login);
        mEditTextPassword.setOnEditorActionListener(new EditMyTextAction());

        mButtonLogin = findViewById(R.id.button_log_in);
        mButtonLogin.setOnClickListener(new ClickMyButtonLogin());

        mProgressBarLogin = findViewById(R.id.progressBarLogin);

        mButtonNewUser = findViewById(R.id.button_new_user);
        mButtonNewUser.setOnClickListener(new ClickMyNewUser());

//        mButtonForgotPassword = findViewById(R.id.button_forgot_password);
//        mButtonForgotPassword.setOnClickListener(new ClickMyForgotPassword());

        mSharedPreferences = getSharedPreferences("HermesPDV", MODE_PRIVATE);

        verifyLogged();

    }
}
