# Rencana pengembangan: aplikasi multi-kelas (1A–6B)

Dokumen serah-terima. Siapa pun (akun/sesi Claude lain) boleh melanjutkan dari status terakhir di bagian "Status tahap".
Baca juga: blok `proyek-handoff` di dalam `index.html` (aturan proyek, fingerprint, golden) — **wajib dipatuhi**.

## Tujuan (dari pengguna, Budi Syahbuana)
1. Dipakai guru kelas 1–6; tiap tingkat punya kelas A dan B (1A,1B … 6A,6B).
2. Sediakan tempat input / impor siswa per kelas.
3. Daftar siswa yang ada sekarang = kelas 4A. **Data jangan sampai hilang.**
4. Tiap kelas terbagi 2 kelompok dengan guru berbeda; tanda tangan rapor = guru yang memegang kelompok **tahfidz** siswa itu.
5. Lakukan perbaikan lain yang masuk akal.

## Aturan keras (jangan dilanggar)
- Bagian terlindungi tidak boleh berubah: jalankan `fingerprint.py` (semua OK) dan `golden.py` (`a998dc93b6deea07 30206`) yang ada di blok handoff; `node --check` pada skrip utama.
- Jangan menyisipkan kode antara `const RP={` dan `function cetak(`. CSS baru di blok `<style>` terakhir.
- Jangan menaikkan versi IndexedDB (`btq-tahfidz` v1). Field baru hanya ditambahkan pada objek; migrasi harus idempoten.
- Setiap perubahan: naikkan `VERSI`, perbarui "Panduan singkat", baris "Riwayat sesi" di handoff, dan README.
- Cetak/PDF tidak bisa diuji otomatis → tulis "perlu dicek manual oleh pengguna".
- Commit tiap tahap, push ke `main` (memicu Build APK + Deploy Pages).

## Rancangan data (v1.34)
- `S.siswa` = **semua** siswa semua kelas (dipakai penyimpanan, sinkron Firebase, cadangan). Tiap siswa punya `kelas` ('1A'…'6B').
- `S.ks` = siswa kelas aktif (turunan dari `S.siswa`, dibangun `ksBuat()` di awal `render()` dan di `urutAZ()`). Semua tampilan (Siswa, Setoran, Input Nilai, Rapor, Rekap, Dashboard, ekspor) memakai `S.ks`.
- `S.set.kelasAktif` = kelas yang sedang dibuka di perangkat ini (tidak disinkronkan).
- Pengaturan per kelas: `S.set.kls[kelas] = {kelas,guru,guruT,kode,klpNama,target,targetMode}` (`KLS_CK`). Nilai kelas aktif **disalin** ke kolom tingkat atas `S.set.*` (`kelasMuat`) supaya kode lama (rapor, guruRapor, target) tak perlu diubah; `kelasSimpan()` menyalin balik sebelum menyimpan/sinkron.
- Kolom per kelas tidak ikut blob `set` Firebase tingkat atas (hanya `kls`).
- Migrasi `kelasMigrasi()`: siswa tanpa `kelas` → kelas lama (`kode` bila valid, selain itu **4A**); cadangan otomatis ke kv `cadanganSebelumMultikelas` sebelum migrasi pertama.
- Nomor absen `no` dihitung per kelas (urut abjad).
- Tanda tangan rapor: `guruRapor(s)` (sudah ada) = `guruT[kelompok tahfidz siswa]`, kosong → `guru` kelas itu.

## Status tahap
- [x] **Tahap 1 (v1.34)** model kelas, migrasi 4A, `S.ks`, pemilih kelas di header.
- [x] **Tahap 2 (v1.35)** halaman **Kelas** (12 kartu: Buka siswa, Impor Excel, Tempel nama per kelas); impor/tambah siswa selalu masuk kelas aktif (`blank()` memberi `kelas`).
- [x] **Tahap 3 (v1.35)** guru tahfidz per kelompok + wali kelas per kelas langsung di kartu Kelas (`S.set.kls[k]`), peringatan bila guru kosong (kartu & halaman Rapor). Tanda tangan rapor = `guruRapor(s)`. **Cetak/PDF: perlu dicek manual oleh pengguna.**
- [x] **Tahap 4 (v1.36)** pindah kelas (pilih Kelas di form siswa), salin guru/kelompok antarkelas (kartu Kelas), kenaikan kelas akhir tahun (`kelasNaik`; cadangan kv `cadanganSebelumNaikKelas`; kelas 6 → `LULUS`, disembunyikan tapi data tetap). **Belum**: kolom Kelas di ekspor leger, impor Excel banyak kelas sekaligus, mengembalikan alumni.
- [x] **Tahap 5 (v1.36)** sinkron Firebase per kelas: kunci `kelas/<kode>`; blob `set` tidak lagi memuat `kls`; perangkat baru mengambil pengaturan kelas dari server (`terapKelas`); blob `set` lama dengan `kls` disemai (`klsTerap`). **Belum**: kode ruang/PIN, hanya mengunduh kelas tertentu.
- [x] **Koordinator tahfidz (v1.56)** halaman Koordinator: antrean lintas kelas dari setoran tahfidz bertanda `harus` yang belum `kor`, input nilai surat/tahap, mode koordinator per perangkat (`localStorage btqKoor`). **v1.57:** halaman depan (gerbang) memuat kartu Koordinator + 12 kelas, pilihan tersimpan per perangkat (`btqKelasDefault` / `btqKoor`), kotak cari + saring kelas. Rekap lintas kelas untuk kepala madrasah masih di Tahap 6.
- [x] **Tahap 6a (v1.72)** Rekap › Semua kelas: ringkasan per kelas (siswa, setor, ke koordinator, target), tombol Buka, cetak landscape, Excel. Cetak/Excel perlu dicek manual.
- [x] **v1.79** Impor data kelas lama (`klImpor`, file .json terpisah, tidak di repo) + tahun lulus alumni (`s.lulus`, `s.kelasLama`). Dipakai 27 Okt 2026 untuk memasukkan data 2025/2026: 1A→2A, 1B→2B, 2B→3B, 3A/3B→4A (yang sudah ada tetap)/4B, 4A→5A, 4B→5B, 5A→6A, 5B→6B, kelas 6 → alumni 2025/2026. Belum ada file kelas 2A (jadi 3A kosong).
- [ ] **Tahap 6b** pemisahan data siswa 4A (`REKAP_BAWAAN`) dari `index.html` (repo publik memuat nama siswa 4A!); menunggu keputusan pengguna.

## Usulan detail tahap berikutnya (untuk akun lain)
- **Tahap 4**: (a) tombol "Pindah kelas" di detail/form siswa (ubah `s.kelas`, jalankan `urutAZ()`); (b) "Salin pengaturan dari kelas lain" (`klsKlon` pada `KLS_CK`); (c) kenaikan tingkat akhir tahun (4A→5A dst., dengan konfirmasi + cadangan otomatis, arsipkan 6A/6B); (d) kolom Kelas di ekspor leger; (e) impor daftar siswa Excel/CSV banyak kelas sekaligus (kolom Kelas).
- **Tahap 5**: pisah kunci Firebase untuk `set.kls[kelas]` (kini satu blob `set`); filter/ruang per kelas agar guru kelas lain tidak mengunduh semua; PIN/kode ruang.
- **Tahap 6**: Rekap lintas kelas untuk koordinator/kepala; pindahkan data siswa 4A keluar dari `index.html` (repo publik!) ke file terpisah/privat; jadikan repo privat bila perlu (Pages butuh paket berbayar untuk privat).
- Menguji: lihat skrip uji Playwright pola di sesi 17–18 (set `S.siswa` dengan `blank()`, `kelasInit()`, `go('kelas')`, cek `S.siswa[].kelas`, `guruRapor`).

## Cara melanjutkan
1. `git pull`, baca bagian ini, kerjakan tahap pertama yang belum dicentang.
2. Jalankan tes protokol (fingerprint, golden, node --check) + uji Chromium (Playwright tersedia di /opt/pw-browsers/chromium).
3. Perbarui centang di sini, bump versi, commit, push.
