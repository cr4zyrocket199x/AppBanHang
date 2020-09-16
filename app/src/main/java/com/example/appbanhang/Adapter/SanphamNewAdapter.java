package com.example.appbanhang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.Activity.ChiTietSPActivity;
import com.example.appbanhang.Activity.LaptopActivity;
import com.example.appbanhang.Activity.MainActivity;
import com.example.appbanhang.Model.Sanpham;
import com.example.appbanhang.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanphamNewAdapter extends RecyclerView.Adapter<SanphamNewAdapter.ItemHolder> {
    Context context;
    ArrayList<Sanpham> arrayListSP;

    public SanphamNewAdapter(Context context, ArrayList<Sanpham> arrayListSP) {
        this.context = context;
        this.arrayListSP = arrayListSP;
    }

    @NonNull
    @Override
    public SanphamNewAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_spmoinhat,null);
        SanphamNewAdapter.ItemHolder itemHolder=new SanphamNewAdapter.ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SanphamNewAdapter.ItemHolder holder, int position) {
        Sanpham sanpham=arrayListSP.get(position);
        holder.tvTenSPNew.setText(sanpham.getTenSP());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.tvGiaSPNew.setText("Giá: "+decimalFormat.format(sanpham.getGiaSP())+" vnđ");
        Picasso.get().load(sanpham.getHinhSP())
                .error(R.drawable.errordisplay_)
                .into(holder.ivHinhSPNew);
    }

    @Override
    public int getItemCount() {
        return arrayListSP.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView ivHinhSPNew;
        public TextView tvTenSPNew,tvGiaSPNew;

        public ItemHolder(View iteamView){
            super(iteamView);
            ivHinhSPNew=(ImageView)iteamView.findViewById(R.id.ivSPMoiNhat);
            tvTenSPNew=(TextView)iteamView.findViewById(R.id.tvTenSPMoiNhat);
            tvGiaSPNew=(TextView) iteamView.findViewById(R.id.tvGiaSPMoiNhat);
            iteamView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle=new Bundle();
                    Intent intent=new Intent(context, ChiTietSPActivity.class);
                    Sanpham sanpham=new Sanpham(arrayListSP.get(getPosition()).getIdSP(),arrayListSP.get(getPosition()).getTenSP(),
                            arrayListSP.get(getPosition()).getGiaSP(),arrayListSP.get(getPosition()).getHinhSP(),
                            arrayListSP.get(getPosition()).getMoTaSP(),arrayListSP.get(getPosition()).getIdLoaiSP());
                    bundle.putSerializable("chitietsp",sanpham);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }
}
