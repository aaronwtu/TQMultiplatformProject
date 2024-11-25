package com.example.common.models

import korlibs.time.DateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class MovieUserMeta(
    @Transient
    var addedToList: DateTime? = null)
