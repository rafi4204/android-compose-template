package com.monstarlab.arch.extensions

import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.*
import com.google.android.material.snackbar.Snackbar
import com.monstarlab.core.sharedui.errorhandling.ViewError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

fun LifecycleOwner.snackErrorFlow(
    targetFlow: SharedFlow<ViewError>,
    root: View,
    length: Int = Snackbar.LENGTH_SHORT
) {
    collectFlow(targetFlow) { viewError ->
        Snackbar.make(root, viewError.message, length).show()
    }
}

fun LifecycleOwner.visibilityFlow(targetFlow: Flow<Boolean>, vararg view: View) {
    collectFlow(targetFlow) { loading ->
        view.forEach { it.isVisible = loading }
    }
}

/**
 * Launches a new coroutine and repeats `collectBlock` every time the Fragment's viewLifecycleOwner
 * is in and out of `minActiveState` lifecycle state.
 */
inline fun <T> LifecycleOwner.collectFlow(
    targetFlow: Flow<T>,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline collectBlock: (T) -> Unit
) {
    this.lifecycleScope.launchWhenStarted {
        targetFlow.flowWithLifecycle(this@collectFlow.lifecycle, minActiveState)
            .collect {
                collectBlock(it)
            }
    }
}

/**
 * Launches a new coroutine and repeats `block` every time the Fragment's viewLifecycleOwner
 * is in and out of `minActiveState` lifecycle state.
 * ```
 *   repeatWithViewLifecycle {
 *           launch {
 *              // collect
 *           }
 *           launch {
 *              // collect
 *           }
 *       }
 * ```
 *
 */

inline fun LifecycleOwner.repeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    this.lifecycleScope.launch {
        this@repeatWithViewLifecycle.lifecycle.repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}

fun LifecycleOwner.launchAndRepeatWithViewLifecycle(
    vararg blocks: suspend CoroutineScope.() -> Unit,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
) {
    this.lifecycleScope.launch {
        this@launchAndRepeatWithViewLifecycle.lifecycle.repeatOnLifecycle(minActiveState) {
            blocks.map {
                launch {
                    it()
                }
            }.joinAll()
        }
    }
}


fun <T1, T2> LifecycleOwner.combineFlows(
    flow1: Flow<T1>,
    flow2: Flow<T2>,
    collectBlock: ((T1, T2) -> Unit)
) {
    collectFlow(flow1.combine(flow2) { v1, v2 ->
        collectBlock.invoke(v1, v2)
    }) {}
}

fun <T1, T2, T3> LifecycleOwner.combineFlows(
    flow1: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    collectBlock: ((T1, T2, T3) -> Unit)
) {
    collectFlow(combine(flow1, flow2, flow3) { v1, v2, v3 ->
        collectBlock.invoke(v1, v2, v3)
    }) {}
}

fun <T1, T2, T3, T4> LifecycleOwner.combineFlows(
    flow1: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    collectBlock: ((T1, T2, T3, T4) -> Unit)
) {
    collectFlow(combine(flow1, flow2, flow3, flow4) { v1, v2, v3, v4 ->
        collectBlock.invoke(v1, v2, v3, v4)
    }) {}
}

fun <T1, T2> LifecycleOwner.zipFlows(flow1: Flow<T1>, flow2: Flow<T2>, collectBlock: ((T1, T2) -> Unit)) {
    collectFlow(flow1.zip(flow2) { v1, v2 ->
        collectBlock.invoke(v1, v2)
    }) {}
}

@ExperimentalCoroutinesApi
fun View.clicks(throttleTime: Long = 400): Flow<Unit> = callbackFlow {
    this@clicks.setOnClickListener {
        trySend(Unit)
    }
    awaitClose { this@clicks.setOnClickListener(null) }
}.throttleFirst(throttleTime)

fun View.onClick(interval: Long = 400L, listenerBlock: (View) -> Unit) =
    setOnClickListener(DebounceOnClickListener(interval, listenerBlock))
