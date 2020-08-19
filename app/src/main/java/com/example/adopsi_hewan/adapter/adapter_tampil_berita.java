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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.adopsi_hewan.R;
import com.example.adopsi_hewan.detail_hewan;
import com.example.adopsi_hewan.model.berita.ResultItem_berita;
import com.example.adopsi_hewan.model.model_tampil_hewan.ResultItem_hewn;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class adapter_tampil_berita extends RecyclerView.Adapter<adapter_tampil_berita.HolderData> {
    private static CountDownTimer countDownTimer;
    String kriim;

    public adapter_tampil_berita(Context ctx, List<ResultItem_berita> mList, String kriim) {
        this.kriim = kriim;
        this.mList = mList;
        this.ctx = ctx;
    }

    private List<ResultItem_berita> mList ;
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
        final ResultItem_berita dm = mList.get(position);
        String waktu;


        Toast.makeText(ctx, "kirim"+ kriim, Toast.LENGTH_SHORT).show();
        holder.nama.setText(dm.getJudul());
        holder.tgl.setText(dm.getTgl());






        Glide.with(ctx)
                .load("http://192.168.43.14/adopsi/gambar/"+dm.getFotoInfo())
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

        @BindView(R.id.txt_judul_new) TextView nama;

        @BindView(R.id.txt_tgl_new) TextView tgl;


        @BindView(R.id.bg_img_new) ImageView gambar;

        ResultItem_berita dm;




        public HolderData (View v)
        {
            super(v);
            ButterKnife.bind(this, itemView);
            nama.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (kriim.equals("admni")){
                        //Toast.makeText(ctx, "ID"+dm.get(), Toast.LENGTH_SHORT).show();
                        click_desc_data_Saya();
                    }else   {
                        //Toast.makeText(ctx, "ID"+dm.getIdRegis(), Toast.LENGTH_SHORT).show();
                        click_desc_data();
                    }

                }
            });
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (kriim.equals("admin")){
                        //  Toast.makeText(ctx, "ID"+dm.getIdRegis(), Toast.LENGTH_SHORT).show();
                       // click_desc_data_Saya();
                    }else   {
                       // Toast.makeText(ctx, "ID"+dm.getStatus(), Toast.LENGTH_SHORT).show();
                        // click_desc_data();
                    }

                }
            });
        }

        public void click_desc_data(){
            Intent goInput = new Intent(ctx, detail_hewan.class);
            goInput.putExtra("nama", dm.getJudul());


            ctx.startActivity(goInput);
        }

        public void click_desc_data_Saya(){
//            Intent goInput = new Intent(ctx, menu_detail_laporan_pelayanan.class);
//            goInput.putExtra("id_rgister", dm.getIdRegis());
//            goInput.putExtra("nama", dm.getNama());
//            ctx.startActivity(goInput);
        }


    }



}
