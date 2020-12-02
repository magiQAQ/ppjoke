package com.xch.libcommon

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class EmptyView : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    
}