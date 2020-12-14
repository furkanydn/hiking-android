package com.mobil.my_a;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

public class InternetActivity {
    private Context context;

    public InternetActivity(Context context){
        this.context = context;
    }
    public void showNoInternetDialog(){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.card_internetyok);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.findViewById(R.id.internet_buton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                System.exit(0);
            }
        });
        dialog.show();
    }
    public  boolean getInternetStatus() {

        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressWarnings("deprecation") NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        @SuppressWarnings("deprecation") boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(!isConnected) {
            showNoInternetDialog();
        }
        return isConnected;
    }
}
