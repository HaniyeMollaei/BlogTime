package com.example.blog.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.blog.R
import kotlinx.android.synthetic.main.first_page.*


class MainHandler:AppCompatActivity() {

//    lateinit var isLoggedIn : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//
//        isLoggedIn = this.getSharedPreferences("isLoggedIn" , MODE_PRIVATE)
//
//        if (!isLoggedIn.getBoolean("isLoggedIn",false)){
//            startActivity(Intent(this , LogIn::class.java))
//        }

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
