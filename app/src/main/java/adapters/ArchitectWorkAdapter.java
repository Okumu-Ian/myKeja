package adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import icelabs.eeyan.mykeja.R;
import models.ArchitectModel;
import models.ArchitectWorkModel;

/**
 * Created by The Architect on 4/18/2018.
 */

public class ArchitectWorkAdapter extends RecyclerView.Adapter<ArchitectWorkAdapter.MyViewHolder>{

    private List<ArchitectWorkModel> workModels;
    private Context context;
    private View view;
    private ArchitectWorkModel architectModel;

    public ArchitectWorkAdapter(List<ArchitectWorkModel> workModels, Context context) {
        this.workModels = workModels;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(context).inflate(R.layout.arch_works_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        architectModel = workModels.get(position);
        Picasso.with(context).load(architectModel.getImage_url()).fit().into(holder.img_holder);

    }

    @Override
    public int getItemCount() {
        return workModels.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView img_holder;


        public MyViewHolder(View itemView) {
            super(itemView);
            img_holder = (ImageView) itemView.findViewById(R.id.img_arch_works);


        }
    }
}
