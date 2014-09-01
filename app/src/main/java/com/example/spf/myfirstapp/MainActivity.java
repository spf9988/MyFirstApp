package com.example.spf.myfirstapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;

public class MainActivity extends Activity
{
    private static final boolean DBG = true;
    private static final String TAG = "MainActivity";

    public final static String EXTRA_MESSAGE = "com.example.spf.myfirstapp.MESSAGE";
    private LockView m_lockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(DBG) Log.d(TAG, "!!!onCreate()");
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        initViews();

    }

    public void initViews()
    {
        // TODO Auto-generated method stub
        m_lockView = (LockView) findViewById(R.id.LockView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //使home物理键失效
    @Override
    public void onAttachedToWindow() {
        // error : android4.0以上版本运行之后就会报错 java.lang.IllegalArgumentException:
        // Window type can not be changed after the window is added. android4.0以下就没有问题
        //this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        //super.onAttachedToWindow();
            }

    //使back键，音量加减键失效
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        return disableKeycode(keyCode, event);
    }

    private boolean disableKeycode(int keyCode, KeyEvent event)
    {
        int key = event.getKeyCode();
        switch (key)
        {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_VOLUME_DOWN:
            case KeyEvent.KEYCODE_VOLUME_UP:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if(DBG) Log.d(TAG, "onDestroy()");

    }

    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        if(DBG) Log.d(TAG, "onResume()");
    }

    @Override
    public void onDetachedFromWindow()
    {
        // TODO Auto-generated method stub
        super.onDetachedFromWindow();
        if(DBG) Log.d(TAG, "onDetachedFromWindow()");
        //解除监听
        //mStatusViewManager.unregisterComponent();
    }
}
