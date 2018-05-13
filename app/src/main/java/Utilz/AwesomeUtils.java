package Utilz;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by The Architect on 4/27/2018.
 */

public class AwesomeUtils {

    private Activity context;
    public static String PROJECT_NAME = "project_name";
    public static String PROJECT_TYPE = "project_type";
    public static String PROJECT_BUDGET = "project_budget";
    public static String PROJECT_ARCHITECT = "project_architect";

   public AwesomeUtils(Activity context)
   {
       this.context = context;
   }

   public void navPage(Activity here, Class there)
   {
       Intent intent = new Intent(here,there);
       context.startActivity(intent);
   }

   public void showToast(String message,int length_of_toast)
   {
       Toast.makeText(context,message,length_of_toast).show();
   }

   public void showSnackBar(String message, int length_of_snackbar,View view)
   {
       Snackbar.make(view,message,length_of_snackbar).show();
   }



}
