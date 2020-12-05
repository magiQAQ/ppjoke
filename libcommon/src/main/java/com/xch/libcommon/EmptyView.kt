package com.xch.libcommon

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import kotlinx.android.synthetic.main.layout_empty_view.view.*

class EmptyView : LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init{
        inflate(context, R.layout.layout_empty_view, this)
        orientation = VERTICAL
        gravity = Gravity.CENTER
    }

    fun setEmptyIcon(@DrawableRes iconRes: Int){
        empty_icon.setImageResource(iconRes)
    }

    fun setTitle(text: String?) {
        if (text.isNullOrEmpty()) {
            empty_text.visibility = GONE
        } else {
            empty_text.text = text
            empty_text.visibility = VISIBLE
        }
    }

    fun setButton(text: String?, listener: (view: View)->Unit) {
        if (text.isNullOrEmpty()) {
            empty_action.visibility = GONE
        } else {
            empty_action.text = text
            empty_action.visibility = VISIBLE
            empty_action.setOnClickListener(listener)
        }
    }
}