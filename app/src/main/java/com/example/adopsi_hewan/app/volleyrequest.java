package com.example.adopsi_hewan.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by RADEN on 6/27/2017.
 */

public class volleyrequest {
    private static volleyrequest volleyrequest;
    private static RequestQueue requestQueue;
    private static Context context;
    private ImageLoader imageLoader;


    private volleyrequest(Context context){
        this.context=context;
        this.requestQueue= getRequestQueue();


        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {

            private final LruCache<String,Bitmap> cache=new LruCache<String, Bitmap>(20);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);

            }
        });


    }

    public static synchronized volleyrequest getInstance(Context context){

        if (volleyrequest==null){
            volleyrequest= new volleyrequest(context);
        }
        return  volleyrequest;
    }

    public RequestQueue getRequestQueue(){
        if ((requestQueue == null)) {

            Cache cache=new DiskBasedCache(context.getCacheDir(),10*1024*1024);
            Network network=new BasicNetwork(new HurlStack());
            requestQueue=new RequestQueue(cache,network);
            requestQueue.start();
        }
        return requestQueue;

    }
    public ImageLoader getImageLoader(){
        return  imageLoader;
    }
}
