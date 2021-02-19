package com.jgeron.quicknotes

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry

object InstrumentedTestUtils {

    fun getResourceId(s: String): Int {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val packageName = targetContext.packageName
        return targetContext.resources.getIdentifier(s, "id", packageName)
    }

}