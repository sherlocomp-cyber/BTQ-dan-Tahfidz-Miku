package com.sherlocomp.btqtahfidz;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.webkit.WebView;

import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

/**
 * Plugin "PrintHelper": membuka dialog cetak Android untuk isi WebView.
 * Di dialog itu pilih tujuan "Simpan sebagai PDF" untuk mengunduh PDF.
 * Dipanggil dari index.html: PrintHelper.print({ name: 'Nama-Dokumen' })
 */
@CapacitorPlugin(name = "PrintHelper")
public class PrintHelperPlugin extends Plugin {

    /** mode: "portrait" | "landscape" | "auto". Mengunci orientasi layar tanpa perlu ketukan. */
    @PluginMethod
    public void setOrientation(final PluginCall call) {
        final String mode = call.getString("mode", "auto");
        getActivity().runOnUiThread(() -> {
            try {
                int o = "portrait".equals(mode) ? ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
                      : "landscape".equals(mode) ? ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                      : ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
                getActivity().setRequestedOrientation(o);
                call.resolve();
            } catch (Exception ex) {
                call.reject(String.valueOf(ex.getMessage()));
            }
        });
    }

    @PluginMethod
    public void print(final PluginCall call) {
        final String name = call.getString("name", "Dokumen");
        getActivity().runOnUiThread(() -> {
            try {
                WebView webView = getBridge().getWebView();
                PrintManager pm = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);
                PrintDocumentAdapter adapter = webView.createPrintDocumentAdapter(name);
                PrintAttributes attrs = new PrintAttributes.Builder()
                        .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                        .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                        .build();
                pm.print(name, adapter, attrs);
                call.resolve();
            } catch (Exception e) {
                call.reject("Gagal membuka dialog cetak: " + e.getMessage());
            }
        });
    }
}
