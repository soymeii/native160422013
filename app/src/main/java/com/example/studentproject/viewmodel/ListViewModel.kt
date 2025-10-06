package com.example.studentproject.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyLog
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.studentproject.model.Student
import com.example.studentproject.util.FileHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.math.log

//b. ganti dari
//class ListViewModel: ViewModel() {
//jadi seperti dibawah *
class ListViewModel(app: Application): AndroidViewModel(app){
    val studentsLD = MutableLiveData<ArrayList<Student>>()
    val studentLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    //a.
    val TAG = "volleytag"
    private var queue: RequestQueue? = null

    //connect ke sumber data
    fun refresh() {
        loadingLD.value = true //progress bar start muncul
        studentLoadErrorLD.value = false //tidak muncul

//        //data dummy
//        studentsLD.value = arrayListOf(
//            Student("16055","Nonie","1998/03/28","5718444778","http://dummyimage.com/75x100"
//                    + ".jpg/cc0000/ffffff"),
//        Student("13312","Rich","1994/12/14","3925444073","http://dummyimage.com/75x100" +
//                ".jpg/5fa2dd/ffffff"),
//        Student("11204","Dinny","1994/10/07","6827808747",
//            "http://dummyimage.com/75x100.jpg/5fa2dd/ffffff1")
//        )

        //c. volley ke API
        queue = Volley.newRequestQueue(getApplication()) //pasang disini *
        val url = "https://www.jsonkeeper.com/b/LLMW"  //link nambah data dri Bapaknya
        val stringRequest = StringRequest(
            //Request di import yang ada VOLLEY nya !!!!!!!!
            Request.Method.GET,
            url,
            {
                //SUKSES

                // it --> mengconvert itu jadi json
                val sType = object : TypeToken<List<Student>>() { }.type
                val result = Gson().fromJson<List<Student>>(it, sType)
                studentsLD.value = result as ArrayList<Student> //disimpan ke arrayList
                loadingLD.value = false
//                Log.d("error", it)


                //array list nya > convert to json > nyimpen juga ke file
                val filehelper = FileHelper(getApplication())
                val jsonstring = Gson().toJson(result)
                filehelper.writeToFile(jsonstring)
                Log.d("print_file", jsonstring)

                //baca json string dari file
                val hasil = filehelper.readFromFile()
                val listStudents = Gson().fromJson<List<Student>>(hasil, sType)
                Log.d("print_file", listStudents.toString())

            },
            {//failed
                studentLoadErrorLD.value = true
            }
        )
        stringRequest.tag = TAG
        //d. mendaftarkan queue
        queue?.add(stringRequest)

//        //matikan loading bar nya
//        loadingLD.value = false
//        studentLoadErrorLD.value = false
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }

    fun testSaveFile() {
        val fileHelper = FileHelper(getApplication())
        fileHelper.writeToFile("Hello, world!")
        val content = fileHelper.readFromFile()
        Log.d("print_file", content)
        Log.d("print_file", fileHelper.getFilePath()) //demonstration untuk show path nya
    }


}