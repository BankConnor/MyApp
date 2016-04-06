package com.example.administrator.lrucachedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.main_listview);
        initData();
        LruCacheAdapter adapter = new LruCacheAdapter(mListView,ImageRes.returnData(),this);
        mListView.setAdapter(adapter);
    }

    private void initData() {
       ImageRes.addUri("http://i0.hdslb.com/video/1d/1df86704f6d3af8bb018b0da470b99d3.jpg");
        ImageRes.addUri("http://i0.hdslb.com/video/6c/6cd6274b8978a796395aeb957456c269.jpg");
        ImageRes.addUri("http://i0.hdslb.com/video/05/059630cf48686a843faae365e0f1baba.jpg");
        ImageRes.addUri("http://i1.hdslb.com/video/53/53725fe7bc6cf557bf4ed43dcf39b6f1.jpg");
        ImageRes.addUri("http://img.xiami.net/images/artistlogo/50/14494684907850_2.jpg");
        ImageRes.addUri("http://i0.hdslb.com/video/d6/d6e74d2b6a2e5133fea2b22a54f8bca1.jpg");
        ImageRes.addUri("http://img3.douban.com/view/photo/photo/public/p307298475.jpg");
        ImageRes.addUri("http://e.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=98b061bb58b5c9ea62a60be7e0099a36/dbb44aed2e738bd41f37e581a28b87d6277ff968.jpg");
        ImageRes.addUri("http://img4.duitang.com/uploads/item/201506/06/20150606203028_etEfx.thumb.224_0.png");
        ImageRes.addUri("http://img4.duitang.com/uploads/item/201406/23/20140623130452_U5UVC.jpeg");
        ImageRes.addUri("http://i2.hdslb.com/video/55/55077abaabbfb711d6a7ff33b0852ffd.jpg");
        ImageRes.addUri("http://www.1ting.com/mv/covers/video/93/860885.jpg");
        ImageRes.addUri("http://i-7.vcimg.com/crop/87dbdb2e6f2460303bc017cbd381e3f0392902%28600x%29/thumb.jpg");
        ImageRes.addUri("http://imgsrc.baidu.com/baike/pic/item/d5462bfa4eaf5dc59e51468b.jpg");
        ImageRes.addUri("http://img5q.duitang.com/uploads/item/201401/31/20140131004143_G4Cvm.thumb.224_0.jpeg");
        ImageRes.addUri("http://mvimg2.meitudata.com/561a97fbc6d983167.jpg");
        ImageRes.addUri("http://i1.hdslb.com/video/27/27110878c0a1bd98421a86e83208f8b5.jpg");
        ImageRes.addUri("http://imgsrc.baidu.com/baike/pic/item/a8ec8a13632762d065d80b10a0ec08fa503dc6bf.jpg");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageRes.returnData().clear();
    }
}
