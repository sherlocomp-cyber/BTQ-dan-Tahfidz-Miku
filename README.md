# BTQ & Tahfidz — Leger & Rapor

Aplikasi web satu file (PWA) untuk mencatat setoran harian, nilai, dan mencetak rapor **BTQ** dan **Tahfidz** di madrasah. Berjalan di browser HP maupun komputer, bisa dipakai tanpa internet, dan bisa dibungkus menjadi APK dengan Capacitor.

Versi saat ini: **v1.36**

## Fitur

- **Siswa & kelompok** — tambah satu per satu, tempel banyak nama, atau impor leger Excel. Satu kelas bisa dibagi 2 kelompok Tahfidz dan 2 kelompok BTQ.
- **Setoran harian** — Tahfidz dan BTQ dengan isian serba dropdown (juz → surat → ayat). Urutan siswa otomatis dari yang paling lama belum setor, ada alasan tidak setor, catatan, mode cepat, dan ringkasan.
- **Input nilai** — nilai surat, aspek, tahapan, dan pencapaian, per siswa atau per kolom.
- **Rapor** — Juz 30 mengikuti format Excel asli; Juz 29–1 per paket 3 juz. Cetak atau simpan PDF untuk satu siswa, siswa terpilih, atau satu kelas.
- **Rekap** — rekap bulanan, antarbulan, dan harian; ekspor Excel dan PDF.
- **Leger Excel** — impor dan ekspor leger.
- **Cadangan** — unduh dan pulihkan cadangan JSON; pengingat cadangan di Dashboard.
- **Penyimpanan** — data disimpan di perangkat (IndexedDB) dan bisa disinkronkan antar guru lewat Firebase Realtime Database. Mulai v1.31 aplikasi meminta browser melindungi data dari penghapusan otomatis (status ada di Pengaturan › Cadangan data).
- **Tema** terang, biru, dan gelap.

## Cara memakai

1. Buka `index.html` lewat alamat **HTTPS**, misalnya GitHub Pages repo ini: https://sherlocomp-cyber.github.io/BTQ-dan-Tahfidz-Miku/ (aktifkan di Settings › Pages › Deploy from a branch › `main` / root). HTTPS dibutuhkan agar bisa dipasang ke layar utama dan agar perlindungan penyimpanan tersedia.
2. Di HP, pasang ke layar utama lewat menu browser.
3. Isi identitas madrasah di **Pengaturan**, lalu tambahkan siswa di halaman **Siswa**.
4. Unduh **cadangan JSON** secara berkala. Cadangan tidak bisa digantikan oleh perlindungan penyimpanan: data tetap hilang bila data situs atau browser dihapus.

Fitur impor dan ekspor Excel memuat SheetJS dari internet satu kali (di APK sudah ikut di dalam aplikasi).

## Struktur repo

| Berkas | Fungsi |
| --- | --- |
| `index.html` | Seluruh aplikasi (HTML, CSS, JS dalam satu berkas) |
| `package.json` | Dependensi Capacitor dan script build |
| `capacitor.config.json` | Konfigurasi pembungkus APK (`webDir`: `www`) |
| `.gitignore` | Berkas yang tidak ikut di-commit |
| `.nojekyll` | Menyuruh GitHub Pages menyajikan berkas apa adanya (tanpa Jekyll) |
| `.github/workflows/build-apk.yml` | Workflow GitHub Actions pembuat APK |
| `prepare-www.js`, `prepare-android.js` | Skrip penyiapan `www/` dan proyek Android saat build APK |
| `PrintHelperPlugin.java` | Templat plugin cetak; dipakai `prepare-android.js` saat build APK |
| `android-plugins/` | Versi manual plugin dan panduan pasang (`PASANG.md`) |

Folder `www/`, `android/`, dan `node_modules/` dibuat saat build dan tidak disimpan di repo.

## Membuat APK (Capacitor)

```bash
npm install
npm run build:web      # menyalin index.html dan xlsx.full.min.js ke www/
npx cap add android    # sekali saja
npm run cap:sync       # build:web + cap sync android
```

Setelah itu buka folder `android/` di Android Studio, atau jalankan `./gradlew assembleDebug` di dalamnya.

Catatan: kode aplikasi memanggil plugin native `PrintHelper` (dialog cetak Android) selain plugin Filesystem dan Share. Karena `android/` tidak disimpan di repo, pastikan plugin `PrintHelper` ikut dibuat di proyek Android saat build.

## Keamanan dan data siswa

- Jangan menaruh data siswa asli (nama, NIS, NISN, file Excel, file cadangan) di repo, terutama bila repo publik. `.gitignore` sudah mengabaikan `*.xlsx`, `*.xls`, dan `cadangan-btq-tahfidz-*.json`.
- Atur **Rules** Firebase Realtime Database agar akses dibatasi sesuai kebutuhan sekolah.

## Catatan pengembang

- JS murni tanpa framework, satu skrip utama diawali `'use strict'` dan `const VERSI`.
- Penyimpanan lokal: IndexedDB `btq-tahfidz` versi 1 (store `kv` dan `siswa`). Jangan menaikkan versi DB; field baru cukup ditambahkan pada objek siswa atau pengaturan.
- Bagian cetak rapor Juz 30 **dilindungi** dan diperiksa dengan sidik jari. Jangan mengubah isi di antara `const RP={` dan `function cetak(`.
- Instruksi kerja dan riwayat sesi pengembangan tersimpan di dalam `index.html` pada dua blok `<script type="text/plain">` dengan id `proyek-instruksi` dan `proyek-handoff`.
- Naikkan `VERSI` pada setiap perubahan dan perbarui panduan singkat di Pengaturan bila ada fitur baru.

## Riwayat singkat

- **v1.36** — Pindah kelas, salin guru/kelompok antarkelas, kenaikan kelas akhir tahun, sinkron pengaturan per kelas.
- **v1.35** — Multi-kelas 1A–6B: halaman Kelas (impor/tempel siswa per kelas, guru tahfidz per kelompok per kelas, peringatan tanda tangan). Data lama otomatis masuk Kelas 4A. Rencana tahap berikutnya: `docs/RENCANA-MULTIKELAS.md`.
- **v1.33** — Input Nilai: penanda "belum dinilai" (surat selesai di Setoran tanpa nilai) dengan filter dan penghitung, Mode cepat satu item per layar, tombol Urungkan setelah Isi massal/Salin, ringkasan kelas di mode Per kolom, header lengket, dan pembaruan satu baris tanpa render ulang.
- **v1.31** — perlindungan penyimpanan (`navigator.storage.persist`), status dan tombol di Pengaturan.
- **v1.30** — setoran minimal 3 ayat (+3), alasan tidak setor, catatan siswa, mode cepat, ringkasan, rekap harian.
- **v1.29** — Setoran per kelompok diurutkan otomatis dari yang terlama belum setor.
- **v1.24** — sinkronisasi antar guru lewat Firebase.
- **v1.27** — jembatan APK (Capacitor).
