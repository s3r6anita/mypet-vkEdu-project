package com.f4.mypet.data.network.model.api

import com.google.gson.annotations.SerializedName

data class ApiWallResponse(
    @SerializedName("response") val response: Response
)

data class Response(
    @SerializedName("count") val count: Int,
    @SerializedName("items") val items: List<Item>
)

data class Item(
    @SerializedName("inner_type") val innerType: String,
    @SerializedName("donut") val donut: Donut,
    @SerializedName("comments") val comments: Comments,
    @SerializedName("marked_as_ads") val markedAsAds: Int,
    @SerializedName("short_text_rate") val shortTextRate: Double,
    @SerializedName("hash") val hash: String,
    @SerializedName("has_translation") val hasTranslation: Boolean,
    @SerializedName("type") val type: String,
    @SerializedName("attachments") val attachments: List<Attachment>?,
    @SerializedName("date") val date: Long,
    @SerializedName("from_id") val fromId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("is_favorite") val isFavorite: Boolean,
    @SerializedName("likes") val likes: Likes,
    @SerializedName("reaction_set_id") val reactionSetId: String?,
    @SerializedName("reactions") val reactions: Reactions?,
    @SerializedName("owner_id") val ownerId: Int,
    @SerializedName("post_source") val postSource: PostSource,
    @SerializedName("post_type") val postType: String,
    @SerializedName("reposts") val reposts: Reposts,
    @SerializedName("text") val text: String?,
    @SerializedName("views") val views: Views
)

data class Donut(
    @SerializedName("is_donut") val isDonut: Boolean
)

data class Comments(
    @SerializedName("can_post") val canPost: Int,
    @SerializedName("can_view") val canView: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("groups_can_post") val groupsCanPost: Boolean
)

data class Attachment(
    @SerializedName("type") val type: String,
    @SerializedName("video") val video: Video?
)

data class Video(
    @SerializedName("response_type") val responseType: String,
    @SerializedName("access_key") val accessKey: String,
    @SerializedName("can_comment") val canComment: Int,
    @SerializedName("can_like") val canLike: Int,
    @SerializedName("can_repost") val canRepost: Int,
    @SerializedName("can_subscribe") val canSubscribe: Int,
    @SerializedName("can_add_to_faves") val canAddToFaves: Int,
    @SerializedName("can_add") val canAdd: Int,
    @SerializedName("comments") val comments: Int,
    @SerializedName("date") val date: Long,
    @SerializedName("description") val description: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("image") val image: List<Image>,
    @SerializedName("first_frame") val firstFrame: List<Image>,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("owner_id") val ownerId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("is_favorite") val isFavorite: Boolean,
    @SerializedName("track_code") val trackCode: String,
    @SerializedName("type") val type: String,
    @SerializedName("views") val views: Int,
    @SerializedName("local_views") val localViews: Int,
    @SerializedName("can_dislike") val canDislike: Int
)

data class Image(
    @SerializedName("url") val url: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("with_padding") val withPadding: Int?
)

data class Likes(
    @SerializedName("can_like") val canLike: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("user_likes") val userLikes: Int,
    @SerializedName("can_publish") val canPublish: Int,
    @SerializedName("repost_disabled") val repostDisabled: Boolean
)

data class Reactions(
    @SerializedName("count") val count: Int,
    @SerializedName("items") val items: List<ReactionItem>
)

data class ReactionItem(
    @SerializedName("id") val id: Int,
    @SerializedName("count") val count: Int
)

data class PostSource(
    @SerializedName("type") val type: String
)

data class Reposts(
    @SerializedName("count") val count: Int,
    @SerializedName("user_reposted") val userReposted: Int
)

data class Views(
    @SerializedName("count") val count: Int
)