package com.krproject.apamprojectnew.util

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


object Helper {

    private val PERM = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private val PERM_CODE = 99

    fun selectImage(activity: Activity) {
        if (checkAppPermission(activity)){
            val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
            val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
            builder.setTitle("Choose your profile picture")
            builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
                if (options[item] == "Take Photo") {
                    val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    activity.startActivityForResult(takePicture, 0)
                } else if (options[item] == "Choose from Gallery") {
                    val pickPhoto =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activity.startActivityForResult(pickPhoto, 1)
                } else if (options[item] == "Cancel") {
                    dialog.dismiss()
                }
            })
            builder.show()
        }else{
            requestPermissionCamera(activity)
        }

    }

    fun checkAppPermission(context: Context): Boolean {
        val cameraPerm = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        )
        val writePerm = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val readPerm = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return (cameraPerm == PackageManager.PERMISSION_GRANTED &&
                writePerm == PackageManager.PERMISSION_GRANTED &&
                readPerm == PackageManager.PERMISSION_GRANTED)
    }

    fun requestPermissionCamera(activity: Activity){
        ActivityCompat.requestPermissions(activity, PERM, PERM_CODE)
    }

    fun call(numbers: String, activity: Activity){
        val number: Uri = Uri.parse("tel:$numbers")
        val callIntent = Intent(Intent.ACTION_DIAL, number)
        activity.startActivity(callIntent)
    }

    fun openMap(context: Context, yourAddress: String){
        val map = "http://maps.google.co.in/maps?q=$yourAddress"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(map))
        context.startActivity(intent)
    }
}