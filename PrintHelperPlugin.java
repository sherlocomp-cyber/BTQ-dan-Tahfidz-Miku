package __PACKAGE__;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.webkit.WebView;

import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

/** Membuat window.print() berfungsi di WebView Android (dialog cetak / Simpan sebagai PDF), dan mengunci orientasi layar tanpa ketukan. */
@CapacitorPlugin(name = "PrintHelper")
public class PrintHelperPlugin extends Plugin {

    /** mode: "portrait" | "landscape" | "auto". Dipanggil dari index.html (oriTerap) saat aplikasi dibuka. */
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
                WebView wv = getBridge().getWebView();
                PrintManager pm = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);
                final PrintDocumentAdapter base = wv.createPrintDocumentAdapter(name);
                PrintDocumentAdapter adapter = new PrintDocumentAdapter() {
                    @Override public void onStart() { base.onStart(); }
                    @Override public void onLayout(PrintAttributes o, PrintAttributes n, CancellationSignal c,
                                                   LayoutResultCallback cb, Bundle e) { base.onLayout(o, n, c, cb, e); }
                    @Override public void onWrite(PageRange[] p, ParcelFileDescriptor d, CancellationSignal c,
                                                  WriteResultCallback cb) { base.onWrite(p, d, c, cb); }
                    @Override public void onFinish() { base.onFinish(); call.resolve(); }
                };
                PrintAttributes attrs = new PrintAttributes.Builder()
                        .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                        .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                        .build();
                pm.print(name, adapter, attrs);
            } catch (Exception ex) {
                call.reject(String.valueOf(ex.getMessage()));
            }
        });
    }
}
