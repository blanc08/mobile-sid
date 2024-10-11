package com.blanc08.sid.data.photo

import kotlinx.serialization.Serializable


@Serializable
data class Photo(
    val id: Int,
    val url: String,
    val description: String,
    val created_at: String,
) {
    override fun toString() = description
}