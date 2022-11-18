package com.monstarlab.arch.extensions

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ActivityViewBindingDelegate<T : ViewBinding>(
    private val activity: AppCompatActivity,
    private val viewBinder: (LayoutInflater) -> T,
    private val beforeSetContent: () -> Unit = {}
) : ReadOnlyProperty<AppCompatActivity, T>, DefaultLifecycleObserver {

    private var activityBinding: T? = null

    init {
        activity.lifecycle.addObserver(this)
    }

    override fun onCreate(owner: LifecycleOwner) {
        initialize()
        beforeSetContent()
        activity.setContentView(activityBinding?.root)
        activity.lifecycle.removeObserver(this)
        super.onCreate(owner)
    }

    private fun initialize() {
        if (activityBinding == null) {
            activityBinding = viewBinder(activity.layoutInflater)
        }
    }

    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
        ensureMainThread()

        initialize()
        return activityBinding!!
    }
}
