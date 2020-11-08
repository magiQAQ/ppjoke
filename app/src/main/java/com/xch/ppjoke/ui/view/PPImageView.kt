package com.xch.ppjoke.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

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
}