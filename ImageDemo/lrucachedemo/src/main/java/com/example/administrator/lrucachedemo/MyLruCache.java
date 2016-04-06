package com.example.administrator.lrucachedemo;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by Administrator on 2016/4/6.
 */
public class MyLruCache {

    private int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取当前App应用的在系统的内存大小
    /**
     * 占用App分配到的内存的10%
     */
    private LruCache<String,Bitmap> cache = new LruCache<String,Bitmap>(maxMemory/10){
        /**
         * 指定Bitmap的缓存到内存的大小
         * @param key
         * @param value
         * @return
         */
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount();//返回传入的Bitmap的字节个数
        }
    };

    // 从LruCache获取中获取缓存对象
    public Bitmap getBitmapFromMemoryCaches(String url) {
        /**
         * 通过唯一标示符 取出对应的Bitmap
         */
        return cache.get(url);
    }

    // 增加缓存对象到LruCache
    public void addBitmapToMemoryCaches(String url,Bitmap bitmap) {
        if (getBitmapFromMemoryCaches(url) == null) {
            cache.put(url, bitmap);
        }
    }
}
