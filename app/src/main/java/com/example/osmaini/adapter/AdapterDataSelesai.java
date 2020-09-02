package com.example.osmaini.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.osmaini.Model.ListModel;
import com.example.osmaini.R;

import java.util.List;

public class AdapterDataSelesai extends RecyclerView.Adapter<AdapterDataSelesai.HolderCoba> {
    private List<ListModel> mList;
    private Context ctx;

    public AdapterDataSelesai(Context ctx, List<ListModel> mList) {
        this.ctx = ctx;
        this.mList = mList;
    }

    @Override
    public AdapterDataSelesai.HolderCoba onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_selesai, parent, false);
        AdapterDataSelesai.HolderCoba holderCoba = new AdapterDataSelesai.HolderCoba(layout);
        return holderCoba;
    }

    @Override
    public void onBindViewHolder(AdapterDataSelesai.HolderCoba holder, int position) {
        ListModel listModel = mList.get(position);
        holder.id_topup.setText(listModel.getId_pembayaran());
        holder.total.setText(listModel.getUang());
        holder.listModel = listModel;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    static class HolderCoba extends RecyclerView.ViewHolder{
        ListModel listModel;
        TextView id_topup, total, keterangan;
        Button metode;
        public HolderCoba (View v)
        {
            super(v);

            id_topup = (TextView) v.findViewById(R.id.texttopup);
            total = (TextView) v.findViewById(R.id.textuang);


        }
    }
}
