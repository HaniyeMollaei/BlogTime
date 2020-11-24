package com.example.blog.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.example.blog.R
import com.example.blog.activity.AlbumContent
import com.example.blog.dataclass.Model
import com.squareup.picasso.Picasso

class GridViewAdapter(val context: AlbumContent, private val photosData : List<Model.Photo>
                      , activity: FragmentActivity?) :BaseAdapter(){

    private val act = activity

    private var layoutInflater: LayoutInflater? = null
    private lateinit var imageView: ImageView

    override fun getCount(): Int = this.photosData.size

    override fun getItem(p0: Int): Any = this.photosData[p0]

    override fun getItemId(p0: Int): Long = this.photosData[p0].id.toLong()

    override fun getView( position : Int, convertView : View?, parent: ViewGroup?): View {

        var convertView = convertView
        if (layoutInflater == null) {
            layoutInflater =
                act!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.thumbnail_photo_item, null)
        }
        imageView = convertView!!.findViewById(R.id.photo)
        Picasso.get().load(photosData[position].thumbnailUrl).into(imageView)
        return convertView
    }


}