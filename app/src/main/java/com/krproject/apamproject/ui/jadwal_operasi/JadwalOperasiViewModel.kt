package com.krproject.apamproject.ui.jadwal_operasi

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.krproject.apamproject.data.network.RequestBodies
import com.krproject.apamproject.data.network.Resource
import com.krproject.apamproject.data.response.LoginResponse
import com.krproject.apamproject.data.response.RegisterResponse
import com.krproject.apamproject.data.response.dokter.GetJadwalDokterResponse
import com.krproject.apamproject.data.response.operasi.GetJadwalOperasi
import com.krproject.apamproject.repository.AppRepository
import com.krproject.apamproject.ui.base.MyApplication
import com.krproject.apamproject.util.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class JadwalOperasiViewModel(
    app: Application,
    private val appRepository: AppRepository
) : AndroidViewModel(app) {

    private val _jadwalResponse: MutableLiveData<Event<Resource<GetJadwalOperasi>>> = MutableLiveData()
    private val disposable = CompositeDisposable()
    val jadwalResponse: LiveData<Event<Resource<GetJadwalOperasi>>>
        get() = _jadwalResponse


    fun jadwalOperasi(search: String) =
        getJadwalOperasi(search)


    private fun getJadwalOperasi(search: String) {
        _jadwalResponse.postValue(Event(Resource.Loading()))
        disposable.add(
            appRepository.getJadwalOperasi(search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<GetJadwalOperasi>() {
                    override fun onSuccess(t: GetJadwalOperasi) {
                        Log.d(TAG, "onSuccess: $t")
                        if (t.header.result == "true") {
                            _jadwalResponse.postValue(Event(Resource.Success(t)))
                        } else {
                            Log.d(TAG, "onError: Login gagal")
                            _jadwalResponse.postValue(Event(Resource.Error(t.header.resultMessage)))
                        }
                    }

                    override fun onError(e: Throwable) {
                        try {
                            e.printStackTrace()
                            when (e) {
                                is HttpException -> {
                                    Log.d(TAG, "onError: HTTP Excep")

                                    _jadwalResponse.postValue(Event(Resource.Error(e.message())))
                                }
                                is SocketTimeoutException -> {
                                    _jadwalResponse.postValue(Event(Resource.Error("Timeout")))
                                }
                                is IOException -> {
                                    _jadwalResponse.postValue(Event(Resource.Error("Error Connection")))
                                }
                                else -> {
                                    Log.d(TAG, "onError: Else")
                                    _jadwalResponse.postValue(Event(Resource.Error(e.message!!)))
                                }
                            }

                        } catch (e: Exception) {
                            Log.d(TAG, "onError: catch")
                            e.printStackTrace()
                            _jadwalResponse.postValue(Event(Resource.Error(e.message!!)))
                        }
                    }

                })

        )

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


    companion object {
        private const val TAG = "AuthViewModel"
    }
}