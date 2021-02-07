package com.xch.ppjoke.model

data class Feed(
    val activityIcon: String,
    val activityText: String,
    val authorId: Long,
    val cover: String,
    val createTime: Int,
    val duration: Double,
    val feeds_text: String,
    val height: Int,
    val id: Int,
    val itemId: Long,
    val itemType: Int,
    val url: String,
    val width: Int,
    val author: User,
    val topComment: Comment,
    val ugc: Ugc
) {

    companion object{
        const val TYPE_IMAGE = 1
        const val TYPE_VIDEO = 2
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Feed) return false
        return id == other.id && itemId == other.itemId && itemType == other.itemType
                && createTime == other.createTime && duration == other.duration
                && feeds_text == other.feeds_text && authorId == other.authorId
                && activityIcon == other.activityIcon && activityText == other.activityText
                && width == other.width && height == other.height && url == other.url
                && cover == other.cover && author == other.author && topComment == other.topComment
                && ugc == other.ugc
    }
}