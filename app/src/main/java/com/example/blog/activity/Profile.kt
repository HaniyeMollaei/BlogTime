package com.example.blog.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.blog.R
import kotlinx.android.synthetic.main.profile_layout.*

class Profile:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_layout)

        back_btn.setOnClickListener {
            startActivity(Intent(this , MainActivity::class.java))
        }

        edit_btn.setOnClickListener {
            Toast.makeText(this , "امکان ویرایش اطلاعات فعلا مهیا نیست ." , Toast.LENGTH_SHORT).show()
        }


        name.setText(intent.getStringExtra("name_ex"))
        username.setText(intent.getStringExtra("username_ex"))
        email.setText(intent.getStringExtra("email_ex"))
        phone.setText(intent.getStringExtra("phone_ex"))
        website.setText(intent.getStringExtra("website_ex"))
        company.setText(intent.getStringExtra("company_ex"))
        address.setText(intent.getStringExtra("address_ex"))


    }


}