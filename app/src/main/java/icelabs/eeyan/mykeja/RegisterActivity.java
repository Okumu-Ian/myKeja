package icelabs.eeyan.mykeja;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Utilz.AwesomeUtils;

public class RegisterActivity extends AppCompatActivity {

    private EditText email,password,confirmPassword;
    private AppCompatButton button;
    private FirebaseAuth auth;
    private SignInButton signInButton;
    private TextView mTextView;
    private AwesomeUtils awesomeUtils = new AwesomeUtils(RegisterActivity.this);
    private RippleView rippleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initUi();
        clicked();

    }

    private void initUi()
    {

        auth =FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.edt_reg_email);
        password = (EditText) findViewById(R.id.edt_reg_password);
        confirmPassword = (EditText) findViewById(R.id.edt_reg_password_confirm);
        button = (AppCompatButton) findViewById(R.id.btn_register_submit);
        signInButton = (SignInButton) findViewById(R.id.reg_google_sign_in);
        mTextView = (TextView) findViewById(R.id.txt_reg_login);
        rippleView = (RippleView) findViewById(R.id.rippleRegister);
        rippleView.setRippleType(RippleView.RippleType.RECTANGLE);
        rippleView.setRippleDuration(2500);
        rippleView.setZooming(false);


    }

    void clicked()
    {

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               changePages(RegisterActivity.this,LogInActivity.class);
                finish();
            }
        });

        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataReg(v);
            }
        });*/

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirect(v);
            }
        });

        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                getDataReg(rippleView);
            }
        });

    }

    void changePages(Activity here , Class there)
    {
        Intent intent = new Intent(here,there);
        startActivity(intent);
    }

    void redirect(View view)
    {
        showSnackBar(view,"Redirecting to sign in at other page.");
        Runnable r = new Runnable() {
            @Override
            public void run() {
                awesomeUtils.navPage(RegisterActivity.this,LogInActivity.class);
                finish();
            }
        };
        new Handler().postDelayed(r,1500);
    }

    void checkData(String name, String pass)
    {

        Pattern pattern = Pattern.compile("([0-9])");
        Matcher m = pattern.matcher(pass);

        if (!name.contains("@") || !name.contains(".") || name.length()<6)
        {
            email.setError("Kindly enter a valid email");
        }else if (pass.length() <6)
        {
            password.setError("Password is too short. \n Choose greater than 6 characters");
        }else if (!m.find())
        {
            password.setError("Make sure your password contains a digit");
        }
        else
            {
                RegisterWithEmail(name, pass);
            }


    }

    void RegisterWithEmail(String username , String myPassword)
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Kindly wait.");
        progressDialog.show();


        auth.createUserWithEmailAndPassword(username,myPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressDialog.dismiss();

                if(task.isSuccessful())
                {

                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));

                }else
                    {
                        Toast.makeText(RegisterActivity.this, "An error ocurred. Kindly try again", Toast.LENGTH_SHORT).show();
                    }

            }
        });


    }

    void getDataReg(View view)
    {

        String mail = email.getText().toString();
        String pass1 = password.getText().toString();
        String pass2 = confirmPassword.getText().toString();

        boolean equals = pass1.equals(pass2);

        if (mail.length()!=0 || pass1.length() !=0)
        {
            if (equals)
            {
                checkData(mail,pass1);

            }else
                {

                    showSnackBar(view,"Your passwords do not match");
                }
        }else
            {
                showSnackBar(view,"Ensure all data is filled");
            }


    }


    void showSnackBar(View view , String message)
    {
        Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show();
    }

}
