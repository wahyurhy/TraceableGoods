package com.wahyurhy.traceablegoods.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.sql.SQLException

class TraceableGoodHelper(context: Context) {

    private var databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE_DATA_INFO = DatabaseContract.DataInformasiColumns.TABLE_NAME
        private const val DATABASE_TABLE_PRODUK = DatabaseContract.ProdukColumns.TABLE_NAME
        private const val DATABASE_TABLE_PRODUSEN = DatabaseContract.ProdusenColumns.TABLE_NAME
        private const val DATABASE_TABLE_DISTRIBUTOR = DatabaseContract.DistributorColumns.TABLE_NAME
        private const val DATABASE_TABLE_PENERIMA = DatabaseContract.PenerimaColumns.TABLE_NAME
        private const val DATABASE_TABLE_PENGGILING = DatabaseContract.PenggilingColumns.TABLE_NAME
        private const val DATABASE_TABLE_PENGEPUL = DatabaseContract.PengepulColumns.TABLE_NAME
        private const val DATABASE_TABLE_GUDANG = DatabaseContract.GudangColumns.TABLE_NAME
        private const val DATABASE_TABLE_TENGKULAK = DatabaseContract.TengkulakColumns.TABLE_NAME
        private const val DATABASE_TABLE_PABRIK_PENGOLAHAN = DatabaseContract.PabrikPengolahanColumns.TABLE_NAME
        private const val DATABASE_TABLE_TRANSAKSI = DatabaseContract.TransaksiColumns.TABLE_NAME
        private const val DATABASE_TABLE_ALUR_DISTRIBUSI = DatabaseContract.AlurDistribusiColumns.TABLE_NAME

        private var INSTANCE: TraceableGoodHelper? = null
        fun getInstance(context: Context): TraceableGoodHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: TraceableGoodHelper(context)
            }
    }

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()

        if (database.isOpen) {
            database.close()
        }
    }

    fun queryAllDataInfo(): Cursor {
        return database.query(
            DATABASE_TABLE_DATA_INFO,
            null,
            null,
            null,
            null,
            null,
            "${DatabaseContract.DataInformasiColumns.COLUMN_ID} ASC",
            null)
    }

    fun insertDataInfo(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE_DATA_INFO, null, values)
    }

    fun queryAllProduk(): Cursor {
        return database.query(
            DATABASE_TABLE_PRODUK,
            null,
            null,
            null,
            null,
            null,
            "${DatabaseContract.ProdukColumns.COLUMN_ID} ASC",
            null)
    }

    fun queryProdukById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE_PRODUK,
            null,
            "${DatabaseContract.ProdukColumns.COLUMN_ID} = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun insertProduk(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE_PRODUK, null, values)
    }

    fun updateProduk(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE_PRODUK, values, "${DatabaseContract.ProdukColumns.COLUMN_ID} = ?", arrayOf(id))
    }

    fun deleteProduk(id: String): Int {
        return database.delete(DATABASE_TABLE_PRODUK, "${DatabaseContract.ProdukColumns.COLUMN_ID} = '$id'", null)
    }

    fun queryAllProdusen(): Cursor {
        return database.query(
            DATABASE_TABLE_PRODUSEN,
            null,
            null,
            null,
            null,
            null,
            "${DatabaseContract.ProdusenColumns.COLUMN_ID} ASC",
            null)
    }

    fun queryProdusenById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE_PRODUSEN,
            null,
            "${DatabaseContract.ProdusenColumns.COLUMN_ID} = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun insertProdusen(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE_PRODUSEN, null, values)
    }

    fun updateProdusen(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE_PRODUSEN, values, "${DatabaseContract.ProdusenColumns.COLUMN_ID} = ?", arrayOf(id))
    }

    fun deleteProdusen(id: String): Int {
        return database.delete(DATABASE_TABLE_PRODUSEN, "${DatabaseContract.ProdusenColumns.COLUMN_ID} = '$id'", null)
    }

    fun queryAllDistributor(): Cursor {
        return database.query(
            DATABASE_TABLE_DISTRIBUTOR,
            null,
            null,
            null,
            null,
            null,
            "${DatabaseContract.DistributorColumns.COLUMN_ID} ASC",
            null)
    }

    fun queryDistributorById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE_DISTRIBUTOR,
            null,
            "${DatabaseContract.DistributorColumns.COLUMN_ID} = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun insertDistributor(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE_DISTRIBUTOR, null, values)
    }

    fun updateDistributor(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE_DISTRIBUTOR, values, "${DatabaseContract.DistributorColumns.COLUMN_ID} = ?", arrayOf(id))
    }

    fun deleteDistributor(id: String): Int {
        return database.delete(DATABASE_TABLE_DISTRIBUTOR, "${DatabaseContract.DistributorColumns.COLUMN_ID} = '$id'", null)
    }

    fun queryAllPenerima(): Cursor {
        return database.query(
            DATABASE_TABLE_PENERIMA,
            null,
            null,
            null,
            null,
            null,
            "${DatabaseContract.PenerimaColumns.COLUMN_ID} ASC",
            null)
    }

    fun queryPenerimaById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE_PENERIMA,
            null,
            "${DatabaseContract.PenerimaColumns.COLUMN_ID} = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun insertPenerima(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE_PENERIMA, null, values)
    }

    fun updatePenerima(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE_PENERIMA, values, "${DatabaseContract.PenerimaColumns.COLUMN_ID} = ?", arrayOf(id))
    }

    fun deletePenerima(id: String): Int {
        return database.delete(DATABASE_TABLE_PENERIMA, "${DatabaseContract.PenerimaColumns.COLUMN_ID} = '$id'", null)
    }

    fun queryAllPenggiling(): Cursor {
        return database.query(
            DATABASE_TABLE_PENGGILING,
            null,
            null,
            null,
            null,
            null,
            "${DatabaseContract.PenggilingColumns.COLUMN_ID} ASC",
            null)
    }

    fun queryPenggilingById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE_PENGGILING,
            null,
            "${DatabaseContract.PenggilingColumns.COLUMN_ID} = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun insertPenggiling(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE_PENGGILING, null, values)
    }

    fun updatePenggiling(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE_PENGGILING, values, "${DatabaseContract.PenggilingColumns.COLUMN_ID} = ?", arrayOf(id))
    }

    fun deletePenggiling(id: String): Int {
        return database.delete(DATABASE_TABLE_PENGGILING, "${DatabaseContract.PenggilingColumns.COLUMN_ID} = '$id'", null)
    }

    fun queryAllPengepul(): Cursor {
        return database.query(
            DATABASE_TABLE_PENGEPUL,
            null,
            null,
            null,
            null,
            null,
            "${DatabaseContract.PengepulColumns.COLUMN_ID} ASC",
            null)
    }

    fun queryPengepulById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE_PENGEPUL,
            null,
            "${DatabaseContract.PengepulColumns.COLUMN_ID} = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun insertPengepul(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE_PENGEPUL, null, values)
    }

    fun updatePengepul(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE_PENGEPUL, values, "${DatabaseContract.PengepulColumns.COLUMN_ID} = ?", arrayOf(id))
    }

    fun deletePengepul(id: String): Int {
        return database.delete(DATABASE_TABLE_PENGEPUL, "${DatabaseContract.PengepulColumns.COLUMN_ID} = '$id'", null)
    }

    fun queryAllGudang(): Cursor {
        return database.query(
            DATABASE_TABLE_GUDANG,
            null,
            null,
            null,
            null,
            null,
            "${DatabaseContract.GudangColumns.COLUMN_ID} ASC",
            null)
    }

    fun queryGudangById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE_GUDANG,
            null,
            "${DatabaseContract.GudangColumns.COLUMN_ID} = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun insertGudang(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE_GUDANG, null, values)
    }

    fun updateGudang(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE_GUDANG, values, "${DatabaseContract.GudangColumns.COLUMN_ID} = ?", arrayOf(id))
    }

    fun deleteGudang(id: String): Int {
        return database.delete(DATABASE_TABLE_GUDANG, "${DatabaseContract.GudangColumns.COLUMN_ID} = '$id'", null)
    }

    fun queryAllTengkulak(): Cursor {
        return database.query(
            DATABASE_TABLE_TENGKULAK,
            null,
            null,
            null,
            null,
            null,
            "${DatabaseContract.TengkulakColumns.COLUMN_ID} ASC",
            null)
    }

    fun queryTengkulakById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE_TENGKULAK,
            null,
            "${DatabaseContract.TengkulakColumns.COLUMN_ID} = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun insertTengkulak(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE_TENGKULAK, null, values)
    }

    fun updateTengkulak(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE_TENGKULAK, values, "${DatabaseContract.TengkulakColumns.COLUMN_ID} = ?", arrayOf(id))
    }

    fun deleteTengkulak(id: String): Int {
        return database.delete(DATABASE_TABLE_TENGKULAK, "${DatabaseContract.TengkulakColumns.COLUMN_ID} = '$id'", null)
    }

    fun queryAllPabrikPengolahan(): Cursor {
        return database.query(
            DATABASE_TABLE_PABRIK_PENGOLAHAN,
            null,
            null,
            null,
            null,
            null,
            "${DatabaseContract.PabrikPengolahanColumns.COLUMN_ID} ASC",
            null)
    }

    fun queryPabrikPengolahanById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE_PABRIK_PENGOLAHAN,
            null,
            "${DatabaseContract.PabrikPengolahanColumns.COLUMN_ID} = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun insertPabrikPengolahan(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE_PABRIK_PENGOLAHAN, null, values)
    }

    fun updatePabrikPengolahan(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE_PABRIK_PENGOLAHAN, values, "${DatabaseContract.PabrikPengolahanColumns.COLUMN_ID} = ?", arrayOf(id))
    }

    fun deletePabrikPengolahan(id: String): Int {
        return database.delete(DATABASE_TABLE_PABRIK_PENGOLAHAN, "${DatabaseContract.PabrikPengolahanColumns.COLUMN_ID} = '$id'", null)
    }

    fun queryAllTransaksi(): Cursor {
        return database.query(
            DATABASE_TABLE_TRANSAKSI,
            null,
            null,
            null,
            null,
            null,
            "${DatabaseContract.TransaksiColumns.COLUMN_BATCH_ID} ASC",
            null)
    }

    fun queryTransaksiById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE_TRANSAKSI,
            null,
            "${DatabaseContract.TransaksiColumns.COLUMN_BATCH_ID} = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun insertTransaksi(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE_TRANSAKSI, null, values)
    }

    fun queryAllAlurDistribusi(): Cursor {
        return database.query(
            DATABASE_TABLE_ALUR_DISTRIBUSI,
            null,
            null,
            null,
            null,
            null,
            "${DatabaseContract.AlurDistribusiColumns.COLUMN_BATCH_ID} ASC",
            null)
    }

    fun queryAlurDistribusiById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE_ALUR_DISTRIBUSI,
            null,
            "${DatabaseContract.AlurDistribusiColumns.COLUMN_BATCH_ID} = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun insertAlurDistribusi(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE_ALUR_DISTRIBUSI, null, values)
    }

}