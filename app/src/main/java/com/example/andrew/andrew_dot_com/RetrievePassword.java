package com.example.andrew.andrew_dot_com;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RetrievePassword extends AppCompatActivity {

    EditText Remail, Rbirthday, Rjob, Rsecret;
    Button recover;
    TextView Rpassword;
    CheckBox not_robot;
    OnlineShoppingDataBase dataBase;
    boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password);

        //[0] email , [1] birthday , [2] job , [3] secret

        Remail = (EditText) findViewById(R.id.ed_v_mail);
        Rbirthday = (EditText) findViewById(R.id.ed_v_birthday);
        Rjob = (EditText) findViewById(R.id.ed_v_job);
        Rsecret = (EditText) findViewById(R.id.ed_v_secret);
        recover = (Button) findViewById(R.id.btn_recover_password);
        Rpassword = (TextView) findViewById(R.id.tv_RecoverdPassword);
        not_robot = (CheckBox) findViewById(R.id.btn_not_a_robot2);
        dataBase = new OnlineShoppingDataBase(getApplicationContext());

        recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] info = {Remail.getText().toString(), Rbirthday.getText().toString(), Rjob.getText().toString(), Rsecret.getText().toString()};
                for (int i = 0; i < info.length; i++)
                    if (info[i].equals("")) check = false;

                if (Remail.getText().toString().equals("") || Rbirthday.getText().toString().equals("") || Rjob.getText().toString().equals("") ||
                        Rsecret.getText().toString().equals("") || !(not_robot.isChecked()))
                    Toast.makeText(RetrievePassword.this, "All fields are required", Toast.LENGTH_SHORT).show();

                else {
                    String pw = dataBase.recoveryPassword(info);
                    if (pw != null) {
                        Rpassword.setText(pw);
                        Toast.makeText(RetrievePassword.this, "Write it down", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(RetrievePassword.this, "NOT matched", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
