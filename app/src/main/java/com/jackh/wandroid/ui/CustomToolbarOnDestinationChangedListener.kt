package com.jackh.wandroid.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.FragmentNavigator
import com.jackh.wandroid.widget.CustomNavToolbar
import java.lang.ref.WeakReference
import java.util.regex.Pattern

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/12 15:15
 * Description:
 */

class CustomToolbarOnDestinationChangedListener(

    private val toolbarWeakReference: WeakReference<CustomNavToolbar>,

    private val config: CustomAppBarConfiguration

) : NavController.OnDestinationChangedListener {

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        val toolbar = toolbarWeakReference.get()
        if (toolbar == null) {
            controller.removeOnDestinationChangedListener(this)
            return
        }
        val label = destination.label
        label?.run {
            // Fill in the data pattern with the args to build a valid URI
            val title = StringBuffer()
            val fillInPattern = Pattern.compile("\\{(.+?)\\}")
            val matcher = fillInPattern.matcher(label)
            while (matcher.find()) {
                val argName = matcher.group(1)
                if (arguments != null && arguments.containsKey(argName)) {
                    matcher.appendReplacement(title, "")

                    title.append(arguments.get(argName)!!.toString())
                } else {
                    throw IllegalArgumentException(
                        "Could not find " + argName + " in "
                                + arguments + " to fill label " + label
                    )
                }
            }
            matcher.appendTail(title)
            toolbar.setTitle(title)
        }

        if (destination is FragmentNavigator.Destination) {
            toolbar.visibility = if (contains(destination.className)) View.GONE else View.VISIBLE
        }
    }

    private fun contains(targetName: String): Boolean {
        var isContain = false
        for (content in config.hideAppBarList) {
            if (targetName == content) {
                isContain = true
                break
            }
        }
        return isContain
    }

}

data class CustomAppBarConfiguration(val hideAppBarList: List<String>)