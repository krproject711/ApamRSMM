package com.krproject.apamproject.ui.poli

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.krproject.apamproject.data.network.Resource
import com.krproject.apamproject.data.response.operasi.GetJadwalOperasi
import com.krproject.apamproject.data.response.poli.ResponseListPoli
import com.krproject.apamproject.repository.AppRepository
import com.krproject.apamproject.util.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class ListPoliViewModel(
    app: Application,
    private val appRepository: AppRepository
) : AndroidViewModel(app) {

    private val _poliResponse: MutableLiveData<Event<Resource<ResponseListPoli>>> = MutableLiveData()
    private val disposable = CompositeDisposable()
    val jadwalResponse: LiveData<Event<Resource<ResponseListPoli>>>
        get() = _poliResponse


    fun jadwalOperasi() =
        getListPoli()


    private fun getListPoli() {
        _poliResponse.postValue(Event(Resource.Loading()))
        disposable.add(
            appRepository.getListPoli()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseListPoli>() {
                    override fun onSuccess(t: ResponseListPoli) {
                        Log.d(TAG, "onSuccess: $t")
                        if (t.header.result == "true") {
                            _poliResponse.postValue(Event(Resource.Success(t)))
                        } else {
                            Log.d(TAG, "onError: Login gagal")
                            _poliResponse.postValue(Event(Resource.Error(t.header.resultMessage)))
                        }
                    }

                    override fun onError(e: Throwable) {
                        try {
                            e.printStackTrace()
                            when (e) {
                                is HttpException -> {
                                    Log.d(TAG, "onError: HTTP Excep")

                                    _poliResponse.postValue(Event(Resource.Error(e.message())))
                                }
                                is SocketTimeoutException -> {
                                    _poliResponse.postValue(Event(Resource.Error("Timeout")))
                                }
                                is IOException -> {
                                    _poliResponse.postValue(Event(Resource.Error("Error Connection")))
                                }
                                else -> {
                                    Log.d(TAG, "onError: Else")
                                    _poliResponse.postValue(Event(Resource.Error(e.message!!)))
                                }
                            }

                        } catch (e: Exception) {
                            Log.d(TAG, "onError: catch")
                            e.printStackTrace()
                            _poliResponse.postValue(Event(Resource.Error(e.message!!)))
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