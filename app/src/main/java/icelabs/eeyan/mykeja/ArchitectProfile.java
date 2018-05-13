package icelabs.eeyan.mykeja;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;

import java.util.ArrayList;
import java.util.List;

import adapters.ArchitectWorkAdapter;
import de.hdodenhof.circleimageview.CircleImageView;
import models.ArchitectWorkModel;

public class ArchitectProfile extends AppCompatActivity {

    private TextView a,b,c,d,e,f,g;
    private RecyclerView recyclerView;
    private List<ArchitectWorkModel> models;
    private ArchitectWorkAdapter adapter;
    private LinearLayoutManager layoutManager;
    private RecyclerView.ItemAnimator animator;
    private ArchitectWorkModel model;
    private int [] images = {R.drawable.onea,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.five,R.drawable.six
            , R.drawable.seven,R.drawable.eight};
    private RelativeLayout relativeLayout;
    private BottomSheetBehavior sheetBehavior;
    private AppCompatButton compatButton;
    private AppCompatImageView imageView;
    private RippleView rippleView;
    private Dialog dialog;
    private AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_architect_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();
        prepareData();



    }


    private void initUI()
    {
        a = (TextView) findViewById(R.id.txt_arch_name);
        b = (TextView) findViewById(R.id.txt_arch_about_me);
        c = (TextView) findViewById(R.id.txt_arch_where_i_work);
        d = (TextView) findViewById(R.id.txt_arch_mail);
        e = (TextView) findViewById(R.id.txt_arch_phone);
        f = (TextView) findViewById(R.id.txt_arch_studies);
        g = (TextView) findViewById(R.id.txt_arch_experience);

        recyclerView = (RecyclerView) findViewById(R.id.arch_works_list);
        relativeLayout = (RelativeLayout) findViewById(R.id.bottom_sheet_dest);
        compatButton = (AppCompatButton) findViewById(R.id.btn_arch_hire);
        imageView = (AppCompatImageView) findViewById(R.id.img_arch_hire_close);
        //rippleView = (RippleView) findViewById(R.id.ripple_arch_hire_ripple);
        View view = LayoutInflater.from(this).inflate(R.layout.hire_dialog,null,false);
        builder = new AlertDialog.Builder(ArchitectProfile.this);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setView(view);
        alertDialog.setCancelable(false);
        initDialog(view,alertDialog);
        models = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        animator = new DefaultItemAnimator();
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setItemAnimator(animator);
        recyclerView.setNestedScrollingEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            relativeLayout.setNestedScrollingEnabled(false);
        }

        sheetBehavior = BottomSheetBehavior.from(relativeLayout);

        compatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                alertDialog.show();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.contact_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleBottomSheet();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }

    private void initDialog(View v,final AlertDialog a)
    {
        CircleImageView imageViewProfile = (CircleImageView) v.findViewById(R.id.img_additional_profile);
        AppCompatImageView compatImageViewDismiss = (AppCompatImageView) v.findViewById(R.id.img_additional_dialog_dismiss);
        AppCompatTextView compatTextViewName = (AppCompatTextView) v.findViewById(R.id.txt_additional_name);
        AppCompatTextView compatTextViewContact = (AppCompatTextView) v.findViewById(R.id.txt_additional_contact);
        AppCompatButton  compatButtonCall = (AppCompatButton) v.findViewById(R.id.btn_additional_call_now);
        AppCompatButton  compatButtonSave = (AppCompatButton) v.findViewById(R.id.btn_additional_save);

        compatImageViewDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a.dismiss();
            }
        });

    }

    private void prepareData()
    {
        for (int x = 0; x<images.length; x++)
        {
            model = new ArchitectWorkModel();
            model.setImage_url(images[x]);
            models.add(model);
            adapter = new ArchitectWorkAdapter(models,this);
            recyclerView.setAdapter(adapter);
        }
    }

    private void toggleBottomSheet()
    {
        if (sheetBehavior.getState()==BottomSheetBehavior.STATE_COLLAPSED)
        {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
        else
            {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }

    }

}
