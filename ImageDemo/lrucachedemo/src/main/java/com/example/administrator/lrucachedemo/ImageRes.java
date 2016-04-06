package com.example.administrator.lrucachedemo;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public class ImageRes {
    private static List<String> datas = new ArrayList<String>();
    
   public static void addUri(String uri)
   {
       if(uri!=null)
       {
           datas.add(uri);
       }
   }

    public static List<String> returnData()
    {
        Log.e("Connor","数据源的大小"+datas.size());
        return datas;
    }

    public static void removeIndexUri(int index)
    {
        datas.remove(index);
    }

}
