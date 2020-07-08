package com.example.testpayday.core.base.presentation

import android.content.Context
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavArgs
import com.example.testpayday.core.base.presentation.viewmodel.ErrorMessageEvent
import com.example.testpayday.core.base.presentation.viewmodel.Event
import com.example.testpayday.core.base.presentation.viewmodel.EventsQueue
import com.example.testpayday.core.base.presentation.viewmodel.MessageEvent
import com.example.testpayday.core.library.dialog.ProgressDialog
import com.example.testpayday.core.library.resources.getString
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinTrigger
import org.kodein.di.android.closestKodein
import java.util.*

abstract class BaseFragment(
    @LayoutRes
    protected val layoutId: Int
) : Fragment(layoutId), BaseView, KodeinAware {

    open val navigationArguments: NavArgs? = null
    private val baseActivity by lazy { activity as? BaseView }

    @IdRes
    private val messagesContainer: Int = android.R.id.content
    private val _parentKodein: Kodein by closestKodein()
    override val kodein: Kodein = Kodein.lazy {
        extend(_parentKodein, true)
        import(kodeinModule, true)
    }

    open val kodeinModule: Kodein.Module = Kodein.Module("default") {}

    override val kodeinTrigger = KodeinTrigger()

    override fun onAttach(context: Context) {
        kodeinTrigger.trigger()
        super.onAttach(context)
    }

    protected var showModalProgress: Boolean = false
        set(value) {
            if (value == field) return else field = value
            if (value) {
                showModalProgress()
            } else {
                hideModalProgress()
            }
        }

    protected open fun onEvent(event: Event) {
        when (event) {
            is MessageEvent -> showMessage(
                messageText = getString(event.messageResource),
                containerResId = messagesContainer
            )
            is ErrorMessageEvent -> showError(
                messageText = getString(event.errorMessageResource),
                containerResId = messagesContainer
            )
        }
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
        baseActivity?.showMessage(messageText, actionTitleId, action, containerResId, duration)
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
        baseActivity?.showError(messageText, actionTitleId, action, containerResId, duration)
    }

    private fun showModalProgress() {
        hideModalProgress()
        ProgressDialog.newInstance().showNow(childFragmentManager, ProgressDialog.PROGRESS_DIALOG_TAG)
    }

    private fun hideModalProgress() {
        dismissDialogByTag(ProgressDialog.PROGRESS_DIALOG_TAG)
    }

    private fun Fragment.dismissDialogByTag(tag: String) {
        (childFragmentManager.findFragmentByTag(tag) as? DialogFragment)?.dismissAllowingStateLoss()
    }

    protected fun Fragment.observe(eventsQueue: EventsQueue, eventHandler: (Event) -> Unit) {
        eventsQueue.observe(viewLifecycleOwner, Observer<Queue<Event>> { queue: Queue<Event>? ->
            while (queue != null && queue.isNotEmpty()) {
                eventHandler(queue.remove())
            }
        })
    }
}
