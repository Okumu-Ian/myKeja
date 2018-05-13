package adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import icelabs.eeyan.mykeja.FormActivity;
import icelabs.eeyan.mykeja.ProjectActivity;
import icelabs.eeyan.mykeja.R;
import models.HomeModel;

/**
 * Created by The Architect on 4/19/2018.
 */

public class HomeAdapter2 extends RecyclerView.Adapter<HomeAdapter2.MyViews>{


    private List<HomeModel> modelList;
    private HomeModel model;
    private Activity context;

    public HomeAdapter2(List<HomeModel> modelList, Activity context) {
        this.modelList = modelList;
        this.context = context;
    }

    @Override
    public MyViews onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViews(LayoutInflater.from(context).inflate(R.layout.main_item_list,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViews holder, final int position) {

        model = modelList.get(position);
        holder.textA.setText(model.getMain_title());
        holder.textB.setText(model.getSecondary_title());
        Picasso.with(context).load(model.getImage()).into(holder.imageView);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNext(position);
            }
        });

    }

    private void goNext(int x)
    {
        if(x==0)
        {
            context.startActivity(new Intent(context, FormActivity.class));
        }else if(x==1)
        {

            context.startActivity(new Intent(context, ProjectActivity.class));

        }else
            {
                Toast.makeText(context, "Coming soon.", Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViews extends RecyclerView.ViewHolder
    {
        private ImageView imageView;
        private TextView textA,textB;
        private LinearLayout layout;
        public MyViews(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.linearItem);
            imageView = (ImageView) itemView.findViewById(R.id.img_main_item_photo);
            textA = (TextView) itemView.findViewById(R.id.txt_main_item_title);
            textB = (TextView) itemView.findViewById(R.id.txt_main_item_text);

        }
    }
}
