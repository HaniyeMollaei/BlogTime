package com.example.blog.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.blog.R
import kotlinx.android.synthetic.main.first_page.*


class MainHandler:AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.first_page)
        replaceFragment(Posts())

        post_btn.setOnClickListener {
            frag.text = "Feed"
            replaceFragment(Posts())
        }
        profile_btn.setOnClickListener {
            frag.text = "Profile"
            replaceFragment(Profile())
        }
        album_btn.setOnClickListener {
            frag.text = "Album"
            replaceFragment(Albums())
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container , fragment)
        fragmentTransaction.commit()
    }
}
