package com.blanc08.sid.data.place

import kotlinx.serialization.Serializable


@Serializable
data class Photo(
    val id: String? = null,
    val name: String = "",
    val description: String = "",
    val thumbnail: String = "",
    val image: String? = "",
    val created_at: String? = "",
) {
    override fun toString() = name
}