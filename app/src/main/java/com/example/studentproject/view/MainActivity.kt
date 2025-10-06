package com.example.studentproject.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studentproject.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

//1. add enable view binding, add safeargs (gradle module dan project), add swipeRefreshLayout (gradle project-dependencies)
//2. make folder [ model, view, view model ] --> moving mainActivity inside the "view" package
//3. implement swipe refresh layout --> Inside the fragment_student_list.xml, delete the default “textview” and add “constraint layout”

//REFRESH FUNCTION
//1. studentListFragment -> siapin binding dll
//dst

//VOlley
//a-d ada di LIST VIEW MODEL
//atau
//step 1-3 ada di DetailViewModel

//SERIALIZE NAME --> di class Student di tambahin serealize



