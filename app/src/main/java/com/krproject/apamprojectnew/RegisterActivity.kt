package com.krproject.apamprojectnew

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import com.krproject.apamprojectnew.databinding.ActivityRegisterBinding
import kotlinx.coroutines.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Back Button Action
        binding.btnBack.setOnClickListener{
            val intent = Intent(this, MainActivity2::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        //Define Array View
        val dropdownMenu = arrayOf(
            "Tn",
            "Ny",
            "Nn",
            "An",
            "By"
        )

        val arrayAdapter = ArrayAdapter(
            this,
            R.layout.dropdown_menu,
            dropdownMenu
        )

        binding.tieSpinner.setAdapter(arrayAdapter)

        //Register Button Action
        binding.btnRegister.setOnClickListener {

            val nik = binding.tieNik.text.toString().trim()
            val name = binding.tieNama.text.toString().trim()
            val nohp = binding.tieNoHp.text.toString().trim()
            val email = binding.tieRegEmail.text.toString().trim()
            val password = binding.tieRegPassword.text.toString().trim()

            //Data Validation
            if (nik.isEmpty()){
                binding.tieNik.error = "NIK tidak boleh kosong"
                binding.tieNik.requestFocus()
                return@setOnClickListener
            }

            if (name.isEmpty()){
                binding.tieNama.error = "Email tidak boleh kosong"
                binding.tieNama.requestFocus()
                return@setOnClickListener
            }

            if (nohp.isEmpty()){
                binding.tieNoHp.error = "No. Handphone tidak boleh kosong"
                binding.tieNoHp.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty() || !email.contains("@")){
                binding.tieRegEmail.error = "Email tidak valid"
                binding.tieRegEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 8){
                binding.tieRegPassword.error = "Password tidak valid"
                binding.tieRegPassword.requestFocus()
                return@setOnClickListener
            }


            binding.pbRegLoading.visibility = View.VISIBLE
            GlobalScope.launch {
                setInvisible()

//            RetrofitClient.instance.createUser(nik, name, nohp, email, password)
//                .enqueue(object: Callback<DefaultResponse>{
//                    override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
//                        if(!response.body()?.error!!) {
//                            Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
//                            navigateUpTo()
//                        }
//                    }
//
//                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
//                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
//                    }
//
//                })
            }
        }
    }

    //Intent ke Main Activity
    private fun navigateUpTo() {
        val intent = Intent (this, MainActivity2::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    //Asyncronous visibilitas progress bar
    private suspend fun setInvisible() {
        withContext(Dispatchers.Default) {
            for (i in 1..50) {
                Log.v("Set Invisible", "$i")
            }
            runOnUiThread {
                binding.pbRegLoading.visibility = View.GONE
            }
        }
    }
}

//            val rDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
//            val rBuilder = AlertDialog.Builder(this)
//                .setView(rDialogView)
//            val rAlertDialog = rBuilder.show()
//
//            //button ok dialog utk konfirmasi dan kembali
//            rDialogView.binding.btn_reg_ok.setOnClickListener {
//                rAlertDialog.dismiss()
//                navigateUpTo()
//            }
