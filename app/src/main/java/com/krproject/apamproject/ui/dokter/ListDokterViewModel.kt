package com.krproject.apamproject.ui.dokter

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.krproject.apamproject.data.network.Resource
import com.krproject.apamproject.data.response.dokter.ResponseGetDokter
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

class ListDokterViewModel(
    app: Application,
    private val appRepository: AppRepository
) : AndroidViewModel(app) {

    private val _dokterResponse: MutableLiveData<Event<Resource<ResponseGetDokter>>> = MutableLiveData()
    private val disposable = CompositeDisposable()
    val jadwalResponse: LiveData<Event<Resource<ResponseGetDokter>>>
        get() = _dokterResponse


    fun daftarDokter() =
        getListDokter()


    private fun getListDokter() {
        _dokterResponse.postValue(Event(Resource.Loading()))
        disposable.add(
            appRepository.getListDokter()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseGetDokter>() {
                    override fun onSuccess(t: ResponseGetDokter) {
                        Log.d(TAG, "onSuccess: $t")
                        if (t.header.result == "true") {
                            _dokterResponse.postValue(Event(Resource.Success(t)))
                        } else {
                            Log.d(TAG, "onError: Login gagal")
                            _dokterResponse.postValue(Event(Resource.Error(t.header.resultMessage)))
                        }
                    }

                    override fun onError(e: Throwable) {
                        try {
                            e.printStackTrace()
                            when (e) {
                                is HttpException -> {
                                    Log.d(TAG, "onError: HTTP Excep")

                                    _dokterResponse.postValue(Event(Resource.Error(e.message())))
                                }
                                is SocketTimeoutException -> {
                                    _dokterResponse.postValue(Event(Resource.Error("Timeout")))
                                }
                                is IOException -> {
                                    _dokterResponse.postValue(Event(Resource.Error("Error Connection")))
                                }
                                else -> {
                                    Log.d(TAG, "onError: Else")
                                    _dokterResponse.postValue(Event(Resource.Error(e.message!!)))
                                }
                            }

                        } catch (e: Exception) {
                            Log.d(TAG, "onError: catch")
                            e.printStackTrace()
                            _dokterResponse.postValue(Event(Resource.Error(e.message!!)))
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