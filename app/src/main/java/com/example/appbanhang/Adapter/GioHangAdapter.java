package com.example.appbanhang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhang.Activity.GioHangActivity;
import com.example.appbanhang.Activity.MainActivity;
import com.example.appbanhang.Model.Giohang;
import com.example.appbanhang.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohang>giohangArrayList;

    public GioHangAdapter(Context context, ArrayList<Giohang> giohangArrayList) {
        this.context = context;
        this.giohangArrayList = giohangArrayList;
    }

    @Override
    public int getCount() {
        return giohangArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return giohangArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView tvTenMonHang,tvGiaMonHang;
        public ImageView ivMonHang;
        public Button btTangSL,btSoluong,btGiamSL;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.dong_giohang,null);
            viewHolder.tvTenMonHang=(TextView)convertView.findViewById(R.id.tvTenMonHang);
            viewHolder.tvGiaMonHang=(TextView)convertView.findViewById(R.id.tvGiaMonHang);
            viewHolder.ivMonHang=(ImageView) convertView.findViewById(R.id.ivGiohang);
            viewHolder.btGiamSL=(Button) convertView.findViewById(R.id.btGiamSL);
            viewHolder.btSoluong=(Button) convertView.findViewById(R.id.btSoluong);
            viewHolder.btTangSL=(Button) convertView.findViewById(R.id.btTangSL);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        final Giohang giohang=(Giohang)getItem(position);
        viewHolder.tvTenMonHang.setText(giohang.getTensp());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.tvGiaMonHang.setText(decimalFormat.format(giohang.getGiasp())+" vnđ");
        Picasso.get().load(giohang.getHinhsp())
                .error(R.drawable.errordisplay_)
                .into(viewHolder.ivMonHang);
        viewHolder.btSoluong.setText(giohang.getSoluongsp()+"");

        int sl=Integer.parseInt(viewHolder.btSoluong.getText().toString());
        if (sl>=10){
            viewHolder.btTangSL.setVisibility(View.INVISIBLE);
            viewHolder.btGiamSL.setVisibility(View.VISIBLE);
        }else if (sl<=1){
            viewHolder.btTangSL.setVisibility(View.VISIBLE);
            viewHolder.btGiamSL.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.btTangSL.setVisibility(View.VISIBLE);
            viewHolder.btGiamSL.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        final ViewHolder finalViewHolder1 = viewHolder;
        final ViewHolder finalViewHolder2 = viewHolder;
        final ViewHolder finalViewHolder3 = viewHolder;
        viewHolder.btTangSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slnew=Integer.parseInt(finalViewHolder.btSoluong.getText().toString())+1;
                int slht= MainActivity.giohangArrayList.get(position).getSoluongsp();
                int giaht=MainActivity.giohangArrayList.get(position).getGiasp();
                MainActivity.giohangArrayList.get(position).setSoluongsp(slnew);
                int gianew=(giaht/slht)*slnew;
                MainActivity.giohangArrayList.get(position).setGiasp(gianew);
                DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
                finalViewHolder1.tvGiaMonHang.setText(decimalFormat.format(gianew)+" vnđ");
                GioHangActivity.EventUltil(); //Update lai DL
                if (slnew>9){
                    finalViewHolder2.btTangSL.setVisibility(View.INVISIBLE);
                    finalViewHolder2.btGiamSL.setVisibility(View.VISIBLE);
                    finalViewHolder3.btSoluong.setText(slnew+"");
                }else {
                    finalViewHolder2.btTangSL.setVisibility(View.VISIBLE);
                    finalViewHolder2.btGiamSL.setVisibility(View.VISIBLE);
                    finalViewHolder3.btSoluong.setText(slnew+"");
                }
            }
        });
        viewHolder.btGiamSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slnew=Integer.parseInt(finalViewHolder.btSoluong.getText().toString())-1;
                int slht= MainActivity.giohangArrayList.get(position).getSoluongsp();
                int giaht=MainActivity.giohangArrayList.get(position).getGiasp();
                MainActivity.giohangArrayList.get(position).setSoluongsp(slnew);
                int gianew=(giaht/slht)*slnew;
                MainActivity.giohangArrayList.get(position).setGiasp(gianew);
                DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
                finalViewHolder1.tvGiaMonHang.setText(decimalFormat.format(gianew)+" vnđ");
                GioHangActivity.EventUltil(); //Update lai DL
                if (slnew<2){
                    finalViewHolder2.btTangSL.setVisibility(View.VISIBLE);
                    finalViewHolder2.btGiamSL.setVisibility(View.INVISIBLE);
                    finalViewHolder3.btSoluong.setText(slnew+"");
                }else {
                    finalViewHolder2.btTangSL.setVisibility(View.VISIBLE);
                    finalViewHolder2.btGiamSL.setVisibility(View.VISIBLE);
                    finalViewHolder3.btSoluong.setText(slnew+"");
                }
            }
        });
        return convertView;
    }
}
