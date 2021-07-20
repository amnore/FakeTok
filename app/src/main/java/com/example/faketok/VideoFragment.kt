package com.example.faketok

import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.io.Serializable
import kotlin.properties.Delegates

enum class ArgName {
    ARG_NICKNAME, ARG_DESCRIPTION, ARG_LIKECOUNT, ARG_URI
}

/**
 * A simple [Fragment] subclass.
 * Use the [VideoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VideoFragment : Fragment() {
    // TODO: Rename and change types of parameters
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(info: VideoInfo): VideoFragment =
            TODO()
    }
}