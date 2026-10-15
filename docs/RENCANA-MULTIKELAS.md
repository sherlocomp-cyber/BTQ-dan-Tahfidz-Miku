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
- [ ] **Tahap 1 (v1.34)** model kelas, migrasi 4A, `S.ks`, pemilih kelas di header.
- [ ] **Tahap 2 (v1.34)** halaman **Kelas** (12 kartu), impor daftar nama (tempel / Excel-CSV) ke kelas aktif, tambah siswa ke kelas aktif.
- [ ] **Tahap 3 (v1.34)** pengaturan guru & nama kelompok per kelas, peringatan bila guru tahfidz belum diisi, cek tanda tangan rapor.
- [ ] **Tahap 4 (usulan)** pindah/naik kelas siswa; salin pengaturan antarkelas; kenaikan tingkat akhir tahun.
- [ ] **Tahap 5 (usulan)** sinkron pengaturan per kelas sebagai kunci Firebase terpisah (kini satu blob `set` → dua guru mengubah pengaturan bersamaan bisa saling menimpa); kode ruang/PIN per madrasah.
- [ ] **Tahap 6 (usulan)** rekap lintas kelas untuk koordinator tahfidz / kepala madrasah; pemisahan data siswa dari `index.html` (repo publik memuat nama/NIS siswa 4A!).

## Cara melanjutkan
1. `git pull`, baca bagian ini, kerjakan tahap pertama yang belum dicentang.
2. Jalankan tes protokol (fingerprint, golden, node --check) + uji Chromium (Playwright tersedia di /opt/pw-browsers/chromium).
3. Perbarui centang di sini, bump versi, commit, push.
