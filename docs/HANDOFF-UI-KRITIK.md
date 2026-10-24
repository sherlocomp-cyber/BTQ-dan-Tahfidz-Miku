# HANDOFF — Perbaikan layout hasil kritik UI (sesi 47, v1.71)

Pelengkap blok `proyek-handoff` di index.html. Tidak berisi base64 atau data siswa.
Aturan proyek tetap: lihat blok `proyek-instruksi` (bagian terlindungi tidak boleh berubah; CSS baru hanya di `<style id="v166">`; JS baru hanya setelah `cetak` atau sebelum `/* ===== EKSPOR LEGER`).

## Status
- File hasil: `aplikasi-btq-tahfidz-s47.html` (master + blok proyek, VERSI v1.71). index.html di repo masih v1.65 sampai file ini di-commit oleh pengguna.
- Validasi tiap tahap: `fingerprint.py` SAMA, `golden.py` = `a998dc93b6deea07 30206`, `node --check` lulus, patch idempoten (jalan 2x = hasil sama), ukuran/overflow diukur dengan Playwright (Chromium).
- Sapuan akhir: 8 halaman (dashboard, kelas, siswa, setoran, nilai, rapor, rekap, setelan) x lebar 360/390/768/1280 = overflow 0 di semua 32 kombinasi, tanpa galat JS.

| Tahap | Versi | Isi | Status |
|---|---|---|---|
| A | v1.66 | Tabel Siswa tidak meluber di desktop 1024–1439 px; kartu Kelompok terlipat bawaan | SELESAI |
| B | v1.66 | Header HP: ikon Pengaturan beda dari ikon tema, area sentuh 44 px, title status simpan | SELESAI |
| C | v1.67 | Setoran HP: tiga baris status digabung jadi satu baris (`.stS`); kolom Tanggal 680→606 px, tombol Simpan mulai di 666 px (360/390) | SELESAI |
| D | v1.66 | Chevron seragam di Pengaturan, tab Rekap satu baris, baris Input Nilai desktop merapat | SELESAI |
| E2 | v1.68 | Angkasa di HP: dekorasi planet diredam (opacity .55→.16) hanya di Setoran dan Nilai; dok bawah 64→60 px | SELESAI |
| E3 | v1.69 | Rapor HP: tombol "Lihat penuh" dan ketuk pratinjau membuka lapisan layar penuh dengan zoom (bawaan 80%) | SELESAI |
| E4 | v1.70 | Dashboard: Radar aspek dan Capaian target jadi satu kalimat saat belum ada data; tinggi halaman HP 3247→2881 px | SELESAI |
| E5 | v1.71 | Header HP: tombol tema menyebut nama tema saat berputar; label kecil Dasbor/Kelas/Atur di bawah ikon | SELESAI |
| E6 | – | Utang teknis: CSS tersebar di 10 blok `<style>` dengan override per tema | BELUM (opsional, butuh keputusan) |

## Keputusan dan temuan sesi 47
- Tahap C: kolom pilihan pengguna di prompt masih `[isi pilihan]`, jadi dipakai default dokumen ini (gabung baris status).
- E2: tema bawaan tetap Angkasa (tidak diganti ke Terang).
- E5: catatan lama menyebut pemilih tema ganda di header dan Pengaturan > Tampilan. Di file, Pengaturan > Tampilan TIDAK punya pemilih tema (hanya mode, orientasi, zoom), jadi tidak ada duplikat yang dihapus. Yang dikerjakan: umpan balik nama tema + label ikon header.
- Panduan singkat diperbarui (kalimat Lihat penuh di Rapor dan nama tema).

## Patch sesi 47 (urut, idempoten)
1. v1.67 (C): di `viewSetoran` tiga elemen status (`<p class="mut stI">` sudah setor, `<div class="row">` tombol urutan, `<p class="mut stI">` terakhir setor) diganti satu `<div class="stS">` berisi satu `<p class="mut stI">` dan `<button class="sm" id="stUrut">` berlabel pendek ("Urut: terlama" / "Urut: absen"; penjelasan penuh di `title`). CSS 'v1.66 Tahap C2': `.stS` flex, di HP `.fl` margin-top 8, `.stB .seg` dan `.stA .card` margin-bottom 8.
2. v1.68 (E2): CSS 'v1.66 Tahap E2' (hanya <=819 px): `html[data-theme=angkasa] body[data-pg=setoran|nilai]::before{opacity:.16}`, `.tabbar` bottom 6 px dan padding vertikal 6 px, `.stw #stSave` bottom 76 px.
3. v1.69 (E3): tombol `#prFull` (class `rfOnly`) di `viewRapor`; pengait di `bindRapor` (tombol dan klik `#pvw` bila lebar <820); fungsi `rpFull`/`rpFullTutup` di blok JS sebelum EKSPOR LEGER (memakai `rapJ` dan `fitText` apa adanya); CSS 'v1.66 Tahap E3' (`#rpFull`, `.rfb`, `.rfs`, `.rfw`, `body.rfo`, tersembunyi saat cetak).
4. v1.70 (E4): `viewDashboard`: kartu Capaian target dan Radar aspek menampilkan kalimat bila `pcTah/pcBTQ` semua kosong / `av` semua `null`; keterangan "T = Tahfidz · B = BTQ" ikut disembunyikan.
5. v1.71 (E5): `MutationObserver` pada atribut `data-theme` yang memunculkan `toast('Tema: …')` hanya dalam 600 ms setelah klik di `#themes`; CSS 'v1.66 Tahap E5' (`#navHdr .hb::before` label, <=480 px, posisi absolut).

## Sisa pekerjaan (urut prioritas)
1. Cek manual di perangkat nyata (APK/iQOO 15R): semua tahap diuji hanya di Chromium desktop, tinggi layar 800 px, data bawaan 23 siswa kelas 4A tanpa setoran/nilai. Khusus: gulir dan pinch-zoom di lapisan Lihat penuh, toast tema di atas dok, kolom Tanggal Setoran di layar yang lebih pendek (bisa kembali tertutup tombol Simpan).
2. E6 (opsional): rapikan CSS tanpa menyentuh bagian terlindungi.
3. Opsional, keputusan pengguna: ubah tema bawaan ke Terang (DEFAULT.tema='angkasa' sejak v1.32).
4. Bila pengguna puas: tawarkan 'rilis bersih'.

## Cara validasi tiap tahap (ulangi sebelum lanjut)
```
python3 alat/validasi.py FILE.html   # fingerprint SAMA, golden a998dc93b6deea07 30206, node --check OK
```
Lalu ukur dengan Playwright pada lebar 360/390/768/1280 (tambah 1024/1440 bila menyentuh layout desktop): tidak ada overflow horizontal, tidak ada galat JS. Gerbang kelas: klik `[data-pk="4A"]`; kelas 4A awalnya kosong, muat data uji lewat `impRekap(REKAP_BAWAAN,'uji')` (hanya di browser uji), lalu `go('<halaman>')`.

## Prompt untuk sesi berikutnya (akun mana pun)
Lanjutkan proyek BTQ–Tahfidz sesuai instruksi di dalam file. Unggah `aplikasi-btq-tahfidz-s47.html` (atau ambil index.html dari repo sherlocomp-cyber/BTQ-dan-Tahfidz-Miku bila sudah di-commit v1.71) dan baca `docs/HANDOFF-UI-KRITIK.md`. Jalankan protokol sesi (ekstrak blok proyek, fingerprint.py dan golden.py harus SAMA). Tindak lanjuti hasil cek manual di perangkat nyata: [tulis temuan], lalu E6 bila saya setuju: [ya/tidak]. Satu tahap per langkah, patch kecil idempoten, CSS hanya di `<style id="v166">`, validasi tiap tahap, naikkan VERSI, tulis riwayat sesi 48 di blok handoff dan perbarui dokumen ini, serahkan satu file `aplikasi-btq-tahfidz-s48.html`. Hemat kuota: jangan cetak base64.
