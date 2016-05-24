package com.hkjc.jessepractice.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class CounterReceiver extends BroadcastReceiver {
    public CounterReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Bundle bundle = intent.getExtras();
        throw new UnsupportedOperationException("Not yet implemented");
    }
}


