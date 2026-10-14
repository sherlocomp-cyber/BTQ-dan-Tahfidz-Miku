# Memasang plugin PrintHelper (cetak / Simpan sebagai PDF di APK)

Tombol **Cetak** dan **Unduh PDF** di APK memanggil plugin native `PrintHelper`.
Folder `android/` dibuat ulang setiap kali `npx cap add android` dijalankan,
jadi plugin ini harus dipasang lagi setiap kali. Gejala bila belum terpasang:
tombol PDF/Cetak tidak berbuat apa-apa.

## Langkah

1. Buat proyek Android seperti biasa:

   ```bash
   npm install
   npm run build:web
   npx cap add android      # sekali saja (atau setelah folder android/ dihapus)
   ```

2. Salin dua berkas dari folder ini ke proyek Android:

   ```
   android-plugins/PrintHelperPlugin.java  ->  android/app/src/main/java/com/sherlocomp/btqtahfidz/PrintHelperPlugin.java
   android-plugins/MainActivity.java       ->  android/app/src/main/java/com/sherlocomp/btqtahfidz/MainActivity.java   (timpa berkas yang ada)
   ```

   Bila `MainActivity.java` hasil `cap add` sudah berbeda (mis. ada kode lain),
   cukup tambahkan satu baris `registerPlugin(PrintHelperPlugin.class);`
   tepat sebelum `super.onCreate(savedInstanceState);`.

3. Sinkronkan dan bangun ulang:

   ```bash
   npm run cap:sync
   cd android && ./gradlew assembleDebug
   ```

4. Pasang APK, buka Rapor, tekan **Unduh PDF**. Di dialog cetak Android pilih
   tujuan **Simpan sebagai PDF**, lalu **Simpan**.

## Catatan

- Belum diuji di perangkat. Bila dialog cetak terbuka tetapi PDF kosong,
  kabari agar gaya cetak di index.html disesuaikan.
- Rekap memakai kertas landscape: pilih orientasi **Lanskap** di dialog cetak Android.
- Package `com.sherlocomp.btqtahfidz` mengikuti `appId` di `capacitor.config.json`.
