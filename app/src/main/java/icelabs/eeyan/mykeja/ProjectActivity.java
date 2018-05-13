package icelabs.eeyan.mykeja;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import Utilz.AwesomeUtils;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProjectActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private TextView project_name,project_budget,project_architect;
    private ImageView project_background;
    private CircleImageView project_image;
    private RecyclerView list;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private AwesomeUtils awesomeUtils = new AwesomeUtils(ProjectActivity.this);
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);



            initUI();
            setProjectDetails();


         }



    private void initUI()
    {


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Projects").child(user.getUid());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        project_name = (TextView) findViewById(R.id.txt_project_name_main);
        project_budget = (TextView) findViewById(R.id.txt_project_budget);
        project_architect = (TextView) findViewById(R.id.txt_project_architect_name);
        project_background = (ImageView) findViewById(R.id.img_project_photo);
        project_image = (CircleImageView) findViewById(R.id.img_project_profile);
        list = (RecyclerView) findViewById(R.id.current_project_list);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Project Page");

        list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        list.setItemAnimator(new DefaultItemAnimator());

    }


    private void setProjectDetails()
    {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name,budget,architect;
                name = transformData(dataSnapshot,AwesomeUtils.PROJECT_NAME);
                budget = transformData(dataSnapshot,AwesomeUtils.PROJECT_BUDGET);
                architect = transformData(dataSnapshot,AwesomeUtils.PROJECT_TYPE);

                project_name.setText(name);
                project_budget.setText(budget);
                project_architect.setText(architect);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.e("ERROR HERE : ",databaseError.getMessage(),new Throwable());

            }
        });

    }


    private String transformData(DataSnapshot snapshot, String child)
    {

        return snapshot.child(child).getValue().toString();

    }


}
