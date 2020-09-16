package com.example.appbanhang.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.Ultil.CheckConnection;
import com.example.appbanhang.Ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongtinKHActivity extends AppCompatActivity {

    EditText etTenKH,etSdtKH,etEmail,etDiachiKH;
    Button btXacnhan,btTrove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin_k_h);

        Anhxa();
        EventButton();
    }

    private void EventButton() {
        btTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                    if (etTenKH.getText().toString().trim().length() == 0 || etSdtKH.getText().toString().trim().length() == 0 || etEmail.getText().toString().trim().length() == 0 || etDiachiKH.getText().toString().trim().length() == 0) {
                        Toast.makeText(getApplicationContext(), "Hãy nhập đủ thông tin trước khi đặt hàng !", Toast.LENGTH_SHORT).show();
                    } else {
                        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.Duongdanpushdonhang, new Response.Listener<String>() {
                            @Override
                            public void onResponse(final String madh) {
                                Log.d("madh",madh);
                                if (Integer.parseInt(madh)>0){
                                    RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
                                    StringRequest request=new StringRequest(Request.Method.POST, Server.Duongdanpushchitietdonhang, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(final String response) {
                                            if (response.equals("1")){
                                                MainActivity.giohangArrayList.clear();
                                                CheckConnection.ShowToask_Short(getApplicationContext(),"Bạn đã thêm dữ liệu giỏ hàng thành công");
                                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                                startActivity(intent);
                                                CheckConnection.ShowToask_Short(getApplicationContext(),"Mời bạn tiếp tục mua hàng !");
                                            }else {
                                                CheckConnection.ShowToask_Short(getApplicationContext(),"Lỗi thêm dữ liệu giỏ hàng");
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }){
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            JSONArray jsonArray=new JSONArray();
                                            for (int i=0;i<MainActivity.giohangArrayList.size();i++){
                                                JSONObject jsonObject=new JSONObject();
                                                try {
                                                    jsonObject.put("IdDH",madh);
                                                    jsonObject.put("IdSP",MainActivity.giohangArrayList.get(i).getIdsp());
                                                    jsonObject.put("TenSP",MainActivity.giohangArrayList.get(i).getTensp());
                                                    jsonObject.put("SoLuong",MainActivity.giohangArrayList.get(i).getSoluongsp());
                                                    jsonObject.put("DonGia",MainActivity.giohangArrayList.get(i).getGiasp());
                                                }catch (JSONException e){
                                                    e.printStackTrace();
                                                }
                                                jsonArray.put(jsonObject);
                                            }
                                            HashMap<String,String> hashMap=new HashMap<>();
                                            hashMap.put("json",jsonArray.toString());
                                            return hashMap;
                                        }
                                    };
                                    queue.add(request);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String,String>hashMap=new HashMap<>();
                                hashMap.put("TenKH",etTenKH.getText().toString().trim());
                                hashMap.put("SDT",etSdtKH.getText().toString().trim());
                                hashMap.put("Email",etEmail.getText().toString().trim());
                                hashMap.put("DiaChi",etDiachiKH.getText().toString().trim());
                                return hashMap;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                }else {
                    CheckConnection.ShowToask_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối !");
                }
            }
        });
    }

    private void Anhxa() {
        etTenKH=findViewById(R.id.etTenKH);
        etSdtKH=findViewById(R.id.etSdtKH);
        etEmail=findViewById(R.id.etEmailKH);
        etDiachiKH=findViewById(R.id.etDiachiKH);
        btXacnhan=findViewById(R.id.btXacnhan);
        btTrove=findViewById(R.id.btTrove);
    }
}
