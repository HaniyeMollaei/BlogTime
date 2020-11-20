package com.example.blog.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.blog.R
import com.example.blog.dataclass.Model
import kotlinx.android.synthetic.main.feed_list_item.view.*


class PostAdapter(private val data: List<Model.Post>):BaseAdapter() {
    override fun getCount(): Int = data.size


    override fun getItem(position: Int): Model.Post = data[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view : View
        val holder : CustomViewHolder

        if (convertView == null){
            view = LayoutInflater.from(parent.context).inflate(R.layout.feed_list_item , null)
            holder = CustomViewHolder()
            holder.title = view.title_txt
            holder.body = view.body_txt
            view.tag = holder
        }else{
            holder = convertView.tag as CustomViewHolder
            view = convertView
        }

        val data1 = getItem(position)

        view.title_txt.text = data1.title
        view.body_txt.text = data1.body
        view.linear_temp.setOnClickListener {
            Toast.makeText(parent.context , "${data1.id}" , Toast.LENGTH_SHORT).show()
        }
        return view
    }

    class CustomViewHolder{
        lateinit var title : TextView
        lateinit var body : TextView
    }
}