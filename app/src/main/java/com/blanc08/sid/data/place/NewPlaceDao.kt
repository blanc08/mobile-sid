package com.blanc08.sid.data.place


data class FieldValidation(
    val isValid: Boolean = true,
    val message: String = ""
)

data class NewPlaceDaoValidation(
    val nameValidator: FieldValidation = FieldValidation(),
    val descriptionValidator: FieldValidation = FieldValidation(),
    val locationValidator: FieldValidation = FieldValidation(),
    val thumbnailValidator: FieldValidation = FieldValidation(),
    val imageValidator: FieldValidation = FieldValidation(),
)

data class NewPlaceDao(
    val name: String = "",

    val description: String = "",

    val location: String? = null,

    val thumbnail: String = "",

    val image: String? = "",
) {
    override fun toString() = name
}