// Dijalankan setelah `npx cap add android`: pasang plugin cetak, tanda tangan tetap, ikon, versi.
const fs = require('fs'), path = require('path');
const cfg = JSON.parse(fs.readFileSync('capacitor.config.json', 'utf8'));
const pkg = cfg.appId, pkgPath = pkg.split('.').join('/');
const app = 'android/app';
const javaDir = path.join(app, 'src/main/java', pkgPath);
fs.mkdirSync(javaDir, { recursive: true });

// 1. Plugin cetak + MainActivity
fs.writeFileSync(path.join(javaDir, 'PrintHelperPlugin.java'),
  fs.readFileSync('PrintHelperPlugin.java', 'utf8').replace('__PACKAGE__', pkg));
fs.writeFileSync(path.join(javaDir, 'MainActivity.java'),
`package ${pkg};

import android.os.Bundle;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        registerPlugin(PrintHelperPlugin.class);
        super.onCreate(savedInstanceState);
    }
}
`);

// 2. Tanda tangan tetap (agar APK baru bisa menimpa versi lama tanpa hapus data) + versi
const gp = path.join(app, 'build.gradle');
let g = fs.readFileSync(gp, 'utf8');
if (!g.includes('btq-debug.keystore')) {
  g = g.replace(/android\s*\{/, `android {
    signingConfigs {
        debug {
            storeFile file("../../btq-debug.keystore")
            storePassword "btqtahfidz"
            keyAlias "btq"
            keyPassword "btqtahfidz"
        }
    }`);
}
const run = process.env.GITHUB_RUN_NUMBER || '1';
g = g.replace(/versionCode\s+\d+/, `versionCode ${run}`).replace(/versionName\s+"[^"]*"/, `versionName "1.${run}"`);
fs.writeFileSync(gp, g);

// 3. Ikon
const res = path.join(app, 'src/main/res');
for (const d of fs.readdirSync(res).filter(d => d.startsWith('mipmap-') && !d.includes('anydpi'))) {
  fs.copyFileSync('icon.png', path.join(res, d, 'ic_launcher.png'));
  fs.copyFileSync('icon.png', path.join(res, d, 'ic_launcher_round.png'));
  fs.rmSync(path.join(res, d, 'ic_launcher_foreground.png'), { force: true });
}
fs.mkdirSync(path.join(res, 'drawable-nodpi'), { recursive: true });
fs.copyFileSync('icon-maskable.png', path.join(res, 'drawable-nodpi/ic_launcher_fg.png'));
const adaptive = `<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@color/ic_launcher_background"/>
    <foreground android:drawable="@drawable/ic_launcher_fg"/>
</adaptive-icon>
`;
const any = path.join(res, 'mipmap-anydpi-v26');
fs.mkdirSync(any, { recursive: true });
fs.writeFileSync(path.join(any, 'ic_launcher.xml'), adaptive);
fs.writeFileSync(path.join(any, 'ic_launcher_round.xml'), adaptive);
fs.rmSync(path.join(res, 'drawable-v24/ic_launcher_foreground.xml'), { force: true });
fs.rmSync(path.join(res, 'drawable/ic_launcher_background.xml'), { force: true });
console.log('Android siap: plugin cetak, tanda tangan, ikon, versi', run);
