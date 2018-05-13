package icelabs.eeyan.mykeja;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FormActivity extends AppCompatActivity {

    private Spinner currency,land,type;
    private EditText name,budget,area,rooms;
    private AppCompatButton button;
    private String stringName,stringBudget,stringArea,stringRooms,stringCurrency,stringLand,stringType;
    private ProgressDialog progressDialog;
    private StringRequest request;
    private AlertDialog.Builder builder,archDialog;
    private DatabaseReference reference;
    private FirebaseAuth auth;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        setToolbar();
        initUI();

    }



    private void setToolbar()
    {
        getSupportActionBar().setIcon(R.drawable.construction_icon);
        getSupportActionBar().setTitle("Your Dream Home Awaits");

    }

    private void initUI()
    {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference().child("Projects").child(auth.getUid());

        name = (EditText) findViewById(R.id.edt_form_name);
        budget = (EditText) findViewById(R.id.edt_form_budget);
        area = (EditText) findViewById(R.id.edt_form_land_area);
        rooms = (EditText) findViewById(R.id.edt_form_room_quantity);

        currency = (Spinner) findViewById(R.id.spinner_currency);
        land = (Spinner) findViewById(R.id.spinner_land);
        type = (Spinner) findViewById(R.id.spinner_type);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Kindly wait as we process your data.");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        button = (AppCompatButton) findViewById(R.id.btn_submit_construction_form);

        setOnClicks();

    }



    public void promptArchitectSelection()
    {
        archDialog = new AlertDialog.Builder(this);
        archDialog.setTitle("Choose Architect");
        archDialog.setMessage("Would you like to choose an architect now?");
        archDialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(FormActivity.this,ProjectActivity.class));
                finish();
            }
        });
        archDialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(FormActivity.this,ArchList.class));
                finish();
            }
        });
        final AlertDialog dialog = archDialog.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void setOnClicks()
    {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmptyFields(v);

            }
        });
    }

    private void checkEmptyFields(View view)
    {

        if(name.getText().length()==0)
        {
            name.setError("");
            createSnackBar(view,"Whoa a project without a name!!");
        }else
        if(budget.getText().length()==0)
        {
            budget.setError("");
            createSnackBar(view,"Your budget please!");
        }else
        if(rooms.getText().length()==0)
        {
            rooms.setError("");
            createSnackBar(view,"How many rooms? Kindly");
        }else
            {

        checkSpinnerOneItem();
        checkSpinnerTwoItem();
        checkSpinnerThreeItem();
        initStrings();
        submitToFirebase();


            }

    }

    private void checkSpinnerOneItem()
    {
        int position = currency.getSelectedItemPosition();
        switch (position)
        {
            case 0:
                stringCurrency = "KSH";
                break;
            case 1:
                stringCurrency = "USD";
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       finish();
    }

    private void checkSpinnerTwoItem()
    {
        int position = land.getSelectedItemPosition();
        switch (position)
        {
            case 0:
                stringLand = "Acres";
                break;
            case 1:
                stringLand = "Hectares";
                break;
        }
    }

    private void checkSpinnerThreeItem()
    {
        int position = type.getSelectedItemPosition();
        switch (position)
        {
            case 0:
                stringType = "Bungalow";
                break;
            case 1:
                stringType = "Villa";
                break;
            case 2:
                stringType = "Masionette";
                break;
        }
    }

    private void initStrings()
    {
        stringName = name.getText().toString();
        stringBudget = budget.getText().toString();
        stringArea = area.getText().toString();
        stringRooms = rooms.getText().toString();
    }

    private void createSnackBar(View view,String message)
    {
        Snackbar.make(view,message,Snackbar.LENGTH_LONG).setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).setActionTextColor(Color.CYAN).show();
    }


    private  void submitToFirebase()
    {
        HashMap<String,String> params = new HashMap<>();
        params.put("project_name",stringName);
        params.put("project_budget",stringBudget);
        params.put("project_currency",stringCurrency);
        params.put("project_area",stringArea);
        params.put("project_land",stringLand);
        params.put("project_type",stringType);
        params.put("project_rooms",stringRooms);

        String uniqueProjectId = reference.push().getKey();
        reference.child(uniqueProjectId).setValue(params);

        promptArchitectSelection();
    }

    private void sendFormData(String submit_url,String uniqid)
    {

        progressDialog.show();
        final String uemail = user.getEmail();
        final String uid = user.getUid();
        final String uproject = uniqid;
        request = new StringRequest(Request.Method.POST, submit_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equalsIgnoreCase("success"))
                {
                    progressDialog.dismiss();
                    Toast.makeText(FormActivity.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(FormActivity.this, "There seems to be an error.\n Try again\n Make sure your internet connection is on.", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String,String> params = new HashMap<>();
                params.put("project_name",stringName);
                params.put("project_budget",stringBudget);
                params.put("project_currency",stringCurrency);
                params.put("project_area",stringArea);
                params.put("project_land",stringLand);
                params.put("project_type",stringType);
                params.put("project_rooms",stringRooms);
                params.put("project_email_id",uemail);
                params.put("project_user_id",uid);
                params.put("project_id",uproject);
                return params;

            }
        };
        Volley.newRequestQueue(this).add(request);
    }
}
