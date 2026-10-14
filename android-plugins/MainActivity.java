package com.sherlocomp.btqtahfidz;

import android.os.Bundle;

import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Plugin lokal harus didaftarkan SEBELUM super.onCreate
        registerPlugin(PrintHelperPlugin.class);
        super.onCreate(savedInstanceState);
    }
}
