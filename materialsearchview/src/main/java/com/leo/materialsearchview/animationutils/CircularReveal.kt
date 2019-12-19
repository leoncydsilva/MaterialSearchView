package com.leo.materialsearchview.animationutils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Dialog
import android.graphics.Rect
import android.view.View
import android.view.ViewAnimationUtils
import kotlin.math.max

object CircularReveal {

    fun startReveal(
        toReveal: Boolean,
        dialog: Dialog,
        clickedView: View?,
        animationDuration: Int
    ) {
        val circularRevealAnimator: Animator
        val clickedViewRect = Rect()
        clickedView?.getGlobalVisibleRect(clickedViewRect)

        val rootView = dialog.window!!.decorView

        //calculate all necessary dimension for reveal
        val cx = clickedViewRect.exactCenterX().toInt()
        val cy = (clickedViewRect.exactCenterY() / 4).toInt()
        val width = rootView.measuredWidth
        val height = rootView.measuredHeight
        val maxRadiusForReveal = max(width, height).toFloat()

        if (toReveal) {
            rootView.visibility = View.INVISIBLE
            circularRevealAnimator =
                ViewAnimationUtils.createCircularReveal(rootView, cx, cy, 0F, maxRadiusForReveal)
            rootView.visibility = View.VISIBLE
        } else {
            circularRevealAnimator =
                ViewAnimationUtils.createCircularReveal(rootView, cx, cy, maxRadiusForReveal, 0F)
            circularRevealAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    dialog.dismiss()
                }
            })
        }
        circularRevealAnimator.duration = animationDuration.toLong()
        circularRevealAnimator.start()
    }
}