package adapters;

import android.app.Activity;
import android.app.DownloadManager;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import icelabs.eeyan.mykeja.R;
import models.GalleryModel;
import models.HomeItemModel;

/**
 * Created by The Architect on 4/19/2018.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {

    private List<GalleryModel> list;
    private Activity context;
    private GalleryModel model;

    public GalleryAdapter(List<GalleryModel> list, Activity context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHolder myHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.gallery_item,parent,false));
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        model = list.get(position);
        Picasso.with(context).load(model.getImage()).fit().into(holder.imageView);
        holder.textView.setText(model.getName());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //downloadImage(Uri.parse(model.getImage()));
            }
        });

    }

    private void downloadImage(Uri uri)
    {
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(View.VISIBLE);
        request.setTitle(model.getName());
        request.setVisibleInDownloadsUi(true);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        private TextView textView;
        private ImageView imageView,button;

        public MyViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.txt_project_name);
            imageView = (ImageView) itemView.findViewById(R.id.img_gallery_image);
            button = (ImageView) itemView.findViewById(R.id.img_project_download);
        }
    }

}
