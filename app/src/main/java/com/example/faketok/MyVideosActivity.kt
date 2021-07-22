package com.example.faketok

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.ThumbnailUtils
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import android.widget.SimpleAdapter.ViewBinder
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class MyVideosActivity : AppCompatActivity() {

    private lateinit var listview: ListView
    private lateinit var adapter: SimpleAdapter
    private var path: String="/data/user/0/com.example.faketok/cache/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_videos)

        listview=findViewById(R.id.list_video)

        initFileList()

        findViewById<Button>(R.id.delete_video).setOnClickListener{deleteVideo()}
        findViewById<Button>(R.id.rename_video).setOnClickListener{renameVideo()}
    }

    private fun renameVideo(v:Button) {
        var view:View
    }

    private fun deleteVideo() {

    }

    private fun initFileList() {
        var files: Array<File>
        files= File(path).listFiles();
        var list: List<File>;
        if (files == null) {
            list =ArrayList<File>();
        } else {
            list = files.toList()
        }

        var listItems: List<Map<String, Any>>
        listItems=ArrayList<Map<String, Any>>()
        for (file in list){
            System.out.println("Path="+file.absolutePath)
            var bitmap:Bitmap?=getVideoThumbnail(file.absolutePath,400,200, MediaStore.Images.Thumbnails.MINI_KIND)
            var d1:Drawable=BitmapDrawable(bitmap)
            System.out.println()
            var map:Map<String, Any>
            map=HashMap<String, Any>()
            if (bitmap==null||file.absolutePath==null) continue
            map.put("file_image",bitmap)
            map.put("file_name",getFileNameWithSuffix(file.absolutePath))
            listItems.add(map)
        }
        var strarr= arrayOf("file_image","file_name")
        var strint:IntArray = intArrayOf(R.id.file_image,R.id.file_name)
        adapter=SimpleAdapter(this,listItems,R.layout.item,strarr,strint)
        adapter.setViewBinder(object : ViewBinder {
            override fun setViewValue(
                view: View?, data: Any?,
                textRepresentation: String?
            ): Boolean {
                if (view is ImageView && data is Bitmap) {
                    view.setImageBitmap(data as Bitmap?)
                    return true
                }
                return false
            }
        })

        listview.adapter=adapter
    }

    private fun getVideoThumbnail(filePath:String , width:Int, height:Int, kind:Int):Bitmap? {
        var bitmap:Bitmap?=null
        try {
            bitmap = ThumbnailUtils.createVideoThumbnail(filePath,kind);
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        } catch (e:Exception) {
            e.printStackTrace();
        }

        if (bitmap == null) return null;
        return bitmap;
    }

    public fun getFileNameWithSuffix(pathandname:String): String  {
        var start:Int = pathandname.lastIndexOf("/");
        if (start != -1) {
            return pathandname.substring(start + 1);
        } else {
            return "";
        }
    }
}