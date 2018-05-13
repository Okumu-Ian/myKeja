package adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import icelabs.eeyan.mykeja.FormActivity;
import icelabs.eeyan.mykeja.R;
import models.HomeModel;

/**
 * Created by The Architect on 4/19/2018.
 */

public class HomeAdapter  extends RecyclerView.Adapter<HomeAdapter.MyViews>{

    private List<HomeModel> modelList;
    private HomeModel model;
    private View view;
    private Activity context;

    public HomeAdapter(List<HomeModel> modelList, Activity context) {
        this.modelList = modelList;
        this.context = context;
    }

    @Override
    public MyViews onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViews(LayoutInflater.from(context).inflate(R.layout.main_item_fab,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViews holder, final int position) {

        model = modelList.get(position);
        holder.floatingActionButton.setImageResource(model.getImage());
        holder.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNext(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    private void goNext(int x)
    {
        if(x==0)
        {
            context.startActivity(new Intent(context, FormActivity.class));
        }else if(x==1)
        {


        }else
        {
            Toast.makeText(context, "Coming soon.", Toast.LENGTH_SHORT).show();
        }
    }


    public class MyViews extends RecyclerView.ViewHolder
    {
        private FloatingActionButton floatingActionButton;
        public MyViews(View itemView) {
            super(itemView);

            floatingActionButton = (FloatingActionButton) itemView.findViewById(R.id.item_fab);

        }
    }

}
