package com.xch.ppjoke.model

import java.io.Serializable

data class User(
    val avatar: String,
    val commentCount: Int,
    val description: String,
    val expires_time: Int,
    val favoriteCount: Int,
    val feedCount: Int,
    val followCount: Int,
    val followerCount: Int,
    val hasFollow: Boolean,
    val historyCount: Int,
    val id: Int,
    val likeCount: Int,
    val name: String,
    val qqOpenId: Any,
    val score: Int,
    val topCommentCount: Int,
    val userId: Long
): Serializable {
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is User) return false
        return name == other.name && avatar == other.avatar && description == other.description
                && likeCount == other.likeCount && topCommentCount == other.topCommentCount
                && followCount == other.followCount && followerCount == other.followerCount
                && expires_time == other.expires_time && score == other.score
                && historyCount == other.historyCount && commentCount == other.commentCount
                && favoriteCount == other.favoriteCount && feedCount == other.feedCount
                && hasFollow == other.hasFollow
    }
}