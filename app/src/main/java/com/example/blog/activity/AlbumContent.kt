package com.example.blog.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.blog.R
import com.example.blog.activity.data.DataRepository
import com.example.blog.adapter.GridViewAdapter
import com.example.blog.dataclass.Model
import com.example.blog.dataclass.PhotoApi
import kotlinx.android.synthetic.main.album_content_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumContent : Fragment(){

    lateinit var gridView: GridView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.album_content_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        gridView = album_gridView
        getPhotosFromApi(requireArguments().getInt("albumId",0))
    }

    private fun getPhotosFromApi(albumId: Int) {
        val dataRepository = DataRepository()
        val call : Call<List<Model.Photo>> = dataRepository.getRetrofit()
            .create(PhotoApi::class.java).GetPhotos(albumId)

        call.enqueue(object : Callback<List<Model.Photo>> {
            override fun onResponse(
                call: Call<List<Model.Photo>>,
                response: Response<List<Model.Photo>>
            ) {

                val mainAdapter = GridViewAdapter(this@AlbumContent, response.body()!!, activity)
                gridView.adapter = mainAdapter
                gridView.onItemClickListener =
                    AdapterView.OnItemClickListener { _, _, position, _ ->

                        Toast.makeText(activity, "You clicked on ${response.body()!![position].id}", Toast.LENGTH_SHORT)
                            .show()

                        val bundle = Bundle()
                        bundle.putInt("photoId", response.body()!![position].id)
                        val fragmentTwo = ShowPhoto()
                        fragmentTwo.arguments = bundle

                        activity!!.supportFragmentManager.beginTransaction()
                            .replace(R.id.container, fragmentTwo).commit()
                    }

            }

            override fun onFailure(call: Call<List<Model.Photo>>, t: Throwable) {
                Log.w("Server Error", t.message.toString())
                println("Failed connection")
            }
        })
    }
}
