# Advanced RecyclerView - Tugas 10

**Mata Kuliah:** Pemrograman Perangkat Bergerak

---

## Identitas

**Nama:** Farrel Ghozy Affifudin
**NIM:** 452024611053
**Prodi:** Teknik Informatika - UNIDA Gontor

---

## Screenshot

**Tampilan Grid (Multiple View Types)**

![Grid View](screenshots/tugas10_grid.png)

 Jadi disini bisa diliat ada 3 jenis tampilan item yang berbeda. Header span nya 3 jadi full satu baris, terus item number sama color masing-masing 1 kolom doang.

**Setelah Update Data**

![After Update](screenshots/tugas10_updated.png)

Ini pas gw pencet tombol Update Data, angka populasinya nambah random. Yang keupdate cuma item-item yang berubah aja, yang lain diem. Makanya pake ListAdapter tuh enak, gak perlu render ulang semua.

**Setelah Di-shuffle**

![After Shuffle](screenshots/tugas10_shuffle.png)

Tombol Shuffle ngacak urutan item tapi Headernya tetep di tempat. DiffUtil otomatis deteksi pergerakan dan cuma animasi item yang pindah doang, mantep.

---

## Fitur yang Dibikin

### 1. ListAdapter + DiffUtil

Biasanya kalo pake RecyclerView.Adapter biasa, tiap kali data berubah kita harus panggil `notifyDataSetChanged()` yang nge-render ulang SEMUA item dari awal. Boros banget apalagi kalo datanya banyak.

Nah disini gw pake `ListAdapter` yang nerima `DiffUtil.ItemCallback`. Jadi pas data di-update, DiffUtil bakal ngebandingin item-item lama sama baru di background thread, terus cuma item yang beneran berubah aja yang di-render ulang. Implementasi callbacknya:

- `areItemsTheSame()` - ngecek pake `id`, kalo id nya sama berarti item itu sama cuma mungkin isinya berubah
- `areContentsTheSame()` - ngecek pake equals data class, kalo semua properti sama ya gak usah di-render ulang

### 2. Multiple View Types

Adapter ini bisa nampilin 3 jenis layout berbeda dalam satu RecyclerView:

- **HEADER** - buat judul section, background gelap, full width
- **NUMBER_ITEM** - nampilin data populasi dengan format angka otomatis (pake BindingAdapter)
- **COLOR_ITEM** - item dengan background warna-warni, pake custom BindingAdapter juga

Caranya tinggal override `getItemViewType()` terus balikin nilai sesuai tipe itemnya.

### 3. GridLayoutManager + SpanSizeLookup

RecyclerView pake GridLayoutManager dengan 3 kolom. Biar Headernya bisa full width, gw pake SpanSizeLookup:

- HEADER -> span 3 (full baris)
- NUMBER_ITEM -> span 1
- COLOR_ITEM -> span 1

Jadi layoutnya rapi, Headernya gak kepotong kolom.

### 4. Custom BindingAdapter

Buat ngisi value dari XML langsung tanpa harus nulis kode binding di ViewHolder, gw bikin 2 BindingAdapter:

- `backgroundColorHex` - ngeset background color dari string hex langsung di XML layout, misal `app:backgroundColorHex="@{item.colorHex}"`
- `formattedNumber` - ngeformat angka gede biar enak dibaca, misal 8.1M, 281K, 1.5M, dll

### 5. ViewHolder Pattern

ViewHolder dibuat pake constructor private terus factory method di companion object. Jadi cara bikin ViewHolder-nya lewat `ViewHolder.from(parent)` aja, lebih bersih dan gak bisa diinstansiasi sembarangan.

---

## Perbedaan RecyclerView.Adapter vs ListAdapter

**RecyclerView.Adapter biasa:**
Pas data berubah, kita panggil `notifyDataSetChanged()` dan BOOM — semua item ke-render ulang meskipun cuma 1 item yang nilainya berubah. Bayangin kalo itemnya ada 16, yang ke render ya 16 semua. Waste banget.

**ListAdapter + DiffUtil:**
Kita tinggal panggil `submitList()` trus DiffUtil otomatis ngehitung di background thread: item mana yang baru, mana yang berubah, mana yang dihapus, mana yang pindah posisi. Hasilnya cuma item-item yang beneran perlu di-update aja yang kena notify. Efisien pol.

Contoh perbandingan dari 16 item:

| Situasi | RecyclerView.Adapter | ListAdapter |
|---------|---------------------|-------------|
| 1 item berubah | 16 item di-render ulang | 1 item doang |
| 3 item berubah | 16 item di-render ulang | 3 item doang |
| Item diacak | 16 item di-render ulang | 2 item dianimasikan |

Jelas banget selisihnya. Di aplikasi realtime kayak chat atau feed sosial yang datanya sering berubah, pake ListAdapter tuh pengaruh banget ke performa apalagi kalo listnya panjang.

---

## Struktur File

```
app/src/main/java/com/example/tugas10/
├── MainActivity.kt
├── MainViewModel.kt
├── model/
│   └── DisplayItem.kt
└── adapter/
    ├── ItemAdapter.kt
    ├── ItemDiffCallback.kt
    ├── ViewHolders.kt
    └── BindingAdapters.kt

app/src/main/res/layout/
├── activity_main.xml
├── item_header.xml
├── item_number.xml
└── item_color.xml
```

---

## Link Repository

**[github.com/FarrelGhozy/Tugas10_Android_AdvancedRV_452024611053](https://github.com/FarrelGhozy/Tugas10_Android_AdvancedRV_452024611053)**

---

## Catatan

- Min SDK: 26 (Android 8)
- Target SDK: 35 (Android 15)
- Pake Kotlin 2.1.0 + AGP 8.7.3
- Dependencies: RecyclerView, CardView, Material3, Data Binding, ViewModel, LiveData
