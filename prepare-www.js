// Menyalin SheetJS lokal ke www/ supaya ekspor Excel jalan tanpa internet.
const fs = require('fs');
fs.mkdirSync('www', { recursive: true });
fs.copyFileSync('node_modules/xlsx/dist/xlsx.full.min.js', 'www/xlsx.full.min.js');
console.log('xlsx.full.min.js disalin ke www/');
