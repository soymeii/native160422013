package com.example.studentproject.util

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FileHelper(val context:Context) {
    val folderName = "myfolder"
    val fileName = "mydata.txt"


    // untuk MENGAKSES file nya / ngambil file (getFile)
    fun getFile(): File {
        val dir = File(context.filesDir, folderName)
        //akan nge-create folder kalau blm ada
        if (!dir.exists()) {
            dir.mkdirs() // makes directory
        }
        return File(dir, fileName)
    }

    //untuk NULIS data nya || String apa yang mau ditulis
    fun writeToFile(data: String) {
        try {
            val file = getFile()
            //append: false artinya kalau file nya sudah ada data, dia di replace. Tapi kalau true itu dia nulis dibawahnya untuk data baru nya
            FileOutputStream(file, false).use { output ->
                output.write(data.toByteArray())
            }
        } catch (e: IOException) {
            e.printStackTrace() //untuk nampilin pesan error
        }
    }

    //untuk BACA string dari file
    fun readFromFile(): String {
        //return try artinya kalau ga error return nya itu, kalau error dia return error nya makanya ada toString
        return try {
            val file = getFile()
            // useLines untuk baca satu baris demi baris
            file.bufferedReader().useLines { lines -> lines.joinToString("\n")
            }
        } catch (e: IOException) {
            e.printStackTrace().toString()
        }

    }

    // utk HAPUS file
    fun deleteFile(): Boolean {
        return getFile().delete()
    }

    // untuk MENGHASILKAN STRING PATH menuju file
    fun getFilePath(): String {
        return getFile().absolutePath
    }

}