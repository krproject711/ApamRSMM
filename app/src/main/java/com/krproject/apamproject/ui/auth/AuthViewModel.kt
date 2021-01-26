package com.krproject.apamproject.ui.auth

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.krproject.apamproject.data.network.RequestBodies
import com.krproject.apamproject.data.network.Resource
import com.krproject.apamproject.data.response.LoginResponse
import com.krproject.apamproject.data.response.RegisterResponse
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

class AuthViewModel(
    app: Application,
    private val appRepository: AppRepository
) : AndroidViewModel(app) {

    private val _loginResponse: MutableLiveData<Event<Resource<LoginResponse>>> = MutableLiveData()
    private val disposable = CompositeDisposable()
    val loginResponse: LiveData<Event<Resource<LoginResponse>>>
        get() = _loginResponse

    private val _registerResponse: MutableLiveData<Event<Resource<RegisterResponse>>> = MutableLiveData()
    val registerResponse: LiveData<Event<Resource<RegisterResponse>>>
        get() = _registerResponse



    fun loginUser(body: RequestBodies.LoginBody) =
        login(body)

    fun registerUser(body: RequestBodies.RegisterBody)
    =register(body)


//    }


    private fun login(body: RequestBodies.LoginBody) {
        _loginResponse.postValue(Event(Resource.Loading()))
        disposable.add(
            appRepository.loginUser(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<LoginResponse>() {
                    override fun onSuccess(t: LoginResponse) {
                        Log.d(TAG, "onSuccess: $t")
                        if (t.header.result == "true") {
                            _loginResponse.postValue(Event(Resource.Success(t)))
                        } else {
                            Log.d(TAG, "onError: Login gagal")
                            _loginResponse.postValue(Event(Resource.Error(t.header.resultMessage)))
                        }
                    }

                    override fun onError(e: Throwable) {
                        try {
                            e.printStackTrace()
                            when (e) {
                                is HttpException -> {
                                    Log.d(TAG, "onError: HTTP Excep")
                                    val jsonObject =
                                        JSONObject(e.response()!!.errorBody()!!.string())
                                    _loginResponse.postValue(Event(Resource.Error(e.message())))
                                }
                                is SocketTimeoutException -> {
                                    _loginResponse.postValue(Event(Resource.Error("Timeout")))
                                }
                                is IOException -> {
                                    _loginResponse.postValue(Event(Resource.Error("Error Connection")))
                                }
                                else -> {
                                    Log.d(TAG, "onError: Else")
                                    _loginResponse.postValue(Event(Resource.Error("Error: Periksa Email dan Password Anda")))
                                }
                            }

                        } catch (e: Exception) {
                            Log.d(TAG, "onError: catch")
                            e.printStackTrace()
                            _loginResponse.postValue(Event(Resource.Error(e.message!!)))
                        }
                    }

                })

        )

    }

    private fun register(body: RequestBodies.RegisterBody) {
        _loginResponse.postValue(Event(Resource.Loading()))
        disposable.add(
            appRepository.registerUser(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<RegisterResponse>() {
                    override fun onSuccess(t: RegisterResponse) {
                        Log.d(TAG, "onSuccess: $t")
                        if (t.header.result == "true") {
                            _registerResponse.postValue(Event(Resource.Success(t)))
                        } else {
                            Log.d(TAG, "onError: Register gagal")
                            _registerResponse.postValue(Event(Resource.Error(t.header.resultMessage)))
                        }
                    }

                    override fun onError(e: Throwable) {
                        try {
                            e.printStackTrace()
                            when (e) {
                                is HttpException -> {
                                    Log.d(TAG, "onError: HTTP Excep")
                                    _loginResponse.postValue(Event(Resource.Error(e.message())))
                                }
                                is SocketTimeoutException -> {
                                    _loginResponse.postValue(Event(Resource.Error("Timeout")))
                                }
                                is IOException -> {
                                    _loginResponse.postValue(Event(Resource.Error("Error Connection")))
                                }
                                else -> {
                                    Log.d(TAG, "onError: Else")
                                    _loginResponse.postValue(Event(Resource.Error(e.message!!)))
                                }
                            }

                        } catch (e: Exception) {
                            Log.d(TAG, "onError: catch")
                            e.printStackTrace()
                            _loginResponse.postValue(Event(Resource.Error(e.message!!)))
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