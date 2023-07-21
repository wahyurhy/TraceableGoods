package com.wahyurhy.traceablegoods.utils

import android.database.Cursor
import com.wahyurhy.traceablegoods.core.data.source.model.*
import com.wahyurhy.traceablegoods.db.DatabaseContract

object MappingHelper {

    fun mapCursorToArrayListDataInfo(dataInfoCursor: Cursor?): ArrayList<DataInformasi> {
        val dataInfoList = ArrayList<DataInformasi>()

        dataInfoCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.DataInformasiColumns.COLUMN_ID))
                val dataName = getString(getColumnIndexOrThrow(DatabaseContract.DataInformasiColumns.COLUMN_DATA_NAME))
                val timeStamp = getString(getColumnIndexOrThrow(DatabaseContract.DataInformasiColumns.COLUMN_TIMESTAMP))
                dataInfoList.add(DataInformasi(id, dataName, timeStamp))
            }
        }
        return dataInfoList
    }

    fun mapCursorToArrayListTransaksi(transaksiCursor: Cursor?): ArrayList<Transaksi> {
        val transaksiList = ArrayList<Transaksi>()

        transaksiCursor?.apply {
            while (moveToNext()) {
                val transaksiId = getInt(getColumnIndexOrThrow(DatabaseContract.TransaksiColumns.COLUMN_TRANSAKSI_ID))
                val batchId = getString(getColumnIndexOrThrow(DatabaseContract.TransaksiColumns.COLUMN_BATCH_ID))
                val date = getString(getColumnIndexOrThrow(DatabaseContract.TransaksiColumns.COLUMN_DATE))
                val jenisProduk = getString(getColumnIndexOrThrow(DatabaseContract.TransaksiColumns.COLUMN_JENIS_PRODUK))
                val penerima = getString(getColumnIndexOrThrow(DatabaseContract.TransaksiColumns.COLUMN_PENERIMA))
                val produk = getString(getColumnIndexOrThrow(DatabaseContract.TransaksiColumns.COLUMN_PRODUK))
                val produkBatch = getString(getColumnIndexOrThrow(DatabaseContract.TransaksiColumns.COLUMN_PRODUK_BATCH))
                val status = getString(getColumnIndexOrThrow(DatabaseContract.TransaksiColumns.COLUMN_STATUS))
                transaksiList.add(Transaksi(transaksiId, batchId, date, jenisProduk, penerima, produk, produkBatch, status))
            }
        }
        return transaksiList
    }

    fun mapCursorToArrayListAlurDistribusi(alurDistribusiCursor: Cursor?): ArrayList<AlurDistribusi> {
        val alurDistribusiList = ArrayList<AlurDistribusi>()

        alurDistribusiCursor?.apply {
            while (moveToNext()) {
                val batchId = getString(getColumnIndexOrThrow(DatabaseContract.AlurDistribusiColumns.COLUMN_BATCH_ID))
                val tahap = getString(getColumnIndexOrThrow(DatabaseContract.AlurDistribusiColumns.COLUMN_TAHAP))
                val status = getString(getColumnIndexOrThrow(DatabaseContract.AlurDistribusiColumns.COLUMN_STATUS))
                val nama = getString(getColumnIndexOrThrow(DatabaseContract.AlurDistribusiColumns.COLUMN_NAMA))
                val produk = getString(getColumnIndexOrThrow(DatabaseContract.AlurDistribusiColumns.COLUMN_PRODUK))
                val produkBatch = getString(getColumnIndexOrThrow(DatabaseContract.AlurDistribusiColumns.COLUMN_PRODUK_BATCH))
                val jenisProduk = getString(getColumnIndexOrThrow(DatabaseContract.AlurDistribusiColumns.COLUMN_JENIS_PRODUK))
                val totalYangDiterima = getString(getColumnIndexOrThrow(DatabaseContract.AlurDistribusiColumns.COLUMN_TOTAL_YANG_DITERIMA))
                val kategoriPenerima = getString(getColumnIndexOrThrow(DatabaseContract.AlurDistribusiColumns.COLUMN_KATEGORI_PENERIMA))
                val distributor = getString(getColumnIndexOrThrow(DatabaseContract.AlurDistribusiColumns.COLUMN_DISTRIBUTOR))
                val totalYangDiDistribusikan = getString(getColumnIndexOrThrow(DatabaseContract.AlurDistribusiColumns.COLUMN_TOTAL_YANG_DIDISTRIBUSIKAN))
                val lokasiAsal = getString(getColumnIndexOrThrow(DatabaseContract.AlurDistribusiColumns.COLUMN_LOKASI_ASAL))
                val lokasiTujuan = getString(getColumnIndexOrThrow(DatabaseContract.AlurDistribusiColumns.COLUMN_LOKASI_TUJUAN))
                val date = getString(getColumnIndexOrThrow(DatabaseContract.AlurDistribusiColumns.COLUMN_DATE))
                alurDistribusiList.add(AlurDistribusi(batchId, date, distributor, jenisProduk, kategoriPenerima, lokasiAsal, lokasiTujuan, nama, produk, produkBatch, status, tahap, totalYangDiDistribusikan, totalYangDiterima))
            }
        }
        return alurDistribusiList
    }

    fun mapCursorToArrayListProduk(produkCursor: Cursor?): ArrayList<Produk> {
        val produkList = ArrayList<Produk>()

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
                produkList.add(Produk(deskripsi, jenisProduk, merek, namaProduk, noLot, produkId, tanggalKadaluarsa, tanggalProduksi, timeStamp))
            }
        }
        return produkList
    }

    fun mapCursorToArrayListProdusen(produsenCursor: Cursor?): ArrayList<Produsen> {
        val produsenList = ArrayList<Produsen>()

        produsenCursor?.apply {
            while (moveToNext()) {
                val produsenId = getInt(getColumnIndexOrThrow(DatabaseContract.ProdusenColumns.COLUMN_ID))
                val namaProdusen = getString(getColumnIndexOrThrow(DatabaseContract.ProdusenColumns.COLUMN_NAMA_PRODUSEN))
                val kategoriProdusen = getString(getColumnIndexOrThrow(DatabaseContract.ProdusenColumns.COLUMN_KATEGORI_PRODUSEN))
                val alamatProdusen = getString(getColumnIndexOrThrow(DatabaseContract.ProdusenColumns.COLUMN_ALAMAT_PRODUSEN))
                val kontakProdusen = getString(getColumnIndexOrThrow(DatabaseContract.ProdusenColumns.COLUMN_KONTAK_PRODUSEN))
                val noNPWP = getString(getColumnIndexOrThrow(DatabaseContract.ProdusenColumns.COLUMN_NO_NPWP))
                val timeStamp = getString(getColumnIndexOrThrow(DatabaseContract.ProdusenColumns.COLUMN_TIMESTAMP))
                produsenList.add(Produsen(alamatProdusen, kategoriProdusen, kontakProdusen, namaProdusen, noNPWP, produsenId, timeStamp))
            }
        }
        return produsenList
    }

    fun mapCursorToArrayListDistributor(distributorCursor: Cursor?): ArrayList<Distributor> {
        val distributorList = ArrayList<Distributor>()

        distributorCursor?.apply {
            while (moveToNext()) {
                val distributorId = getInt(getColumnIndexOrThrow(DatabaseContract.DistributorColumns.COLUMN_ID))
                val namaDistributor = getString(getColumnIndexOrThrow(DatabaseContract.DistributorColumns.COLUMN_NAMA_DISTRIBUTOR))
                val alamatDistributor = getString(getColumnIndexOrThrow(DatabaseContract.DistributorColumns.COLUMN_ALAMAT_DISTRIBUTOR))
                val kontakDistributor = getString(getColumnIndexOrThrow(DatabaseContract.DistributorColumns.COLUMN_KONTAK_DISTRIBUTOR))
                val timeStamp = getString(getColumnIndexOrThrow(DatabaseContract.DistributorColumns.COLUMN_TIMESTAMP))
                distributorList.add(Distributor(alamatDistributor, distributorId, kontakDistributor, namaDistributor, timeStamp))
            }
        }
        return distributorList
    }

    fun mapCursorToArrayListPenerima(penerimaCursor: Cursor?): ArrayList<Penerima> {
        val penerimaList = ArrayList<Penerima>()

        penerimaCursor?.apply {
            while (moveToNext()) {
                val penerimaId = getInt(getColumnIndexOrThrow(DatabaseContract.PenerimaColumns.COLUMN_ID))
                val namaPenerima = getString(getColumnIndexOrThrow(DatabaseContract.PenerimaColumns.COLUMN_NAMA_PENERIMA))
                val kategoriPenerima = getString(getColumnIndexOrThrow(DatabaseContract.PenerimaColumns.COLUMN_KATEGORI_PENERIMA))
                val alamatPenerima = getString(getColumnIndexOrThrow(DatabaseContract.PenerimaColumns.COLUMN_ALAMAT_PENERIMA))
                val kontakPenerima = getString(getColumnIndexOrThrow(DatabaseContract.PenerimaColumns.COLUMN_KONTAK_PENERIMA))
                val timeStamp = getString(getColumnIndexOrThrow(DatabaseContract.PenerimaColumns.COLUMN_TIMESTAMP))
                penerimaList.add(Penerima(alamatPenerima, kategoriPenerima, kontakPenerima, namaPenerima, penerimaId, timeStamp))
            }
        }
        return penerimaList
    }

    fun mapCursorToArrayListPenggiling(penggilingCursor: Cursor?): ArrayList<Penggiling> {
        val penggilingList = ArrayList<Penggiling>()

        penggilingCursor?.apply {
            while (moveToNext()) {
                val penggilingId = getInt(getColumnIndexOrThrow(DatabaseContract.PenggilingColumns.COLUMN_ID))
                val namaPenggiling = getString(getColumnIndexOrThrow(DatabaseContract.PenggilingColumns.COLUMN_NAMA_PENGGILING))
                val alamatPenggiling = getString(getColumnIndexOrThrow(DatabaseContract.PenggilingColumns.COLUMN_ALAMAT_PENGGILING))
                val kontakPenggiling = getString(getColumnIndexOrThrow(DatabaseContract.PenggilingColumns.COLUMN_KONTAK_PENGGILING))
                val timeStamp = getString(getColumnIndexOrThrow(DatabaseContract.PenggilingColumns.COLUMN_TIMESTAMP))
                penggilingList.add(Penggiling(alamatPenggiling, kontakPenggiling, namaPenggiling, penggilingId, timeStamp))
            }
        }
        return penggilingList
    }

    fun mapCursorToArrayListPengepul(pengepulCursor: Cursor?): ArrayList<Pengepul> {
        val pengepulList = ArrayList<Pengepul>()

        pengepulCursor?.apply {
            while (moveToNext()) {
                val pengepulId = getInt(getColumnIndexOrThrow(DatabaseContract.PengepulColumns.COLUMN_ID))
                val namaPengepul = getString(getColumnIndexOrThrow(DatabaseContract.PengepulColumns.COLUMN_NAMA_PENGEPUL))
                val alamatPengepul = getString(getColumnIndexOrThrow(DatabaseContract.PengepulColumns.COLUMN_ALAMAT_PENGEPUL))
                val kontakPengepul = getString(getColumnIndexOrThrow(DatabaseContract.PengepulColumns.COLUMN_KONTAK_PENGEPUL))
                val timeStamp = getString(getColumnIndexOrThrow(DatabaseContract.PengepulColumns.COLUMN_TIMESTAMP))
                pengepulList.add(Pengepul(alamatPengepul, kontakPengepul, namaPengepul, pengepulId, timeStamp))
            }
        }
        return pengepulList
    }

    fun mapCursorToArrayListGudang(gudangCursor: Cursor?): ArrayList<Gudang> {
        val gudangList = ArrayList<Gudang>()

        gudangCursor?.apply {
            while (moveToNext()) {
                val gudangId = getInt(getColumnIndexOrThrow(DatabaseContract.GudangColumns.COLUMN_ID))
                val namaGudang = getString(getColumnIndexOrThrow(DatabaseContract.GudangColumns.COLUMN_NAMA_GUDANG))
                val alamatGudang = getString(getColumnIndexOrThrow(DatabaseContract.GudangColumns.COLUMN_ALAMAT_GUDANG))
                val kontakGudang = getString(getColumnIndexOrThrow(DatabaseContract.GudangColumns.COLUMN_KONTAK_GUDANG))
                val timeStamp = getString(getColumnIndexOrThrow(DatabaseContract.GudangColumns.COLUMN_TIMESTAMP))
                gudangList.add(Gudang(alamatGudang, gudangId, kontakGudang, namaGudang, timeStamp))
            }
        }
        return gudangList
    }

    fun mapCursorToArrayListTengkulak(tengkulakCursor: Cursor?): ArrayList<Tengkulak> {
        val tengkulakList = ArrayList<Tengkulak>()

        tengkulakCursor?.apply {
            while (moveToNext()) {
                val tengkulakId = getInt(getColumnIndexOrThrow(DatabaseContract.TengkulakColumns.COLUMN_ID))
                val namaTengkulak = getString(getColumnIndexOrThrow(DatabaseContract.TengkulakColumns.COLUMN_NAMA_TENGKULAK))
                val alamatTengkulak = getString(getColumnIndexOrThrow(DatabaseContract.TengkulakColumns.COLUMN_ALAMAT_TENGKULAK))
                val kontakTengkulak = getString(getColumnIndexOrThrow(DatabaseContract.TengkulakColumns.COLUMN_KONTAK_TENGKULAK))
                val timeStamp = getString(getColumnIndexOrThrow(DatabaseContract.TengkulakColumns.COLUMN_TIMESTAMP))
                tengkulakList.add(Tengkulak(alamatTengkulak, kontakTengkulak, namaTengkulak, tengkulakId, timeStamp))
            }
        }
        return tengkulakList
    }

    fun mapCursorToArrayListPabrikPengolahan(pabrikCursor: Cursor?): ArrayList<PabrikPengolahan> {
        val pabrikList = ArrayList<PabrikPengolahan>()

        pabrikCursor?.apply {
            while (moveToNext()) {
                val pabrikId = getInt(getColumnIndexOrThrow(DatabaseContract.PabrikPengolahanColumns.COLUMN_ID))
                val namaPabrikPengolahan = getString(getColumnIndexOrThrow(DatabaseContract.PabrikPengolahanColumns.COLUMN_NAMA_PABRIK))
                val alamatPabrikPengolahan = getString(getColumnIndexOrThrow(DatabaseContract.PabrikPengolahanColumns.COLUMN_ALAMAT_PABRIK))
                val kontakPabrikPengolahan = getString(getColumnIndexOrThrow(DatabaseContract.PabrikPengolahanColumns.COLUMN_KONTAK_PABRIK))
                val timeStamp = getString(getColumnIndexOrThrow(DatabaseContract.PabrikPengolahanColumns.COLUMN_TIMESTAMP))
                pabrikList.add(PabrikPengolahan(alamatPabrikPengolahan, kontakPabrikPengolahan, namaPabrikPengolahan, pabrikId, timeStamp))
            }
        }
        return pabrikList
    }

}