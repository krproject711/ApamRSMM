package com.krproject.apamprojectnew.ui.reservasi

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.krproject.apamprojectnew.data.network.Resource
import com.krproject.apamprojectnew.data.response.auth.ReservasiResponse
import com.krproject.apamprojectnew.repository.AppRepository
import com.krproject.apamprojectnew.util.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class ReservasiViewModel(
    app: Application,
    private val appRepository: AppRepository
) : AndroidViewModel(app) {

    private val disposable = CompositeDisposable()
    private val _reservasiResponse: MutableLiveData<Event<Resource<ReservasiResponse>>> = MutableLiveData()
    val reservasiResponse: LiveData<Event<Resource<ReservasiResponse>>>
        get() = _reservasiResponse


    fun registerUser(noRM: String, nama_pas: String, drName: String,
                     userPoli: String, transMethod: String,
                     email: String, tanggal: String)
    =register(noRM, nama_pas, drName, userPoli, transMethod, email, tanggal)

    private fun register(noRM: String, nama_pas: String, drName: String,
                         userPoli: String, transMethod: String,
                         email: String, tanggal: String) {
        _reservasiResponse.postValue(Event(Resource.Loading()))
        disposable.add(
            appRepository.reservasi(noRM, nama_pas, drName, userPoli, transMethod, email, tanggal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ReservasiResponse>() {
                    override fun onSuccess(t: ReservasiResponse) {
                        Log.d(TAG, "onSuccess: $t")
                        if (t.header.result == "true") {
                            _reservasiResponse.postValue(Event(Resource.Success(t)))
                        } else {
                            Log.d(TAG, "onError: Reservasi gagal")
                            _reservasiResponse.postValue(Event(Resource.Error(t.header.resultMessage)))
                        }
                    }

                    override fun onError(e: Throwable) {
                        try {
                            e.printStackTrace()
                            when (e) {
                                is HttpException -> {
                                    Log.d(TAG, "onError: HTTP Excep")
                                    _reservasiResponse.postValue(Event(Resource.Error(e.message())))
                                }
                                is SocketTimeoutException -> {
                                    _reservasiResponse.postValue(Event(Resource.Error("Timeout")))
                                }
                                is IOException -> {
                                    _reservasiResponse.postValue(Event(Resource.Error("Error Connection")))
                                }
                                else -> {
                                    Log.d(TAG, "onError: Else")
                                    _reservasiResponse.postValue(Event(Resource.Error(e.message!!)))
                                }
                            }

                        } catch (e: Exception) {
                            Log.d(TAG, "onError: catch")
                            e.printStackTrace()
                            _reservasiResponse.postValue(Event(Resource.Error(e.message!!)))
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