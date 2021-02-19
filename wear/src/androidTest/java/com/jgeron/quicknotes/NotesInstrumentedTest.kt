package com.jgeron.quicknotes

import android.view.InputDevice
import android.view.MotionEvent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.CoordinatesProvider
import androidx.test.espresso.action.GeneralClickAction
import androidx.test.espresso.action.Press
import androidx.test.espresso.action.Tap
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.jgeron.quicknotes.view.MainActivity
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class NotesInstrumentedTest {


    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
        = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.jgeron.quicknotes", appContext.packageName)
    }

    @Test
    fun addNoteTest(){
        //given

        onView(withId(R.id.main_bt_addNote))
            .perform(click())

        //when
//        intending(hasAction(equalTo(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)))
//            .respondWith(
//                Instrumentation.ActivityResult(
//                    Activity.RESULT_OK, Intent().putExtra(
//                        SpeechRecognizer.RESULTS_RECOGNITION, arrayOf("I am test.")
//                    )
//                )
//            )

        onView(clickIn(0, 0).constraints)

        //then
        onView(withId(R.id.noteItem_tv_title))
            .check(matches(withText("I am test.")))
    }

    private fun clickIn(x: Int, y: Int): ViewAction {
        return GeneralClickAction(
            Tap.SINGLE,
            CoordinatesProvider { view ->
                val screenPos = IntArray(2)
                view?.getLocationOnScreen(screenPos)

                val screenX = (screenPos[0] + x).toFloat()
                val screenY = (screenPos[1] + y).toFloat()

                floatArrayOf(screenX, screenY)
            },
            Press.FINGER,
            InputDevice.SOURCE_MOUSE,
            MotionEvent.BUTTON_PRIMARY
        )
    }

}