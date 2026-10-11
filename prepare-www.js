// Menyiapkan folder www/: index.html + SheetJS lokal (ekspor Excel tanpa internet).
const fs = require('fs');
fs.mkdirSync('www', { recursive: true });
fs.copyFileSync('index.html', 'www/index.html');
fs.copyFileSync('node_modules/xlsx/dist/xlsx.full.min.js', 'www/xlsx.full.min.js');
console.log('www/ siap');
