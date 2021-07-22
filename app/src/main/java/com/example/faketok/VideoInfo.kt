package com.example.faketok

import android.net.Uri
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class VideoInfo(
    val _id: String,
    val feedurl: String,
    val nickname: String,
    val description: String,
    val likecount: Long,
    val avatar: String,
    val thumbnails: String
)
