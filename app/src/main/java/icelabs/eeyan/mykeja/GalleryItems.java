package icelabs.eeyan.mykeja;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapters.GalleryAdapter;
import models.GalleryModel;

public class GalleryItems extends AppCompatActivity {

    private List<GalleryModel> models;
    private GalleryModel model;
    private RecyclerView recyclerView;
    private GalleryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_items);
    }


    private void initUI()
    {
        models = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.photo_list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

    }
}
