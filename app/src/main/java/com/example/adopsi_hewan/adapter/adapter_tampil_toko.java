package com.example.adopsi_hewan.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.adopsi_hewan.model.model_tampil_hewan.ResultItem_hewn;
import com.example.adopsi_hewan.model.toko.ResultItem_toko;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class adapter_tampil_toko extends RecyclerView.Adapter<adapter_tampil_toko.HolderData> {
    private static CountDownTimer countDownTimer;
    String kriim;

    public adapter_tampil_toko(Context ctx, List<ResultItem_toko> mList, String kriim) {
        this.kriim = kriim;
        this.mList = mList;
        this.ctx = ctx;
    }

    private List<ResultItem_toko> mList ;
    private Context ctx;

    String status_laporan,status_baru;
    String timer;

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_hewan,parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(final HolderData holder, int position) {
        //  Toast.makeText(ctx, waktu, Toast.LENGTH_SHORT).show();
        final ResultItem_toko dm = mList.get(position);
        String waktu;


        Toast.makeText(ctx, "kirim"+ kriim, Toast.LENGTH_SHORT).show();
        holder.nama.setText(dm.getNamaToko());
        holder.alamat.setText(dm.getAlamat());
        holder.jam.setText(dm.getJamOprsional());
        holder.jarak.setText( "" + round(Double.parseDouble(dm.getJarak()), 2)+" KM");






        Glide.with(ctx)
                .load("http://192.168.43.14/adopsi/gambar/"+dm.getGambar())
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

        @BindView(R.id.txt_nama) TextView nama;

        @BindView(R.id.txt_jam) TextView jam;

        @BindView(R.id.txt_alamat) TextView alamat;

        @BindView(R.id.txt_jarak) TextView jarak;


        @BindView(R.id.imgHewan) ImageView gambar;

        ResultItem_toko dm;




        public HolderData (View v)
        {
            super(v);
            ButterKnife.bind(this, itemView);
            nama.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri gmmIntentUri = null;
                        Intent mapIntent;
                        String goolgeMap = "com.google.android.apps.maps";

                        if (dm.getJarak()!=null){
                            gmmIntentUri = Uri.parse("google.navigation:q=" + dm.getLat().toString() + "," + dm.getLng().toString());
                        }
                        else {

                        }
                        mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage(goolgeMap);
                        if (mapIntent.resolveActivity(ctx.getPackageManager()) != null) {
                            ctx.startActivity(mapIntent);
                        } else {
                            Toast.makeText(ctx, "Google Maps Belum Terinstal. Install Terlebih dahulu.",
                                    Toast.LENGTH_LONG).show();
                        }

                    }
            });
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri gmmIntentUri = null;
                    Intent mapIntent;
                    String goolgeMap = "com.google.android.apps.maps";

                    if (dm.getJarak()!=null){
                        gmmIntentUri = Uri.parse("google.navigation:q=" + dm.getLat().toString() + "," + dm.getLng().toString());
                    }
                    else {

                    }
                    mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage(goolgeMap);
                    if (mapIntent.resolveActivity(ctx.getPackageManager()) != null) {
                        ctx.startActivity(mapIntent);
                    } else {
                        Toast.makeText(ctx, "Google Maps Belum Terinstal. Install Terlebih dahulu.",
                                Toast.LENGTH_LONG).show();
                    }

                }
            });
        }

        public void click_desc_data(){

        }

        public void click_desc_data_Saya(){
//            Intent goInput = new Intent(ctx, menu_detail_laporan_pelayanan.class);
//            goInput.putExtra("id_rgister", dm.getIdRegis());
//            goInput.putExtra("nama", dm.getNama());
//            ctx.startActivity(goInput);
        }


    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}
