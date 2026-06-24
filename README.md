# Advanced RecyclerView - Tugas 10

**Mata Kuliah:** Pemrograman Perangkat Bergerak

---

## Identitas

**Nama:** Farrel Ghozy Affifudin
**NIM:** 452024611053
**Prodi:** Teknik Informatika - UNIDA Gontor

---

## Screenshot (HP Android 15 — Xiaomi 25098RA98G)

**Tampilan Grid (Multiple View Types)**

![Grid View](screenshots/tugas10_grid.png)

Ada 3 jenis tampilan item: Header (span 3, full baris), Number Item (1 kolom), dan Color Item (1 kolom). Deskripsi dan angka sekarang terpisah rapi.

**Setelah Update Data**

![After Update](screenshots/tugas10_updated.png)

Pas tombol Update Data ditekan, angka populasi nambah random. DiffUtil cuma nge-render ulang item yang berubah aja, sisanya diem.

**Setelah Di-shuffle**

![After Shuffle](screenshots/tugas10_shuffle.png)

Tombol Shuffle ngacak urutan item non-Header. Header tetep di tempat, DiffUtil deteksi pergerakan dan cuma animasi item yang pindah.

---

## Fitur yang Dibikin

### 1. ListAdapter + DiffUtil

Pake `ListAdapter` yang nerima `DiffUtil.ItemCallback`. Pas data di-update, DiffUtil ngebandingin item-item lama vs baru di background thread, terus cuma item yang berubah aja yang di-render ulang.

- `areItemsTheSame()` — ngecek pake `id`
- `areContentsTheSame()` — ngecek pake equals data class

### 2. Multiple View Types

Adapter nampilin 3 jenis layout dalam satu RecyclerView:
- **HEADER** — judul section, full width (span 3)
- **NUMBER_ITEM** — data populasi dengan format angka otomatis
- **COLOR_ITEM** — item dengan background warna-warni

### 3. GridLayoutManager + SpanSizeLookup

GridLayoutManager 3 kolom + SpanSizeLookup:
- HEADER → span 3 (full baris)
- NUMBER_ITEM → span 1
- COLOR_ITEM → span 1

### 4. Custom BindingAdapter

2 BindingAdapter kustom:
- `backgroundColorHex` — set background color dari string hex di XML
- `formattedNumber` — format angka besar (8.1B, 281.0M, 2.5M, dll)

### 5. ViewHolder Pattern

Private constructor + companion object factory `from(parent)`, lebih clean.

### 6. Android 15 — Edge-to-Edge

Panggil `enableEdgeToEdge()` di `onCreate()` biar UI optimal di HP Android 15.

### 7. CardView Dependency Eksplisit

`implementation("androidx.cardview:cardview:1.0.0")` ditambahkan biar dependency lebih jelas.

---

## Perbedaan RecyclerView.Adapter vs ListAdapter

**RecyclerView.Adapter biasa:**
Pas data berubah, `notifyDataSetChanged()` nge-render ulang SEMUA item. Waste banget.

**ListAdapter + DiffUtil:**
`submitList()` → DiffUtil hitung di background thread → cuma item yang berubah kena notify.

Contoh perbandingan dari 16 item:

| Situasi | RecyclerView.Adapter | ListAdapter |
|---------|---------------------|-------------|
| 1 item berubah | 16 item di-render ulang | 1 item doang |
| 3 item berubah | 16 item di-render ulang | 3 item doang |
| Item diacak | 16 item di-render ulang | 2 item dianimasikan |

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
- Kotlin 2.1.0 + AGP 8.7.3 + Gradle 8.13
- Dependencies: RecyclerView 1.4.0, CardView 1.0.0, Material3, Data Binding, ViewModel, LiveData
- Dicek pada HP Xiaomi 25098RA98G (Android 15 / API 35)
