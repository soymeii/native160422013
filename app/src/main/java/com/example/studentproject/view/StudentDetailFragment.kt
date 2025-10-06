package com.example.studentproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.studentproject.R
import com.example.studentproject.databinding.FragmentStudentDetailBinding
import com.example.studentproject.databinding.FragmentStudentListBinding
import com.example.studentproject.model.Student
import com.example.studentproject.viewmodel.DetailViewModel


class StudentDetailFragment : Fragment() {

    private lateinit var binding: FragmentStudentDetailBinding
    private lateinit var viewmodel: DetailViewModel
    private lateinit var student : Student

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //catch argument
        val id = StudentDetailFragmentArgs.fromBundle(requireArguments()).id

        viewmodel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewmodel.fetch(id)

        observeViewModel()
    }

    fun observeViewModel(){
        viewmodel.studentLD.observe(viewLifecycleOwner, Observer {
            student = it

            //update UI
            binding.txtID.setText(student.id)
            binding.txtName.setText(student.name)
            binding.txtBod.setText(student.bod)
            binding.txtPhone.setText(student.phone)
        })
    }


}