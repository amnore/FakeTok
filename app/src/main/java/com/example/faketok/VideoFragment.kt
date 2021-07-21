package com.example.faketok

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.faketok.util.Constant
import kotlin.properties.Delegates


enum class ArgName {
    ARG_NICKNAME, ARG_DESCRIPTION, ARG_LIKECOUNT, ARG_URI
}

class VideoFragment : Fragment() {

    lateinit var nickname: String
    lateinit var description: String
    var likeCount by Delegates.notNull<Long>()
    lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            nickname = it.getString(ArgName.ARG_NICKNAME.toString())!!
            description = it.getString(ArgName.ARG_DESCRIPTION.toString())!!
            likeCount = it.getString(ArgName.ARG_LIKECOUNT.toString())!!.toLong()
            uri = Uri.parse(it.getString(ArgName.ARG_URI.toString())!!)
        }

        Log.d(Constant.APP, "VideoFragment, onCreate finishes")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView: TextView = view.findViewById(R.id.text_demo)
        textView.text = "$nickname, $description, $likeCount, $uri"

        Log.d(Constant.APP, "VideoFragment, onViewCreated finishes, text is ${textView.text}")
    }

    companion object {
        @JvmStatic
        fun newInstance(info: VideoInfo): VideoFragment =
            TODO()
    }
}