package com.example.faketok

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.request.request
import io.ktor.client.statement.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

private val api = "https://beiyou.bytedance.com/api/invoke/video/invoke/video"

class MainActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var pager: ViewPager2
    private lateinit var videos: MutableList<VideoInfo>

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        videos = Collections.synchronizedList(ArrayList())
        val adapter = PagerAdapter(this, videos)
        pager = findViewById<ViewPager2?>(R.id.pager).apply {
            setAdapter(adapter)
        }

        findViewById<Button>(R.id.gotoCaptureActivity).setOnClickListener {
            startActivity(Intent(this, CaptureActivity::class.java))
        }

        launch {
            val client = HttpClient() {
                install(JsonFeature)
            }
            val response = client.request<HttpResponse> {
                url(api)
            }.receive<List<VideoInfo>>()

            client.close()
            videos.addAll(response)
            runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }
}

private class PagerAdapter(ac: AppCompatActivity, val items: List<VideoInfo>) : FragmentStateAdapter(ac) {
    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment {
        val it = items[position]

        return VideoFragment.newInstance(
            it.nickname,
            it.description,
            it.likecount,
            it.feedurl.uri
        )
    }
}

@Serializable
private data class VideoInfo(
    val _id: String,
    val feedurl: UriWrapper,
    val nickname: String,
    val description: String,
    val likecount: Long,
    val avatar: UriWrapper
)

@Serializable(with = UriSerializer::class)
private class UriWrapper(val uri: Uri)

private class UriSerializer: KSerializer<UriWrapper> {
    override fun deserialize(decoder: Decoder): UriWrapper{
        return UriWrapper(Uri.parse(decoder.decodeString()))
    }

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("UriWrapper", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: UriWrapper) {
        encoder.encodeString(value.uri.toString())
    }
}