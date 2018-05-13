package icelabs.eeyan.mykeja;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import adapters.ConstructionItem;
import adapters.HomeAdapter;
import adapters.HomeAdapter2;
import adapters.HomeItem;
import models.HomeItemModel;
import models.HomeModel;

public class MainActivity extends AppCompatActivity {

    private int [] icons = {R.drawable.ic_construction_new,R.drawable.ic_construction,R.drawable.ic_catering
                            ,R.drawable.ic_repair,R.drawable.ic_cleaning,R.drawable.ic_driving,R.drawable.ic_ground_works};
    private String [] titles = {"Create your new home","Continue construction","Need some food"
                             ,"Fix it","Clean Up","Chauffeur Services","Ground Works"};
    private String [] secondaryTitles = {"Join thousands and build","Pick up where you left","Get a chef at your doorstep"
                            ,"Get your repairs done","Make your house spic and span","We can drive you around in your car"
                            ,"Get your grass mowed or till land"};
    private Activity context = MainActivity.this;
    private RecyclerView recyclerView,recyclerView1;
    private List<HomeModel> modelList;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selector_layout);
        Authenticate();
        initUI();
    }


    private void Authenticate()
    {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user==null)
        {
            startActivity(new Intent(MainActivity.this,LogInActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        switch (id)
        {
            case R.id.shop_action:
                break;
            case R.id.exit_action:
                auth.signOut();
                startActivity(new Intent(MainActivity.this,LogInActivity.class));
                finish();
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();

    }

    private void initUI()
    {
        try{

            getSupportActionBar().setIcon(R.drawable.ic_home_main);
            getSupportActionBar().setLogo(R.drawable.ic_home_main);

        }catch(NullPointerException exception)
        {
            Log.e("TAG_ERROR",exception.toString());
        }


        modelList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.main_list_icon);
        recyclerView1 = (RecyclerView) findViewById(R.id.main_list_text);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        recyclerView1.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

        prepareHomeList();
        prepareConstructionList();

    }


    private void prepareHomeList()
    {

        for (int x = 0; x<icons.length; x++)
        {
            HomeModel homeModel = new HomeModel();
            homeModel.setImage(icons[x]);
            modelList.add(homeModel);
        }

        recyclerView.setAdapter(new HomeAdapter(modelList,context));
    }

    private void prepareConstructionList()
    {
        modelList.clear();
      for (int x = 0; x<icons.length; x++)
      {
          HomeModel homeModel = new HomeModel();
          homeModel.setImage(icons[x]);
          homeModel.setMain_title(titles[x]);
          homeModel.setSecondary_title(secondaryTitles[x]);
          modelList.add(homeModel);
      }

      recyclerView1.setAdapter(new HomeAdapter2(modelList,context));

    }

}
