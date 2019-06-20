package com.example.andrew.andrew_dot_com;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;

public class SignUp extends AppCompatActivity {

    OnlineShoppingDataBase dataBase;
    ArrayList<String> customerDate;
    Calendar calendar;
    DatePickerDialog BirthDate_calender ;
    TextView BirthDate_text, secret , birthDate_tv;
    Button btn_signUp;
    CheckBox not_robot;
    EditText cName,cEmail,cPassword,cJob;
    Spinner cGender;

    String gender;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

//-------------------------------------------------------------------------------------------------------------
        BirthDate_text = (TextView) findViewById(R.id.tv_BirthDate);
        birthDate_tv = (TextView) findViewById(R.id.birthDate_tv);
        secret = (TextView) findViewById(R.id.tv_secret);
        btn_signUp = (Button) findViewById(R.id.btn_SignUp);
        dataBase = new OnlineShoppingDataBase(getApplicationContext());
        not_robot = (CheckBox) findViewById(R.id.btn_not_a_robot);
        cName = (EditText) findViewById(R.id.ed_userName);
        cEmail = (EditText) findViewById(R.id.ed_email);
        cPassword = (EditText) findViewById(R.id.ed_password);
        cJob = (EditText) findViewById(R.id.ed_job);
        cGender = (Spinner) findViewById(R.id.gender_spinner);
        customerDate = new ArrayList<String>();
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        //if(firebaseAuth.getCurrentUser()!=null) //go to Home activity
//-------------------------------------------------------------------------------------------------------------

        //select gender
        cGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = cGender.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(SignUp.this, "Gender is a required field", Toast.LENGTH_SHORT).show();
            }
        });



        birthDate_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance() ;
                int day= calendar.get(Calendar.DAY_OF_MONTH) ,
                        month = calendar.get(Calendar.MONTH) ,
                        year = calendar.get(Calendar.YEAR) ;

                BirthDate_calender = new DatePickerDialog(SignUp.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        BirthDate_text.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                } , day  , month , year) ;

                BirthDate_calender.show();
            }
        });

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = cEmail.getText().toString().trim();
                String password = cPassword.getText().toString().trim();
                //validate input fields
                //TextUtils.isEmpty(BirthDate_text)
                if (BirthDate_text.getText().equals("") || secret.getText().equals("") || cName.getText().toString().equals("") ||
                        cEmail.getText().toString().equals("") || cPassword.getText().toString().equals("") ||
                        cJob.getText().toString().equals("") || gender.equals("") || !(not_robot.isChecked()))
                    Toast.makeText(SignUp.this, "All fields are required", Toast.LENGTH_SHORT).show();

                else
                 {
                  progressDialog.setMessage("Registering User. . .");
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(email , password)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        offline();
                                        Toast.makeText(SignUp.this, "You Have registered Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                        Toast.makeText(SignUp.this, "Oops !! something wrong ,try agine later", Toast.LENGTH_SHORT).show();

                                    progressDialog.dismiss();
                                }
                            });
                }
            }
        });
    }

    private void offline() {
        customerDate.add(cName.getText().toString());
        customerDate.add(cEmail.getText().toString());
        customerDate.add(cPassword.getText().toString());
        customerDate.add(cJob.getText().toString());
        customerDate.add(gender);
        customerDate.add(BirthDate_text.getText().toString());
        customerDate.add(secret.getText().toString());

        int id = dataBase.SignUp(customerDate);
        GlobalID custID = new GlobalID();
        custID.setId(id);
        Intent intent = new Intent(getApplicationContext(), Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}