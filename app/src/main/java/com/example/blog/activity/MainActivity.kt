package com.example.blog.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.blog.R
import com.example.blog.activity.data.UserRepository
import com.example.blog.dataclass.Model
import com.example.blog.dataclass.UserApi
import kotlinx.android.synthetic.main.log_in_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var usersList: List<Model.User>
    private lateinit var appUser : Model.User

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_in_layout)

        getUsersFromApi()

        log_in_btn.setOnClickListener {
            if( !checkUsername() || !checkEmail() )
                return@setOnClickListener
            if( !validateUser())
                return@setOnClickListener

            Toast.makeText(this, "خوش آمدید", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }

        exit_btn.setOnClickListener {

            finish()
        }
    }

    override fun onDestroy() {
        email_txt.text.clear()
        username_txt.text.clear()
        super.onDestroy()
    }

    private fun loading(show: Boolean) {
        if(show){
            login_pb.visibility =  ProgressBar.VISIBLE
            log_in_btn.visibility = ProgressBar.GONE
        } else {
            login_pb.visibility =  ProgressBar.GONE
            log_in_btn.visibility = ProgressBar.VISIBLE
        }
    }

    private fun getUsersFromApi() {
        val userRepository = UserRepository()
        val call : Call<List<Model.User>> = userRepository.getRetrofit().create(UserApi::class.java).GetUsers()

        call.enqueue(object : Callback<List<Model.User>> {
            override fun onResponse(
                call: Call<List<Model.User>>,
                response: Response<List<Model.User>>
            ) {
                loading(false)
                Log.w(
                    "Server Ok",
                    response.body()!![2].email + "   |||   " + response.body()!![2].username
                )
                println("this is response :  \n"
                        +"Email :  "+ response.body()!![2].email + "  ||  Username" + response.body()!![2].username)
                usersList = response.body()!!
                printUsers(usersList)
            }

            override fun onFailure(call: Call<List<Model.User>>, t: Throwable) {
                Log.w("Server Error", t.message.toString())
                println("Failed connection")
            }

        })
    }

    private fun printUsers(usersList: List<Model.User>) {
        for (it in usersList.indices){
            println("user  " +it+"  Username : "+usersList[it].username+"    Email : "+usersList[it].email)

        }
    }


    private fun checkEmail() : Boolean {
        val email = email_txt.text.toString().trim() //baraye az beyne bordane fasele haye ebteda va enteha
        return when{

            email.isEmpty() -> {
                email_txt.error = "این فیلد نمیتواند خالی باشد"
                false
            }
            email.length > 30 -> {
                email_txt.error = "طول ایمیل بیش از حد مجاز است"
                false
            }
            !email.contains('@') -> {
                email_txt.error = "لطفا آدرس ایمیل را به درستی وارد کنید"
                false
            }
            else -> {
                email_txt.error = null
                true
            }
        }
    }

    private fun checkUsername() :Boolean{
        val username = username_txt.text.toString().trim() //baraye az beyne bordane fasele haye ebteda va enteha
        return when{

            username.isEmpty() -> {
                username_txt.error = "این فیلد نمیتواند خالی باشد"
                false
            }
            username.length > 15 -> {
                username_txt.error = "طول نام کاربری بیش از حد مجاز است"
                false
            }
            else -> {
                username_txt.error = null
                true
            }
        }
    }


    private fun validateUser() : Boolean {
        val username = username_txt.text.toString()
        val email = email_txt.text.toString()

        println("Person : $username  $email")
        for ( it in usersList.indices){
            if (username.toLowerCase() == usersList[it].username.toLowerCase()) {
                return if(email.toLowerCase() == usersList[it].email.toLowerCase()) {
                    appUser = usersList[it]
                    true
                } else {
                    Toast.makeText(this, "ایمیل یافت نشد", Toast.LENGTH_SHORT).show()
                    false
                }
            }
            if (email.toLowerCase() == usersList[it].email.toLowerCase()){
                return if (username.toLowerCase() == usersList[it].username.toLowerCase()) {
                    appUser = usersList[it]
                    true
                } else {
                    Toast.makeText(this, "نام کاربری یافت نشد", Toast.LENGTH_SHORT).show()
                    false
                }
            }
        }
        Toast.makeText(this, "کاربر یافت نشد", Toast.LENGTH_SHORT).show()
        return false
    }

}


