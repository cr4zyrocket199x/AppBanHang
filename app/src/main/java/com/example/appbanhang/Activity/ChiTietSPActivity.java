package com.example.appbanhang.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.Model.Giohang;
import com.example.appbanhang.Model.Sanpham;
import com.example.appbanhang.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSPActivity extends AppCompatActivity {
    public static Sanpham sanpham;
    Toolbar tbChiTietSP;
    ImageView ivChiTietSP;
    TextView tvTenCTSP,tvGiaCTSP,tvTTCTSP;
    Spinner spChitietSP;
    Button btThemGioHang;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.giohang,menu);
        return true;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_s_p);

        Anhxa();
        ActionToolbar();
        GetThongtinSP();
        CatchEventSpinner();
        EventButon();


    }



    private void EventButon() {
        btThemGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.giohangArrayList.size()>0){
                    int sl=Integer.parseInt(spChitietSP.getSelectedItem().toString());
                    boolean exits=false;
                    for (int i=0;i<MainActivity.giohangArrayList.size();i++){
                        if (MainActivity.giohangArrayList.get(i).getIdsp()==sanpham.getIdSP()){
                            MainActivity.giohangArrayList.get(i).setSoluongsp(MainActivity.giohangArrayList.get(i).getSoluongsp()+sl);
                            if (MainActivity.giohangArrayList.get(i).getSoluongsp()>=10){
                                MainActivity.giohangArrayList.get(i).setSoluongsp(10);
                            }
                            MainActivity.giohangArrayList.get(i).setGiasp(sanpham.getGiaSP()*MainActivity.giohangArrayList.get(i).getSoluongsp());
                            exits=true;
                        }
                    }
                    if (exits==false){
                        int soluong=Integer.parseInt(spChitietSP.getSelectedItem().toString());
                        int giamoi=soluong*sanpham.getGiaSP();
                        MainActivity.giohangArrayList.add(new Giohang(sanpham.getIdSP(),sanpham.getTenSP(),giamoi,sanpham.getHinhSP(),soluong));
                    }
                }else {
                    int soluong=Integer.parseInt(spChitietSP.getSelectedItem().toString());
                    int giamoi=soluong*sanpham.getGiaSP();
                    MainActivity.giohangArrayList.add(new Giohang(sanpham.getIdSP(),sanpham.getTenSP(),giamoi,sanpham.getHinhSP(),soluong));
                }
                Intent intent=new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });

    }

    private void CatchEventSpinner() {
        Integer[]soluong=new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer>arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spChitietSP.setAdapter(arrayAdapter);
    }

    private void GetThongtinSP() {
        sanpham=(Sanpham)getIntent().getSerializableExtra("chitietsp");
        tvTenCTSP.setText(sanpham.getTenSP());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        tvGiaCTSP.setText("Giá: " + decimalFormat.format(sanpham.getGiaSP())+" vnđ");
        tvTTCTSP.setText(sanpham.getMoTaSP());
        Picasso.get().load(sanpham.getHinhSP())
                .error(R.drawable.errordisplay_)
                .into(ivChiTietSP);
    }

    private void ActionToolbar() {
        setSupportActionBar(tbChiTietSP);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbChiTietSP.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        tbChiTietSP=(Toolbar)findViewById(R.id.tbChiTietSP);
        ivChiTietSP=findViewById(R.id.ivChiTietSP);
        tvTenCTSP=findViewById(R.id.tvTenCTSP);
        tvGiaCTSP=findViewById(R.id.tvGiaCTSP);
        tvTTCTSP=findViewById(R.id.tvChitietSP);
        spChitietSP=findViewById(R.id.spGiohang);
        btThemGioHang=findViewById(R.id.btThemGioHang);
    }
}
