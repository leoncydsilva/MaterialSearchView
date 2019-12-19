package com.leo.materialsearchview

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.leo.materialsearchview.animationutils.CircularReveal
import kotlinx.android.synthetic.main.search_view.*
import kotlinx.android.synthetic.main.search_view.view.*


@Suppress("MemberVisibilityCanBePrivate")
class MaterialSearchView(private val context: Context) : LifecycleObserver {

    var animationDuration: Int = 250
    var showKeyBoardDefault: Boolean = true
    var searchHint = context.getString(android.R.string.search_go)
    var backButtonDrawable: Drawable? = context.getDrawable(R.drawable.ic_arrow_back)
    var clearSearchOnDismiss: Boolean = true
    private var backButtonColorInt: Int = 0

    private lateinit var onQueryTextListener: OnQueryTextListener
    private lateinit var dialog: AlertDialog
    private var clickedView: View? = null

    init {
        if (context is AppCompatActivity) {
            context.lifecycle.addObserver(this)
        }
    }

    fun setBackButtonTint(@ColorRes backButtonColor: Int) {
        backButtonColorInt = backButtonColor
    }

    fun show(view: View) {
        if (!::dialog.isInitialized || !dialog.isShowing) {
            val dialogView = View.inflate(context, R.layout.search_view, null)
            val dialogBuilder = AlertDialog.Builder(
                context,
                R.style.SearchDialog
            )
            clickedView = view
            dialogBuilder.setView(dialogView)
                .setCancelable(false)
            dialog = dialogBuilder.create()
            dialog.initDialog(dialogView)
            dialog.show()
        }
    }

    fun setOnQueryTextListener(onQueryTextListener: OnQueryTextListener) {
        this@MaterialSearchView.onQueryTextListener = onQueryTextListener
    }

    fun dismiss() {
        if (::dialog.isInitialized && dialog.isShowing && dialog.window?.decorView?.isAttachedToWindow!!)
            dialog.circularHide()
    }


    //Helper Methods
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun dismissOnDestroy() {
        if (::dialog.isInitialized && dialog.isShowing && dialog.window?.decorView?.isAttachedToWindow!!)
            dialog.dismiss()
    }

    private fun AlertDialog.initDialog(view: View) {
        setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                this@MaterialSearchView.dismiss()
            }; false
        }

        //Initialization of Dialog Views
        view.backButton.setOnClickListener { this@MaterialSearchView.dismiss() }
        view.searchView.queryHint = searchHint
        if (::onQueryTextListener.isInitialized)
            view.searchView.setOnQueryTextListener(onQueryTextListener)
        view.backButton.setImageDrawable(backButtonDrawable)
        if (backButtonColorInt != 0)
            view.backButton.setColorFilter(ContextCompat.getColor(context, backButtonColorInt))
        if (showKeyBoardDefault)
            view.searchView.requestFocusFromTouch()

        circularShow()
        setWindowAttributes()
    }

    private fun AlertDialog.setWindowAttributes() {
        val params = window?.attributes
        params?.gravity = Gravity.TOP
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.attributes = params
        window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        )
        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    private fun AlertDialog.circularShow() {
        setOnShowListener {
            CircularReveal.startReveal(
                true,
                this,
                clickedView,
                animationDuration
            )
        }
    }

    private fun AlertDialog.circularHide() {
        if (clearSearchOnDismiss && searchView != null)
            searchView.setQuery("", true)
        CircularReveal.startReveal(
            false,
            this,
            clickedView,
            animationDuration
        )
    }
}