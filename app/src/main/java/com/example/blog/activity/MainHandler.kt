package com.example.blog.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.blog.R
import kotlinx.android.synthetic.main.first_page.*
import java.util.zip.Inflater

class MainHandler:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_page)

        replaceFragment(Feed())

        post_btn.setOnClickListener {
            replaceFragment(Feed())
        }
        profile_btn.setOnClickListener {
            replaceFragment(Profile())
        }
        album_btn.setOnClickListener {
            replaceFragment(Albums())
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container , fragment)
        fragmentTransaction.commit()
    }
}
