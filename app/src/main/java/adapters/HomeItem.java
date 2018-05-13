package adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import icelabs.eeyan.mykeja.R;
import models.HomeItemModel;

/**
 * Created by The Architect on 4/17/2018.
 */

public class HomeItem extends RecyclerView.Adapter<HomeItem.HomeViewHolder>{

    private List<HomeItemModel> itemModelList;
    private Activity context;
    private View view;
    private HomeViewHolder viewHolder;
    private HomeItemModel model;

    public HomeItem(List<HomeItemModel> itemModelList, Activity context) {
        this.itemModelList = itemModelList;
        this.context = context;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(context).inflate(R.layout.home_list_item,parent,false);
        viewHolder = new HomeViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {

        model = itemModelList.get(position);
        Picasso.with(context).load(model.getImages()).fit().into(holder.itemImage);
        holder.text.setText(model.getTitle());
    }

    @Override
    public int getItemCount() {
        return itemModelList.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView menu,itemImage;
        private TextView text;
        public HomeViewHolder(View itemView) {
            super(itemView);

            menu = (ImageView) itemView.findViewById(R.id.img_home_item_options);
            itemImage = (ImageView) itemView.findViewById(R.id.img_home_item);
            text = (TextView) itemView.findViewById(R.id.txt_home_item_title);

        }
    }

}
