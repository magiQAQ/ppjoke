package com.xch.ppjoke.model

data class Ugc(
    val commentCount: Int,
    val hasFavorite: Boolean,
    val hasLiked: Boolean,
    val hasdiss: Boolean,
    val likeCount: Int,
    val shareCount: Int
) {
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Ugc) return false
        return likeCount == other.likeCount && shareCount == other.shareCount
                && commentCount == other.commentCount && hasFavorite == other.hasFavorite
                && hasLiked == other.hasLiked && hasdiss == other.hasdiss
    }
}