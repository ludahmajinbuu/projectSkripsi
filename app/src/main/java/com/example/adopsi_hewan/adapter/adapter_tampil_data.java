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
import com.example.adopsi_hewan.model.model_tampil_hewan.ResultItem_hewan_new;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class adapter_tampil_data extends RecyclerView.Adapter<adapter_tampil_data.HolderData> {
    private static CountDownTimer countDownTimer;
    String kriim;

    public adapter_tampil_data(Context ctx, List<ResultItem_hewan_new> mList, String kriim) {
        this.kriim = kriim;
        this.mList = mList;
        this.ctx = ctx;
    }

    private List<ResultItem_hewan_new> mList ;
    private Context ctx;

    String status_laporan,status_baru;
    String timer;

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.hewan_simple,parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(final HolderData holder, int position) {
        //  Toast.makeText(ctx, waktu, Toast.LENGTH_SHORT).show();
        final ResultItem_hewan_new dm = mList.get(position);
        String waktu;


        Toast.makeText(ctx, "kirim"+ kriim, Toast.LENGTH_SHORT).show();
        holder.nama.setText("NAMA : "+dm.getNama());
        holder.umur.setText("UMUR : "+dm.getUmur());
        holder.kelamin.setText("Jenis Kelamin : "+dm.getJk());
        holder.jenis.setText("Jenis : "+dm.getJenis());
        holder.status.setText(dm.getStatus());





        Glide.with(ctx)
                .load("http://192.168.43.109/adopsi/gambar/"+dm.getFotoHewan())
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

        @BindView(R.id.txt_nmaHewan) TextView nama;

        @BindView(R.id.txt_jnsHewan) TextView jenis;

        @BindView(R.id.txt_klmnHewan) TextView kelamin;

        @BindView(R.id.txt_Umur) TextView umur;

        @BindView(R.id.txt_sttusHewan) TextView status;

        @BindView(R.id.imgHewan) ImageView gambar;

        ResultItem_hewan_new dm;




        public HolderData (View v)
        {
            super(v);
            ButterKnife.bind(this, itemView);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click_desc_data();

                }
            });
        }

        public void click_desc_data(){
            Intent goInput = new Intent(ctx, detail_hewan.class);
               goInput.putExtra("nama", dm.getNama());
            goInput.putExtra("id", dm.getId());
               goInput.putExtra("umur", dm.getUmur());
               goInput.putExtra("kelamin", dm.getJk());
            goInput.putExtra("berat", dm.getBerat());
            goInput.putExtra("jenis", dm.getJenis());
            goInput.putExtra("steril", dm.getSteril());
            goInput.putExtra("vaksin", dm.getVaksin());
            goInput.putExtra("foto", dm.getFotoHewan());
            goInput.putExtra("keterangan", dm.getKeterangan());
            goInput.putExtra("tgl", dm.getTgl());
            goInput.putExtra("status", dm.getStatus());
            goInput.putExtra("nik", dm.getNik());
            goInput.putExtra("status", dm.getStatus());
            goInput.putExtra("id_alasan", dm.getIdAlasan());
            goInput.putExtra("id_adopsi", dm.getIdAdopsi());
            goInput.putExtra("alasan", dm.getAlasan());
            goInput.putExtra("nik", dm.getNik());


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
