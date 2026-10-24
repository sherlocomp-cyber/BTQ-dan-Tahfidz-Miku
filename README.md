# BTQ & Tahfidz — Leger & Rapor

Aplikasi web satu file (PWA) untuk mencatat setoran harian, nilai, dan mencetak rapor **BTQ** dan **Tahfidz** di madrasah. Berjalan di browser HP maupun komputer, bisa dipakai tanpa internet, dan bisa dibungkus menjadi APK dengan Capacitor.

Versi saat ini: **v1.74**

## Fitur

- **Siswa & kelompok** — tambah satu per satu, tempel banyak nama, atau impor leger Excel. Satu kelas bisa dibagi 2 kelompok Tahfidz dan 2 kelompok BTQ.
- **Setoran harian** — Tahfidz dan BTQ dengan isian serba dropdown (juz → surat → ayat). Urutan siswa otomatis dari yang paling lama belum setor, ada alasan tidak setor, catatan, mode cepat, dan ringkasan.
- **Input nilai** — nilai surat, aspek, tahapan, dan pencapaian, per siswa atau per kolom.
- **Halaman depan** — saat dibuka di sebuah perangkat, pilih kelas atau Koordinator Tahfidz. Pilihan tersimpan di perangkat itu dan berikutnya langsung masuk; ketuk logo di kiri atas (atau Pengaturan › Halaman awal) untuk mengganti. Kartu menunjukkan jumlah siswa yang menunggu nilai koordinator, total dan per kelas.
- **Koordinator tahfidz** — siswa dari semua kelas yang oleh guru tahfidz dicentang "Harus setor ke koordinator tahfidz" (Setoran satu per satu maupun per kelompok) muncul otomatis untuk diberi nilai surat atau tahap. Ada kotak cari nama (sebagian nama, urutan bebas), saring per kelas dengan jumlahnya, dan kotak cari kosong lagi setelah menilai agar bisa langsung mencari siswa berikutnya. Nilai masuk ke Input Nilai dan rapor; siswa yang sudah dinilai satu koordinator hilang dari antrean koordinator lain.
- **Rapor** — Juz 30 mengikuti format Excel asli; Juz 29–1 per paket 3 juz. Cetak atau simpan PDF untuk satu siswa, siswa terpilih, atau satu kelas.
- **Rekap** — rekap bulanan, antarbulan, dan harian; ekspor Excel dan PDF.
- **Leger Excel** — impor dan ekspor leger.
- **Cadangan** — unduh dan pulihkan cadangan JSON; pengingat cadangan di Dashboard.
- **Penyimpanan** — data disimpan di perangkat (IndexedDB) dan bisa disinkronkan antar guru lewat Firebase Realtime Database. Mulai v1.31 aplikasi meminta browser melindungi data dari penghapusan otomatis (status ada di Pengaturan › Cadangan data).
- **Tema** terang, biru, gelap, Angkasa, dan Dunia (ilustrasi kertas-potong: bola dunia, pesawat, topi toga, awan, dan gedung).

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

- **v1.74** — Perbaikan orientasi: pilihan Potret/Lanskap kini diterapkan ulang saat aplikasi dibuka atau kembali ke depan (sebelumnya hanya saat tombol di Pengaturan diketuk); bila peramban menolak tanpa sentuhan, ketukan pertama menerapkannya.
- **v1.73** — Setoran Tahfidz: tombol − surat / + surat untuk pindah ke surat sebelumnya/berikutnya dalam juz terpilih.
- **v1.72** — Tahap 6a: Rekap › Semua kelas (ringkasan lintas kelas untuk koordinator/kepala madrasah; cetak landscape dan Excel).
- **v1.65** — Panduan singkat: teks "Pilih kelas & default" disesuaikan dengan halaman depan.
- **v1.64** — UI/UX Tahap 6 (QA akhir): 5 tema pada 360/412/768/1280 px tanpa gulir mendatar; paragraf Tampilan HP dan nomor versi di Panduan singkat kini ikut masuk lipatan. Seri perbaikan UI/UX v1.60–v1.64 selesai.
- **v1.63** — UI/UX Tahap 4: kartu di Pengaturan bisa dilipat (status tersimpan per perangkat); paragraf Panduan singkat yang bocor keluar lipatan dirapikan.
- **v1.62** — UI/UX Tahap 3: halaman depan menampilkan kelas berisi dulu (kelas kosong dilipat) dan kartu Pengingat cadangan di Dashboard lebih ringan.
- **v1.61** — UI/UX Tahap 2: di HP, filter kelompok Setoran satu baris yang bisa digeser dan tombol Simpan setoran selalu terlihat di atas bilah bawah.
- **v1.60** — UI/UX Tahap 1: chip tidak terpotong, tombol kecil jadi 44 px di HP, latar Angkasa lebih redup di HP, status simpan punya tooltip.
- **v1.59** — Perbaikan tampilan HP: tidak lagi tetap mengecil setelah memakai mode Desktop (viewport dihitung ulang).
- **v1.58** — Koordinator: sakelar Aktif/Nonaktif per perangkat (Pengaturan › Pilihan Koordinator tahfidz) agar guru kelas tidak masuk ke Koordinator; tombol Batalkan nilai di tab Sudah dinilai (nilai kembali seperti sebelumnya, siswa kembali ke antrean Menunggu).
- **v1.57** — Halaman depan (pilih kelas atau Koordinator, pilihan tersimpan per perangkat, logo = Beranda). Koordinator: kotak cari nama, saring per kelas dengan jumlah, fokus kembali ke kotak cari setelah menilai. Kartu Koordinator dan kartu kelas menampilkan jumlah yang menunggu. Kartu "Mode koordinator" di Pengaturan diganti "Halaman awal perangkat ini".
- **v1.56** — Halaman Koordinator Tahfidz: antrean otomatis lintas kelas dari centang "Harus setor ke koordinator tahfidz", input nilai surat/tahap dengan Urungkan, tab Sudah dinilai. Centang yang sama ditambahkan di Setoran Per kelompok. Mode koordinator di Pengaturan (per perangkat); menu muncul di bilah bawah.
- **v1.54** — Tema Dunia: gaya kertas-potong bertema wisata dan pendidikan, dengan palet teal, navy, koral, dan kuning. Kontras teks tetap memenuhi WCAG AA. Tombol tema ke-5 di header.
- **v1.37** — Pengaturan › Tampilan: mode Otomatis/HP/Desktop dan perbesar/perkecil tampilan (per perangkat).
- **v1.36** — Pindah kelas, salin guru/kelompok antarkelas, kenaikan kelas akhir tahun, sinkron pengaturan per kelas.
- **v1.35** — Multi-kelas 1A–6B: halaman Kelas (impor/tempel siswa per kelas, guru tahfidz per kelompok per kelas, peringatan tanda tangan). Data lama otomatis masuk Kelas 4A. Rencana tahap berikutnya: `docs/RENCANA-MULTIKELAS.md`.
- **v1.33** — Input Nilai: penanda "belum dinilai" (surat selesai di Setoran tanpa nilai) dengan filter dan penghitung, Mode cepat satu item per layar, tombol Urungkan setelah Isi massal/Salin, ringkasan kelas di mode Per kolom, header lengket, dan pembaruan satu baris tanpa render ulang.
- **v1.31** — perlindungan penyimpanan (`navigator.storage.persist`), status dan tombol di Pengaturan.
- **v1.30** — setoran minimal 3 ayat (+3), alasan tidak setor, catatan siswa, mode cepat, ringkasan, rekap harian.
- **v1.29** — Setoran per kelompok diurutkan otomatis dari yang terlama belum setor.
- **v1.24** — sinkronisasi antar guru lewat Firebase.
- **v1.27** — jembatan APK (Capacitor).
