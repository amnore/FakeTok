package com.example.faketok

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.example.faketok.util.Constant
import kotlin.properties.Delegates


enum class ArgName {
    ARG_NICKNAME, ARG_DESCRIPTION, ARG_LIKECOUNT, ARG_URI, ARG_IMAGE
}

class VideoFragment : Fragment(), MediaPlayer.OnPreparedListener {

    private lateinit var progressBar: ProgressBar
    private lateinit var imageView: ImageView
    private lateinit var nicknameView: TextView
    private lateinit var desView: TextView
    private lateinit var likeCountView: TextView
    private lateinit var videoView: VideoView

    lateinit var nickname: String
    lateinit var description: String
    var likeCount by Delegates.notNull<Long>()
    lateinit var uri: Uri
    lateinit var image: Uri

    private var gesture: GestureDetector = GestureDetector(context,
        object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                return super.onDoubleTap(e)
            }
        })


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            nickname = it.getString(ArgName.ARG_NICKNAME.toString())!!
            description = it.getString(ArgName.ARG_DESCRIPTION.toString())!!
            likeCount = it.getString(ArgName.ARG_LIKECOUNT.toString())!!.toLong()
            uri = Uri.parse(it.getString(ArgName.ARG_URI.toString())!!)
            image = Uri.parse(it.getString(ArgName.ARG_IMAGE.toString())!!)
        }

        Log.d(Constant.APP, "VideoFragment, onCreate finishes")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View? = inflater.inflate(R.layout.fragment_video, container, false)
        this.initProgress(view)
        this.initImage(view)
        this.initTexts(view)
        this.setVideo(view)
        return view
    }

    private fun setVideo(view: View?) {
        videoView = view?.findViewById(R.id.video)!!

        videoView.apply {
            contentDescription = "$nickname, $description, $likeCount, $uri, $image"
            visibility = View.GONE
//            setVideoURI(Uri.parse("android.resource://${context?.packageName}/${R.raw.mihoyo}"))
//            setVideoURI(uri)
            setVideoURI(Uri.parse("https://teachingoss.applysquare.com/mp4MultibitrateIn42/2020/03/06/13/24/16/904/o_1e2n4qegoo5r1a044ub4vha2ph.mp4/960.mp4?OSSAccessKeyId=LTAI5tRvp4vmhaFUwAAcBybz&Expires=1626960174&Signature=upnogak1Q9YBwauTN3oVbJytdpA%3D"))
            Log.d(Constant.APP, "VideoFragment, $uri")
            setOnPreparedListener(this@VideoFragment)

            setOnClickListener {
                Log.d(Constant.APP, "VideoFragment, VideoView click")
                if (videoView.isPlaying) videoView.pause()
                else videoView.start()
            }

        }

        imageView.setOnClickListener {
            Log.d(Constant.APP, "VideoFragment, ImageView click")
            imageView.visibility = View.GONE
            if (videoView.isPlaying) videoView.pause()
            else videoView.start()
        }
    }

    private fun initProgress(view: View?) {
        progressBar = view?.findViewById(R.id.progress)!!
        progressBar.visibility = View.VISIBLE
    }

    private fun initImage(view: View?) {
        imageView = view?.findViewById(R.id.image)!!
        context?.let {
            Glide.with(it).load(image).transition(withCrossFade()).into(imageView)
        }
        imageView.visibility = View.VISIBLE
    }

    private fun initTexts(view: View?) {
        nicknameView = view?.findViewById(R.id.nickname)!!
        nicknameView.text = "@$nickname"
        desView = view.findViewById(R.id.description)!!
        desView.text = description
        likeCountView = view.findViewById(R.id.like_count)!!
        likeCountView.text = likeCount.toString()
    }

    override fun onPrepared(mp: MediaPlayer?) {
        Log.d(Constant.APP, "VideoFragment, onPrepared")
        progressBar.visibility = View.GONE
        videoView.visibility = View.VISIBLE
        mp?.start()
        mp?.pause()
    }

    companion object {
        @JvmStatic
        fun newInstance(
            nickname: String,
            description: String,
            likeCount: Long,
            uri: Uri,
            image: Uri
        ) =
            VideoFragment().apply {
                arguments = Bundle().apply {
                    arrayOf(
                        Pair(ArgName.ARG_DESCRIPTION, description),
                        Pair(ArgName.ARG_LIKECOUNT, likeCount),
                        Pair(ArgName.ARG_NICKNAME, nickname),
                        Pair(ArgName.ARG_URI, uri),
                        Pair(ArgName.ARG_IMAGE, image)
                    ).forEach {
                        putString(it.first.toString(), it.second.toString())
                    }
                }
            }
    }
}