package com.example.appbanhang.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.appbanhang.R;

public class LienHeActivity extends AppCompatActivity {

    public static final int REQUEST_CALL=1;
    LinearLayout linearLayout;
    androidx.appcompat.widget.Toolbar toolbarlienhe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);

        linearLayout = (LinearLayout) findViewById(R.id.linearlayoutgoi);
        toolbarlienhe = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbarlienhe) ;
        ActionBar();
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==REQUEST_CALL){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }else {
                Toast.makeText(this, "Quyền gọi điện bị từ chối !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void makePhoneCall() {
        String call = "tel:0968437696";
        if (ActivityCompat.checkSelfPermission(LienHeActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LienHeActivity.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            return;
        }
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(call)));
    }

    private void ActionBar() {
        setSupportActionBar(toolbarlienhe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarlienhe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
