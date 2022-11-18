package com.monstarlab.arch.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import java.io.Serializable

inline fun <reified VM : ViewModel> ViewModelStoreOwner.getViewModel(factory: ViewModelProvider.Factory): VM {
    return ViewModelProvider(this, factory).get(VM::class.java)
}

inline fun <reified VM : ViewModel> Fragment.getSharedViewModel(factory: ViewModelProvider.Factory): VM {
    return ViewModelProvider(requireActivity(), factory).get(VM::class.java)
}

private object UninitializedValue

/**
 * This was copied from SynchronizedLazyImpl but modified to automatically initialize in ON_CREATE.
 */
@Suppress("ClassName")
class lifecycleAwareLazy<out T>(private val owner: LifecycleOwner, initializer: () -> T) :
    Lazy<T>,
    Serializable {
    private var initializer: (() -> T)? = initializer
    @Volatile
    private var _value: Any? = UninitializedValue
    // final field is required to enable safe publication of constructed instance
    private val lock = this

    init {
        owner.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            fun onStart() {
                if (!isInitialized()) value
                owner.lifecycle.removeObserver(this)
            }
        })
    }

    @Suppress("LocalVariableName")
    override val value: T
        get() {
            val _v1 = _value
            if (_v1 !== UninitializedValue) {
                @Suppress("UNCHECKED_CAST")
                return _v1 as T
            }

            return synchronized(lock) {
                val _v2 = _value
                if (_v2 !== UninitializedValue) {
                    @Suppress("UNCHECKED_CAST") (_v2 as T)
                } else {
                    val typedValue = initializer!!()
                    _value = typedValue
                    initializer = null
                    typedValue
                }
            }
        }

    override fun isInitialized(): Boolean = _value !== UninitializedValue

    override fun toString(): String =
        if (isInitialized()) value.toString() else "Lazy value not initialized yet."
}
