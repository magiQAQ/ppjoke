package com.xch.ppjoke.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.xch.ppjoke.R

class ListPlayerView: FrameLayout {

    constructor(context: Context): super(context)

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    private val bufferView: View
    private val cover: ImageView
    private val blur: ImageView
    private val playBtn: ImageView

    init {
        inflate(context, R.layout.layout_player_view, this)
        bufferView = findViewById(R.id.buffer_view)
        cover = findViewById(R.id.cover)
        blur = findViewById(R.id.blur_background)
        playBtn = findViewById(R.id.player_btn)
    }

    fun bindData(category: String, widthPx: Int, heightPx: Int, coverUrl: String, videoUrl: String) {

    }
}