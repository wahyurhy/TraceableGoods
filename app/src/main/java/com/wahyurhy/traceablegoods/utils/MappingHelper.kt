package com.wahyurhy.traceablegoods.utils

import android.database.Cursor
import com.wahyurhy.traceablegoods.db.DatabaseContract
import com.wahyurhy.traceablegoods.model.datainfo.Item

object MappingHelper {

    fun mapCursorToArrayListDataInfo(dataInfoCursor: Cursor?): ArrayList<Item> {
        val dataInfoList = ArrayList<Item>()

        dataInfoCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.DataInformasiColumns.COLUMN_ID))
                val dataName = getString(getColumnIndexOrThrow(DatabaseContract.DataInformasiColumns.COLUMN_DATA_NAME))
                val timeStamp = getString(getColumnIndexOrThrow(DatabaseContract.DataInformasiColumns.COLUMN_TIMESTAMP))
                dataInfoList.add(Item(id, dataName, timeStamp))
            }
        }
        return dataInfoList
    }

    fun mapCursorToArrayListProduk(produkCursor: Cursor?): ArrayList<com.wahyurhy.traceablegoods.model.produk.Item> {
        val produkList = ArrayList<com.wahyurhy.traceablegoods.model.produk.Item>()

        produkCursor?.apply {
            while (moveToNext()) {
                val produkId = getInt(getColumnIndexOrThrow(DatabaseContract.ProdukColumns.COLUMN_ID))
                val jenisProduk = getString(getColumnIndexOrThrow(DatabaseContract.ProdukColumns.COLUMN_JENIS_PRODUK))
                val namaProduk = getString(getColumnIndexOrThrow(DatabaseContract.ProdukColumns.COLUMN_NAMA_PRODUK))
                val merek = getString(getColumnIndexOrThrow(DatabaseContract.ProdukColumns.COLUMN_MEREK))
                val noLot = getString(getColumnIndexOrThrow(DatabaseContract.ProdukColumns.COLUMN_NOMOR_LOT))
                val tanggalProduksi = getString(getColumnIndexOrThrow(DatabaseContract.ProdukColumns.COLUMN_TANGGAL_PRODUKSI))
                val tanggalKadaluarsa = getString(getColumnIndexOrThrow(DatabaseContract.ProdukColumns.COLUMN_TANGGAL_KADALUARSA))
                val deskripsi = getString(getColumnIndexOrThrow(DatabaseContract.ProdukColumns.COLUMN_DESKRIPSI))
                val timeStamp = getString(getColumnIndexOrThrow(DatabaseContract.ProdukColumns.COLUMN_TIMESTAMP))
                produkList.add(com.wahyurhy.traceablegoods.model.produk.Item(deskripsi, jenisProduk, merek, namaProduk, noLot, produkId, tanggalKadaluarsa, tanggalProduksi, timeStamp))
            }
        }
        return produkList
    }

    fun mapCursorToArrayListProdusen(produsenCursor: Cursor?): ArrayList<com.wahyurhy.traceablegoods.model.produsen.Item> {
        val produsenList = ArrayList<com.wahyurhy.traceablegoods.model.produsen.Item>()

        produsenCursor?.apply {
            while (moveToNext()) {
                val produsenId = getInt(getColumnIndexOrThrow(DatabaseContract.ProdusenColumns.COLUMN_ID))
                val namaProdusen = getString(getColumnIndexOrThrow(DatabaseContract.ProdusenColumns.COLUMN_NAMA_PRODUSEN))
                val kategoriProdusen = getString(getColumnIndexOrThrow(DatabaseContract.ProdusenColumns.COLUMN_KATEGORI_PRODUSEN))
                val alamatProdusen = getString(getColumnIndexOrThrow(DatabaseContract.ProdusenColumns.COLUMN_ALAMAT_PRODUSEN))
                val kontakProdusen = getString(getColumnIndexOrThrow(DatabaseContract.ProdusenColumns.COLUMN_KONTAK_PRODUSEN))
                val noNPWP = getString(getColumnIndexOrThrow(DatabaseContract.ProdusenColumns.COLUMN_NO_NPWP))
                val timeStamp = getString(getColumnIndexOrThrow(DatabaseContract.ProdusenColumns.COLUMN_TIMESTAMP))
                produsenList.add(com.wahyurhy.traceablegoods.model.produsen.Item(alamatProdusen, kategoriProdusen, kontakProdusen, namaProdusen, noNPWP, produsenId, timeStamp))
            }
        }
        return produsenList
    }

    fun mapCursorToArrayListDistributor(distributorCursor: Cursor?): ArrayList<com.wahyurhy.traceablegoods.model.distributor.Item> {
        val distributorList = ArrayList<com.wahyurhy.traceablegoods.model.distributor.Item>()

        distributorCursor?.apply {
            while (moveToNext()) {
                val distributorId = getInt(getColumnIndexOrThrow(DatabaseContract.DistributorColumns.COLUMN_ID))
                val namaDistributor = getString(getColumnIndexOrThrow(DatabaseContract.DistributorColumns.COLUMN_NAMA_DISTRIBUTOR))
                val alamatDistributor = getString(getColumnIndexOrThrow(DatabaseContract.DistributorColumns.COLUMN_ALAMAT_DISTRIBUTOR))
                val kontakDistributor = getString(getColumnIndexOrThrow(DatabaseContract.DistributorColumns.COLUMN_KONTAK_DISTRIBUTOR))
                val timeStamp = getString(getColumnIndexOrThrow(DatabaseContract.DistributorColumns.COLUMN_TIMESTAMP))
                distributorList.add(com.wahyurhy.traceablegoods.model.distributor.Item(alamatDistributor, distributorId, kontakDistributor, namaDistributor, timeStamp))
            }
        }
        return distributorList
    }

    fun mapCursorToArrayListPenerima(penerimaCursor: Cursor?): ArrayList<com.wahyurhy.traceablegoods.model.penerima.Item> {
        val penerimaList = ArrayList<com.wahyurhy.traceablegoods.model.penerima.Item>()

        penerimaCursor?.apply {
            while (moveToNext()) {
                val penerimaId = getInt(getColumnIndexOrThrow(DatabaseContract.PenerimaColumns.COLUMN_ID))
                val namaPenerima = getString(getColumnIndexOrThrow(DatabaseContract.PenerimaColumns.COLUMN_NAMA_PENERIMA))
                val kategoriPenerima = getString(getColumnIndexOrThrow(DatabaseContract.PenerimaColumns.COLUMN_KATEGORI_PENERIMA))
                val alamatPenerima = getString(getColumnIndexOrThrow(DatabaseContract.PenerimaColumns.COLUMN_ALAMAT_PENERIMA))
                val kontakPenerima = getString(getColumnIndexOrThrow(DatabaseContract.PenerimaColumns.COLUMN_KONTAK_PENERIMA))
                val timeStamp = getString(getColumnIndexOrThrow(DatabaseContract.PenerimaColumns.COLUMN_TIMESTAMP))
                penerimaList.add(com.wahyurhy.traceablegoods.model.penerima.Item(alamatPenerima, kategoriPenerima, kontakPenerima, namaPenerima, penerimaId, timeStamp))
            }
        }
        return penerimaList
    }

    fun mapCursorToArrayListPenggiling(penggilingCursor: Cursor?): ArrayList<com.wahyurhy.traceablegoods.model.penggiling.Item> {
        val penggilingList = ArrayList<com.wahyurhy.traceablegoods.model.penggiling.Item>()

        penggilingCursor?.apply {
            while (moveToNext()) {
                val penggilingId = getInt(getColumnIndexOrThrow(DatabaseContract.PenggilingColumns.COLUMN_ID))
                val namaPenggiling = getString(getColumnIndexOrThrow(DatabaseContract.PenggilingColumns.COLUMN_NAMA_PENGGILING))
                val alamatPenggiling = getString(getColumnIndexOrThrow(DatabaseContract.PenggilingColumns.COLUMN_ALAMAT_PENGGILING))
                val kontakPenggiling = getString(getColumnIndexOrThrow(DatabaseContract.PenggilingColumns.COLUMN_KONTAK_PENGGILING))
                val timeStamp = getString(getColumnIndexOrThrow(DatabaseContract.PenggilingColumns.COLUMN_TIMESTAMP))
                penggilingList.add(com.wahyurhy.traceablegoods.model.penggiling.Item(alamatPenggiling, kontakPenggiling, namaPenggiling, penggilingId, timeStamp))
            }
        }
        return penggilingList
    }

    fun mapCursorToArrayListPengepul(pengepulCursor: Cursor?): ArrayList<com.wahyurhy.traceablegoods.model.pengepul.Item> {
        val pengepulList = ArrayList<com.wahyurhy.traceablegoods.model.pengepul.Item>()

        pengepulCursor?.apply {
            while (moveToNext()) {
                val pengepulId = getInt(getColumnIndexOrThrow(DatabaseContract.PengepulColumns.COLUMN_ID))
                val namaPengepul = getString(getColumnIndexOrThrow(DatabaseContract.PengepulColumns.COLUMN_NAMA_PENGEPUL))
                val alamatPengepul = getString(getColumnIndexOrThrow(DatabaseContract.PengepulColumns.COLUMN_ALAMAT_PENGEPUL))
                val kontakPengepul = getString(getColumnIndexOrThrow(DatabaseContract.PengepulColumns.COLUMN_KONTAK_PENGEPUL))
                val timeStamp = getString(getColumnIndexOrThrow(DatabaseContract.PengepulColumns.COLUMN_TIMESTAMP))
                pengepulList.add(com.wahyurhy.traceablegoods.model.pengepul.Item(alamatPengepul, kontakPengepul, namaPengepul, pengepulId, timeStamp))
            }
        }
        return pengepulList
    }

    fun mapCursorToArrayListGudang(gudangCursor: Cursor?): ArrayList<com.wahyurhy.traceablegoods.model.gudang.Item> {
        val gudangList = ArrayList<com.wahyurhy.traceablegoods.model.gudang.Item>()

        gudangCursor?.apply {
            while (moveToNext()) {
                val gudangId = getInt(getColumnIndexOrThrow(DatabaseContract.GudangColumns.COLUMN_ID))
                val namaGudang = getString(getColumnIndexOrThrow(DatabaseContract.GudangColumns.COLUMN_NAMA_GUDANG))
                val alamatGudang = getString(getColumnIndexOrThrow(DatabaseContract.GudangColumns.COLUMN_ALAMAT_GUDANG))
                val kontakGudang = getString(getColumnIndexOrThrow(DatabaseContract.GudangColumns.COLUMN_KONTAK_GUDANG))
                val timeStamp = getString(getColumnIndexOrThrow(DatabaseContract.GudangColumns.COLUMN_TIMESTAMP))
                gudangList.add(com.wahyurhy.traceablegoods.model.gudang.Item(alamatGudang, gudangId, kontakGudang, namaGudang, timeStamp))
            }
        }
        return gudangList
    }

    fun mapCursorToArrayListTengkulak(tengkulakCursor: Cursor?): ArrayList<com.wahyurhy.traceablegoods.model.tengkulak.Item> {
        val tengkulakList = ArrayList<com.wahyurhy.traceablegoods.model.tengkulak.Item>()

        tengkulakCursor?.apply {
            while (moveToNext()) {
                val tengkulakId = getInt(getColumnIndexOrThrow(DatabaseContract.TengkulakColumns.COLUMN_ID))
                val namaTengkulak = getString(getColumnIndexOrThrow(DatabaseContract.TengkulakColumns.COLUMN_NAMA_TENGKULAK))
                val alamatTengkulak = getString(getColumnIndexOrThrow(DatabaseContract.TengkulakColumns.COLUMN_ALAMAT_TENGKULAK))
                val kontakTengkulak = getString(getColumnIndexOrThrow(DatabaseContract.TengkulakColumns.COLUMN_KONTAK_TENGKULAK))
                val timeStamp = getString(getColumnIndexOrThrow(DatabaseContract.TengkulakColumns.COLUMN_TIMESTAMP))
                tengkulakList.add(com.wahyurhy.traceablegoods.model.tengkulak.Item(alamatTengkulak, kontakTengkulak, namaTengkulak, tengkulakId, timeStamp))
            }
        }
        return tengkulakList
    }

    fun mapCursorToArrayListPabrikPengolahan(pabrikCursor: Cursor?): ArrayList<com.wahyurhy.traceablegoods.model.pabrikpengolahan.Item> {
        val pabrikList = ArrayList<com.wahyurhy.traceablegoods.model.pabrikpengolahan.Item>()

        pabrikCursor?.apply {
            while (moveToNext()) {
                val pabrikId = getInt(getColumnIndexOrThrow(DatabaseContract.PabrikPengolahanColumns.COLUMN_ID))
                val namaPabrikPengolahan = getString(getColumnIndexOrThrow(DatabaseContract.PabrikPengolahanColumns.COLUMN_NAMA_PABRIK))
                val alamatPabrikPengolahan = getString(getColumnIndexOrThrow(DatabaseContract.PabrikPengolahanColumns.COLUMN_ALAMAT_PABRIK))
                val kontakPabrikPengolahan = getString(getColumnIndexOrThrow(DatabaseContract.PabrikPengolahanColumns.COLUMN_KONTAK_PABRIK))
                val timeStamp = getString(getColumnIndexOrThrow(DatabaseContract.PabrikPengolahanColumns.COLUMN_TIMESTAMP))
                pabrikList.add(com.wahyurhy.traceablegoods.model.pabrikpengolahan.Item(alamatPabrikPengolahan, kontakPabrikPengolahan, namaPabrikPengolahan, pabrikId, timeStamp))
            }
        }
        return pabrikList
    }

}