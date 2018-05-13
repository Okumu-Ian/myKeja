package icelabs.eeyan.mykeja;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private EditText uname,password;
    private RippleView rippleView;
    private TextView textView,textViewReg;
    private SignInButton signInButton;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private GoogleApiClient client;
    private int RC_SIGN = 100;
    private ProgressDialog dialog;
    private AppCompatButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        configureSignIn();
        initUI();
        clicks();

    }

    private void initUI()
    {
        auth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait.");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        uname = (EditText) findViewById(R.id.edt_sign_email);
        password = (EditText) findViewById(R.id.edt_sign_password);
        textView = (TextView) findViewById(R.id.txt_forgot_password);
        textViewReg = (TextView) findViewById(R.id.txt_sign_in_reg);
        signInButton = (SignInButton) findViewById(R.id.google_sign_in);
        button = (AppCompatButton) findViewById(R.id.btn_sign_in_submit);


    }

    private void clicks()
    {
        textViewReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this,RegisterActivity.class));
                finish();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Wewe sahau tu.",Snackbar.LENGTH_LONG).show();
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInNow();
            }
        });

    }

    public void getData(View view)
    {
        String name = uname.getText().toString();
        String pass = password.getText().toString();
        if(name.length()!=0 && pass.length()!=0)
        {
            checkData(name,pass,view);

        }else
            {
                Snackbar.make(view,"Kindly fill in all the details",Snackbar.LENGTH_SHORT).show();
            }
    }

    private void checkData(String namee, String passworded,View view)
    {
        Pattern p = Pattern.compile("([0-9])");
        Matcher m = p.matcher(passworded);

        if(!namee.contains("@") || !namee.contains(".") || namee.length()<6)
        {
            uname.setError("Kindly enter a valid email.");
        }else if(passworded.length()<6)
        {
            password.setError("Enter a longer password.");
        }else if(!m.find())
        {
            password.setError("Ensure your password has a digit.");
        }else
            {
                LogInWithEmail(namee,passworded,view);
            }
    }

    private void LogInWithEmail(String unamee,String pword,final View view)
    {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Kindly wait.");
        progressDialog.show();

        auth.signInWithEmailAndPassword(unamee,pword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressDialog.dismiss();
                if(!task.isSuccessful())
                {
                    Snackbar.make(view, "Error try again. \n Make sure you are already a user.", Snackbar.LENGTH_LONG).show();
                }
                else
                {
                    startActivity(new Intent(LogInActivity.this,MainActivity.class));
                    finish();
                }

            }
        });

    }


    private void configureSignIn()
    {
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        client = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,options)
                .build();
    }

    public void signInNow()
    {
        Intent mIntent;
        mIntent = Auth.GoogleSignInApi.getSignInIntent(client);
        startActivityForResult(mIntent,RC_SIGN);
    }


    private void signInFirebase(GoogleSignInAccount account)
    {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful())
                {
                    dialog.dismiss();
                    Toast.makeText(LogInActivity.this, "Error connecting to google. \n Check your connection and try again", Toast.LENGTH_LONG).show();
                }else
                {
                    startActivity(new Intent(LogInActivity.this,MainActivity.class));
                    dialog.dismiss();
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RC_SIGN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess())
            {
                GoogleSignInAccount account =result.getSignInAccount();
                dialog.show();
                signInFirebase(account);
            }else
                {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                }
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
