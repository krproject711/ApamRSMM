package com.krproject.apamprojectnew.ui.dashboard

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.krproject.apamprojectnew.R
import com.krproject.apamprojectnew.data.network.Resource
import com.krproject.apamprojectnew.databinding.FragmentBerandaBinding
import com.krproject.apamprojectnew.repository.AppRepository
import com.krproject.apamprojectnew.ui.base.BaseFragment
import com.krproject.apamprojectnew.ui.base.ViewModelProviderFactory
import com.krproject.apamprojectnew.ui.tiket.ListTiketViewModel
import com.krproject.apamprojectnew.ui.tiket.LkisTiketAdapter
import com.krproject.apamprojectnew.util.SharedPreferenceHelper
import com.synnapps.carouselview.ImageListener
import java.io.*

class BerandaFragment : BaseFragment<FragmentBerandaBinding>() {

    lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    lateinit var tiketViewModel: ListTiketViewModel
    lateinit var tiketAdapter: LkisTiketAdapter

    private val imageList = intArrayOf(
        R.drawable.car_covid19,
        R.drawable.car_wash,
        R.drawable.car_contact
    )

    private val PERM = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    private val PERM_CODE = 99

    private val PICK_IMAGE_CAMERA = 11
    private val PICK_IMAGE_GALLERY = 12

    private val imageListListener = ImageListener {
            position, imageView -> imageView.setImageResource(imageList[position])
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()

        sharedPreferenceHelper = SharedPreferenceHelper(requireContext())

        binding.daftar.setOnClickListener {
            findNavController().navigate(R.id.action_berandaFragment3_to_poliklinkFormFragment)
        }

        binding.btnJadwalOperasi.setOnClickListener {
            findNavController().navigate(R.id.action_berandaFragment3_to_jadwalOperasiFragment)
        }

        binding.jadwalDokter.setOnClickListener {
            findNavController().navigate(R.id.action_berandaFragment3_to_jadwalDokterFragment)
        }

        binding.btnLogout.setOnClickListener {
            findNavController().navigate(R.id.action_berandaFragment3_to_welcomeFragment)
        }

        binding.tvHistory.setOnClickListener {
            findNavController().navigate(R.id.action_berandaFragment3_to_historyFragment)
        }

        binding.imageView4.setOnClickListener {

            selectAction(requireContext())
//            findNavController().navigate(R.id.action_berandaFragment3_to_profileFragment)
        }



        binding.imageSlider.setImageListener(imageListListener)
        binding.imageSlider.pageCount = imageList.size

        binding.nama.text = sharedPreferenceHelper.getNama()
        binding.noRm.text = sharedPreferenceHelper.getNoRm()


        binding.btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Logout")
            builder.setMessage("Apakah Anda yakin ingin Logout?")
            builder.setPositiveButton("Ya"
            ) { dialog, which ->
                dialog?.dismiss()
                logout()
            }

            .setNegativeButton("Tidak"
            ) { dialog, which -> dialog?.dismiss() }

                .show()

        }

        showAvatar(requireContext(), binding.imageView4, R.drawable.user)

//        getTiket(sharedPreferenceHelper.getNoRm()?:"")

    }

    override fun onResume() {
        super.onResume()
        getTiket(sharedPreferenceHelper.getNoRm()?:"")
    }

    private fun getTiket(noRm: String) {
        tiketAdapter.clearJadwalDokter()
        tiketViewModel.daftarTiket(noRm)
        tiketViewModel.jadwalResponse.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let { loginResponse ->
//                            Toast.makeText(
//                                requireContext(),
//                                "Berhasil get ${loginResponse.body.data.size}",
//                                Toast.LENGTH_SHORT
//                            ).show()

                            tiketAdapter.setJadwalDokter(loginResponse.body.data)

                            tiketAdapter.mOnItemClickListener = object : LkisTiketAdapter.OnItemClickListener{
                                override fun onItemClick() {
                                    val bundle = bundleOf("responseTiket" to loginResponse)
//                                    navController.navigate(R.id.action_specifyAmountFragment_to_confirmationFragment2, bundle)
                                    findNavController().navigate(R.id.action_berandaFragment3_to_historyFragment, bundle)
                                }

                            }

                            with(binding.rvTiket) {
                                layoutManager = LinearLayoutManager(context)
                                setHasFixedSize(true)
                                adapter = tiketAdapter
                                visibility = View.VISIBLE
                            }
                        }
                    }

                    is Resource.Error -> {
                        response.message?.let { message ->
//                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                            println("Error $message")
                        }

                    }

                    is Resource.Loading -> {

                    }
                }
            }
        })
    }

    private fun init(){
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(requireActivity().application, repository)
        tiketViewModel = ViewModelProvider(this, factory).get(ListTiketViewModel::class.java)
        tiketAdapter = LkisTiketAdapter(requireActivity())
    }

    private fun logout(){
        sharedPreferenceHelper.clearAllDataShared()
        findNavController().navigate(R.id.action_berandaFragment3_to_welcomeFragment)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentBerandaBinding.inflate(inflater, container, false)

    private fun selectAction(context: Context) {
        try {
                val options = arrayOf<CharSequence>(
                    getString(R.string.ambil_foto),
                    getString(R.string.update_profile),
                    getString(R.string.batal)
                )
                val builder = androidx.appcompat.app.AlertDialog.Builder(context)
                builder.setTitle(getString(R.string.pilihopsi))
                builder.setItems(options) { dialog, item ->
                    when {
                        options[item] == getString(R.string.ambil_foto) -> {

                            selectImage(requireContext())
                        }
                        options[item] == getString(R.string.update_profile) -> {
                            dialog.dismiss()
                            findNavController().navigate(R.id.action_berandaFragment3_to_profileFragment)
                        }
                        options[item] == getString(R.string.batal) -> {
                            dialog.dismiss()
                        }
                    }
                }
                builder.show()

        } catch (e: Exception) {
            Toast.makeText(context, context.getString(R.string.kesalahan_izin), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun selectImage(context: Context) {
        try {
            if (checkAppPermission(context)) {
                val options = arrayOf<CharSequence>(
                    getString(R.string.dari_kamera),
                    getString(R.string.pilih_dari_galeri),
                    getString(R.string.batal)
                )
                val builder = androidx.appcompat.app.AlertDialog.Builder(context)
                builder.setTitle(getString(R.string.pilihopsi))
                builder.setItems(options) { dialog, item ->
                    if (options[item] == getString(R.string.dari_kamera)) {
                        dialog.dismiss()

                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                        startActivityForResult(intent, PICK_IMAGE_CAMERA)
                    } else if (options[item] == getString(R.string.pilih_dari_galeri)) {
                        dialog.dismiss()
                        val pickPhoto =
                            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY)
                    } else if (options[item] == getString(R.string.batal)) {
                        dialog.dismiss()
                    }
                }
                builder.show()
            } else {
                ActivityCompat.requestPermissions(requireActivity(), PERM, PERM_CODE)
            }
        } catch (e: Exception) {
            Toast.makeText(context, context.getString(R.string.kesalahan_izin), Toast.LENGTH_SHORT)
                .show()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val ctx = this
            var destination: File? = null
            var source: Uri? = null
            var bitmap: Bitmap? = null
                when (requestCode) {
                    PICK_IMAGE_CAMERA -> {
                        destination = getOutputMediaFile(requireContext(), getString(R.string.filePhoto))

                        val thumbnail = data!!.extras!!["data"] as Bitmap?
                        val bytes = ByteArrayOutputStream()
                        thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
                        val fo: FileOutputStream
                        try {
                            destination!!.createNewFile()
                            fo = FileOutputStream(destination)
                            fo.write(bytes.toByteArray())
                            fo.close()
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        requireActivity().runOnUiThread {
                            showAvatar(requireContext(), binding.imageView4, R.drawable.user)
                        }
                    }
                    PICK_IMAGE_GALLERY -> {
                        source = data?.data
                        try {
                            if (Build.VERSION.SDK_INT < 28) {
                                bitmap = rotateImageIfRequired(
                                    requireContext(),
                                    MediaStore.Images.Media.getBitmap(
                                        requireContext().contentResolver,
                                        source
                                    ),
                                    source!!
                                )
                            } else {
                                val sourceDecoder =
                                    ImageDecoder.createSource(requireContext().contentResolver, source!!)
                                bitmap = rotateImageIfRequired(
                                    requireContext(),
                                    ImageDecoder.decodeBitmap(sourceDecoder),
                                    source!!
                                )
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        bitmap?.let {
                            val destination =
                                getOutputMediaFile(requireContext(), getString(R.string.filePhoto))
                            val fo: FileOutputStream
                            val bytesBitmap = ByteArrayOutputStream()
                            it.compress(Bitmap.CompressFormat.JPEG, 50, bytesBitmap)
                            try {
                                destination.createNewFile()
                                fo = FileOutputStream(destination)
                                fo.write(bytesBitmap.toByteArray())
                                fo.close()
                            } catch (e: FileNotFoundException) {
                                e.printStackTrace()
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                            requireActivity().runOnUiThread {
                                showAvatar(requireContext(), binding.imageView4, R.drawable.user)
                            }
                        }
                    }
                    else -> null
                }
        }
        super.onActivityResult(requestCode, resultCode, data)

    }

    fun getOutputMediaFile(context: Context, name: String): File {
        val mediaStorageDir = getOutputMediaDir(context)
        return mediaStorageDir.let { File(it, name) }
    }

    fun getOutputMediaDir(context: Context): File? {
        val mediaStorageDir = File(
            context.getString(R.string.file_location) + getApplicationName(context)
        )
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }
        return mediaStorageDir
    }

    fun getApplicationName(context: Context): String? {
        val applicationInfo: ApplicationInfo = context.applicationInfo
        val stringId: Int = applicationInfo.labelRes
        return if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else context.getString(
            stringId
        )
    }

    fun showAvatar(context: Context, imageView: ImageView, placeholder: Int) {
        try {
            val permStorage: Int = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            if (permStorage == PackageManager.PERMISSION_GRANTED) {

                val f: File = getOutputMediaFile(context, context.getString(R.string.filePhoto))
                if (f != null && f.exists()) {
                    Log.d("CEKAVATAR", "showAvatar: isSet")
                    Glide.with(context).load(f)
                        .apply(RequestOptions.circleCropTransform())
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .placeholder(context.getResources().getDrawable(placeholder))
                        .into(imageView);
                }else{
                    //add Moengage event if profile picture not set yet
                    Log.d("CEKAVATAR", "showAvatar: is Not Set Yet")
                }
            }else{
                //add Moengage event if profile picture not set yet
                Log.d("CEKAVATAR", "showAvatar: permission is not allowed")
            }
        } catch (e: Resources.NotFoundException) {

        }
    }

    @Throws(IOException::class)
    fun rotateImageIfRequired(
        context: Context,
        img: Bitmap,
        selectedImage: Uri
    ): Bitmap? {
        val input =
            context.contentResolver.openInputStream(selectedImage)
        val ei: ExifInterface
        ei = if (Build.VERSION.SDK_INT > 23) ExifInterface(input!!) else ExifInterface(
            selectedImage.path?:""
        )
        val orientation = ei.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )
        return  when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(
                img,
                90f
            )
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(
                img,
                180f
            )
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(
                img,
                270f
            )
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> flip(
                img,
                true,
                false
            )
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> flip(
                img,
                false,
                true
            )
            else -> img
        }
    }

    fun rotateImage(
        source: Bitmap,
        angle: Float
    ): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }

    private fun flip(
        bitmap: Bitmap,
        horizontal: Boolean,
        vertical: Boolean
    ): Bitmap? {
        val matrix = Matrix()
        matrix.preScale(if (horizontal) -1f else 1f, if (vertical) -1f else 1f)
        return Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    }
}