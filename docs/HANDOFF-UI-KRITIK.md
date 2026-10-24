# HANDOFF — Perbaikan layout hasil kritik UI (sesi 46, v1.66)

Pelengkap blok `proyek-handoff` di index.html. Tidak berisi base64 atau data siswa.
Aturan proyek tetap: lihat blok `proyek-instruksi` (bagian terlindungi tidak boleh berubah; CSS baru hanya di `<style id="v166">`; JS baru hanya setelah `cetak` atau sebelum `/* ===== EKSPOR LEGER`).

## Status
- File hasil: `aplikasi-btq-tahfidz-s46.html` (master + blok proyek, VERSI v1.66). index.html di repo mungkin masih v1.65 sampai file ini di-commit oleh pengguna.
- Validasi tiap tahap: `fingerprint.py` SAMA, `golden.py` = `a998dc93b6deea07 30206`, `node --check` lulus, patch idempoten (jalan 2x = hasil sama), ukuran/overflow diukur dengan Playwright (Chromium).

| Tahap | Isi | Status |
|---|---|---|
| A | Tabel Siswa tidak meluber di desktop 1024–1439 px; kartu Kelompok terlipat bawaan | SELESAI (meluber 0 px di 1024/1100/1280/1439/1440/1600; sebelumnya sampai 406 px) |
| B | Header HP: ikon Pengaturan beda dari ikon tema, area sentuh 44 px, title status simpan | SELESAI (tanpa overflow di 360/390/430/768) |
| C | Setoran HP: baris status diringkas | SEBAGIAN (kolom Tanggal naik 24 px, tetapi masih tertutup tombol Simpan di layar pertama) |
| D | Chevron seragam di Pengaturan, tab Rekap satu baris, baris Input Nilai desktop merapat | SELESAI (jarak nama→tombol 449→304 px) |
| E | Dekorasi tema Angkasa di HP; pratinjau Rapor HP; kartu kosong Dashboard; pemilih tema ganda | BELUM |

## Patch yang diterapkan (urut, idempoten; cek jumlah kemunculan sebelum ganti)
1. VERSI: `const VERSI='v1.65'` → `'v1.66'`.
2. `klpCard`: ganti `<details id="klpD"${klpOpen||DESK()?' open':''}>` menjadi `<details id="klpD"${klpOpen?' open':''}>`.
3. `IC.setelan` → `'<path d="M4 7h9M17 7h3M4 17h3M11 17h9"/><circle cx="15" cy="7" r="2"/><circle cx="9" cy="17" r="2"/>'`.
4. Header: `<div class="save" id="saveInd">` → tambah `title="Status penyimpanan"`.
5. viewSetoran: dua `<p class="mut" style="text-align:center;margin-bottom:10px">` (awalan "Sudah setor ${tglF(ST.tgl)}" dan "Terakhir setor:") diberi class `mut stI`.
6. Sisipkan `<style id="v166">…</style>` tepat setelah `</style>` terakhir (v160), sebelum `</head>`:

```css
/* v1.66 Tahap A */
@media(min-width:1024px) and (max-width:1439px){
 .md{grid-template-columns:minmax(0,1fr) 320px}
 .sw,.swh{grid-template-columns:40px minmax(160px,1fr) minmax(96px,.8fr) minmax(96px,.8fr) 20px}
 .swh>span:nth-child(3),.swh>span:nth-child(4),.sw>.dk:nth-child(3),.sw>.dk:nth-child(4){display:none}
 .sw .nm .mob{display:block}
}
@media(min-width:1024px) and (max-width:1199px){
 .md{grid-template-columns:minmax(0,1fr) 280px}
 .sw,.swh{grid-template-columns:36px minmax(140px,1fr) minmax(72px,.6fr) minmax(72px,.6fr) 16px}
}
/* v1.66 Tahap B */
.hb{position:relative}
.hb::after{content:"";position:absolute;top:-3px;right:-3px;bottom:-3px;left:-3px}
@media(max-width:480px){
 header{gap:8px;padding-left:14px;padding-right:14px}
 .hnav{gap:4px}.hb{width:38px;height:38px}
 .ksw select{min-height:44px}
}
/* v1.66 Tahap C */
@media(max-width:819px){
 .stw .stI{font-size:12px;line-height:1.35;margin-bottom:6px!important}
 .stw .stC .row[style*="justify-content:center"]{margin-bottom:6px!important}
 .stw .last{padding:8px 12px;margin-bottom:8px}
 .stw{padding-bottom:12px}
}
/* v1.66 Tahap D */
body[data-pg=setelan] .card>details:not(.stg)>summary{cursor:pointer;list-style:none;display:flex;align-items:center;justify-content:space-between;gap:10px;min-height:44px;font-size:14px;font-weight:600;color:var(--mu)}
body[data-pg=setelan] .card>details:not(.stg)>summary::-webkit-details-marker{display:none}
body[data-pg=setelan] .card>details:not(.stg)>summary::after{content:"";flex:none;width:9px;height:9px;margin-right:4px;border-right:2px solid currentColor;border-bottom:2px solid currentColor;transform:rotate(45deg)}
body[data-pg=setelan] .card>details[open]:not(.stg)>summary::after{transform:rotate(-135deg)}
body[data-pg=rekap] .seg button{white-space:nowrap;padding-left:8px;padding-right:8px}
@media(min-width:1024px){body[data-pg=nilai] .it{grid-template-columns:minmax(180px,280px) minmax(0,380px);column-gap:24px}}
```

## Sisa pekerjaan (urut prioritas)
1. Tahap C lanjutan (butuh keputusan pengguna): kolom Tanggal dan Per surat/Per tahap masih di bawah tombol Simpan sticky di layar pertama HP. Opsi: gabung tiga baris status jadi satu; pindahkan pencarian dan Mode ke menu; atau tombol Simpan tidak sticky sampai form terlihat.
2. Tema Angkasa (bawaan) di HP: planet dekoratif menyembul di antara kartu, dok bawah memakan ±70 px. Opsi: turunkan dekorasi di halaman kerja (Setoran/Nilai), atau ubah bawaan ke Terang (keputusan pengguna; DEFAULT.tema='angkasa' sejak v1.32).
3. Pratinjau Rapor di HP terlalu kecil (A4 diskala ke 358 px): tambah tombol "Lihat penuh"/zoom TANPA menyentuh bagian terlindungi.
4. Dashboard: kartu "Distribusi predikat" dan radar kosong terlalu besar saat belum ada nilai.
5. Pemilih tema ada di header dan di Pengaturan > Tampilan (duplikat); ikon Dashboard/Kelas/Pengaturan di header HP tanpa label.
6. Utang teknis: CSS tersebar di 10 blok `<style>` dengan override per tema (5 tema).
7. Cek manual di perangkat nyata (APK/iQOO 15R): semua tahap diuji hanya di Chromium desktop dengan data 12 siswa tanpa setoran/nilai.

## Cara validasi tiap tahap (ulangi sebelum lanjut)
```
python3 fingerprint.py FILE.html   # harus SAMA dengan baku
python3 golden.py FILE.html        # harus a998dc93b6deea07 30206
node --check pada tiap <script> utama (bukan type="text/plain")
```
Lalu ukur dengan Playwright pada lebar 360/390/768/1024/1280/1440: tidak ada overflow horizontal, tidak ada galat JS, tampilan awal (gerbang kelas) mengharuskan klik kelas 4A lalu `go('<halaman>')`.

## Prompt untuk sesi berikutnya (akun mana pun)
Lanjutkan proyek BTQ–Tahfidz sesuai instruksi di dalam file. Unggah `aplikasi-btq-tahfidz-s46.html` (atau ambil index.html dari repo sherlocomp-cyber/BTQ-dan-Tahfidz-Miku bila sudah di-commit v1.66) dan baca `docs/HANDOFF-UI-KRITIK.md`. Jalankan protokol sesi (ekstrak blok proyek, cek fingerprint.py dan golden.py harus SAMA). Lanjutkan Tahap C (keputusan: gabung baris status Setoran HP) lalu Tahap E nomor 2–5, satu tahap per langkah. Setiap tahap: patch kecil idempoten, CSS hanya di `<style id="v166">`, validasi (fingerprint, golden, node --check, ukur overflow di 360/390/768/1280), baru lanjut. Naikkan VERSI per tahap, tulis riwayat sesi 47 di blok handoff dan perbarui dokumen ini, serahkan satu file `aplikasi-btq-tahfidz-s47.html`. Hemat kuota: jangan cetak base64, jangan render ulang halaman yang tidak berubah.
