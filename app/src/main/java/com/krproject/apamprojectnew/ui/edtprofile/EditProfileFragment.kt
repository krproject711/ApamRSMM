package com.krproject.apamprojectnew.ui.edtprofile

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Base64.encodeToString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.krproject.apamprojectnew.R
import com.krproject.apamprojectnew.data.network.RequestBodies
import com.krproject.apamprojectnew.data.network.Resource
import com.krproject.apamprojectnew.repository.AppRepository
import com.krproject.apamprojectnew.ui.auth.AuthViewModel
import com.krproject.apamprojectnew.ui.base.ViewModelProviderFactory
import com.krproject.apamprojectnew.util.Helper
import com.krproject.apamprojectnew.util.SharedPreferenceHelper
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*


class EditProfileFragment : Fragment() {

    lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    lateinit var authViewModel: AuthViewModel
    var goldar = ""
    var pendidikan = ""
    var statusNikah = ""
    var jenisKelamin = ""
    var tanggal = ""
    var photoKK: Bitmap? = null
    var photoKTP: Bitmap? = null
    var photoBPJS: Bitmap? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedPreferenceHelper = SharedPreferenceHelper(requireContext())
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(requireActivity().application, repository)
        authViewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        setValueFromShared()

        tie_form_tanggal.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(
                requireActivity(),
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    // Display Selected date in textbox
                    tie_form_tanggal.setText(
                        "$year-${String.format("%02d", (monthOfYear + 1))}-" +
                                String.format("%02d", (dayOfMonth))
                    )
                    tanggal = "$year-${String.format("%02d", (monthOfYear + 1))}-" +
                            "${String.format("%02d", (dayOfMonth))}"

                },
                year,
                month,
                day
            )

//            dpd.datePicker.minDate = System.currentTimeMillis() - 1000
//            dpd.datePicker.maxDate = System.currentTimeMillis() + 259200000

            dpd.show()
        }

        btn_register.setOnClickListener {
            when {
                tie_nik.text.toString().isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        "Masukkan NIK terlebih dahulu", Toast.LENGTH_SHORT
                    ).show()
                }
                tie_nama.text.toString().isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        "Masukkan Nama terlebih dahulu", Toast.LENGTH_SHORT
                    ).show()
                }
                tie_no_hp.text.toString().isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        "Masukkan No Hp terlebih dahulu", Toast.LENGTH_SHORT
                    ).show()
                }
                tie_reg_email.text.toString().isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        "Masukkan Email terlebih dahulu", Toast.LENGTH_SHORT
                    ).show()
                }
                tie_tempat_lahir.text.toString().isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        "Masukkan Tempat Lahir terlebih dahulu", Toast.LENGTH_SHORT
                    ).show()
                }
                tanggal.isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        "Masukkan Tanggal Lahir terlebih dahulu", Toast.LENGTH_SHORT
                    ).show()
                }
                tie_orangtua.text.toString().isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        "Masukkan Nama Orangtua terlebih dahulu", Toast.LENGTH_SHORT
                    ).show()
                }
                tie_address.text.toString().isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        "Masukkan Alamat terlebih dahulu", Toast.LENGTH_SHORT
                    ).show()
                }
                goldar.isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        "Pilih Golongan darah terlebih dahulu", Toast.LENGTH_SHORT
                    ).show()
                }
                pendidikan.isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        "Pilih Pendidikan terlebih dahulu", Toast.LENGTH_SHORT
                    ).show()
                }
                statusNikah.isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        "Pilih Status Nikah terlebih dahulu", Toast.LENGTH_SHORT
                    ).show()
                }
                jenisKelamin.isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        "Pilih Jenis Kelamin terlebih dahulu", Toast.LENGTH_SHORT
                    ).show()
                }
                photoKK == null -> {
                    Toast.makeText(
                        requireContext(), "Pilih Foto KK Terlebih dahulu",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                photoKTP == null -> {
                    Toast.makeText(
                        requireContext(), "Pilih Foto KTP Terlebih dahulu",
                        Toast.LENGTH_SHORT
                    ).show()
                }

//                photoBPJS == null -> {
//                    Toast.makeText(
//                        requireContext(), "Pilih Foto BPJS Terlebih dahulu",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }

                else -> {
                    showProgressLoading()
                    authViewModel.updateProfile(
                        RequestBodies.EditProfileBody(
                            sharedPreferenceHelper.getNoRm() ?: "",
                            tie_nik.text.toString(),
                            tie_nama.text.toString(),
                            tie_tempat_lahir.text.toString(),
                            tanggal,
                            goldar,
                            tie_no_hp.text.toString(),
                            tie_work.text.toString(),
                            tie_edu.text.toString(),
                            statusNikah,
                            tie_orangtua.text.toString(),
                            jenisKelamin,
                            tie_address.text.toString()
                        )
                    )
                }
            }
        }


        tie_goldar.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                goldar = if (position > 0) {
                    tie_goldar.text.toString()
                } else {
                    ""
                }
            }
        }

        tie_edu.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                pendidikan = if (position > 0) {
                    tie_goldar.text.toString()
                } else {
                    ""
                }
            }
        }

        tie_status_nikah.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                statusNikah = if (position > 0) {
                    tie_goldar.text.toString()
                } else {
                    ""
                }
            }
        }

        tie_jenis_kelamin.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                jenisKelamin = if (position > 0) {
                    tie_goldar.text.toString()
                } else {
                    ""
                }
            }
        }

        authViewModel.uploadImgResponse.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { event ->
                event.getContentIfNotHandled()?.let { response ->
                    when (response) {
                        is Resource.Success -> {
//                            hideProgressBar()
                            response.data?.let { loginResponse ->
//                            showDialogSuccess()
                                authViewModel.uploadImageKTP(
                                    RequestBodies.UploadImageBody(
                                        sharedPreferenceHelper.getNoRm().toString(),
                                        encode(photoKTP!!),
                                        "ktp"
                                    )
                                )
                            }
                        }

                        is Resource.Error -> {
                            hideProgressBar()
                            response.message?.let { message ->
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                                println("Error $message")
                            }

                        }

                        is Resource.Loading -> {
                            showProgressLoading()
                        }
                    }
                }
            })

        authViewModel.uploadImgResponseKTP.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { event ->
                event.getContentIfNotHandled()?.let { response ->
                    when (response) {
                        is Resource.Success -> {
//                            hideProgressBar()
                            response.data?.let { loginResponse ->
//                            showDialogSuccess()

                                if (photoBPJS != null){
                                    authViewModel.uploadImageBPJS(
                                        RequestBodies.UploadImageBody(
                                            sharedPreferenceHelper.getNoRm().toString(),
                                            encode(photoBPJS!!),
                                            "bpjs"
                                        )
                                    )
                                }else{
                                    hideProgressBar()
                                    showDialogSuccess()
                                }
                            }
                        }

                        is Resource.Error -> {
                            hideProgressBar()
                            response.message?.let { message ->
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                                println("Error $message")
                            }

                        }

                        is Resource.Loading -> {
                            showProgressLoading()
                        }
                    }
                }
            })

        authViewModel.uploadImgResponseBPJS.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { event ->
                event.getContentIfNotHandled()?.let { response ->
                    when (response) {
                        is Resource.Success -> {
                            hideProgressBar()
                            response.data?.let { loginResponse ->
                                showDialogSuccess()
                            }
                        }

                        is Resource.Error -> {
                            hideProgressBar()
                            response.message?.let { message ->
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                                println("Error $message")
                            }

                        }

                        is Resource.Loading -> {
                            showProgressLoading()
                        }
                    }
                }
            })



        authViewModel.updateProfileResponse.observe(
            viewLifecycleOwner, { event ->
                event.getContentIfNotHandled()?.let { response ->
                    when (response) {
                        is Resource.Success -> {
                            response.data?.let { loginResponse ->
//                            showDialogSuccess()
                                authViewModel.uploadImageKK(
                                    RequestBodies.UploadImageBody(
                                        sharedPreferenceHelper.getNoRm().toString(),
                                        encode(photoKK!!),
                                        "kk"
                                    )
                                )
                            }
                        }

                        is Resource.Error -> {
                            hideProgressBar()
                            response.message?.let { message ->
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                                println("Error $message")
                            }

                        }

                        is Resource.Loading -> {
                            showProgressLoading()
                        }
                    }
                }
            })

        imgAtraksi.setOnClickListener {
            if (Helper.checkAppPermission(requireActivity())) {
                val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
                val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
                builder.setTitle("Choose your profile picture")
                builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
                    if (options[item] == "Take Photo") {
                        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(takePicture, 0)
                    } else if (options[item] == "Choose from Gallery") {
                        val pickPhoto =
                            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(pickPhoto, 1)
                    } else if (options[item] == "Cancel") {
                        dialog.dismiss()
                    }
                })
                builder.show()
            } else {
                Helper.requestPermissionCamera(requireActivity())
            }
        }

        imgKTP.setOnClickListener {
            if (Helper.checkAppPermission(requireActivity())) {
                val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
                val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
                builder.setTitle("Choose your profile picture")
                builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
                    if (options[item] == "Take Photo") {
                        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(takePicture, 2)
                    } else if (options[item] == "Choose from Gallery") {
                        val pickPhoto =
                            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(pickPhoto, 3)
                    } else if (options[item] == "Cancel") {
                        dialog.dismiss()
                    }
                })
                builder.show()
            } else {
                Helper.requestPermissionCamera(requireActivity())
            }
        }

        imgBPJS.setOnClickListener {
            if (Helper.checkAppPermission(requireActivity())) {
                val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
                val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
                builder.setTitle("Choose your profile picture")
                builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
                    if (options[item] == "Take Photo") {
                        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(takePicture, 4)
                    } else if (options[item] == "Choose from Gallery") {
                        val pickPhoto =
                            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(pickPhoto, 5)
                    } else if (options[item] == "Cancel") {
                        dialog.dismiss()
                    }
                })
                builder.show()
            } else {
                Helper.requestPermissionCamera(requireActivity())
            }
        }
    }

    private fun setValueFromShared() {
        tie_nama.setText(sharedPreferenceHelper.getNama() ?: "")
        tie_reg_email.setText(sharedPreferenceHelper.getEmail() ?: "")

        val goldarItems = resources.getStringArray(R.array.array_goldar)

        val goldarAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu, goldarItems)
        (tie_goldar as? AutoCompleteTextView)?.setAdapter(goldarAdapter)

        val pendidikanItems = resources.getStringArray(R.array.array_pendidikan)

        val pendidikanAdapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_menu,
            pendidikanItems
        )
        (tie_edu as? AutoCompleteTextView)?.setAdapter(pendidikanAdapter)

        val statusNikahItems = resources.getStringArray(R.array.array_status_nikah)

        val statusNikahAdapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_menu,
            statusNikahItems
        )
        (tie_status_nikah as? AutoCompleteTextView)?.setAdapter(statusNikahAdapter)

        val jkItems = resources.getStringArray(R.array.array_jenis_kelamin)

        val jkAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu, jkItems)
        (tie_jenis_kelamin as? AutoCompleteTextView)?.setAdapter(jkAdapter)


        //Define Array View
        val dropdownMenu = listOf(
            "Tn",
            "Ny",
            "Nn",
            "An",
            "By"
        )

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_menu,
            dropdownMenu
        )

        (tie_spinner as? AutoCompleteTextView)?.setAdapter(adapter)

    }

    private fun hideProgressBar() {
        pb_reg_loading.visibility = View.GONE
    }

    private fun showProgressLoading() {
        pb_reg_loading.visibility = View.VISIBLE
    }

    private fun showDialogSuccess() {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.dialog_success_register, null)
        val builder = AlertDialog.Builder(context)
        builder.setView(view)
        val alertDialog = builder.create()


        val buttonAllow = view.findViewById<Button>(R.id.btnAllow)
        view.findViewById<TextView>(R.id.desc).text = "Berhasil Update Profile"
        buttonAllow.setOnClickListener {
            alertDialog.dismiss()
            findNavController().navigateUp()
        }

        alertDialog.show()

    }

    private fun encode(photo: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
        return encodeToString(byteArray, Base64.DEFAULT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("CEKIMAGE", "onActivityResult: $resultCode")
        if (resultCode != AppCompatActivity.RESULT_CANCELED) {
            when (requestCode) {
                0 -> if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                    photoKK = data.extras?.get("data") as Bitmap?
                    Log.d("CEKIMAGE", "onActivityResult: Camera")
                    Glide.with(requireActivity())
                        .load(photoKK)
                        .into(imgAtraksiIn)
                }
                1 -> if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                    val selectedImage: Uri = data.getData()!!
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    if (selectedImage != null) {
                        val cursor: Cursor? = requireActivity().contentResolver.query(
                            selectedImage,
                            filePathColumn, null, null, null
                        )
                        if (cursor != null) {
                            Log.d("CEKIMAGE", "onActivityResult: Gallery")
                            cursor.moveToFirst()
                            val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
                            val picturePath: String = cursor.getString(columnIndex)
                            val imgFile = File(picturePath)
                            if (imgFile.exists()) {
                                photoKK = BitmapFactory.decodeFile(imgFile.absolutePath)
                                //Drawable d = new BitmapDrawable(getResources(), myBitmap);
                            }
//                            imgWisata.setImageBitmap(BitmapFactory.decodeFile(picturePath))
                            Glide.with(requireActivity())
                                .load(photoKK)
                                .into(imgAtraksiIn)
                            cursor.close()
                        }
                    }
                }

                2 -> if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                    photoKTP = data.extras?.get("data") as Bitmap?
                    Log.d("CEKIMAGE", "onActivityResult: Camera")
                    Glide.with(requireActivity())
                        .load(photoKTP)
                        .into(imgKTPIn)
                }
                3 -> if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                    val selectedImage: Uri = data.getData()!!
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    if (selectedImage != null) {
                        val cursor: Cursor? = requireActivity().contentResolver.query(
                            selectedImage,
                            filePathColumn, null, null, null
                        )
                        if (cursor != null) {
                            Log.d("CEKIMAGE", "onActivityResult: Gallery")
                            cursor.moveToFirst()
                            val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
                            val picturePath: String = cursor.getString(columnIndex)
                            val imgFile = File(picturePath)
                            if (imgFile.exists()) {
                                photoKTP = BitmapFactory.decodeFile(imgFile.absolutePath)
                                //Drawable d = new BitmapDrawable(getResources(), myBitmap);
                            }
//                            imgWisata.setImageBitmap(BitmapFactory.decodeFile(picturePath))
                            Glide.with(requireActivity())
                                .load(photoKTP)
                                .into(imgKTPIn)
                            cursor.close()
                        }
                    }
                }

                4 -> if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                    photoBPJS = data.extras?.get("data") as Bitmap?
                    Log.d("CEKIMAGE", "onActivityResult: Camera")
                    Glide.with(requireActivity())
                        .load(photoBPJS)
                        .into(imgBPJSIn)
                }
                5 -> if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                    val selectedImage: Uri = data.getData()!!
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    if (selectedImage != null) {
                        val cursor: Cursor? = requireActivity().contentResolver.query(
                            selectedImage,
                            filePathColumn, null, null, null
                        )
                        if (cursor != null) {
                            Log.d("CEKIMAGE", "onActivityResult: Gallery")
                            cursor.moveToFirst()
                            val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
                            val picturePath: String = cursor.getString(columnIndex)
                            val imgFile = File(picturePath)
                            if (imgFile.exists()) {
                                photoBPJS = BitmapFactory.decodeFile(imgFile.absolutePath)
                                //Drawable d = new BitmapDrawable(getResources(), myBitmap);
                            }
//                            imgWisata.setImageBitmap(BitmapFactory.decodeFile(picturePath))
                            Glide.with(requireActivity())
                                .load(photoBPJS)
                                .into(imgBPJSIn)
                            cursor.close()
                        }
                    }
                }
            }
        }
    }
}