package ng.com.codetrik.cipmas.network

import com.google.gson.GsonBuilder
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClientInstance {

    fun getRetrofitInstance() : Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .client(Ok.getOkHttpClient())
                .build()
    }

    object Ok{
        fun getOkHttpClient() : OkHttpClient{

            return OkHttpClient().newBuilder()
                    .connectTimeout(60,TimeUnit.SECONDS)
                    .readTimeout(60,TimeUnit.SECONDS)
                    .writeTimeout(60,TimeUnit.SECONDS)
                    .addInterceptor { chain ->
                        val originalRequest = chain.request()

                        val builder = originalRequest.newBuilder()
                            .header("Authorization", Credentials.basic(
                                "codetrik@gmail.com", "ADEWALEHAMZAT1992a."
                             ))
                            .header("Content-Type","application/json")
                            .header("Accept","application/json")
                        val newRequest = builder.build()
                        chain.proceed(newRequest)
                        }
                    .build()
        }
    }
}