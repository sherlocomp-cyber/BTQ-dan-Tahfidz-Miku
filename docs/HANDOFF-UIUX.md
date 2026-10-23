# HANDOFF: perbaikan UI/UX bertahap

Berkas ini hanya ada selama pekerjaan UI/UX berlangsung. **Setelah Tahap 6 selesai dan tervalidasi, hapus berkas ini** (dan baris rujukannya di README bila ada).
Aturan proyek tetap berlaku: lihat `docs/RENCANA-MULTIKELAS.md` dan blok `proyek-handoff` di `index.html`.

## Aturan untuk akun berikutnya (WAJIB, urut)
1. `git pull`, baca berkas ini dari atas.
2. **Validasi tahap sebelumnya dulu**, jangan langsung lanjut: jalankan daftar "Cek validasi" milik setiap tahap berstatus SELESAI. Bila ada yang gagal, perbaiki dan catat di "Catatan perubahan" tahap itu sebelum membuka tahap berikutnya.
3. Kerjakan tahap pertama berstatus BELUM. Satu tahap = satu commit (atau beberapa) lalu push ke `main`.
4. Selesai satu tahap: ubah statusnya jadi SELESAI, isi "Catatan perubahan" (apa yang diubah, fungsi/kelas CSS yang disentuh, apa yang sengaja tidak diubah), isi "Perlu dicek manual", lalu commit.
5. Setiap tahap: naikkan `VERSI`, tambah baris "Riwayat sesi" di blok proyek-handoff, tambah baris README.
6. Uji minimum tiap tahap: `fingerprint.py` SAMA, `golden.py` = `a998dc93b6deea07 30206` (skrip ada di blok proyek-handoff), `node --check` skrip utama, tangkapan layar 360 px dan 1280 px pada halaman yang disentuh.
7. Jangan menyentuh bagian terlindungi (rapor/cetak). CSS baru hanya di blok style `v156`/blok style terakhir; JS baru sebelum penanda `/* ===== EKSPOR LEGER`.
8. Setelah Tahap 6: hapus berkas ini dan catat di Riwayat sesi.

## Hasil audit (tangkapan layar 360x740 dan 1280x800, data uji 8 siswa)
- Label chip terpotong di desktop ("Kelompo k 1 (0)").
- Ikon header (kotak, rumah, matahari, planet) tanpa label; titik oranye status kirim tanpa penjelasan di HP.
- Setoran di HP: 6 kontrol bertumpuk sebelum kartu siswa; kartu siswa baru muncul setelah layar pertama.
- Gambar planet latar tampil di belakang tombol dan chip sehingga mengganggu keterbacaan.
- Halaman depan: kelas kosong (1A, 1B, ...) memakan separuh layar, kelas yang berisi tenggelam.
- Dashboard: kartu "Pengingat cadangan" oranye menutupi isi utama.
- Pengaturan: satu halaman panjang tanpa pengelompokan.
- Input Nilai/Koordinator: tombol nilai sudah besar; nama siswa tidak menempel saat gulir.

## Daftar tahap
| # | Tahap | Status |
|---|-------|--------|
| 1 | Fondasi: chip tidak terpotong, target sentuh 44 px, fokus papan ketik, label ikon header, latar tidak mengganggu | SELESAI v1.60 |
| 2 | Setoran di HP: kartu siswa lebih cepat terlihat | SELESAI v1.61 |
| 3 | Halaman depan dan Dashboard: kelas kosong dilipat, pengingat lebih ringan | SELESAI v1.62 |
| 4 | Pengaturan: kelompok yang bisa dilipat | BELUM |
| 5 | Input Nilai dan Koordinator: nama siswa menempel, kemajuan terlihat | BELUM |
| 6 | QA akhir: semua tema, 360/412/768/1280 px, fokus papan ketik, gerak dikurangi; hapus berkas ini | BELUM |

Detail tiap tahap ada di bagian bawah. Isi "Catatan perubahan" saat tahap selesai.

---

## Tahap 1: Fondasi
Status: SELESAI (v1.60)
Cek validasi (untuk akun berikutnya): chip "Kelompok 1 (0)" utuh di 1280 px dan 360 px (Setoran); `.sm`, `.chip`, `.seg button` tinggi minimal 44 px di layar sentuh 360 px; Tab menampilkan cincin fokus jelas; ikon header punya nama aksesibel (aria-label/title); `document.getElementById('saveInd').title` berisi teks status.
Catatan perubahan:
- CSS baru di `<style id="v160">` (setelah `v156`): `.klpsg button` lebar mengikuti isi + `nowrap` (turun ke baris kedua bila tak muat); `.sm/.chip/.hd .btn/.mbtn` min-height 44 px di <=820 px; `.seg button` min-height 44 px di `pointer:coarse`; latar tema Angkasa opacity .55 di <820 px.
- JS: MutationObserver kecil setelah `/* ===== /v1.56 */` menyalin teks `#saveInd` ke `title` (di HP hanya titik yang tampak).
- Tidak diubah (sudah memadai): cincin fokus (`--fok`, baris ~474) dan `aria-label`/`title` ikon header (`.hb`, `.themes button`). Ikon header tetap tanpa teks di 360 px karena tidak muat; sudah ada tooltip dan nama aksesibel.
- Percobaan yang gagal: `nowrap` saja membuat teks terpotong karena tombol `flex:1` dipaksa sama lebar; diperbaiki dengan `flex:1 1 auto`.
Perlu dicek manual: tampilan 3 tema (terang, biru, dunia) pada tombol segmen; latar Angkasa di HP sungguhan.

## Tahap 2: Setoran di HP
Status: SELESAI (v1.61)
Cek validasi: di 360x740 kartu siswa mulai di y<=430 (`.stC`), `.stw .klpsg` satu baris (tinggi <=48 px) dan bisa digeser; setelah digulir 500 px tombol `#stSave` tampak tepat di atas bilah bawah (`position:sticky`); mode Per kelompok dan desktop tidak berubah; semua fungsi lama (cari, kelompok, urutan, target, simpan) tetap bekerja.
Catatan perubahan:
- CSS di `<style id="v160">` (blok "v1.61 Tahap 2"), hanya `@media(max-width:819px)`: `.stw .klpsg` nowrap + gulir mendatar tanpa scrollbar; `.stw #stSave` sticky `bottom:84px+safe-area`, bayangan agar terpisah dari isian di bawahnya.
- Hasil ukur: kartu siswa y=473 -> 425; filter kelompok 94 px -> 46 px.
- Tidak diubah: teks tombol "Urutan: ..." (dua baris di HP), isi form, urutan isian.
Perlu dicek manual: di HP sungguhan tombol Simpan tidak menutup isian terakhir (Keterangan) saat papan ketik muncul.

## Tahap 3: Halaman depan dan Dashboard
Status: SELESAI (v1.62)
Cek validasi: buka halaman depan (logo kiri atas) dengan siswa hanya di 4A: tampil kartu Koordinator + kartu 4A, 11 kelas lain ada di lipatan "Kelas belum berisi siswa (11)" dan bisa dipilih dari dalam lipatan; tanpa siswa sama sekali semua 12 kelas tampil tanpa lipatan; Dashboard dengan cadangan >14 hari/belum pernah: kartu "Pengingat cadangan" lebih pendek, tombol `#bkNow` (kini `.sm`) tetap mengunduh; pilihan kelas default per perangkat tetap tersimpan.
Catatan perubahan:
- JS `gerbangBuka`: variabel `ada0/ada/kos` (kelas berisi atau aktif atau default dulu; bila `!KLS_ALL.some(k=>n[k])` semua tampil), `<details class="pkd">` untuk kelas kosong.
- JS `kartuCad`: kelas `cad`, tombol `.btn` -> `.sm`, baris tombol tanpa margin inline.
- CSS di `<style id="v160">` (blok "v1.62 Tahap 3"): `.pkd`, `.cad`.
- Bug yang sempat ada: kelas aktif/default dihitung "berisi" sehingga instalasi kosong tetap melipat 11 kelas; diperbaiki dengan syarat `KLS_ALL.some(k=>n[k])`.
- Tidak diubah: urutan kartu Dashboard, teks pengingat, kartu hero kelas (gambar planet tema Angkasa).
Perlu dicek manual: tampilan lipatan di tema terang/biru/dunia.

## Tahap 4: Pengaturan
Status: BELUM
Cek validasi: tiap kelompok (Halaman awal, Koordinator, Identitas, Tampilan, Cadangan, Panduan) bisa dilipat; isian tetap tersimpan; sakelar Koordinator (v1.58) tetap bekerja.
Catatan perubahan: -
Perlu dicek manual: -

## Tahap 5: Input Nilai dan Koordinator
Status: BELUM
Cek validasi: nama siswa menempel di atas saat menggulir daftar surat; Batalkan nilai (v1.58) tetap bekerja; tombol nilai tetap >= 44 px.
Catatan perubahan: -
Perlu dicek manual: -

## Tahap 6: QA akhir
Status: BELUM
Cek validasi: tiga tema (terang, biru, gelap) terbaca; 360/412/768/1280 px tanpa gulir mendatar; `prefers-reduced-motion` dihormati; fingerprint dan golden SAMA; cetak rapor/PDF tidak berubah (cek manual).
Catatan perubahan: -
Perlu dicek manual: -
