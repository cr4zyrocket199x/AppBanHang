package com.example.appbanhang.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhang.Model.Sanpham;
import com.example.appbanhang.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanphamAdapter extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<Sanpham> DienthoaiArrayList;
    CustomFilter filter;
    ArrayList<Sanpham> filterList;

    public SanphamAdapter(Context context, ArrayList<Sanpham> dienthoaiArrayList) {
        this.context = context;
        DienthoaiArrayList = dienthoaiArrayList;
        this.filterList=dienthoaiArrayList;
    }

    @Override
    public int getCount() {
        return DienthoaiArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return DienthoaiArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        if (filter==null){
            filter=new CustomFilter();
        }
        return filter;
    }

    public class ViewHolder{
        public TextView tvTenSP,tvGiaSP,tvTTSP;
        public ImageView ivSP;
    }

    private class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();
                int gia = Integer.parseInt((String) constraint);
                ArrayList<Sanpham> filters = new ArrayList<>();
                for (int i = 0; i < filterList.size(); i++) {
                    if (filterList.get(i).getGiaSP() < gia) {
                        Sanpham p= new Sanpham(filterList.get(i).getIdSP(),filterList.get(i).getTenSP(),filterList.get(i).getGiaSP(),filterList.get(i).getHinhSP(),filterList.get(i).getMoTaSP(),filterList.get(i).getIdLoaiSP());
                        filters.add(p);
                    }
                }
                results.count=filters.size();
                results.values=filters;
            }else {
                results.count=filterList.size();
                results.values=filterList;
            }
            return results;
        }



        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            DienthoaiArrayList = (ArrayList<Sanpham>) results.values;
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SanphamAdapter.ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new SanphamAdapter.ViewHolder();
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.dong_sanpham,null);
            viewHolder.tvTenSP=convertView.findViewById(R.id.tvTenSP);
            viewHolder.tvGiaSP=convertView.findViewById(R.id.tvGiaSP);
            viewHolder.tvTTSP=convertView.findViewById(R.id.tvTTSP);
            viewHolder.ivSP=convertView.findViewById(R.id.ivSP);
            convertView.setTag(viewHolder);

        }else {
            viewHolder=(SanphamAdapter.ViewHolder) convertView.getTag();
        }
        Sanpham sanpham=(Sanpham)getItem(position);

        viewHolder.tvTenSP.setText(sanpham.getTenSP());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.tvGiaSP.setText("Giá: "+decimalFormat.format(sanpham.getGiaSP())+" vnđ");
        viewHolder.tvTTSP.setMaxLines(2);
        viewHolder.tvTTSP.setEllipsize(TextUtils.TruncateAt.END);//Hiện dấu ... ở cuối
        viewHolder.tvTTSP.setText(sanpham.getMoTaSP());
        Picasso.get().load(sanpham.getHinhSP())
                .error(R.drawable.errordisplay_)
                .into(viewHolder.ivSP);
        return convertView;
    }
}
