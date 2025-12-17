package com.rare.launcher.model

import com.google.gson.annotations.SerializedName

data class LibraryResponse(
    @SerializedName("records")
    val records: List<GameRecord>,
    
    @SerializedName("responseMetadata")
    val responseMetadata: ResponseMetadata
)

data class GameRecord(
    @SerializedName("appName")
    val appName: String,
    
    @SerializedName("catalogItemId")
    val catalogItemId: String,
    
    @SerializedName("namespace")
    val namespace: String,
    
    @SerializedName("productMetadata")
    val productMetadata: ProductMetadata?
)

data class ProductMetadata(
    @SerializedName("title")
    val title: String?,
    
    @SerializedName("developer")
    val developer: String?,
    
    @SerializedName("description")
    val description: String?,
    
    @SerializedName("keyImages")
    val keyImages: List<KeyImage>?
)

data class KeyImage(
    @SerializedName("type")
    val type: String,
    
    @SerializedName("url")
    val url: String
)

data class ResponseMetadata(
    @SerializedName("nextCursor")
    val nextCursor: String?
)
