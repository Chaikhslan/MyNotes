package com.example.mynotes.screens.note

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.databinding.FragmentMainBinding
import com.example.mynotes.databinding.FragmentNoteBinding
import com.example.mynotes.models.AppNote
import com.example.mynotes.screens.main.MainAdapter
import com.example.mynotes.screens.main.MainFragmentViewModel
import com.example.mynotes.utilits.APP_ACTIVITY


class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mViewModel: NoteFragmentViewModel
    private lateinit var mCurrentNote:AppNote


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(layoutInflater,container,false)
        mCurrentNote = arguments?.getSerializable("note") as AppNote
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        setHasOptionsMenu(true)
        mBinding.noteText.text = mCurrentNote.text
        mBinding.noteName.text = mCurrentNote.name
        mViewModel = ViewModelProvider(this).get(NoteFragmentViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_action_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btn_delete -> {
                mViewModel.delete(mCurrentNote){
                    APP_ACTIVITY.navController.navigate(R.id.action_noteFragment_to_mainFragment)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}