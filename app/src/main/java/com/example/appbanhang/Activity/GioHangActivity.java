package com.example.appbanhang.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.Adapter.GioHangAdapter;
import com.example.appbanhang.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangActivity extends AppCompatActivity {
    ListView lvGioHang;
    TextView tvThongbao;
    static TextView tvTongTien;
    Button btThanhToan,btTieptucMua;
    Toolbar tbGioHang;

    GioHangAdapter gioHangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        Anhxa();
        gioHangAdapter.notifyDataSetChanged();
        ActionToolbar();
        KiemtraDL();
        EventUltil(); //Do DU lIEU
        LongClickIteam();
        EventButton();

    }

    private void EventButton() {
        btTieptucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.giohangArrayList.size()>0){
                    Intent intent=new Intent(getApplicationContext(), ThongtinKHActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Giỏ hàng của bạn đang còn trống !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void LongClickIteam() {
        lvGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này không ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.giohangArrayList.size()<=0){
                            tvThongbao.setVisibility(View.VISIBLE);
                        }else {
                            MainActivity.giohangArrayList.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            EventUltil();
                            if (MainActivity.giohangArrayList.size()<=0){
                                tvThongbao.setVisibility(View.VISIBLE);
                            }else {
                                tvThongbao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                EventUltil();
                            }

                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gioHangAdapter.notifyDataSetChanged();
                        EventUltil();
                    }
                });
                builder.show();
                return false;
            }
        });
    }

    public static void EventUltil() {
        int tongtien=0;
        for (int i=0;i<MainActivity.giohangArrayList.size();i++){
            tongtien+=MainActivity.giohangArrayList.get(i).getGiasp();
        }
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        tvTongTien.setText(decimalFormat.format(tongtien)+" vnđ");
    }

    private void KiemtraDL() {
        if (MainActivity.giohangArrayList.size()<=0){
            gioHangAdapter.notifyDataSetChanged();
            tvThongbao.setVisibility(View.VISIBLE);
            lvGioHang.setVisibility(View.INVISIBLE);
        }else {
            gioHangAdapter.notifyDataSetChanged();
            tvThongbao.setVisibility(View.INVISIBLE);
            lvGioHang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(tbGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        lvGioHang=findViewById(R.id.lvGioHang);
        tvThongbao=findViewById(R.id.tvThongbao);
        tvTongTien=findViewById(R.id.tvTongtien);
        btThanhToan=findViewById(R.id.btThanhtoan);
        btTieptucMua=findViewById(R.id.btTieptucMua);
        tbGioHang=findViewById(R.id.tbGioHang);
        if (MainActivity.giohangArrayList==null){
            MainActivity.giohangArrayList=new ArrayList<>();
        }
        gioHangAdapter=new GioHangAdapter(GioHangActivity.this,MainActivity.giohangArrayList);
        lvGioHang.setAdapter(gioHangAdapter);
    }
}
