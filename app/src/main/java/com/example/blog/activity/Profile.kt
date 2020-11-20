package com.example.blog.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.blog.R
import kotlinx.android.synthetic.main.profile_layout.*

class Profile:AppCompatActivity() {

    private lateinit var getEditor : SharedPreferences
    private lateinit var userDataArr : List<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_layout)
        getEditor = this.getSharedPreferences("appUser" , MODE_PRIVATE)
        val userData = getEditor.getString("appUser" , "-1*هانیه ملائی*Haniye_Mli*Haniyemolaei1378@gmail.com*تهران*09371544159*https://github.com/HaniyeMollaei*آرمان سامانه")

        userDataArr = userData!!.split("*")

        back_btn.setOnClickListener {
            finish()
        }

        edit_btn.setOnClickListener {
            Toast.makeText(this , "امکان ویرایش اطلاعات فعلا مهیا نیست ." , Toast.LENGTH_SHORT).show()
        }


        name.text = userDataArr[1]
        username.text = userDataArr[2]
        email.text = userDataArr[3]
        phone.text = userDataArr[5]
        website.text = userDataArr[6]
        company.text = userDataArr[7]
        address.text = userDataArr[4]


    }


}