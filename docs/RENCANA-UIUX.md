# Rencana perbaikan UI/UX (dasar: v1.83)

Dikerjakan bertahap. Setiap tahap: ubah, uji di HP (360 px) dan desktop, naikkan `VERSI`, centang di sini.
Bagian rapor/cetak (`.page`, `.s1`–`.s64`, `#printArea`) JANGAN disentuh.

## Tahap 0 – Privasi (kerjakan lebih dulu)
- [ ] Keluarkan data siswa dari kode: konstanta `NIS_RESMI` (nama, NIS, NISN) dan `REKAP_BAWAAN` (tombol "Muat data Rekap kelas 4A") di `index.html`.
- [ ] Ganti dengan alur impor .json yang sudah ada (data tetap di perangkat/berkas terpisah, bukan di repo).
- [ ] Riwayat git masih menyimpan data lama: bersihkan riwayat (mis. git filter-repo) atau pindahkan ke repo baru bila repo publik.

## Tahap 1 – Navigasi mengikuti frekuensi pakai
Masalah: tab bawah berurutan Siswa, Setoran, Nilai, Rapor, Rekap; Kelas (tugas tahunan) punya ikon tetap di header dengan ikon rumah.
- [ ] `buildNav()` menyusun tombol dari urutan array `PAGES` (difilter `NAV_INTI`), jadi urutan diubah di `PAGES`: pindahkan `['setoran','Setoran']` sebelum `['siswa','Siswa']`. Urutan baru: Setoran, Siswa, Nilai, Rapor, Rekap.
- [ ] Pindahkan `kelas` dari header ke Pengaturan: tambah kartu "Kelas & kenaikan" di `VIEWS.setelan` yang memanggil `go('kelas')`, lalu keluarkan `kelas` dari ikon header (`#navHdr`) dan label `::before` ("Kelas") di CSS v1.66 Tahap E5. Halaman `kelas` tetap ada.
- [ ] Bila `kelas` tetap di header: ganti ikon `IC.kelas` (rumah) dengan ikon kelompok/papan tulis.
- [ ] Periksa: hash `#kelas`, tombol Kembali, dan sidebar desktop (`#navSide`) tetap berfungsi.

## Tahap 2 – Layar Setoran lebih ringan
- [ ] Tampilkan di muka: kelompok, siswa, juz/surat/ayat, nilai, Simpan.
- [ ] Lipat di "Lainnya": Keterangan (dropdown + chip + ketik), "Tidak setor…", Mundurkan, mode Per tahap.
- [ ] Gabungkan "Harus setor lagi (belum lancar)" dan "Harus setor ke koordinator" jadi satu pilihan eksklusif: Lancar / Ulang / Ke koordinator. Petakan ke field yang sudah ada agar rekap dan rapor tidak berubah. Uji data lama.

## Tahap 3 – Bantuan
- [ ] Pecah "Panduan singkat" (±30 paragraf) menjadi: bantuan kontekstual per halaman (ikon ?, 2–3 kalimat), daftar langkah awal, dan "Yang baru" terpisah dari panduan.

## Tahap 4 – Tindakan merusak & status simpan
- [ ] "Hapus semua data" → "Reset data di perangkat ini"; jauhkan dari Unduh/Pulihkan; pakai dialog ketik-konfirmasi seperti "Hapus semua siswa"; perjelas pesan soal data server.
- [ ] Indikator simpan: dua status, "Tersimpan di perangkat" dan "Tersinkron HH.MM / belum terkirim".
- [ ] `toast()`: tambahkan `role="status"` (dan `aria-live="polite"`).

## Tahap 5 – Tema, istilah, ukuran teks
- [ ] Ubah `DEFAULT.tema` dari `'angkasa'` ke `'terang'`.
- [ ] HP ≤480 px: satu tombol tema yang membuka daftar pilihan (bukan memutar).
- [ ] Seragamkan istilah: Dashboard/Dasbor, Input Nilai/Nilai, Pengaturan/Atur.
- [ ] Ukuran minimum: label 12 px, teks bantu 14 px (tab bawah, label ikon header, `.mut`, chip).
- [ ] Pindahkan penjelasan panjang di `FIELDS` dari label ke teks bantu.

## Tahap 6 – Rapikan CSS
- [ ] Gabungkan blok `<style id="v128|v132|v140|v155|v156|v160|v166|v177">` menjadi satu sistem token + komponen; buang `!important` (v177).
- [ ] Selesaikan bentrok nama kelas: `.kc` (chip vs kartu kelas, kini ditambal `.card.kc`) dan `.kg` (didefinisikan dua kali). Ganti nama salah satunya.
