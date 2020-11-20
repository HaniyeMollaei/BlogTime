package com.example.blog.adapter


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.blog.R
import com.example.blog.activity.AlbumContent
import com.example.blog.activity.data.DataRepository
import com.example.blog.dataclass.Model
import com.example.blog.dataclass.PhotoApi
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumAdapter(private val albumData: List<Model.Album>, private val context : Context )
    : RecyclerView.Adapter<AlbumAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val title: TextView = itemView.findViewById(R.id.card_title)
        val img: ImageView = itemView.findViewById(R.id.card_img)
        val tempView: LinearLayout = itemView.findViewById(R.id.card_view)
    }
    override fun getItemCount(): Int = albumData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.album_item , parent, false))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        getImageFromApi(1 , holder , position)
        holder.title.text = albumData[position].title
        holder.tempView.setOnClickListener {
            var intent_temp = Intent(context , AlbumContent::class.java)
                .putExtra("albumId" , albumData[position].id)
            context.startActivity(intent_temp)
        }
    }

    private fun getImageFromApi(album_id: Int, holder: CustomViewHolder, position: Int) {
        val dataRepository = DataRepository()
        val call : Call<List<Model.Photo>> = dataRepository.getRetrofit()
            .create(PhotoApi::class.java).GetPhotos(album_id)

        call.enqueue(object : Callback<List<Model.Photo>> {
            override fun onResponse(
                call: Call<List<Model.Photo>>,
                response: Response<List<Model.Photo>>
            ) {
                println("this is response :  \n"
                        +"Email :  "+ response.body()!![0].title + "  ||  Username" + response.body()!![0].url)
                Picasso.get().load(response.body()!![position].thumbnailUrl).into(holder.img)
            }

            override fun onFailure(call: Call<List<Model.Photo>>, t: Throwable) {
                Log.w("Server Error", t.message.toString())
                println("Failed connection")
            }
        })
    }
}