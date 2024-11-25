package com.example.common.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.aaronwtlu.project.createUUID
import kotlin.uuid.ExperimentalUuidApi


@Serializable
data class ImageData @OptIn(ExperimentalUuidApi::class) constructor(
    @Transient
    val id: String = createUUID(),
    val aspect_ratio: Float,
    val file_path: String,
    val height: Int,
    val width: Int
)
