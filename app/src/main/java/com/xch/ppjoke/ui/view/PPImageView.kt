package com.xch.ppjoke.ui.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.xch.libcommon.dp2px
import com.xch.libcommon.getScreenHeight
import com.xch.libcommon.getScreenWidth
import jp.wasabeef.glide.transformations.BlurTransformation

internal class PPImageView: AppCompatImageView {

    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    companion object{
        @BindingAdapter(value = ["imageUrl","isCircle"], requireAll = true)
        fun setImageUrl(view: PPImageView, imageUrl: String, isCircle: Boolean){
            val builder = Glide.with(view).load(imageUrl)
            if (isCircle) builder.transform(CircleCrop())
            view.layoutParams?.let {
                if (it.width > 0 && it.height > 0) builder.override(it.width, it.height)
            }
            builder.into(view)
        }
    }

    fun bindData(widthPx: Int, heightPx: Int, marginStart: Int, imageUrl: String) {
        bindData(widthPx, heightPx, marginStart, getScreenWidth(), getScreenHeight(), imageUrl)
    }

    private fun bindData(widthPx: Int, heightPx: Int, marginStart: Int, maxWidth: Int, maxHeight: Int, imageUrl: String) {
        if (widthPx <= 0 || heightPx <= 0) {
            Glide.with(this).load(imageUrl).into(object :CustomTarget<Drawable>(){
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    val height = resource.intrinsicHeight
                    val width = resource.intrinsicWidth
                    setSize(width, height, marginStart, maxWidth, maxHeight)

                    setImageDrawable(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
            return
        }

        setSize(widthPx, heightPx, marginStart, maxWidth, maxHeight)
        setImageUrl(this, imageUrl, false)
    }

    private fun setSize(width: Int, height: Int, marginStart: Int, maxWidth: Int, maxHeight: Int) {
        val finalWidth: Int
        val finalHeight: Int
        if (width > height) {
            finalWidth = maxWidth
            finalHeight = (height / (width * 1f / finalWidth)).toInt()
        } else {
            finalHeight = maxHeight
            finalWidth = (width / (height * 1f / finalHeight)).toInt()
        }

        val params = ViewGroup.MarginLayoutParams(finalWidth, finalHeight)
        params.leftMargin = if (height>width) dp2px(marginStart) else 0
        layoutParams = params
    }

    fun setBlurImageUrl(coverUrl: String, radius: Int) {
        Glide.with(this).load(coverUrl).override(50)
            .transform(BlurTransformation())
            .dontAnimate()
            .into(object : CustomTarget<Drawable>(){
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    background = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}