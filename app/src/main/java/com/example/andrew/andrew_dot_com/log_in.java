package com.example.andrew.andrew_dot_com;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class log_in extends AppCompatActivity {

    OnlineShoppingDataBase dataBase;
    EditText userName, password;
    CheckBox remember;
    private String srt_username, str_password;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        //if(firebaseAuth.getCurrentUser()!=null) //go to Home activity


        userName = (EditText) findViewById(R.id.UserName_id);
        password = (EditText) findViewById(R.id.login_Password_id);
        remember = (CheckBox) findViewById(R.id.cb_rememberMe_id);

        RelativeLayout signIN = (RelativeLayout) findViewById(R.id.SIGNIN_ID_relative_layout);
        TextView signUP = (TextView) findViewById(R.id.tv_signUp_id);
        TextView forgetPass = (TextView) findViewById(R.id.tv_forgetPassword);
        dataBase = new OnlineShoppingDataBase(this);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            userName.setText(loginPreferences.getString("username", ""));
            password.setText(loginPreferences.getString("password", ""));
            remember.setChecked(true);
        }

        // authentication
        signIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //remember me
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(userName.getWindowToken(), 0);

                srt_username = userName.getText().toString();
                str_password = password.getText().toString();

                if (remember.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("username", srt_username);
                    loginPrefsEditor.putString("password", str_password);
                    loginPrefsEditor.commit();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }
                online();
            }

        });

        //sign up
        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(log_in.this, SignUp.class);
                startActivity(intent);
            }
        });

        //forgot my password
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), resetPassword.class);
                startActivity(intent);
            }
        });
    }

    private void online() {
        String email = userName.getText().toString().trim();
        String pass = password.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "unfortunately we need your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "unfortunately we need a password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Logging , please wait . . .");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(log_in.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                            offline();
                        else
                            Toast.makeText(getApplicationContext(), "Oop !! , error in connecting", Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();
                    }
                });
    }

    private void offline() {
        int id = dataBase.SignIN(userName.getText().toString().trim(), password.getText().toString().trim());
        if (id > -1) {
            GlobalID custID = new GlobalID();
            custID.setId(id);
            custID.cartItems.clear();
            custID.clearCart();
            Toast.makeText(getApplicationContext(), "Welcome  " + userName.getText().toString().trim(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), Home.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // will not be able to go back
            startActivity(intent);
            log_in.this.finish();
        } else
            Toast.makeText(getApplicationContext(), "Wrong UserName OR Password !!", Toast.LENGTH_SHORT).show();
    }
}