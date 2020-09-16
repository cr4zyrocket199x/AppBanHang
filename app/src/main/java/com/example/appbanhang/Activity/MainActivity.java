package com.example.appbanhang.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.Adapter.SanphamNewAdapter;
import com.example.appbanhang.Model.Giohang;
import com.example.appbanhang.Model.Sanpham;
import com.example.appbanhang.R;
import com.example.appbanhang.Ultil.CheckConnection;
import com.example.appbanhang.Ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    public static int ACTIVITY_DIENTHOAI=101;
    public static int ACTIVITY_LAPTOP=102;
    public static ArrayList<Giohang> giohangArrayList;
    public static ArrayList<Sanpham>sanphamallArrayList;

    ImageView ivTrangchu,ivDienthoai,ivLaptop,ivLienhe,ivThongtin;
    DrawerLayout dlMHChinh;
    androidx.appcompat.widget.Toolbar tbMHChinh;
    RecyclerView rvMHChinh;
    ViewFlipper vfMHChinh;
    NavigationView nvMHChinh;

    ArrayList<Sanpham>sanphamnewArrayList;
    SanphamNewAdapter sanphamnewAdapter;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.giohang,menu);
        return true;
    }
    @Override
    public void onBackPressed() {
        DlgQuit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent=new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sanphamnewAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Anhxa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
            GetDuLieuTuServer();
        }else {
            CheckConnection.ShowToask_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối !");
        }

        SetOnClickListener();

    }

    private void GetDuLieuTuServer() {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest1=new JsonArrayRequest(Server.Duongdanallsp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response !=null){
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject=response.getJSONObject(i);
                            sanphamallArrayList.add(new Sanpham(jsonObject.getInt("IdSP"),jsonObject.getString("TenSP"),jsonObject.getInt("GiaSP"),jsonObject.getString("HinhSP"),jsonObject.getString("MoTaSP"),jsonObject.getInt("IdLoaiSP")));
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToask_Short(getApplicationContext(),error.toString());
            }
        });
        JsonArrayRequest jsonArrayRequest2=new JsonArrayRequest(Server.Duongdanspnew, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response !=null){
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject=response.getJSONObject(i);
                            sanphamnewArrayList.add(new Sanpham(jsonObject.getInt("IdSP"),jsonObject.getString("TenSP"),jsonObject.getInt("GiaSP"),jsonObject.getString("HinhSP"),jsonObject.getString("MoTaSP"),jsonObject.getInt("IdLoaiSP")));
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToask_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest1);
        requestQueue.add(jsonArrayRequest2);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.ivTrangchu:
                Intent FacebookPage=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/RocketCr4zyYTB"));
                startActivity(FacebookPage);
                break;
            case R.id.ivDienthoai:
                intent=new Intent(MainActivity.this,DienthoaiActivity.class);
                startActivityForResult(intent,ACTIVITY_DIENTHOAI);
                break;
            case R.id.ivLaptop:
                intent=new Intent(MainActivity.this,LaptopActivity.class);
                startActivityForResult(intent,ACTIVITY_LAPTOP);
                break;
            case R.id.ivLienhe:
                intent=new Intent(getApplicationContext(), LienHeActivity.class);
                startActivity(intent);
                break;
            case R.id.ivThongtin:
                intent=new Intent(getApplicationContext(), ThongTinActivity.class);
                startActivity(intent);
                break;
        }
    }


    //Hien thi dlgQuit
    public void DlgQuit(){
        AlertDialog alertDialog=new AlertDialog.Builder(MainActivity.this)
                .setMessage("Bạn có chắc bạn muốn thoát không ?")
                .setNegativeButton("Hủy",null)
                .setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(RESULT_OK,new Intent().putExtra("EXIT",true));
                        finish();
                    }
                }).create();
        alertDialog.show();

    }
    private void ActionViewFlipper() {
        ArrayList<String>arrayListQuangCao=new ArrayList<>();
        arrayListQuangCao.add("https://scontent.fhan2-1.fna.fbcdn.net/v/t1.0-9/s960x960/96734197_2740555286178447_4767268782068465664_o.jpg?_nc_cat=107&_nc_sid=dd9801&_nc_ohc=wCre5edj-L0AX-FhyKD&_nc_ht=scontent.fhan2-1.fna&_nc_tp=7&oh=96a693178f16e2fbf56c7822ea74b181&oe=5EDF849A");
        arrayListQuangCao.add("https://cdn.tgdd.vn/Files/2019/09/10/1196951/ketthuciphone_800x419.png");
        arrayListQuangCao.add("https://cdn.mediamart.vn/News/mua-vaio-rinh-qua-xperia-cung-media-mart-924201273231am.jpg");
        arrayListQuangCao.add("https://laptop88.vn/media/news/2404_800x300.png");
        for (int i=0;i<arrayListQuangCao.size();i++){
            ImageView imageView=new ImageView(getApplicationContext());
            //Picasso.with(getApplicationContext()).load(arrayListQuangCao.get(i)).into(imageView);
            Picasso.get().load(arrayListQuangCao.get(i))
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            vfMHChinh.addView(imageView);
        }
        vfMHChinh.setFlipInterval(5000);
        vfMHChinh.setAutoStart(true);
        Animation animation_slide_in= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        vfMHChinh.setInAnimation(animation_slide_in);
        vfMHChinh.setOutAnimation(animation_slide_out);
        vfMHChinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent123=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.hanoicomputer.vn/"));
                startActivity(intent123);
            }
        });
    }

    private void ActionBar() {
        setSupportActionBar(tbMHChinh);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbMHChinh.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        tbMHChinh.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlMHChinh.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa() {
        tbMHChinh=(Toolbar) findViewById(R.id.toolbarManHinhChinh);
        rvMHChinh=(RecyclerView) findViewById(R.id.recyclerviewManHinhChinh);
        vfMHChinh=(ViewFlipper) findViewById(R.id.viewflipperManHinhChinh);
        nvMHChinh=(NavigationView) findViewById(R.id.navigationviewManHinhChinh);
        dlMHChinh=(DrawerLayout)findViewById(R.id.drawerlayoutMHChinh);
        sanphamallArrayList=new ArrayList<>();
        sanphamnewArrayList=new ArrayList<>();
        sanphamnewAdapter=new SanphamNewAdapter(MainActivity.this,sanphamnewArrayList);
        rvMHChinh.setHasFixedSize(true);
        rvMHChinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        rvMHChinh.setAdapter(sanphamnewAdapter);

        ivDienthoai=findViewById(R.id.ivDienthoai);
        ivLaptop=findViewById(R.id.ivLaptop);
        ivLienhe=findViewById(R.id.ivLienhe);
        ivTrangchu=findViewById(R.id.ivTrangchu);
        ivThongtin=findViewById(R.id.ivThongtin);

        if (giohangArrayList==null){
            giohangArrayList=new ArrayList<>();
        }
    }

    public void SetOnClickListener(){
        ivTrangchu.setOnClickListener(MainActivity.this);
        ivDienthoai.setOnClickListener(MainActivity.this);
        ivLaptop.setOnClickListener(MainActivity.this);
        ivLienhe.setOnClickListener(MainActivity.this);
        ivThongtin.setOnClickListener(MainActivity.this);
    }
}
