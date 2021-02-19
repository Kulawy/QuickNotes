package com.jgeron.quicknotes.view

import android.app.Activity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import androidx.wear.widget.WearableLinearLayoutManager
import com.jgeron.quicknotes.R
import com.jgeron.quicknotes.data.Note
import com.jgeron.quicknotes.view.handlers.CustomScrollingLayoutCallback
import com.jgeron.quicknotes.view.handlers.NotesSharedPreferencesHandler
import com.jgeron.quicknotes.view.adapters.NotesRecyclerViewAdapter
import com.jgeron.quicknotes.viewmodel.NotesViewModel
import com.jgeron.quicknotes.viewmodel.NotesViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity() {

    private val TAG = "MainActivity"

    //private lateinit var notesViewModel: NotesViewModel
    private val notesViewModel: NotesViewModel by viewModels(
        factoryProducer = { NotesViewModelFactory(NotesSharedPreferencesHandler.bind(this)) }
    )
    private lateinit var notesAdapter: NotesRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val viewModelFactory = NotesViewModelFactory(NotesSharedPreferencesHandler)
        //notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        setUpListeners()
        setUpNotesRecyclerView()
        setUpLiveData()
    }

    override fun onResume() {
        super.onResume()
        notesViewModel.updateNotesListFromStore()
    }

    private fun setUpListeners() {
        main_bt_addNote.setOnClickListener {
            //UiNotificationUtilities.displaySpeechScreen(this)
            UiNotificationUtilities.displaySpeechScreen(resultLauncher)
        }

        main_tv_title.setOnClickListener {
            Log.d(TAG, "Title clicked -> should get list from Memory")
            notesViewModel.updateNotesListFromStore()
        }
    }

    private fun setUpNotesRecyclerView(){
        notesAdapter = NotesRecyclerViewAdapter(notesViewModel.notesList, clickNote)
        main_rv_notes.apply {
            isEdgeItemsCenteringEnabled = true
            isCircularScrollingGestureEnabled = true
            bezelFraction = 0.5f
            scrollDegreesPerScreen = 90f
            setHasFixedSize(true)
            adapter = notesAdapter
            layoutManager = WearableLinearLayoutManager(this.context, CustomScrollingLayoutCallback())
        }
    }

    private fun setUpLiveData(){
        notesViewModel.notesLiveData.observe(this, Observer {
            notesAdapter.submitList(it)
        })

        notesViewModel.noteLiveData.observe(this, Observer {
            notesAdapter.submitList(notesViewModel.notesList)
        })
    }

    private val clickNote: (position: Int, note: Note) -> Unit = { position, note ->
        Log.d(TAG, "clickNote: ${note.id}")
        val deleteFragment = DeleteNoteFragment.newInstance(position, note)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fl_container, deleteFragment)
            .addToBackStack(null)
            .commit()
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null){
                val msg = result.data!!.extras?.getStringArrayList(RecognizerIntent.EXTRA_RESULTS)
                val message = msg?.joinToString()
                notesViewModel.addNote(message?:getString(R.string.error_decoding)
                )
                UiNotificationUtilities.displayConfirmation(
                    getString(R.string.note_saved_confirmation),
                    this
                )
            }
        }

    /*
    //OLD_WAY
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1001 && resultCode == RESULT_OK && data != null) {
            val msg = data.extras?.getStringArrayList(RecognizerIntent.EXTRA_RESULTS)
            //val results = data.getStringArrayExtra(RecognizerIntent.EXTRA_RESULTS)
            val message = msg?.joinToString()
            notesViewModel.addNote(message?:"BAD TRANSLATION ON ADDING", this)
            UiNotificationUtilities.displayConfirmation("Note saved!", this)
        }
    }
    */

}