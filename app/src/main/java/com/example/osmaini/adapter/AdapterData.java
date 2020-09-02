package com.example.osmaini.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.osmaini.Model.ListModel;
import com.example.osmaini.R;
import com.example.osmaini.Upload;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {
    private List<ListModel> mList;
    private Context ctx;

    public AdapterData (Context ctx, List<ListModel> mList) {
        this.ctx = ctx;
        this.mList = mList;
    }

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list, parent, false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(HolderData holder, int position) {
        ListModel listModel = mList.get(position);
        holder.id_topup.setText(listModel.getId_pembayaran());
        holder.total.setText(listModel.getUang());
        holder.listModel = listModel;
    }


    static class HolderData extends RecyclerView.ViewHolder{
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        ListModel listModel;
        int totals = 0;
        TextView id_topup, total;
        Button metode;
        public HolderData (View v)
        {
            super(v);


            id_topup = (TextView) v.findViewById(R.id.texttopup);
            total = (TextView) v.findViewById(R.id.textuang);
            metode = (Button) v.findViewById(R.id.button6);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String stotal = total.getText().toString();
                    totals = Integer.parseInt(stotal);
                    total.setText(formatRupiah.format((double)totals));
                    Intent intent = new Intent(v.getContext(), Upload.class);
                    intent.putExtra("id_pembayaran", id_topup.getText().toString());
                    intent.putExtra("angka", total.getText().toString());
//                    intent.putExtra("angkaz", total.getText().toString());
                    v.getContext().startActivity(intent);
                }
            });

        }
    }
}
