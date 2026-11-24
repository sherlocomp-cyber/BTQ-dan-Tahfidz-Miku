# Rencana pengaman data (banyak guru, banyak kelas) — v1.98

Tujuan dari pengguna: aplikasi dipakai beberapa guru bersamaan di kelas berbeda; **data tidak boleh tertimpa atau hilang lagi**.

## Celah yang ditemukan di v1.95
1. Penghapusan di server menjalar: `Cloud.rekon()` menghapus siswa lokal bila node server hilang dan lokal belum berubah. Satu kali `/btq` terhapus mengosongkan semua HP.
2. Alamat Firebase ada di repo publik; aturan terbuka tanpa login (belum diperiksa di Firebase Console).
3. Cadangan otomatis harian ada di IndexedDB yang sama dengan data (ikut hilang bila data browser dibersihkan).
4. Hapus siswa/kelas dan Pulihkan JSON ikut tersinkron ke semua guru; Urungkan hanya beberapa detik; Pulihkan JSON tanpa salinan pengaman.

## Yang dibangun (v1.98, semua di `index.html`, tanpa mengubah skema IndexedDB)
- **Rem darurat** (`guardHapus`/`tanyaHapus`): penghapusan dari server >=5 siswa sekaligus, atau semua siswa (bila >=3), atau >=30 setoran/30%, ditahan. Guru ditanya; OK = data perangkat dipertahankan dan dikirim balik ke server (pilihan aman); Batal lalu konfirmasi kedua = diterapkan setelah salinan pengaman (`cadanganSebelumHapusServer`) dan Sampah server dibuat. Penghapusan kecil (1-4 siswa) mengalir seperti biasa.
- **Cadangan harian server**: `btq-cadangan/harian/<tanggal>` (jalur terpisah dari `btq`), sekali sehari oleh perangkat pertama yang tersambung, 30 hari. Tidak dibuat bila jumlah siswa turun di bawah 50% cadangan terakhir (mencegah merekam keadaan rusak).
- **Sampah server 30 hari**: `btq-cadangan/sampah/<id>` untuk Hapus siswa, Hapus semua siswa kelas, Pulihkan JSON, dan penghapusan massal yang disetujui. Bila offline, antre di kv `sampahTunda`.
- **Pulihkan dari server** (Pengaturan › Cadangan & sampah di server): hanya MENAMBAH siswa/setoran/pengaturan kelas yang hilang; data yang ada tidak ditimpa; idempoten; salinan perangkat `cadanganSebelumPulihServer` dibuat dulu.
- **Pulihkan dari cadangan JSON**: salinan pengaman (`cadanganSebelumPulih` + Sampah server) dan ketik PULIHKAN bila cadangan berisi lebih sedikit siswa.
- Cadangan otomatis di perangkat: 3 → 7 hari.

## Yang harus dilakukan pengguna (tidak bisa dari kode)
1. Pasang aturan di `docs/firebase-rules.json` di Firebase Console › Realtime Database › Rules (uji dulu di Rules Playground). Aturan ini menolak penghapusan seluruh `btq`, dan melindungi cadangan harian/sampah dari ditimpa atau dihapus sebelum 30 hari. Tanpa login, pihak yang tahu alamat database tetap bisa menghapus sebagian data; cadangan server + rem darurat membuat itu dapat dipulihkan.
2. Tetap unduh cadangan JSON berkala; simpan di luar HP (mis. Google Drive).
3. Jadikan repo privat bila memungkinkan (alamat Firebase dan nama siswa 4A di `REKAP_BAWAAN` terlihat publik); GitHub Pages privat butuh paket berbayar.

## Diketahui / belum
- Pulihkan JSON yang disengaja akan memunculkan peringatan rem darurat di perangkat guru lain (pilihan aman = pertahankan).
- Guru yang lama offline lalu terhubung setelah ada penghapusan massal yang sah juga ditanya sekali; pilih Batal lalu konfirmasi bila memang disengaja.
- Belum ada autentikasi per guru (butuh Firebase Auth); belum ada ekspor otomatis ke luar server.
- Uji otomatis (server tiruan, Chromium): `rem` total, hapus kecil, cadangan harian, sampah, pulihkan hanya-menambah, terapkan setelah dua konfirmasi. Belum diuji di HP nyata dengan dua guru sungguhan.
