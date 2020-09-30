package com.quibbler.news.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.quibbler.news.view.callback.Receiver;

import java.lang.ref.WeakReference;

public class NetWorkChangeReceiver extends BroadcastReceiver {
    private static final String TAG = "TAG_NetWorkChangeReceiver";

    public static final String CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";

    private WeakReference<Receiver> mReceiverCallback = null;

    public NetWorkChangeReceiver(Receiver mReceiver) {
        this.mReceiverCallback = new WeakReference<Receiver>(mReceiver);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "onReceive action = " + action);
        if (CONNECTIVITY_CHANGE.equals(action) && null != mReceiverCallback.get()) {
            mReceiverCallback.get().onReceive();
        }
    }
}
