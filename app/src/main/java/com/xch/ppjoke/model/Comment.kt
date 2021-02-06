package com.xch.ppjoke.model

data class Comment(
    val author: User,
    val commentCount: Int,
    val commentId: Long,
    val commentText: String,
    val commentType: Int,
    val createTime: Int,
    val hasLiked: Boolean,
    val height: Int,
    val id: Int,
    val imageUrl: String,
    val itemId: Long,
    val likeCount: Int,
    val ugc: Ugc,
    val userId: Int,
    val videoUrl: String,
    val width: Int
) {
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Comment) return false
        return likeCount == other.likeCount && hasLiked == other.hasLiked
                && author == other.author && ugc == other.ugc
    }
}