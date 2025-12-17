package com.rare.launcher.api

import com.rare.launcher.model.AuthResponse
import com.rare.launcher.model.LibraryResponse
import retrofit2.Response
import retrofit2.http.*

interface EpicGamesApi {
    
    @FormUrlEncoded
    @POST("account/api/oauth/token")
    suspend fun authenticate(
        @Header("Authorization") authorization: String,
        @Field("grant_type") grantType: String,
        @Field("code") code: String?,
        @Field("token_type") tokenType: String = "eg1"
    ): Response<AuthResponse>
    
    @GET("library/api/public/items")
    suspend fun getLibrary(
        @Header("Authorization") authorization: String,
        @Query("includeMetadata") includeMetadata: Boolean = true,
        @Query("cursor") cursor: String? = null
    ): Response<LibraryResponse>
}
