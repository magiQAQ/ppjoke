package com.xch.ppjoke.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.xch.libcommon.getScreenWidth
import com.xch.ppjoke.R

class ListPlayerView: FrameLayout {

    constructor(context: Context): super(context)

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    private val bufferView: View
    private val cover: PPImageView
    private val blur: PPImageView
    private val playBtn: ImageView
    private var mCategory: String ? = null
    private var mVideoUrl: String ? = null

    init {
        inflate(context, R.layout.layout_player_view, this)
        bufferView = findViewById(R.id.buffer_view)
        cover = findViewById(R.id.cover)
        blur = findViewById(R.id.blur_background)
        playBtn = findViewById(R.id.player_btn)
    }

    fun bindData(category: String, widthPx: Int, heightPx: Int, coverUrl: String, videoUrl: String) {
        mCategory = category
        mVideoUrl = videoUrl

        PPImageView.setImageUrl(cover, coverUrl, false)
        if (widthPx < heightPx) {
            blur.setBlurImageUrl(coverUrl, 10)
            blur.visibility = VISIBLE
        } else {
            blur.visibility = INVISIBLE
        }
        setSize(widthPx, heightPx)
    }

    protected fun setSize(widthPx: Int, heightPx: Int) {
        val maxWidth = getScreenWidth()
        val maxHeight = maxWidth

        val layoutWidth = maxWidth
        val layoutHeight: Int

        val coverWidth: Int
        val coverHeight: Int
        if (widthPx > heightPx) {
            coverWidth = maxWidth
            (heightPx / (widthPx * 1f / maxWidth)).toInt().let {
                layoutHeight = it
                coverHeight = it
            }
        } else {
            layoutHeight = maxHeight
            coverHeight = maxHeight
            coverWidth = (widthPx / (heightPx * 1f / maxHeight)).toInt()
        }
        val params = layoutParams
        params.width = layoutWidth
        params.height = layoutHeight
        layoutParams = params

        val blurParams = blur.layoutParams
        blurParams.width = layoutWidth
        blurParams.height = layoutHeight
        blur.layoutParams = blurParams

        val coverParams = cover.layoutParams as LayoutParams
        coverParams.width = coverWidth
        coverParams.height = coverHeight
        cover.layoutParams = coverParams
    }
}