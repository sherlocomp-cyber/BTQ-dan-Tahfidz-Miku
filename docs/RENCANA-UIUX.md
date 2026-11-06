# Rencana perbaikan UI/UX (dasar: v1.83)

Dikerjakan bertahap. Setiap tahap: ubah, uji di HP (360 px) dan desktop, naikkan `VERSI`, centang di sini.
Bagian rapor/cetak (`.page`, `.s1`–`.s64`, `#printArea`) JANGAN disentuh.

## Tahap 0 – Privasi (kerjakan lebih dulu)
- [ ] Keluarkan data siswa dari kode: konstanta `NIS_RESMI` (nama, NIS, NISN) dan `REKAP_BAWAAN` (tombol "Muat data Rekap kelas 4A") di `index.html`.
- [ ] Ganti dengan alur impor .json yang sudah ada (data tetap di perangkat/berkas terpisah, bukan di repo).
- [ ] Riwayat git masih menyimpan data lama: bersihkan riwayat (mis. git filter-repo) atau pindahkan ke repo baru bila repo publik.

## Tahap 1 – Navigasi mengikuti frekuensi pakai
Masalah: tab bawah berurutan Siswa, Setoran, Nilai, Rapor, Rekap; Kelas (tugas tahunan) punya ikon tetap di header dengan ikon rumah.
- [x] (v1.84) Urutan `PAGES` diubah: Setoran sebelum Siswa. Urutan tab bawah dan sidebar: Setoran, Siswa, Nilai, Rapor, Rekap.
- [x] (v1.84) Kartu "Kelas & kenaikan kelas" di puncak Pengaturan (judulnya bukan `h3` agar tidak ikut dilipat pelipat v1.63) membuka halaman Kelas lewat atribut umum `data-golink`. Kelas keluar dari ikon header HP; di sidebar desktop tetap ada.
- [x] Tidak perlu: Kelas tidak lagi di header, jadi ikon rumah tidak dipakai di sana.
- [x] Diuji di Chrome (360/390/768/1280): hash `#kelas`, tombol Kembali ke Pengaturan, sidebar desktop berfungsi; tanpa galat JS.

## Tahap 2 – Layar Setoran lebih ringan
- [x] (v1.85) Di muka: kelompok, siswa, juz/surat/ayat, nilai, pilihan "Setelah setoran ini", Simpan. Tinggi halaman Setoran HP turun 2051 → 1565 px.
- [x] (v1.85) Dilipat di "Lainnya": Keterangan (dropdown + chip + ketik), "Tidak setor…", Mundurkan, mode Per tahap. Terbuka otomatis bila sudah ada isinya; status buka diingat (`ST.more`).
- [x] (v1.85) BTQ: Lancar / Ulang lagi / Koordinator; Tahfidz: Lanjut dengan guru / Ke koordinator (Tahfidz tidak punya "ulang" sebagai tanda; Mengulang tetap di dropdown Status). Dipetakan ke `harus`/`koor` yang sama sehingga rekap dan rapor tidak berubah. Setoran Per kelompok tidak diubah (masih centang).

## Tahap 3 – Bantuan
- [x] (v1.86) "Panduan singkat" dipecah jadi kartu Bantuan: 6 langkah awal, tombol ? per halaman (9 halaman, status buka diingat), dan 3 lipatan (Panduan dasar, Rekap dan kelompok, Yang baru). Isi 29 paragraf lama dipertahankan; kalimat yang menyebut centang/posisi lama diperbarui.

## Tahap 4 – Tindakan merusak & status simpan
- [x] (v1.87) Jadi kartu sendiri "Reset data di perangkat ini" (terlipat, merah) di bawah Data bawaan, terpisah dari Unduh/Pulihkan; wajib mengetik RESET; pesan menegaskan data server tidak dihapus. Diuji: salah ketik → 23 siswa utuh, ketik RESET → terhapus.
- [x] (v1.87) Lencana header: "Tersinkron HH.MM" / "Belum terkirim" (title menjelaskan keduanya); Pengaturan › Cadangan memuat dua baris "Di perangkat ini … Di server …". Diturunkan dari teks Cloud.tampil lewat MutationObserver, logika sinkron tidak disentuh. Catatan: di ≤380 px teks lencana disembunyikan (aturan lama), jadi detail ada di Pengaturan.
- [x] Sudah terpenuhi sebelumnya: `toast()` yang aktif (v1.7x, menimpa definisi awal) memakai wadah `#toastBox` ber-`role="status"` dan `aria-live="polite"`. Tidak diubah.

## Tahap 5 – Tema, istilah, ukuran teks
- [x] (v1.88) `DEFAULT.tema` = `'terang'`. Hanya berlaku untuk perangkat baru atau setelah Reset; perangkat yang sudah menyimpan tema tetap memakai pilihannya.
- [x] (v1.88) HP ≤480 px: tombol tema aktif membuka menu bernama (Terang, Biru gelap, Gelap, Angkasa, Dunia), item 48 px, Esc/ketuk luar menutup, panah atas-bawah berpindah, aria-haspopup/expanded/menuitemradio. Pemutar lama dihapus. Desktop tidak berubah.
- [x] Sebagian (v1.88): "Dashboard" → "Dasbor" di judul, navigasi, dan panduan. Sengaja TIDAK diubah: "Input Nilai" (istilah Anda; tab bawah memakai "Nilai" karena sempit) dan label header HP "Atur" (sempit). Bila ingin satu istilah penuh di mana-mana, itu keputusan terpisah.
- [x] (v1.88) Tab bawah, label ikon header, `.rkp-t small`, `.kr .kg small`, `.nbt`, `.kcb`, `.abt button small` → 12 px; `.mut`, `.chip` → 14 px. Hanya `@media screen`, jadi cetak tidak berubah. Tidak ada overflow baru di 320/360/390/768/1280.
- [x] (v1.88) `FIELDS` menerima elemen ketiga (teks bantu `.hint`, 14 px): Nama guru tahfidz dan Kode kelas.

## Tahap 6 – Rapikan CSS
- [ ] Gabungkan blok `<style id="v128|v132|v140|v155|v156|v160|v166|v177">` menjadi satu sistem token + komponen; buang `!important` (v177).
- [ ] Selesaikan bentrok nama kelas: `.kc` (chip vs kartu kelas, kini ditambal `.card.kc`) dan `.kg` (didefinisikan dua kali). Ganti nama salah satunya.

## Temuan tambahan (belum dijadwalkan)
- Rekap overflow mendatar 14 px di 360 px, 7 px di 390 px, 24 px di 320 px (ada sejak v1.83 dan tidak berubah oleh Tahap 1–5; diukur terhadap berkas asli).
