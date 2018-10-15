package com.example.haran.agritec;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText UserEmail,UserPassword,UserConfirmPassword;
    private Button CreateAccountButton;
    private ProgressDialog loadingBar;


    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth =FirebaseAuth.getInstance();



        UserEmail =(EditText)findViewById(R.id.register_email);
        UserPassword =(EditText)findViewById(R.id.register_password);
       UserConfirmPassword =(EditText)findViewById(R.id.register_confirm_password);
       CreateAccountButton =(Button) findViewById(R.id.register_create_account);
            loadingBar = new ProgressDialog(this);



       CreateAccountButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view)
           {

               CreateNewAccount();

           }
       });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser =mAuth.getCurrentUser();

        if(currentUser != null)
        {
            SendUserToMainActivity();//if not user logged in send to login page

        }

    }


    private void SendUserToMainActivity()
    {
        Intent mainIntent =new Intent(RegisterActivity.this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }




    private void CreateNewAccount() {

        String email = UserEmail.getText().toString();
        String password = UserPassword.getText().toString();
        String confirmPassword = UserConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(RegisterActivity.this, "Please write your email", Toast.LENGTH_SHORT).show();


        } else if (TextUtils.isEmpty(password)) {

            Toast.makeText(RegisterActivity.this, "Please write your email", Toast.LENGTH_SHORT).show();


        } else if (TextUtils.isEmpty(confirmPassword)) {

            Toast.makeText(RegisterActivity.this, "Please confirm your password...", Toast.LENGTH_SHORT).show();


        } else if (!password.equals(confirmPassword)) {

            Toast.makeText(RegisterActivity.this, "Your password do not match with your confirm password...", Toast.LENGTH_SHORT).show();


        } else {

            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Please wait,while we are creating your new Account...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                SendUserToSetupActivity();
                               // Toast.makeText(RegisterActivity.this, "You are authenticated successfully...");
                                Toast.makeText(RegisterActivity.this, "You are authenticated successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            } else {
                                String message = task.getException().getMessage();
                                Toast.makeText(RegisterActivity.this, "Error Occured:" + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });

        }




    }

    private void SendUserToSetupActivity()
    {
        Intent setupIntent =new Intent(RegisterActivity.this,SetupActivity.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish();
    }
}



