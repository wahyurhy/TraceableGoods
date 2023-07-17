package com.wahyurhy.traceablegoods.utils

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset
import java.util.*


class PrintBluetooth : AppCompatActivity() {

    // android build in classes for bluetooth operations
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var socket: BluetoothSocket
    private lateinit var mDevice: BluetoothDevice

    private lateinit var outputStream: OutputStream
    private lateinit var inputStream: InputStream
    private lateinit var workerThread: Thread

    private var readBuffer: ByteArray = ByteArray(10)
    private var readBufferPosition = 0


    @Volatile
    private var stopWorker: Boolean = false

    var printId = ""

    fun findBT() {
        println("Printer ID : $printId")
        try {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            if (!bluetoothAdapter.isEnabled) {
                val enableBluetooth = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                startActivityForResult(enableBluetooth, 0)
            }
            val pairedDevices: Set<BluetoothDevice> = bluetoothAdapter.bondedDevices
            if (pairedDevices.isNotEmpty()) {
                for (device in pairedDevices) {
                    if (device.name == printId) {
                        mDevice = device
                        break
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // tries to open a connection to the bluetooth printer device
    @Throws(IOException::class)
    fun openBT() {
        try {
            // Standard SerialPortService ID
            val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            socket = mDevice.createRfcommSocketToServiceRecord(uuid)
            socket.connect()
            outputStream = socket.outputStream
            inputStream = socket.inputStream
            beginListenForData()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun printQrCode(qRBit: Bitmap?) {
        try {
            val printPic1: PrintPic = PrintPic.getInstance()
            printPic1.init(qRBit)
            val bitmapdata2: ByteArray = printPic1.printDraw()
            outputStream.write(bitmapdata2)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun printText(text1: String, text2: String, text3: String) {
        try {
            var text = "---------------------\n"
            text += "Text 1  : $text1\n"
            text += "Text 2  : $text2\n"
            text += "Text 3  : $text3\n"
            println("Hai dari PrintBluetooth punya text: $text")
            outputStream.write(text.toByteArray())
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun printStruk(nama_produk: String, pengiriman: String, Date: String) {
        println("Nama Produk: $nama_produk")
        println("Barang: $pengiriman")
        println("Date: $Date")
        try {
            var text = "---------------------\n"
            text += "Produk : $nama_produk\n"
            text += "Barang : $pengiriman\n"
            text += "Tanggal: $Date\n"
            outputStream.write(text.toByteArray())
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    /*
     * after opening a connection to bluetooth printer device,
     * we have to listen and check if a data were sent to be printed.
     */
    fun beginListenForData() {
        try {
            val handler = Handler()
            // this is the ASCII code for a newline character
            val delimiter: Byte = 10
            stopWorker = false
            readBufferPosition = 0
            readBuffer = ByteArray(1024)
            workerThread = Thread {
                while (!Thread.currentThread().isInterrupted && !stopWorker) {
                    try {
                        val bytesAvailable: Int = inputStream.available()
                        if (bytesAvailable > 0) {
                            val packetBytes = ByteArray(bytesAvailable)
                            inputStream.read(packetBytes)
                            for (i in 0 until bytesAvailable) {
                                val b = packetBytes[i]
                                if (b == delimiter) {
                                    val encodedBytes = ByteArray(readBufferPosition)
                                    System.arraycopy(
                                        readBuffer, 0,
                                        encodedBytes, 0,
                                        encodedBytes.size
                                    )
                                    // specify US-ASCII encoding
                                    val charset: Charset = Charset.forName("US-ASCII")
                                    val data = String(encodedBytes, charset)
                                    readBufferPosition = 0
                                    // tell the user data were sent to bluetooth printer device
                                    handler.post(Runnable {
                                        // myLabel.setText(data);
                                    })
                                } else {
                                    readBuffer[readBufferPosition++] = b
                                }
                            }
                        }
                    } catch (ex: IOException) {
                        stopWorker = true
                    }
                }
            }
            workerThread.start()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    //    this will update data printer name in ModelUser
    // close the connection to bluetooth printer.
    @Throws(IOException::class)
    fun closeBT() {
        try {
            stopWorker = true
            outputStream.close()
            inputStream.close()
            socket.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }


}