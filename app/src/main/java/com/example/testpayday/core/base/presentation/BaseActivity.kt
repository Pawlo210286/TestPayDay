package com.example.testpayday.core.base.presentation

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.testpayday.R
import com.example.testpayday.core.library.extensions.decorate
import com.example.testpayday.core.library.resources.getColorFromAttr
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinTrigger
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein

abstract class BaseActivity(
    @LayoutRes
    layoutId: Int
) : AppCompatActivity(layoutId), BaseView, KodeinAware {

    private val _parentKodein by closestKodein()
    override val kodein: Kodein by retainedKodein {
        extend(_parentKodein)
        import(diModule(), allowOverride = true)
    }
    override val kodeinTrigger = KodeinTrigger()

    private val mainView by lazy { window.decorView }

    abstract fun diModule(): Kodein.Module

    override fun onCreate(savedInstanceState: Bundle?) {
        kodeinTrigger.trigger()
        super.onCreate(savedInstanceState)
    }

    override fun showMessage(
        messageTextId: Int,
        actionTitleId: Int?,
        action: ((View) -> Unit)?,
        containerResId: Int,
        duration: Int
    ) {
        val messageText = getString(messageTextId)
        showMessage(messageText, actionTitleId, action, containerResId, duration)
    }

    override fun showMessage(
        messageText: String,
        actionTitleId: Int?,
        action: ((View) -> Unit)?,
        containerResId: Int,
        duration: Int
    ) {
        val snack = createSnack(
            mainView = mainView,
            messageText = messageText,
            backgroundColor = android.R.color.black,
            textColor = android.R.color.white,
            containerResId = containerResId,
            duration = duration
        )

        actionTitleId?.let {
            snack?.setAction(it, action)
        }

        snack?.show()
    }

    override fun showError(
        messageTextId: Int,
        actionTitleId: Int?,
        action: ((View) -> Unit)?,
        containerResId: Int,
        duration: Int,
        onDismiss: (() -> Unit)?
    ) {
        val messageText = getString(messageTextId)
        showError(messageText, actionTitleId, action, containerResId, duration)
    }

    override fun showError(
        messageText: String,
        actionTitleId: Int?,
        action: ((View) -> Unit)?,
        containerResId: Int,
        duration: Int,
        onDismiss: (() -> Unit)?
    ) {
        val snack = createSnack(
            mainView = mainView,
            messageText = messageText,
            backgroundColor = getColorFromAttr(R.attr.colorError),
            textColor = getColorFromAttr(R.attr.colorOnError),
            containerResId = containerResId,
            duration = duration
        )

        actionTitleId?.let {
            snack?.setAction(it, action)
        }

        snack?.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                onDismiss?.invoke()
            }
        })

        snack?.show()
    }

    override fun DialogFragment.show(tag: String) {
        (supportFragmentManager.findFragmentByTag(tag) as? DialogFragment)?.dismissAllowingStateLoss()
        show(supportFragmentManager, tag)
    }

    @Suppress("LongParameterList")
    private fun createSnack(
        mainView: View,
        messageText: String,
        @ColorRes backgroundColor: Int,
        @ColorRes textColor: Int,
        containerResId: Int,
        duration: Int
    ): Snackbar? {
        val viewGroup = mainView.findViewById(containerResId) as ViewGroup?

        return viewGroup?.let {
            Snackbar
                .make(viewGroup, messageText, duration)
                .decorate(backgroundColor, textColor)
        }
    }
}
