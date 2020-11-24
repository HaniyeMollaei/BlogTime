package com.example.blog.activity

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.blog.R
import kotlinx.android.synthetic.main.profile_layout.*

class Profile:Fragment() {

    private lateinit var userData : SharedPreferences
    private lateinit var isLoggedIn : SharedPreferences
    private lateinit var userDataArr : List<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        userData = activity?.getSharedPreferences("appUser" , MODE_PRIVATE) ?: userData
        val dataTmp = userData.getString("appUser" , "-1*هانیه ملائی*Haniye_Mli*Haniyemolaei1378@gmail.com*تهران*09371544159*https://github.com/HaniyeMollaei*شرکت ...")

        userDataArr = dataTmp!!.split("*")

        edit_btn.setOnClickListener {
            Toast.makeText( activity , "Exit" , Toast.LENGTH_SHORT).show()
            this.isLoggedIn = requireActivity().getSharedPreferences("isLoggedIn" , MODE_PRIVATE)
            userData = requireActivity().getSharedPreferences("appUser" , MODE_PRIVATE)

            val editor = isLoggedIn.edit()
            editor.putBoolean("isLoggedIn" , false)
            editor.apply()

            val editor2 = userData.edit()
            editor2.putString("appUser" , "")
            editor2.apply()

            startActivity(Intent(activity , LogIn::class.java))
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