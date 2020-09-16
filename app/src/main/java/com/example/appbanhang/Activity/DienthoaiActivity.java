package com.example.appbanhang.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.appbanhang.Adapter.SanphamAdapter;
import com.example.appbanhang.Model.Sanpham;
import com.example.appbanhang.R;

import java.util.ArrayList;

public class DienthoaiActivity extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar tbDt;
    ListView lvDt;
    EditText etTK;
    SanphamAdapter dienthoaiAdapter;
    ArrayList<Sanpham> DienthoaiArrayList;
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
        setContentView(R.layout.activity_dienthoai);

        Anhxa();
        ActionToolbar();
        GetData();

        lvDt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                Intent intent=new Intent(DienthoaiActivity.this,ChiTietSPActivity.class);
                Sanpham sanpham=new Sanpham(DienthoaiArrayList.get(position).getIdSP(),DienthoaiArrayList.get(position).getTenSP(),
                        DienthoaiArrayList.get(position).getGiaSP(),DienthoaiArrayList.get(position).getHinhSP(),
                        DienthoaiArrayList.get(position).getMoTaSP(),DienthoaiArrayList.get(position).getIdLoaiSP());
                bundle.putSerializable("chitietsp",sanpham);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        etTK.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dienthoaiAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void Anhxa(){
        tbDt= findViewById(R.id.tbDienthoai);
        lvDt=findViewById(R.id.lvDienthoai);
        DienthoaiArrayList=new ArrayList<>();
        dienthoaiAdapter=new SanphamAdapter(getApplicationContext(),DienthoaiArrayList);
        lvDt.setAdapter(dienthoaiAdapter);
        etTK=findViewById(R.id.etTimKiem);
    }
    public void ActionToolbar(){
        setSupportActionBar(tbDt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbDt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void GetData(){
        for (int i=0;i<MainActivity.sanphamallArrayList.size();i++){
            if (MainActivity.sanphamallArrayList.get(i).getIdLoaiSP()==1){
                DienthoaiArrayList.add(MainActivity.sanphamallArrayList.get(i));
            }
        }
    }
}
