package com.jgeron.quicknotes.view

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.jgeron.quicknotes.R
import com.jgeron.quicknotes.data.Note
import com.jgeron.quicknotes.utils.Constants.CONFIRMATION_TIME
import com.jgeron.quicknotes.utils.Constants.NOTE_ID_KEY
import com.jgeron.quicknotes.utils.Constants.POSITION
import com.jgeron.quicknotes.view.handlers.NotesSharedPreferencesHandler
import com.jgeron.quicknotes.viewmodel.NotesViewModel
import com.jgeron.quicknotes.viewmodel.NotesViewModelFactory
import kotlinx.android.synthetic.main.fragment_delete_note.*

/**
 * A simple [Fragment] subclass.
 * Use the [DeleteNoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DeleteNoteFragment : Fragment() {

    private var noteId: String? = null
    private var position: Int? = null

    private val notesViewModel: NotesViewModel by viewModels(
        factoryProducer = { NotesViewModelFactory(NotesSharedPreferencesHandler.bind(this.requireContext())) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            noteId = it.getString(NOTE_ID_KEY)
            position = it.getInt(POSITION)
        }
        //val viewModelFactory = NotesViewModelFactory(NotesSharedPreferencesHandler)
        //notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_delete_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpDeleteConfirmation()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of DeleteNoteFragment
         * this fragment using the provided parameters.
         *
         * @param position Position of current note on list.
         * @param note Current note.
         * @return A new instance of fragment DeleteNoteFragment.
         */
        @JvmStatic
        fun newInstance(position: Int, note: Note) =
            DeleteNoteFragment().apply {
                arguments = Bundle().apply {
                    putString(NOTE_ID_KEY, note.id)
                    putInt(POSITION, position)
                }
            }
    }

    private fun setUpDeleteConfirmation() {
        deleteNoteFragment_dcv_confirmation.totalTime = CONFIRMATION_TIME
        val ctx = this

        deleteNoteFragment_dcv_confirmation.setOnTimerFinishedListener {
            notesViewModel.removeNote(noteId!!)
            UiNotificationUtilities.displayConfirmation(getString(R.string.deleted), this.requireContext())
            Handler().apply {
                postDelayed({
                    activity
                        ?.supportFragmentManager
                        ?.beginTransaction()
                        ?.remove(ctx)
                        ?.commit()
                }, 1000)
            }
        }

        deleteNoteFragment_dcv_confirmation.setOnClickListener {
            if (deleteNoteFragment_dcv_confirmation.isTimerRunning){
                UiNotificationUtilities.displayConfirmation(getString(R.string.canceled), this.requireContext())
                deleteNoteFragment_dcv_confirmation.stopTimer()
            }
            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.remove(this)
                ?.commit()
        }
        deleteNoteFragment_dcv_confirmation.startTimer()
    }
}