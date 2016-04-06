package com.example.administrator.lrucachedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/4/6.
 * 用户加载网络图片的
 */
public class ImageLoader {

    private List<String> datas;
    private ListView mListview;
    private MyLruCache cache;
    private Set<ASyncDownloadImage> tasks;//所有下载请求全部加入到这里 方便随时停止下载
    public ImageLoader(ListView listview, List<String> datas)
    {
        this.datas = datas;
        mListview = listview;
        cache = new MyLruCache();//初始化LruCache对象
        tasks = new HashSet<ASyncDownloadImage>();//初始化下载合计容器
    }

    public void showImage(String uri, ImageView imageView)
    {
        Bitmap bitmap = cache.getBitmapFromMemoryCaches(uri);
        if(bitmap!=null)
        {
            imageView.setImageBitmap(bitmap);
        }else
        {
            /**
             * 用户开始屏幕滑动 设置成默认头像
             * 或者网络错误
             */
            imageView.setImageResource(R.mipmap.ic_launcher);
        }
    }


    public void loadImage(int start, int end)
    {
        for (int i=start;i<end; ++i)
        {
            String uri = datas.get(i);
            Log.e("Connor",uri);
            /**
             * 如果从缓存中获取不到数据
             * 就重新去网络上下载图片
             */
            Bitmap bitmap = cache.getBitmapFromMemoryCaches(uri);
            if(bitmap==null)
            {
                /**
                 * 请求网络下载
                 */
                ASyncDownloadImage downloadImage = new ASyncDownloadImage(uri);//传入当前滑动到的Item去获取他的图片
                tasks.add(downloadImage);//把当前下载任务组
                downloadImage.execute();//启动下载
            }
            else{
                //缓存中有这张URI对应的图片取出来设置上去
                ImageView imageView = (ImageView) mListview.findViewWithTag(uri);
                if(bitmap!=null&&imageView!=null)
                {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }


    class ASyncDownloadImage extends AsyncTask<Void, Void, Bitmap> {

        private String url;

        public ASyncDownloadImage(String url) {
            this.url = url;
        }


        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap bitmap = getBitmapFromUrl(url);//获取到网络上的图片后开始下载
            if (bitmap != null) {
                cache.addBitmapToMemoryCaches(url, bitmap);//下载完毕就把他保存到缓存中
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            /**
             * 在Listview中设置每个Item时候
             * 绑定了Uri与当期ImageView
             * 两者是对应关系
             */
            ImageView imageView = (ImageView) mListview.findViewWithTag(url);
            if (imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);//下载当前图片完毕就设置到图片上
            }
            tasks.remove(this);//下载完毕 就把自己从下载合集里删除掉
        }
    }

    private static Bitmap getBitmapFromUrl(String urlString) {
        Bitmap bitmap;
        InputStream is = null;
        try {
            /**
             *下载任务开始
             */
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(conn.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            conn.disconnect();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
        }
        return null;
    }

    public void cancelAllTasks() {
        if (tasks != null) {
            for (ASyncDownloadImage task : tasks) {
                /**
                 * 产生停止条件
                 * 当前用户在快速滑动屏幕
                 * 禁止掉后台下载任务保证 滑动的平稳性
                 */
                task.cancel(false);//停止掉当前的Task任务
            }
        }
    }
}
