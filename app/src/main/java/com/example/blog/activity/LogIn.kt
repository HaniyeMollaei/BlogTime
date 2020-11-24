package com.example.blog.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.blog.R
import com.example.blog.activity.data.DataRepository
import com.example.blog.dataclass.Model
import com.example.blog.dataclass.UserApi
import kotlinx.android.synthetic.main.log_in_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogIn : AppCompatActivity() {

    lateinit var isLoggedIn : SharedPreferences
    lateinit var userData : SharedPreferences

    private lateinit var appUser : Model.User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_in_layout)

        isLoggedIn = this.getSharedPreferences("isLoggedIn" , MODE_PRIVATE)
        userData = this.getSharedPreferences("appUser" , MODE_PRIVATE)

        if (isLoggedIn.getBoolean("isLoggedIn",false)){
            val intent = Intent(this , MainHandler::class.java)
            startActivity(intent)
        }

        log_in_btn.setOnClickListener {
            if( !checkUsername() || !checkEmail() )
                return@setOnClickListener
            loading(true)
            if( !getUsersFromApi())
                return@setOnClickListener
            loading(false)

            startActivity( Intent(this , MainHandler::class.java))
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

    override fun onRestart() {
        finish()
        super.onRestart()
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

    private fun getUsersFromApi(): Boolean {

        val username = username_txt.text.toString()
        val email = email_txt.text.toString()

        val userRepository = DataRepository()
        val call : Call<List<Model.User>> = userRepository.getRetrofit()
                    .create(UserApi::class.java).GetUsers(username)

        call.enqueue(object : Callback<List<Model.User>> {
            override fun onResponse(
                call: Call<List<Model.User>>,
                response: Response<List<Model.User>>
            ) {
                if (!response.body()!!.isEmpty()){
                    appUser = response.body()!!.get(0)
                    if(username == appUser.username && email == appUser.email){

                        Toast.makeText(this@LogIn, "خوش آمدید", Toast.LENGTH_SHORT).show()

                        val editor = isLoggedIn.edit()
                        editor.putBoolean("isLoggedIn" , true)
                        editor.apply()

                        val editor2 = userData.edit()
                        editor2.putString("appUser" ,
                            appUser.id.toString()+"*"+  //0
                                    appUser.name+"*"+           //1
                                    appUser.username+"*"+       //2
                                    appUser.email+"*"+          //3
                                    appUser.address.city+"*"+   //4
                                    appUser.phone+"*"+          //5
                                    appUser.website+"*"+        //6
                                    appUser.company.name)       //7
                        editor2.apply()
                        val intent = Intent(this@LogIn , MainHandler::class.java)
                        startActivity(intent)
                    }else {
                        Toast.makeText(this@LogIn, "کاربر یافت نشد", Toast.LENGTH_SHORT).show()
                        loading(false)
                        return
                    }

                }else{
                    Toast.makeText(this@LogIn, "کاربر یافت نشد", Toast.LENGTH_SHORT).show()
                    loading(false)
                    return
                }
            }

            override fun onFailure(call: Call<List<Model.User>>, t: Throwable) {
                Log.w("Server Error", t.message.toString())
                println("Failed connection")
            }

        })

        return false
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
            !email.contains('@') || !email.contains('.') -> {
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

}


