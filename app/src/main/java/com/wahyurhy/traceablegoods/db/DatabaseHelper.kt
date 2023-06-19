package com.wahyurhy.traceablegoods.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

internal class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbtraceablegoodapp"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_DATA_INFO =
            "CREATE TABLE ${DatabaseContract.DataInformasiColumns.TABLE_NAME}" +
                    " (${DatabaseContract.DataInformasiColumns.COLUMN_ID} TEXT NOT NULL," +
                    " ${DatabaseContract.DataInformasiColumns.COLUMN_DATA_NAME} TEXT NOT NULL)"

        private const val SQL_CREATE_TABLE_PRODUK =
            "CREATE TABLE ${DatabaseContract.ProdukColumns.TABLE_NAME}" +
                    " (${DatabaseContract.ProdukColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " ${DatabaseContract.ProdukColumns.COLUMN_DATA_INFO_ID_FK} TEXT NOT NULL," +
                    " ${DatabaseContract.ProdukColumns.COLUMN_JENIS_PRODUK} TEXT NOT NULL," +
                    " ${DatabaseContract.ProdukColumns.COLUMN_NAMA_PRODUK} TEXT NOT NULL," +
                    " ${DatabaseContract.ProdukColumns.COLUMN_MEREK} TEXT," +
                    " ${DatabaseContract.ProdukColumns.COLUMN_NOMOR_LOT} TEXT," +
                    " ${DatabaseContract.ProdukColumns.COLUMN_TANGGAL_PRODUKSI} TEXT," +
                    " ${DatabaseContract.ProdukColumns.COLUMN_TANGGAL_KADALUARSA} TEXT," +
                    " ${DatabaseContract.ProdukColumns.COLUMN_DESKRIPSI} TEXT," +
                    " ${DatabaseContract.ProdukColumns.COLUMN_TIMESTAMP} TEXT," +
                    " FOREIGN KEY (${DatabaseContract.ProdukColumns.COLUMN_DATA_INFO_ID_FK}) REFERENCES ${DatabaseContract.DataInformasiColumns.TABLE_NAME}(${DatabaseContract.DataInformasiColumns.COLUMN_ID}))"

        private const val SQL_CREATE_TABLE_PRODUSEN =
            "CREATE TABLE ${DatabaseContract.ProdusenColumns.TABLE_NAME}" +
                    " (${DatabaseContract.ProdusenColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " ${DatabaseContract.ProdusenColumns.COLUMN_DATA_INFO_ID_FK} TEXT NOT NULL," +
                    " ${DatabaseContract.ProdusenColumns.COLUMN_NAMA_PRODUSEN} TEXT NOT NULL," +
                    " ${DatabaseContract.ProdusenColumns.COLUMN_KATEGORI_PRODUSEN} TEXT NOT NULL," +
                    " ${DatabaseContract.ProdusenColumns.COLUMN_ALAMAT_PRODUSEN} TEXT," +
                    " ${DatabaseContract.ProdusenColumns.COLUMN_KONTAK_PRODUSEN} TEXT," +
                    " ${DatabaseContract.ProdusenColumns.COLUMN_NO_NPWP} TEXT," +
                    " ${DatabaseContract.ProdusenColumns.COLUMN_TIMESTAMP} TEXT," +
                    " FOREIGN KEY (${DatabaseContract.ProdusenColumns.COLUMN_DATA_INFO_ID_FK}) REFERENCES ${DatabaseContract.DataInformasiColumns.TABLE_NAME}(${DatabaseContract.DataInformasiColumns.COLUMN_ID}))"

        private const val SQL_CREATE_TABLE_DISTRIBUTOR =
            "CREATE TABLE ${DatabaseContract.DistributorColumns.TABLE_NAME}" +
                    " (${DatabaseContract.DistributorColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " ${DatabaseContract.DistributorColumns.COLUMN_DATA_INFO_ID_FK} TEXT NOT NULL," +
                    " ${DatabaseContract.DistributorColumns.COLUMN_NAMA_DISTRIBUTOR} TEXT NOT NULL," +
                    " ${DatabaseContract.DistributorColumns.COLUMN_ALAMAT_DISTRIBUTOR} TEXT," +
                    " ${DatabaseContract.DistributorColumns.COLUMN_KONTAK_DISTRIBUTOR} TEXT," +
                    " ${DatabaseContract.DistributorColumns.COLUMN_TIMESTAMP} TEXT," +
                    " FOREIGN KEY (${DatabaseContract.DistributorColumns.COLUMN_DATA_INFO_ID_FK}) REFERENCES ${DatabaseContract.DataInformasiColumns.TABLE_NAME}(${DatabaseContract.DataInformasiColumns.COLUMN_ID}))"

        private const val SQL_CREATE_TABLE_PENERIMA =
            "CREATE TABLE ${DatabaseContract.PenerimaColumns.TABLE_NAME}" +
                    " (${DatabaseContract.PenerimaColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " ${DatabaseContract.PenerimaColumns.COLUMN_DATA_INFO_ID_FK} TEXT NOT NULL," +
                    " ${DatabaseContract.PenerimaColumns.COLUMN_NAMA_PENERIMA} TEXT NOT NULL," +
                    " ${DatabaseContract.PenerimaColumns.COLUMN_ALAMAT_PENERIMA} TEXT," +
                    " ${DatabaseContract.PenerimaColumns.COLUMN_KONTAK_PENERIMA} TEXT," +
                    " ${DatabaseContract.PenerimaColumns.COLUMN_TIMESTAMP} TEXT," +
                    " FOREIGN KEY (${DatabaseContract.PenerimaColumns.COLUMN_DATA_INFO_ID_FK}) REFERENCES ${DatabaseContract.DataInformasiColumns.TABLE_NAME}(${DatabaseContract.DataInformasiColumns.COLUMN_ID}))"

        private const val SQL_CREATE_TABLE_PENGGILING =
            "CREATE TABLE ${DatabaseContract.PenggilingColumns.TABLE_NAME}" +
                    " (${DatabaseContract.PenggilingColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " ${DatabaseContract.PenggilingColumns.COLUMN_DATA_INFO_ID_FK} TEXT NOT NULL," +
                    " ${DatabaseContract.PenggilingColumns.COLUMN_NAMA_PENGGILING} TEXT NOT NULL," +
                    " ${DatabaseContract.PenggilingColumns.COLUMN_ALAMAT_PENGGILING} TEXT," +
                    " ${DatabaseContract.PenggilingColumns.COLUMN_KONTAK_PENGGILING} TEXT," +
                    " ${DatabaseContract.PenggilingColumns.COLUMN_TIMESTAMP} TEXT," +
                    " FOREIGN KEY (${DatabaseContract.PenggilingColumns.COLUMN_DATA_INFO_ID_FK}) REFERENCES ${DatabaseContract.DataInformasiColumns.TABLE_NAME}(${DatabaseContract.DataInformasiColumns.COLUMN_ID}))"

        private const val SQL_CREATE_TABLE_PENGEPUL =
            "CREATE TABLE ${DatabaseContract.PengepulColumns.TABLE_NAME}" +
                    " (${DatabaseContract.PengepulColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " ${DatabaseContract.PengepulColumns.COLUMN_DATA_INFO_ID_FK} TEXT NOT NULL," +
                    " ${DatabaseContract.PengepulColumns.COLUMN_NAMA_PENGEPUL} TEXT NOT NULL," +
                    " ${DatabaseContract.PengepulColumns.COLUMN_ALAMAT_PENGEPUL} TEXT," +
                    " ${DatabaseContract.PengepulColumns.COLUMN_KONTAK_PENGEPUL} TEXT," +
                    " ${DatabaseContract.PengepulColumns.COLUMN_TIMESTAMP} TEXT," +
                    " FOREIGN KEY (${DatabaseContract.PengepulColumns.COLUMN_DATA_INFO_ID_FK}) REFERENCES ${DatabaseContract.DataInformasiColumns.TABLE_NAME}(${DatabaseContract.DataInformasiColumns.COLUMN_ID}))"

        private const val SQL_CREATE_TABLE_GUDANG =
            "CREATE TABLE ${DatabaseContract.GudangColumns.TABLE_NAME}" +
                    " (${DatabaseContract.GudangColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " ${DatabaseContract.GudangColumns.COLUMN_DATA_INFO_ID_FK} TEXT NOT NULL," +
                    " ${DatabaseContract.GudangColumns.COLUMN_NAMA_GUDANG} TEXT NOT NULL," +
                    " ${DatabaseContract.GudangColumns.COLUMN_ALAMAT_GUDANG} TEXT," +
                    " ${DatabaseContract.GudangColumns.COLUMN_KONTAK_GUDANG} TEXT," +
                    " ${DatabaseContract.GudangColumns.COLUMN_TIMESTAMP} TEXT," +
                    " FOREIGN KEY (${DatabaseContract.GudangColumns.COLUMN_DATA_INFO_ID_FK}) REFERENCES ${DatabaseContract.DataInformasiColumns.TABLE_NAME}(${DatabaseContract.DataInformasiColumns.COLUMN_ID}))"

        private const val SQL_CREATE_TABLE_TENGKULAK =
            "CREATE TABLE ${DatabaseContract.TengkulakColumns.TABLE_NAME}" +
                    " (${DatabaseContract.TengkulakColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " ${DatabaseContract.TengkulakColumns.COLUMN_DATA_INFO_ID_FK} TEXT NOT NULL," +
                    " ${DatabaseContract.TengkulakColumns.COLUMN_NAMA_TENGKULAK} TEXT NOT NULL," +
                    " ${DatabaseContract.TengkulakColumns.COLUMN_ALAMAT_TENGKULAK} TEXT," +
                    " ${DatabaseContract.TengkulakColumns.COLUMN_KONTAK_TENGKULAK} TEXT," +
                    " ${DatabaseContract.TengkulakColumns.COLUMN_TIMESTAMP} TEXT," +
                    " FOREIGN KEY (${DatabaseContract.TengkulakColumns.COLUMN_DATA_INFO_ID_FK}) REFERENCES ${DatabaseContract.DataInformasiColumns.TABLE_NAME}(${DatabaseContract.DataInformasiColumns.COLUMN_ID}))"

        private const val SQL_CREATE_TABLE_PABRIK_PENGOLAHAN =
            "CREATE TABLE ${DatabaseContract.PabrikPengolahanColumns.TABLE_NAME}" +
                    " (${DatabaseContract.PabrikPengolahanColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " ${DatabaseContract.PabrikPengolahanColumns.COLUMN_DATA_INFO_ID_FK} TEXT NOT NULL," +
                    " ${DatabaseContract.PabrikPengolahanColumns.COLUMN_NAMA_PABRIK} TEXT NOT NULL," +
                    " ${DatabaseContract.PabrikPengolahanColumns.COLUMN_ALAMAT_PABRIK} TEXT," +
                    " ${DatabaseContract.PabrikPengolahanColumns.COLUMN_KONTAK_PABRIK} TEXT," +
                    " ${DatabaseContract.PabrikPengolahanColumns.COLUMN_TIMESTAMP} TEXT," +
                    " FOREIGN KEY (${DatabaseContract.PabrikPengolahanColumns.COLUMN_DATA_INFO_ID_FK}) REFERENCES ${DatabaseContract.DataInformasiColumns.TABLE_NAME}(${DatabaseContract.DataInformasiColumns.COLUMN_ID}))"

        private const val SQL_CREATE_TABLE_TRANSAKSI =
            "CREATE TABLE ${DatabaseContract.TransaksiColumns.TABLE_NAME}" +
                    " (${DatabaseContract.TransaksiColumns.COLUMN_BATCH_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " ${DatabaseContract.TransaksiColumns.COLUMN_STATUS} TEXT NOT NULL," +
                    " ${DatabaseContract.TransaksiColumns.COLUMN_JENIS_PRODUK} TEXT NOT NULL," +
                    " ${DatabaseContract.TransaksiColumns.COLUMN_PRODUK} TEXT NOT NULL," +
                    " ${DatabaseContract.TransaksiColumns.COLUMN_PRODUK_BATCH} TEXT NOT NULL," +
                    " ${DatabaseContract.TransaksiColumns.COLUMN_PENERIMA} TEXT NOT NULL," +
                    " ${DatabaseContract.TransaksiColumns.COLUMN_DATE} TEXT NOT NULL)"

        private const val SQL_CREATE_TABLE_ALUR_DISTRIBUSI =
            "CREATE TABLE ${DatabaseContract.AlurDistribusiColumns.TABLE_NAME}" +
                    " (${DatabaseContract.AlurDistribusiColumns.COLUMN_BATCH_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " ${DatabaseContract.AlurDistribusiColumns.COLUMN_TAHAP} TEXT NOT NULL," +
                    " ${DatabaseContract.AlurDistribusiColumns.COLUMN_STATUS} TEXT NOT NULL," +
                    " ${DatabaseContract.AlurDistribusiColumns.COLUMN_NAMA} TEXT NOT NULL," +
                    " ${DatabaseContract.AlurDistribusiColumns.COLUMN_JENIS_PRODUK} TEXT NOT NULL," +
                    " ${DatabaseContract.AlurDistribusiColumns.COLUMN_PRODUK} TEXT NOT NULL," +
                    " ${DatabaseContract.AlurDistribusiColumns.COLUMN_PRODUK_BATCH} TEXT NOT NULL," +
                    " ${DatabaseContract.AlurDistribusiColumns.COLUMN_TOTAL_YANG_DITERIMA} TEXT NOT NULL," +
                    " ${DatabaseContract.AlurDistribusiColumns.COLUMN_KATEGORI_PENERIMA} TEXT NOT NULL," +
                    " ${DatabaseContract.AlurDistribusiColumns.COLUMN_DISTRIBUTOR} TEXT NOT NULL," +
                    " ${DatabaseContract.AlurDistribusiColumns.COLUMN_TOTAL_YANG_DIDISTRIBUSIKAN} TEXT NOT NULL," +
                    " ${DatabaseContract.AlurDistribusiColumns.COLUMN_LOKASI_ASAL} TEXT NOT NULL," +
                    " ${DatabaseContract.AlurDistribusiColumns.COLUMN_LOKASI_TUJUAN} TEXT NOT NULL," +
                    " ${DatabaseContract.AlurDistribusiColumns.COLUMN_DATE} TEXT NOT NULL)"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_DATA_INFO)
        db.execSQL(SQL_CREATE_TABLE_PRODUK)
        db.execSQL(SQL_CREATE_TABLE_PRODUSEN)
        db.execSQL(SQL_CREATE_TABLE_DISTRIBUTOR)
        db.execSQL(SQL_CREATE_TABLE_PENERIMA)
        db.execSQL(SQL_CREATE_TABLE_PENGGILING)
        db.execSQL(SQL_CREATE_TABLE_PENGEPUL)
        db.execSQL(SQL_CREATE_TABLE_GUDANG)
        db.execSQL(SQL_CREATE_TABLE_TENGKULAK)
        db.execSQL(SQL_CREATE_TABLE_PABRIK_PENGOLAHAN)
        db.execSQL(SQL_CREATE_TABLE_TRANSAKSI)
        db.execSQL(SQL_CREATE_TABLE_ALUR_DISTRIBUSI)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.DataInformasiColumns.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.ProdukColumns.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.ProdusenColumns.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.DistributorColumns.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.PenerimaColumns.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.PenggilingColumns.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.PengepulColumns.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.GudangColumns.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.TengkulakColumns.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.PabrikPengolahanColumns.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.TransaksiColumns.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.AlurDistribusiColumns.TABLE_NAME}")

        onCreate(db)
    }
}