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

public class LaptopActivity extends AppCompatActivity {

    EditText etTK;
    androidx.appcompat.widget.Toolbar tbLaptop;
    ListView lvLaptop;
    SanphamAdapter laptopAdapter;
    ArrayList<Sanpham>laptopArrayList;
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
        setContentView(R.layout.activity_laptop);

        Anhxa();
        ActionToolbar();
        GetData();

        lvLaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                Intent intent=new Intent(LaptopActivity.this,ChiTietSPActivity.class);
                Sanpham sanpham=new Sanpham(laptopArrayList.get(position).getIdSP(),laptopArrayList.get(position).getTenSP(),
                        laptopArrayList.get(position).getGiaSP(),laptopArrayList.get(position).getHinhSP(),
                        laptopArrayList.get(position).getMoTaSP(),laptopArrayList.get(position).getIdLoaiSP());
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
                laptopAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void Anhxa(){
        tbLaptop= findViewById(R.id.tbLaptop);
        lvLaptop=findViewById(R.id.lvLaptop);
        laptopArrayList=new ArrayList<>();
        laptopAdapter=new SanphamAdapter(getApplicationContext(),laptopArrayList);
        lvLaptop.setAdapter(laptopAdapter);
        etTK=findViewById(R.id.etTimKiem);
    }
    public void ActionToolbar(){
        setSupportActionBar(tbLaptop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbLaptop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void GetData(){
        for (int i=0;i<MainActivity.sanphamallArrayList.size();i++){
            if (MainActivity.sanphamallArrayList.get(i).getIdLoaiSP()==2){
                laptopArrayList.add(MainActivity.sanphamallArrayList.get(i));
            }
        }
    }
}
