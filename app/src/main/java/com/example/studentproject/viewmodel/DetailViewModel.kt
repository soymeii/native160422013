package com.example.studentproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.studentproject.model.Student
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

//step 1. ganti ke android view model
class DetailViewModel(app:Application):AndroidViewModel(app) {
    val studentLD = MutableLiveData<Student>() // cuman satu data, bukan semua data

    //step 2. prepartion
    val TAG = "volleytag"
    private var queue: RequestQueue? = null

    fun fetch(id:String){
//        val data =arrayListOf(
//            Student("16055","Nonie","1998/03/28","5718444778","http://dummyimage.com/75x100"
//                    + ".jpg/cc0000/ffffff"),
//            Student("13312","Rich","1994/12/14","3925444073","http://dummyimage.com/75x100" +
//                    ".jpg/5fa2dd/ffffff"),
//            Student("11204","Dinny","1994/10/07","6827808747",
//                "http://dummyimage.com/75x100.jpg/5fa2dd/ffffff1")
//        )

        //step 3. all
        queue = Volley.newRequestQueue(getApplication()) //pasang disini *
        val url = "https://www.jsonkeeper.com/b/LLMW"  //link nambah data dri Bapaknya
        val stringRequest = StringRequest(
            //Request di import yang ada VOLLEY nya !!!!!!!!
            Request.Method.GET,
            url,
            {
                //sukses
                // it --> mengconvert itu jadi json
                val sType = object : TypeToken<List<Student>>() { }.type
                val result = Gson().fromJson<List<Student>>(it, sType)
                val arrStudent = result as ArrayList<Student>
                studentLD.value = arrStudent.find{it.id == id} as Student//akan nge looting data satu persatu dan check spya id nya sama kayak yg kita cari, trus hasilnya langsung disimpan di studentLD

            },
            {//failed
            }
        )
        stringRequest.tag = TAG
        //d. mendaftarkan queue
        queue?.add(stringRequest)
    }
}

//perbedaan view model ini sm yang lain adalah kalau ini single live data