package com.theitfox.core.network

import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * This interceptor transforms a GET request
 * into a POST request for security reasons.
 */
class ConvertGetToPostInterceptor : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val fullUrl = originalRequest.url()

        // The builder to build a request body
        val builder = FormBody.Builder()

        // Iterating through the GET params, then add each param to the request body builder
        run {
            val itr = fullUrl.queryParameterNames().iterator()
            while (itr.hasNext()) {
                val name = itr.next()
                builder.add(name, fullUrl.queryParameter(name)!!)
            }
        }

        // Build the request body
        val body = builder.build()

        // The builder to build a HttpUrl
        val bareUrlBuilder = HttpUrl.Builder()
                .scheme(fullUrl.scheme())
                .host(fullUrl.host())

        // Iterating through the path segments and append them to the url builder
        val itr = fullUrl.pathSegments().iterator()
        while (itr.hasNext()) {
            bareUrlBuilder.addPathSegment(itr.next())
        }

        // Build the bare url whose GET params were striped off
        val bareUrl = bareUrlBuilder.build().toString()

        // Build a nre request with the request body and the bare url
        val newRequest = originalRequest.newBuilder()
                .url(bareUrl)
                .post(body)
                .build()

        // Proceed the chain with the new request
        return chain.proceed(newRequest)
    }
}
