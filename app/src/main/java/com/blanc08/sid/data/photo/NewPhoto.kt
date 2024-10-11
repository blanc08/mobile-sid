package com.blanc08.sid.data.photo

import kotlinx.serialization.Serializable

@Serializable
data class NewPhoto(
    val url: String = "",
    val description: String = "",
) {
    override fun toString() = description
}