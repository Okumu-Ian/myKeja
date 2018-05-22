package icelabs.eeyan.mykeja;

import android.animation.TypeConverter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Utilz.AwesomeUtils;
import adapters.ArchitectAdapter;
import models.ArchModel;
import models.ArchitectModel;

public class ArchList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridLayoutManager manager;
    private Context context = this;
    private RecyclerView.ItemAnimator animator;
    private ArchModel model;
    private ArchitectAdapter adapter;
    private List<ArchModel> modelList;
    private CardView cardView;
    private SearchView searchView;
    private final String url = "http://192.168.43.120:5500/archs.json";
    private AwesomeUtils utils = new AwesomeUtils(ArchList.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arch_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Choose an architect.");

        initUI();
        fetchData(url);
        searchList();

    }

    private String fetchObject(JSONObject object , String name) throws JSONException {
        return object.getString(name);
    }

    private void fetchData(String web_url)
    {


        StringRequest request = new StringRequest(Request.Method.GET,web_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                    Toast.makeText(context, ""+response,Toast.LENGTH_LONG).show();
                    Log.d("RESPONSE",response);


                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray array = jsonObject.getJSONArray("data");
                        for (int i = 0; i <array.length() ; i++)
                        {
                            model = new ArchModel();
                            JSONObject object = array.getJSONObject(i);
                            model.setBio(object.getString("bio"));
                            model.setEmail(fetchObject(object,"email"));
                            model.setExp(fetchObject(object,"experience"));
                            model.setFirm(fetchObject(object,"firm"));
                            model.setId(fetchObject(object,"architect_id"));
                            model.setImage(fetchObject(object,"image"));
                            model.setName(fetchObject(object,"architect_full_name"));
                            model.setPhone(fetchObject(object,"phone"));
                            model.setStudy(fetchObject(object,"studies"));
                            modelList.add(model);

                            Toast.makeText(context, ""+object.getString("firm"), Toast.LENGTH_SHORT).show();
                        }

                        adapter = new ArchitectAdapter(modelList,context);
                        recyclerView.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, ""+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {



            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Cookie", "__test=+toHex(slowAES.decrypt(c,2,a,b))+; expires=Thu, 31-Dec-37 23:55:55 GMT; path=/; location.href=\"http://www.mykeja.epizy.com/testImages/data.json?i=3\";");
                return map;
            }*/
        };

        JsonObjectRequest requestA = new JsonObjectRequest(web_url, null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(context, ""+response.toString(), Toast.LENGTH_SHORT).show();

                try {
                    JSONArray array = response.getJSONArray("data");
                    for (int i = 0; i <array.length() ; i++)
                    {
                        model = new ArchModel();
                        JSONObject object = array.getJSONObject(i);
                        model.setBio(object.getString("bio"));
                        model.setEmail(fetchObject(object,"email"));
                        model.setExp(fetchObject(object,"experience"));
                        model.setFirm(fetchObject(object,"firm"));
                        model.setId(fetchObject(object,"architect_id"));
                        model.setImage(fetchObject(object,"image"));
                        model.setName(fetchObject(object,"architect_full_name"));
                        model.setPhone(fetchObject(object,"phone"));
                        model.setStudy(fetchObject(object,"studies"));
                        modelList.add(model);

                        Toast.makeText(context, ""+object.getString("firm"), Toast.LENGTH_SHORT).show();
                    }

                    adapter = new ArchitectAdapter(modelList,context);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, ""+error.toString(), Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(context).add(request);


    }

    private void initUI()
    {
        modelList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.arch_list);
        cardView = (CardView) findViewById(R.id.card_search);
        searchView = (SearchView) findViewById(R.id.search_architect_list);

        manager = new GridLayoutManager(context,2);
        animator = new DefaultItemAnimator();

        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(animator);


    }

    private void searchList()
    {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
               // utils.showToast(String.valueOf( adapter.getItemCount()), Toast.LENGTH_SHORT);

                return true;
            }
        });
    }

   /* private void prepareData()
    {
        for(int x = 0; x<images.length; x++)
        {
            model = new ArchitectModel();
            model.setArchImageTest(images[x]);
            model.setArchName(names[x]);
            modelList.add(model);

        }

        adapter = new ArchitectAdapter(modelList,context);
        recyclerView.setAdapter(adapter);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_arch_list,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_settings:
                dismissPage();
                break;
            case R.id.action_search:
                showSearch();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void dismissPage()
    {
        startActivity(new Intent(this,ProjectActivity.class));
        finish();
    }

    private void showSearch()
    {
        if(cardView.getVisibility()==View.GONE)
        {
            cardView.setVisibility(View.VISIBLE);
            Animation down = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
            cardView.startAnimation(down);
        }else if(cardView.getVisibility()==View.VISIBLE)
        {
            cardView.setVisibility(View.GONE);
            Animation down = AnimationUtils.loadAnimation(context,android.R.anim.slide_out_right);
            cardView.startAnimation(down);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
