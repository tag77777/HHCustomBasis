package a77777_888.me.t.https.hhcustombasis.source

import a77777_888.me.t.https.hhcustombasis.model.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitSource {

//    private val moshi = Moshi.Builder()
////        .addLast(KotlinJsonAdapterFactory())
//        .build()

    val retrofit = createRetrofit()

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Singleton.BASE_URL)
            .client(okHttpClient())
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }
}

