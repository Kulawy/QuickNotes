package com.jgeron.quicknotes.view

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.result.ActivityResultLauncher
import androidx.wear.activity.ConfirmationActivity

object UiNotificationUtilities {

    fun displayConfirmation(message: String, context: Context){
        val intent = Intent(context, ConfirmationActivity::class.java).apply {
            putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.SUCCESS_ANIMATION)
            putExtra(ConfirmationActivity.EXTRA_MESSAGE, message)
        }
        context.startActivity(intent)
    }

    fun displaySpeechScreen(activity: Activity){
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_ORIGIN, "What is the title?")
        }
        activity.startActivityForResult(intent, 1001)
    }

    fun displaySpeechScreen(resultLauncher: ActivityResultLauncher<Intent>) {
        //val intent = Intent(this, SomeActivity::class.java)
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_ORIGIN, "What is the title?")
        }
        resultLauncher.launch(intent)
    }


}