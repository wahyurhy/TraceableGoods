package com.wahyurhy.traceablegoods.core.data.source.local.db

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class DataInformasiColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "data_info"
            const val COLUMN_ID = "_id"
            const val COLUMN_DATA_NAME = "data_name"
            const val COLUMN_TIMESTAMP = "timestamp"
        }
    }

    internal class ProdukColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "product"
            const val COLUMN_ID = "product_id"
            const val COLUMN_DATA_INFO_ID_FK = "data_info_id_fk"
            const val COLUMN_JENIS_PRODUK = "jenis_produk"
            const val COLUMN_NAMA_PRODUK = "nama_produk"
            const val COLUMN_MEREK = "merek"
            const val COLUMN_NOMOR_LOT = "nomor_lot"
            const val COLUMN_TANGGAL_PRODUKSI = "tanggal_produksi"
            const val COLUMN_TANGGAL_KADALUARSA = "tanggal_kadaluarsa"
            const val COLUMN_DESKRIPSI = "deskripsi"
            const val COLUMN_TIMESTAMP = "timestamp"
        }
    }

    internal class ProdusenColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "produsen"
            const val COLUMN_ID = "produsen_id"
            const val COLUMN_DATA_INFO_ID_FK = "data_info_id_fk"
            const val COLUMN_NAMA_PRODUSEN = "nama_produsen"
            const val COLUMN_KATEGORI_PRODUSEN = "kategori_produsen"
            const val COLUMN_ALAMAT_PRODUSEN = "alamat_produsen"
            const val COLUMN_KONTAK_PRODUSEN = "kontak_produsen"
            const val COLUMN_NO_NPWP = "no_npwp"
            const val COLUMN_TIMESTAMP = "timestamp"
        }
    }

    internal class DistributorColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "distributor"
            const val COLUMN_ID = "distributor_id"
            const val COLUMN_DATA_INFO_ID_FK = "data_info_id_fk"
            const val COLUMN_NAMA_DISTRIBUTOR = "nama_distributor"
            const val COLUMN_ALAMAT_DISTRIBUTOR = "alamat_distributor"
            const val COLUMN_KONTAK_DISTRIBUTOR = "kontak_distributor"
            const val COLUMN_TIMESTAMP = "timestamp"
        }
    }

    internal class PenerimaColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "penerima"
            const val COLUMN_ID = "penerima_id"
            const val COLUMN_DATA_INFO_ID_FK = "data_info_id_fk"
            const val COLUMN_NAMA_PENERIMA = "nama_penerima"
            const val COLUMN_KATEGORI_PENERIMA = "kategori_penerima"
            const val COLUMN_ALAMAT_PENERIMA = "alamat_penerima"
            const val COLUMN_KONTAK_PENERIMA = "kontak_penerima"
            const val COLUMN_TIMESTAMP = "timestamp"
        }
    }

    internal class PenggilingColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "penggiling"
            const val COLUMN_ID = "penggiling_id"
            const val COLUMN_DATA_INFO_ID_FK = "data_info_id_fk"
            const val COLUMN_NAMA_PENGGILING = "nama_penggiling"
            const val COLUMN_ALAMAT_PENGGILING = "alamat_penggiling"
            const val COLUMN_KONTAK_PENGGILING = "kontak_penggiling"
            const val COLUMN_TIMESTAMP = "timestamp"
        }
    }

    internal class PengepulColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "pengepul"
            const val COLUMN_ID = "pengepul_id"
            const val COLUMN_DATA_INFO_ID_FK = "data_info_id_fk"
            const val COLUMN_NAMA_PENGEPUL = "nama_pengepul"
            const val COLUMN_ALAMAT_PENGEPUL = "alamat_pengepul"
            const val COLUMN_KONTAK_PENGEPUL = "kontak_pengepul"
            const val COLUMN_TIMESTAMP = "timestamp"
        }
    }

    internal class GudangColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "gudang"
            const val COLUMN_ID = "gudang_id"
            const val COLUMN_DATA_INFO_ID_FK = "data_info_id_fk"
            const val COLUMN_NAMA_GUDANG = "nama_gudang"
            const val COLUMN_ALAMAT_GUDANG = "alamat_gudang"
            const val COLUMN_KONTAK_GUDANG = "kontak_gudang"
            const val COLUMN_TIMESTAMP = "timestamp"
        }
    }

    internal class TengkulakColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "tengkulak"
            const val COLUMN_ID = "tengkulak_id"
            const val COLUMN_DATA_INFO_ID_FK = "data_info_id_fk"
            const val COLUMN_NAMA_TENGKULAK = "nama_tengkulak"
            const val COLUMN_ALAMAT_TENGKULAK = "alamat_tengkulak"
            const val COLUMN_KONTAK_TENGKULAK = "kontak_tengkulak"
            const val COLUMN_TIMESTAMP = "timestamp"
        }
    }

    internal class PabrikPengolahanColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "pabrik_pengeolahan"
            const val COLUMN_ID = "pabrik_id"
            const val COLUMN_DATA_INFO_ID_FK = "data_info_id_fk"
            const val COLUMN_NAMA_PABRIK = "nama_pabrik"
            const val COLUMN_ALAMAT_PABRIK = "alamat_pabrik"
            const val COLUMN_KONTAK_PABRIK = "kontak_pabrik"
            const val COLUMN_TIMESTAMP = "timestamp"
        }
    }

    internal class TransaksiColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "transaksi"
            const val COLUMN_TRANSAKSI_ID = "transaksi_id"
            const val COLUMN_BATCH_ID = "batch_id"
            const val COLUMN_STATUS = "status"
            const val COLUMN_JENIS_PRODUK = "jenis_produk"
            const val COLUMN_PRODUK = "produk"
            const val COLUMN_PRODUK_BATCH = "produk_batch"
            const val COLUMN_PENERIMA = "penerima"
            const val COLUMN_DATE = "date"
        }
    }

    internal class AlurDistribusiColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "alur_distribusi"
            const val COLUMN_BATCH_ID = "batch_id"
            const val COLUMN_TAHAP = "tahap"
            const val COLUMN_STATUS = "status"
            const val COLUMN_NAMA = "nama"
            const val COLUMN_PRODUK = "produk"
            const val COLUMN_PRODUK_BATCH = "produk_batch"
            const val COLUMN_JENIS_PRODUK = "jenis_produk"
            const val COLUMN_TOTAL_YANG_DITERIMA = "total_yang_diterima"
            const val COLUMN_KATEGORI_PENERIMA = "kategori_penerima"
            const val COLUMN_DISTRIBUTOR = "distributor"
            const val COLUMN_TOTAL_YANG_DIDISTRIBUSIKAN = "total_yang_didistribusikan"
            const val COLUMN_LOKASI_ASAL = "lokasi_asal"
            const val COLUMN_LOKASI_TUJUAN = "lokasi_tujuan"
            const val COLUMN_DATE = "date"
        }
    }
}