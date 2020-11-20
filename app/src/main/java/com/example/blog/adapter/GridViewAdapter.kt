package com.example.blog.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.blog.R
import com.example.blog.activity.AlbumContent
import com.example.blog.dataclass.Model
import com.squareup.picasso.Picasso

class GridViewAdapter(val context: AlbumContent , val photosData : List<Model.Photo>):BaseAdapter(){
    private var layoutInflater: LayoutInflater? = null
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView

    override fun getCount(): Int = photosData.size

    override fun getItem(p0: Int): Any = photosData[p0]

    override fun getItemId(p0: Int): Long = photosData[p0].id.toLong()

    override fun getView( position : Int, convertView : View?, parent: ViewGroup?): View {



        var convertView = convertView
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.thumbnail_photo_item, null)
        }
        imageView = convertView!!.findViewById(R.id.photo)
        Picasso.get().load(photosData[position].thumbnailUrl).into(imageView)
        return convertView
    }


}