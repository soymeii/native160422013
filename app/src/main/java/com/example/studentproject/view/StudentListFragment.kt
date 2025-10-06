package com.example.studentproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentproject.R
import com.example.studentproject.databinding.FragmentStudentListBinding
import com.example.studentproject.viewmodel.ListViewModel


class StudentListFragment : Fragment() {
    //prepartion : hapus all dan sisain cmn onCreateView

    //step 1.
    private lateinit var binding: FragmentStudentListBinding
    private lateinit var viewModel: ListViewModel //declare view model nya
    private val studentListAdapter  = StudentListAdapter(arrayListOf()) //karena pakai recycle view, maka perlu datanya (arraylistnya). Jadi bikin array kosongan beserta adapternya
    //kenapa ga var? gapapa jadi var, tp kalau mau update isi harus ada adapter baru. Nah karena di adapternya uda ada function update di adapternya
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //step 2.
        binding = FragmentStudentListBinding.inflate(inflater,container, false)
        return binding.root

    }

    //step 3. making onViewCreatedFunction
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //step 4. init the vm
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh() //load or fetch data

        //testing file
//        viewModel.testSaveFile()

        //set 5. set-up recycle view
        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = studentListAdapter //masang adapternya

        // swipe refresh --> buat kalau nge swipe kebawah lepas, buat nge download ulang isi studentnya
        binding.refreshLayout.setOnRefreshListener {
            binding.recView.visibility = View.GONE
            binding.txtError.visibility = View.GONE
            binding.progressLoad.visibility = View.VISIBLE

            viewModel.refresh()

            binding.refreshLayout.isRefreshing = false //supaya loadingnya ga melulu muncul


        }


        //"subscribe"
        observeViewModel()

    }

    //step 6. bikin function buat subscribe
    fun observeViewModel(){
        //ada 3 variable yang mau di subs

        //observe - live data - arrayList student
        viewModel.studentsLD.observe(viewLifecycleOwner, Observer {
            studentListAdapter.updateStudentList(it)
        })

        //observe - live data - loadingLD
        //This observe “loading error” livedata. For now its just use dummy data
        viewModel.studentLoadErrorLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                //still loading
                binding.txtError?.visibility = View.VISIBLE
                binding.recView.visibility =View.INVISIBLE
            } else {
                //sudah ga loading
                binding.txtError?.visibility = View.GONE
                binding.recView.visibility = View.VISIBLE
            }
        })

        //observe - live data - errorLD
        //This observe “loading” livedata. For now its just use dummy data
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                binding.txtError.text = "Something went wrong! when load student data"
                binding.txtError.visibility = View.VISIBLE
                binding.progressLoad.visibility = View.VISIBLE
                binding.recView.visibility = View.GONE
            } else {
                binding.txtError.visibility = View.INVISIBLE
                binding.recView.visibility = View.VISIBLE
                binding.progressLoad.visibility = View.GONE //menyembunyikan recycle view
            }
        })


    }

}


//OnCreateView = untuk nge load layout
//OnViewCreated = dipanggil setelah selesai nge set layout, tmpt buat otak atik UI

//kalau di ACTIVITY mau ambil context tinggal pakai "this"
//kalau di FRAGMENT pakai "this.context"