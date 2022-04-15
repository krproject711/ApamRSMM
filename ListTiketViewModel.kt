package com.krproject.apamprojectnew.ui.tiket

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.krproject.apamprojectnew.data.network.Resource
import com.krproject.apamprojectnew.repository.AppRepository
import com.krproject.apamprojectnew.util.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class ListTiketViewModel(
    app: Application,
    private val appRepository: AppRepository
) : AndroidViewModel(app) {

    private val _tiketResponse: MutableLiveData<Event<Resource<ResponseTiket>>> = MutableLiveData()
    private val disposable = CompositeDisposable()
    val jadwalResponse: LiveData<Event<Resource<ResponseTiket>>>
        get() = _tiketResponse


    fun daftarTiket(noRm: String) =
        getListTiket(noRm)

    private fun getListTiket(noRm: String) {
        _tiketResponse.postValue(Event(Resource.Loading()))
        disposable.add(
            appRepository.getListTiket(noRm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseTiket>() {
                    override fun onSuccess(t: ResponseTiket) {
                        Log.d(TAG, "onSuccess: $t")
                        if (t.header.result == "true") {
                            _tiketResponse.postValue(Event(Resource.Success(t)))
                        } else {
                            Log.d(TAG, "onError: Login gagal")
                            _tiketResponse.postValue(Event(Resource.Error(t.header.resultMessage?:"")))
                        }
                    }

                    override fun onError(e: Throwable) {
                        try {
                            e.printStackTrace()
                            when (e) {
                                is HttpException -> {
                                    Log.d(TAG, "onError: HTTP Excep")

                                    _tiketResponse.postValue(Event(Resource.Error(e.message())))
                                }
                                is SocketTimeoutException -> {
                                    _tiketResponse.postValue(Event(Resource.Error("Timeout")))
                                }
                                is IOException -> {
                                    _tiketResponse.postValue(Event(Resource.Error("Error Connection")))
                                }
                                else -> {
                                    Log.d(TAG, "onError: Else")
                                    _tiketResponse.postValue(Event(Resource.Error(e.message!!)))
                                }
                            }

                        } catch (e: Exception) {
                            Log.d(TAG, "onError: catch")
                            e.printStackTrace()
                            _tiketResponse.postValue(Event(Resource.Error(e.message!!)))
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