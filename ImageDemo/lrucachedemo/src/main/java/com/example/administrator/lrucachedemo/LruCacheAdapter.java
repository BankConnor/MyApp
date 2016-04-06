package com.example.administrator.lrucachedemo;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public class LruCacheAdapter extends BaseAdapter implements AbsListView.OnScrollListener {

    private final static String TAG="Connor";
    private ListView mListView;
    private ImageLoader mImageLoader;
    private List<String>datas;
    private Activity act;
    private int start;
    private int end;
    private boolean flag;//第一次时候需要自己加载当前第一屏的图片

    /**
     * 外部获取到数据源 需要被适配器适配的Listview 和当前Activty
     * @param listview
     * @param datas
     */
    public LruCacheAdapter(ListView listview,  List<String>datas,Activity act)
    {
        mListView = listview;
        this.datas = datas;
        mImageLoader = new ImageLoader(listview,datas);
        this.act = act;
        listview.setOnScrollListener(this);//滑动监听
        flag=true;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null)
        {
            holder = new ViewHolder();
            convertView = act.getLayoutInflater().inflate(R.layout.image_layout,null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.image_main);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageView.setTag(datas.get(position));
        holder.imageView.setImageResource(R.mipmap.ic_launcher);//默认加载默认图片 刷新时候在更换
        mImageLoader.showImage(datas.get(position), holder.imageView);
        Log.e(TAG,"刷新ListView"+position);
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState==SCROLL_STATE_TOUCH_SCROLL)
        {
            /**
             * 只有在滚动结束时候 才开始加载网络图片
             */
            mImageLoader.cancelAllTasks();
            Log.e(TAG,"准备滚动");
        }else if(scrollState==SCROLL_STATE_FLING)
        {
            /**
         * 只有在滚动结束时候 才开始加载网络图片
         */
            mImageLoader.cancelAllTasks();
            Log.e(TAG,"持续滚动中...");
        }else if(scrollState==SCROLL_STATE_IDLE)
        {
            /**
             * 在滚动结束的时候开始更新图片资源
             * 在滚动时候不处理 这样保证滚动的平滑性
             */
            Log.e(TAG,"滚动结束");
            mImageLoader.loadImage(start,end);
            //滚动结束根据从某个位置显示到整个屏幕的所有的Item全部进行网络连接访问 一旦开始滑动立马停止网络访问
        }
        else
        {
            /**
             * 只有在滚动结束时候 才开始加载网络图片
             */
            mImageLoader.cancelAllTasks();
        }
    }

    /**
     * 持续滚动
     * 记录滑动信息
     * @param view ListView
     * @param firstVisibleItem 屏幕上可见的第一条在Adapter可见的下标个数(起始也算是数据源的下标数)
     * @param visibleItemCount 表示屏幕上一共显示出的个数(部分显示也算)
     * @param totalItemCount 表示整个ListView下标个数(总和)
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        start = firstVisibleItem;//当前显示的第一条是这个
        end = visibleItemCount+firstVisibleItem;
        Log.e(TAG,firstVisibleItem+" -- "+visibleItemCount);
        if(flag&&visibleItemCount>0)
        {
            /**
             * 仅仅是用来加载第一屏幕的数据图片
             */
        Log.e(TAG,"开始下载");
            mImageLoader.loadImage(start,end);
            flag=false;
        }
    }

    public class ViewHolder {
        public ImageView imageView;
    }
}
