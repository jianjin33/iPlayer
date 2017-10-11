package com.iplayer.mine.ui;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.MediaStore;

import com.iplayer.basiclib.util.LogUtils;
import com.iplayer.mine.R;
import com.iplayer.mine.util.AudioRecorderUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AudioRecorderActivity extends AppCompatActivity implements View.OnTouchListener, AudioRecorderUtils.OnAudioStatusUpdateListener {

    private TextView button;
    private AudioRecorderDialog recorderDialog;
    private AudioRecorderUtils recorderUtils;
    private long downT;

    private ExecutorService mExecutorService;
    private MediaPlayer mediaPlayer;
    private boolean mIsPlaying = false;
    private ListView mRecorderList;
    private Handler handler;
    private String mRecorderPath;
    private File recorderParentFile;
    private File[] subs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_recorder);

        button = (TextView) findViewById(android.R.id.button1);
        mRecorderList = (ListView) findViewById(android.R.id.list);
        button.setOnTouchListener(this);

        recorderDialog = new AudioRecorderDialog(this);
        recorderDialog.setShowAlpha(0.98f);

        //录音JNI函数不具有线程安全性，因此用单线程
        mExecutorService = Executors.newSingleThreadExecutor();

        configPermission();

        handler = new MyHandler();

        mRecorderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recorder/";

        recorderParentFile = new File(mRecorderPath);

        if (!recorderParentFile.exists()) recorderParentFile.mkdirs();

        recorderUtils = new AudioRecorderUtils(recorderParentFile);
        recorderUtils.setOnAudioStatusUpdateListener(this);

        mRecorderList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {


                new AlertDialog.Builder(AudioRecorderActivity.this)
                        .setMessage("是否删除？")
                        .setPositiveButton("ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialoginterface, int arg) {
                                        dialoginterface.dismiss(); //按钮事件
                                        new File(mRecorderPath + File.separator + subs[i].getName()).delete();
                                        fileListAdapter.remove(subs[i].getName());
                                        fileListAdapter.notifyDataSetChanged();
                                    }
                                })
                        .show();
                return false;
            }
    });

}


    private static final int PERMISSION_REQUEST = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };

    /**
     * 配置权限
     */
    private void configPermission() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int recordPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        if (permission != PackageManager.PERMISSION_GRANTED || recordPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    PERMISSION_REQUEST
            );
        }

//        int recordPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
//        if (recordPermission != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(
//                    this,
//                    new String[]{Manifest.permission.RECORD_AUDIO},
//                    PERMISSION_REQUEST
//            );
//        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                recorderUtils.startRecord();
                downT = System.currentTimeMillis();
                recorderDialog.showAtLocation(view, Gravity.CENTER, 0, 0);
                button.setBackgroundResource(R.drawable.shape_recoder_btn_recorder);
                break;
            case MotionEvent.ACTION_UP:
                recorderDialog.dismiss();
                button.setBackgroundResource(R.drawable.shape_recoder_btn_normal);

                if (System.currentTimeMillis() - downT < 1000){
                    recorderUtils.releaseRecorder();
                    Toast.makeText(AudioRecorderActivity.this, "时间太短", Toast.LENGTH_SHORT).show();
                    return true;
                }
                recorderUtils.stopRecord();
                break;
        }
        return true;
    }

    @Override
    public void onUpdate(double db) {
        if (null != recorderDialog) {
            int level = (int) db;
            recorderDialog.setLevel((int) db);
            recorderDialog.setTime(System.currentTimeMillis() - downT);
        }
    }

    public void play(View view) {
        playRecorder();
    }

    /**
     * 播放录音
     */
    public void playRecorder() {
        if (subs == null)
            return;

        if (!mIsPlaying) {
            mExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    mIsPlaying = true;
                    doPlay(new File(mRecorderPath + File.separator + subs[subs.length - 1].getName()));
                }
            });

        } else {
            Toast.makeText(AudioRecorderActivity.this, "正在播放", Toast.LENGTH_SHORT).show();
        }
    }


    private void doPlay(File audioFile) {
        try {
            //配置播放器 MediaPlayer
            mediaPlayer = new MediaPlayer();
            //设置声音文件
            mediaPlayer.setDataSource(audioFile.getAbsolutePath());
            //配置音量,中等音量
            mediaPlayer.setVolume(1, 1);
            //播放是否循环
            mediaPlayer.setLooping(false);

            //设置监听回调 播放完毕
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });

            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    stopPlayer();
                    Toast.makeText(AudioRecorderActivity.this, "播放失败", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

            //设置播放
            mediaPlayer.prepare();
            mediaPlayer.start();

            //异常处理，防止闪退

        } catch (Exception e) {
            e.printStackTrace();
            stopPlayer();
        }


    }

    private void stopPlayer() {
        mIsPlaying = false;
        mediaPlayer.release();
        mediaPlayer = null;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当activity关闭时，停止这个线程，防止内存泄漏
        mExecutorService.shutdownNow();
    }

    public void scan(View view) {
        new Thread(scanFileThread).start();
    }

    public void filter(View view) {
        new Thread(FilterFileThread).start();
    }

private class MyHandler extends Handler {
    private int recorderCount;

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                mRecorderList.setAdapter(fileListAdapter);
                fileListAdapter.notifyDataSetChanged();
                recorderCount = 0;
                break;
            case 2:
                fileListAdapter.add(msg.obj.toString());

                LogUtils.d("Hint", "已扫描到" + ++recorderCount + "个录音!");
                break;
            case 3:
                fileListAdapter = new ArrayAdapter<String>(AudioRecorderActivity.this, android.R.layout.simple_list_item_1);
                break;
            case 4:
                recorderCount = 0;
                break;
        }
    }

}

    private final String[] sFilter = {".avi", ".mp3", ".m4a", ".m4v", ".mkv", ".3gp", ".aac", ".rmvb",
            ".wmv", ".wma", ".divx", ".mp4", ".flv", ".mpg", ".mpeg", ".divx", ".rm", ".m3u", ".pls", ".amr"};
    private ArrayAdapter<String> fileListAdapter = null;

    private ArrayList<File> listFiles = new ArrayList<File>();


    private Runnable scanFileThread = new Runnable() {
        public void run() {
            handler.sendEmptyMessage(3);
            ScanFileOfMusic(recorderParentFile);
            handler.sendEmptyMessage(1);
        }
    };


    private boolean ScanFileOfMusic(File file) {
        if (!file.exists()) {
            return false;
        }

        LogUtils.d(this, file.getAbsolutePath());

        subs = file.listFiles();

        if (subs == null) {
            return false;
        }

        for (int i = 0; i < subs.length; i++) {
            if (subs[i].isDirectory()) {
                ScanFileOfMusic(subs[i]);
            } else {
                for (int j = 0; j < sFilter.length; j++) {
                    if (subs[i].getName().toLowerCase().endsWith(sFilter[j])) {
                        listFiles.add(subs[i]);

                        Message msg = Message.obtain();
                        msg.obj = subs[i].getName();
                        msg.what = 2;
                        handler.sendMessage(msg);
                        break;
                    }
                }
            }
        }
        return true;
    }

    private Runnable FilterFileThread = new Runnable() {
        public void run() {
            FilterFile();
        }
    };


    private void FilterFile() {
        Cursor cursor = query(AudioRecorderActivity.this,
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media.DISPLAY_NAME},
                MediaStore.Audio.Media.IS_MUSIC + "=1", null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER, 8);
        // 暂时只将重复的音频文件去掉，后面要将视频文件也去掉
        for (int i = 0; i < listFiles.size(); i++) {
            cursor.moveToFirst();
            for (int j = 0; j < cursor.getCount(); j++) {
                if (cursor.getString(0).equals(listFiles.get(i).getName())) {
                    break;
                }
                if (!cursor.moveToNext()) {
                    fileListAdapter.add(listFiles.get(i).getName());
                }
            }
        }
        cursor.close();
        handler.sendEmptyMessage(4);

    }


    private Cursor query(Context context, Uri uri, String[] projection,
                         String selection, String[] selectionArgs, String sortOrder,
                         int limit) {
        try {
            ContentResolver resolver = context.getContentResolver();
            if (resolver == null) {
                return null;
            }
            if (limit > 0) {
                uri = uri.buildUpon().appendQueryParameter("limit", "" + limit)
                        .build();
            }
            return resolver.query(uri, projection, selection, selectionArgs,
                    sortOrder);
        } catch (UnsupportedOperationException ex) {
            return null;
        }
    }
}
