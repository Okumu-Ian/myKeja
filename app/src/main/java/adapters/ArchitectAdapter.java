package adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import icelabs.eeyan.mykeja.ArchitectProfile;
import icelabs.eeyan.mykeja.R;
import models.ArchModel;
import models.ArchitectModel;

/**
 * Created by The Architect on 4/18/2018.
 */

public class ArchitectAdapter  extends RecyclerView.Adapter<ArchitectAdapter.MyViewHolder> implements Filterable{

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView img_holder,img_menu;
        private TextView txt_name;


        public MyViewHolder(View itemView) {
            super(itemView);
            img_holder = (ImageView) itemView.findViewById(R.id.img_arch_item);
            img_menu = (ImageView) itemView.findViewById(R.id.img_arch_item_options);
            txt_name = (TextView) itemView.findViewById(R.id.txt_arch_item_title);
        }
    }

    private List<ArchModel> modelList;
    private Context context;
    private View view;
    private ArchModel model;
    private List<ArchModel> modelListFiltered;


    public ArchitectAdapter(List<ArchModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
        this.modelListFiltered = modelList;

    }

    private void archProfile()
    {
        context.startActivity(new Intent(context, ArchitectProfile.class));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(context).inflate(R.layout.arch_list_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

       /* model = modelListFiltered.get(position);
        Picasso.with(context).load(model.getArchImageTest()).fit().into(holder.img_holder);
        holder.txt_name.setText(model.getArchName());
        */

       model = modelListFiltered.get(position);
       Picasso.with(context).load(model.getImage()).fit().into(holder.img_holder);
       holder.txt_name.setText(model.getName());
        holder.img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopUpMenu(v);
            }
        });
        holder.img_holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                archProfile();
            }
        });


    }

    private void createPopUpMenu(View v)
    {
        PopupMenu popupMenu = new PopupMenu(context,v);
        popupMenu.inflate(R.menu.popup_architect);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Toast.makeText(context, ""+item.getTitle(), Toast.LENGTH_SHORT).show();

                return true;
            }
        });
        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        return modelListFiltered.size();
    }


    @Override
    public Filter getFilter()
    {


        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String charString = constraint.toString().toLowerCase().trim();

                if(charString.isEmpty())
                {
                    modelListFiltered = modelList;
                }else
                    {
                        List<ArchModel> listFiltered = new ArrayList<>();

                        for (ArchModel model1 : modelList)
                        {

                          if(model1.getName().toLowerCase().contains(charString))
                          {

                              listFiltered.add(model1);
                          }

                        }

                        modelListFiltered = listFiltered;

                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = modelListFiltered;
                    return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

               // modelListFiltered = (List<ArchitectModel>) results.values;
                notifyDataSetChanged();

            }
        };
    }


    public class CustomFilter extends Filter
    {

        private ArchitectAdapter adapter;
        private CustomFilter(ArchitectAdapter adapter)
        {
            super();
            this.adapter = adapter;
        }


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {


            final FilterResults results = new FilterResults();

            if (constraint.length()==0)
            {

                modelListFiltered = modelList;

            }else
                {
                    List<ArchModel> listed = new ArrayList<>();

                    final String filterPattern = constraint.toString().toLowerCase().trim();
                    for(ArchModel ab : modelList)
                    {
                        if (ab.getName().toLowerCase().startsWith(filterPattern))listed.add(ab);

                    }

                    modelListFiltered = listed;

                }

                results.values = modelListFiltered;
                results.count = modelListFiltered.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            modelListFiltered = (List<ArchModel>) results.values;
            this.adapter.notifyDataSetChanged();

        }
    }


}
