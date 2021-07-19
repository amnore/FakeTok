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

enum class ArgName(val key: String) {
    ARG_NICKNAME("nickname"), ARG_DESCRIPTION("description"), ARG_LIKECOUNT("likeCount"), ARG_URI("uri")
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
            nickname = it.getString(ArgName.ARG_NICKNAME.key)!!
            description = it.getString(ArgName.ARG_DESCRIPTION.key)!!
            likeCount = it.getString(ArgName.ARG_LIKECOUNT.key)!!.toLong()
            uri = Uri.parse(it.getString(ArgName.ARG_URI.key)!!)
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
        fun newInstance(nickname: String, description: String, likeCount: Long, uri: Uri) =
            VideoFragment().apply {
                arguments = Bundle().apply {
                    arrayOf(
                        Pair(ArgName.ARG_DESCRIPTION, description),
                        Pair(ArgName.ARG_LIKECOUNT, likeCount),
                        Pair(ArgName.ARG_NICKNAME, nickname),
                        Pair(ArgName.ARG_URI, uri)
                    ).forEach {
                        putString(it.first.toString(), it.second.toString())
                    }
                }
            }
    }
}