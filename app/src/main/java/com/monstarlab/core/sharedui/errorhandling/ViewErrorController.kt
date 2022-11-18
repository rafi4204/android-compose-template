package com.monstarlab.core.sharedui.errorhandling

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.monstarlab.core.domain.error.ErrorModel
import javax.inject.Inject

fun Fragment.showErrorDialog(
    error: ViewError,
    cancelable: Boolean = true,
    dismissAction: (() -> Unit)? = null
) {
    val builder = AlertDialog.Builder(requireContext())
    builder.setTitle(error.title)
    builder.setMessage(error.message)
    builder.setPositiveButton("Ok") { _, _ ->
        ViewErrorController.isShowingError = false
    }
    builder.setOnDismissListener {
        ViewErrorController.isShowingError = false
        dismissAction?.invoke()
    }
    if (!ViewErrorController.isShowingError) {
        ViewErrorController.isShowingError = true
        val dialog = builder.show()
        dialog.setCancelable(cancelable)
        dialog.setCanceledOnTouchOutside(cancelable)
    }
}

fun Fragment.showErrorSnackbar(
    view: View,
    error: ViewError,
    showAction: Boolean = false,
    dismissAction: (() -> Unit)? = null,
) {
    val showLength = if (showAction) Snackbar.LENGTH_INDEFINITE else Snackbar.LENGTH_LONG
    val snackbar = Snackbar.make(view, error.message, showLength)
    if (showAction) {
        snackbar.setAction("Ok") {
            ViewErrorController.isShowingError = false
            dismissAction?.invoke()
        }
    }
    if (!ViewErrorController.isShowingError) {
        ViewErrorController.isShowingError = true
        snackbar.show()
    }
}

@Suppress("LongMethod")
fun ErrorModel.mapToViewError(): ViewError {
    return when (this) {
        is ErrorModel.Http.Forbidden,
        is ErrorModel.Http.Unauthorized -> {
            ViewError(
                title = "Translation.error.errorTitle",
                message = "Translation.error.authenticationError",
            )
        }
        is ErrorModel.Http.ServerError -> {
            ViewError(
                title = "Translation.error.errorTitle",
                message = "Translation.error.unknownError",
            )
        }
        is ErrorModel.Http -> {
            ViewError(
                title = "Translation.error.errorTitle",
                message = "Translation.error.unknownError",
            )
        }
        is ErrorModel.Connection -> {
            ViewError(
                title = "Translation.error.errorTitle",
                message = "Translation.error.unknownError",
            )
        }
        else -> {
            ViewError(
                title = "Translation.error.errorTitle",
                message = "Translation.error.unknownError",
            )
        }
    }
}

class ViewErrorController @Inject constructor() {
    companion object {
        var isShowingError = false
    }
}
