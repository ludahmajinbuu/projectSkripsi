package com.example.adopsi_hewan.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.adopsi_hewan.R;
import com.example.adopsi_hewan.detail_hewan;
import com.example.adopsi_hewan.model.berita.ResultItem;

import java.util.List;

import butterknife.BindView;

public class adapter_tampil_berita extends RecyclerView.Adapter<adapter_tampil_berita.HolderData> {
    private static CountDownTimer countDownTimer;
    String kriim;

    public adapter_tampil_berita(Context ctx, List<ResultItem> mList, String kriim) {
        this.kriim = kriim;
        this.mList = mList;
        this.ctx = ctx;
    }

    private List<ResultItem> mList ;
    private Context ctx;

    String status_laporan,status_baru;
    String timer;

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_berita,parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(final HolderData holder, int position) {
        //  Toast.makeText(ctx, waktu, Toast.LENGTH_SHORT).show();
        final ResultItem dm = mList.get(position);
        String waktu;


        Toast.makeText(ctx, "kirim"+ kriim, Toast.LENGTH_SHORT).show();
        holder.judul.setText(dm.getJudul());




        Glide.with(ctx)
                .load("http://192.168.1.71/adopsi/gambar/"+dm.getFotoInfo())
                .apply(new RequestOptions()
                        .fitCenter()
                        .error(R.drawable.ic_map_black_48dp))
                .into(holder.gambar);

        holder.dm = dm;



    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    class HolderData extends  RecyclerView.ViewHolder{

        @BindView(R.id.txt_judul) TextView judul;

        @BindView(R.id.txt_tgl) TextView tgl;
        @BindView(R.id.textView3) TextView textView3;


        @BindView(R.id.bg_img) ImageView gambar;

        ResultItem dm;




        public HolderData (View v)
        {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click_desc_data();

                }
            });
        }

        public void click_desc_data(){
            Intent goInput = new Intent(ctx, detail_hewan.class);
               goInput.putExtra("nama", dm.getJudul());
               goInput.putExtra("isi", dm.getIsi());
               goInput.putExtra("tgl", dm.getTgl());
                goInput.putExtra("foto", dm.getFotoInfo());
                ctx.startActivity(goInput);
        }



    }



}
