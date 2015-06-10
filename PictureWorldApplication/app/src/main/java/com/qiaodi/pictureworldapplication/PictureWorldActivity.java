package com.qiaodi.pictureworldapplication;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;

import io.fabric.sdk.android.Fabric;

import org.lucasr.twowayview.widget.TwoWayView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PictureWorldActivity extends ActionBarActivity {

    private static final int PARSE_COMPLETED = 0;

    private TwoWayView mMainView;

    private MainViewAdapter mMainViewAdapter;

    private ExecutorService mExecutorService;

    private MainHandler mMainHandle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        mExecutorService = Executors.newSingleThreadExecutor();
        mExecutorService.execute(new parsePictureObject());
        Fresco.initialize(getApplicationContext());
        setContentView(R.layout.activity_picture_world);
        mMainHandle = new MainHandler(getMainLooper());
        mMainView = (TwoWayView) findViewById(R.id.list);
        mMainViewAdapter = new MainViewAdapter();
        mMainView.setAdapter(mMainViewAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_picture_world, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class parsePictureObject implements Runnable {
        @Override
        public void run() {
            ArrayList pictureObjects = new ArrayList<PictureObject>();

            try {
                String str = getPictureData(null);
                ;
                Log.w("PictureWorld", "source code: " + str);
                Pattern p = Pattern.compile("img\\s+src\\s*=\\s*(\"[^\"]*\"|'[^']*')");
                Matcher m = p.matcher(str);
                while (m.find()) {
                    int slen;
                    String imgsrc;
                    imgsrc = m.group();
                    slen = imgsrc.length();
                    char c = imgsrc.charAt(slen - 1);
                    pictureObjects.add(new PictureObject("http://www.tuubar.com" + imgsrc.substring(imgsrc.indexOf(c) + 1, slen - 1), null));
                    Log.w("PictureWorld", "Parse uri " + imgsrc.substring(imgsrc.indexOf(c) + 1, slen - 1));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Message msg = Message.obtain();
            msg.what = PARSE_COMPLETED;
            msg.obj = pictureObjects;
            mMainHandle.sendMessage(msg);
        }
    }

    public String getPictureData(@Nullable String path) throws Exception {
        // 类 URL 代表一个统一资源定位符，它是指向互联网“资源”的指针。
        URL url = new URL("http://www.tuubar.com/");
        // 每个 HttpURLConnection 实例都可用于生成单个请求，
        //但是其他实例可以透明地共享连接到 HTTP 服务器的基础网络
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置 URL 请求的方法
        conn.setRequestMethod("GET");
        //设置一个指定的超时值（以毫秒为单位），
        //该值将在打开到此 URLConnection 引用的资源的通信链接时使用。
        conn.setConnectTimeout(5 * 1000);
        // conn.getInputStream()返回从此打开的连接读取的输入流
        InputStream inStream = conn.getInputStream();// 通过输入流获取html数据
        byte[] data = readInputStream(inStream);// 得到html的二进制数据
        String html = new String(data);
        return html;

    }

    //读取输入流中的数据，返回字节数组byte[]
    public byte[] readInputStream(InputStream inStream) throws Exception {
        //此类实现了一个输出流，其中的数据被写入一个 byte 数组
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // 字节数组
        byte[] buffer = new byte[1024];
        int len = 0;
        //从输入流中读取一定数量的字节，并将其存储在缓冲区数组buffer 中
        while ((len = inStream.read(buffer)) != -1) {
            // 将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此输出流
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        //toByteArray()创建一个新分配的 byte 数组。
        return outStream.toByteArray();
    }

    private class MainHandler extends Handler {
        public MainHandler(Looper mainLooper) {
            super(mainLooper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PARSE_COMPLETED:
                    mMainViewAdapter.setPictureObjects((ArrayList<PictureObject>) msg.obj);
                    break;
                default:
                    break;
            }
        }
    }
}
