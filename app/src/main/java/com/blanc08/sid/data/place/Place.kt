package com.blanc08.sid.data.place

import kotlinx.serialization.Serializable


@Serializable
data class Place(
    val id: String,
    val name: String,
    val description: String? = null,
    val thumbnail: String? = null,
    val image: String? = null,
    val created_at: String? = null,
) {
    override fun toString() = name
}