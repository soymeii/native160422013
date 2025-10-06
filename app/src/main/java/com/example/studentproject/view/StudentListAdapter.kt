package com.example.studentproject.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.studentproject.databinding.StudentListItemBinding
import com.example.studentproject.model.Student

//Prepare Adapter
class StudentListAdapter(val studentList:ArrayList<Student>)
    : RecyclerView.Adapter<StudentListAdapter.StudentViewHolder>()
{
    class StudentViewHolder(var binding: StudentListItemBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int):StudentViewHolder
    {
        val binding = StudentListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

//    override fun getItemCount(): Int {
//        return studentList.size
//    }
    override fun getItemCount() = studentList.size //code satu baris! same thing kayak yang diatas nya

    //FUNCTION : nampilin data
    override fun onBindViewHolder(
        holder: StudentViewHolder,
        position: Int)
    {
        holder.binding.txtID.text = studentList[position].id
        holder.binding.txtName.text = studentList[position].name

        holder.binding.btnDetail.setOnClickListener {
            val id = studentList[position].id.toString() //ambil parameter id keknya (?)
            val action = StudentListFragmentDirections.actionStudentDetail(id) //harus diisi parameternya, karena di navigationnya dia hrs ngirim argument "id"
            it.findNavController().navigate(action)
        }

    }

    //FUNCTION : refresh Student List
    fun updateStudentList(
        newStudentList: ArrayList<Student>)
    {
        studentList.clear()
        studentList.addAll(newStudentList) //masukin yang baru
        notifyDataSetChanged() //recycle view nya nge refresh data yang baru
    }



}
