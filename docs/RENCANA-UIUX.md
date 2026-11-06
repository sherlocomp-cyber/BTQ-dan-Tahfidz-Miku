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

## Temuan tambahan (belum dijadwalkan)
- Rekap overflow mendatar 14 px di 360 px dan 7 px di 390 px (ada sejak v1.83, bukan akibat tahap ini).
