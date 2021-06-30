package com.krproject.apamprojectnew.ui.auth

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.krproject.apamprojectnew.data.network.RequestBodies
import com.krproject.apamprojectnew.data.network.Resource
import com.krproject.apamprojectnew.data.response.LoginResponse
import com.krproject.apamprojectnew.data.response.auth.RegisterResponse
import com.krproject.apamprojectnew.data.response.verifikasiEmail.VerifikasiEmailResponse
import com.krproject.apamprojectnew.repository.AppRepository
import com.krproject.apamprojectnew.util.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class AuthViewModel(
    app: Application,
    private val appRepository: AppRepository
) : AndroidViewModel(app) {
    private val disposable = CompositeDisposable()
    private val _loginResponse: MutableLiveData<Event<Resource<LoginResponse>>> = MutableLiveData()
    val loginResponse: LiveData<Event<Resource<LoginResponse>>>
        get() = _loginResponse

    private val _verfikasiEmailResponse: MutableLiveData<Event<Resource<VerifikasiEmailResponse>>> = MutableLiveData()
    val verfikasiEmailResponse: LiveData<Event<Resource<VerifikasiEmailResponse>>>
        get() = _verfikasiEmailResponse

    private val _forgetPasswordResponse: MutableLiveData<Event<Resource<VerifikasiEmailResponse>>> = MutableLiveData()
    val forgetPasswordResponse: LiveData<Event<Resource<VerifikasiEmailResponse>>>
        get() = _forgetPasswordResponse

    private val _registerResponse: MutableLiveData<Event<Resource<RegisterResponse>>> = MutableLiveData()
    val registerResponse: LiveData<Event<Resource<RegisterResponse>>>
        get() = _registerResponse

    private val _updateProfileResponse: MutableLiveData<Event<Resource<RegisterResponse>>> = MutableLiveData()
    val updateProfileResponse: LiveData<Event<Resource<RegisterResponse>>>
        get() = _updateProfileResponse

    private val _uploadImgResponse: MutableLiveData<Event<Resource<RegisterResponse>>> = MutableLiveData()
    val uploadImgResponse: LiveData<Event<Resource<RegisterResponse>>>
        get() = _uploadImgResponse

    private val _uploadImgResponseKTP: MutableLiveData<Event<Resource<RegisterResponse>>> = MutableLiveData()
    val uploadImgResponseKTP: LiveData<Event<Resource<RegisterResponse>>>
        get() = _uploadImgResponseKTP

    private val _uploadImgResponseBPJS: MutableLiveData<Event<Resource<RegisterResponse>>> = MutableLiveData()
    val uploadImgResponseBPJS: LiveData<Event<Resource<RegisterResponse>>>
        get() = _uploadImgResponseBPJS


    fun loginUser(body: RequestBodies.LoginBody) =
        login(body)

    fun registerUser(body: RequestBodies.RegisterBody)
    =register(body)

    fun updateProfile(body: RequestBodies.EditProfileBody)
    =editProfile(body)

    fun verifEmail(body: RequestBodies.VerikasiEmailBody)
    =verifikasiEmail(body)

    fun forgetYourPassword(body: RequestBodies.ForgetPasswordBody)
            =forgetPassword(body)


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

    private fun verifikasiEmail(body: RequestBodies.VerikasiEmailBody) {
        _verfikasiEmailResponse.postValue(Event(Resource.Loading()))
        disposable.add(
            appRepository.verifikasiEmail(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<VerifikasiEmailResponse>() {
                    override fun onSuccess(t: VerifikasiEmailResponse) {
                        Log.d(TAG, "onSuccess: $t")
                        if (t.header.result == "true") {
                            _verfikasiEmailResponse.postValue(Event(Resource.Success(t)))
                        } else {
                            Log.d(TAG, "onError: Login gagal")
                            _verfikasiEmailResponse.postValue(Event(Resource.Error(t.header.resultMessage)))
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
                                    _verfikasiEmailResponse.postValue(Event(Resource.Error(e.message())))
                                }
                                is SocketTimeoutException -> {
                                    _verfikasiEmailResponse.postValue(Event(Resource.Error("Timeout")))
                                }
                                is IOException -> {
                                    _verfikasiEmailResponse.postValue(Event(Resource.Error("Error Connection")))
                                }
                                else -> {
                                    Log.d(TAG, "onError: Else")
                                    _verfikasiEmailResponse.postValue(Event(Resource.Error("Error: Periksa Email dan Password Anda")))
                                }
                            }

                        } catch (e: Exception) {
                            Log.d(TAG, "onError: catch")
                            e.printStackTrace()
                            _verfikasiEmailResponse.postValue(Event(Resource.Error(e.message!!)))
                        }
                    }

                })

        )

    }

    private fun forgetPassword(body: RequestBodies.ForgetPasswordBody) {
        _forgetPasswordResponse.postValue(Event(Resource.Loading()))
        disposable.add(
            appRepository.forgetPassword(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<VerifikasiEmailResponse>() {
                    override fun onSuccess(t: VerifikasiEmailResponse) {
                        Log.d(TAG, "onSuccess: $t")
                        if (t.header.result == "true") {
                            _forgetPasswordResponse.postValue(Event(Resource.Success(t)))
                        } else {
                            Log.d(TAG, "onError: Login gagal")
                            _forgetPasswordResponse.postValue(Event(Resource.Error(t.header.resultMessage)))
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
                                    _forgetPasswordResponse.postValue(Event(Resource.Error(e.message())))
                                }
                                is SocketTimeoutException -> {
                                    _forgetPasswordResponse.postValue(Event(Resource.Error("Timeout")))
                                }
                                is IOException -> {
                                    _forgetPasswordResponse.postValue(Event(Resource.Error("Error Connection")))
                                }
                                else -> {
                                    Log.d(TAG, "onError: Else")
                                    _forgetPasswordResponse.postValue(Event(Resource.Error("Error: Periksa Email dan Password Anda")))
                                }
                            }

                        } catch (e: Exception) {
                            Log.d(TAG, "onError: catch")
                            e.printStackTrace()
                            _forgetPasswordResponse.postValue(Event(Resource.Error(e.message!!)))
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

    private fun editProfile(body: RequestBodies.EditProfileBody) {
        _updateProfileResponse.postValue(Event(Resource.Loading()))
        disposable.add(
            appRepository.editProfile(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<RegisterResponse>() {
                    override fun onSuccess(t: RegisterResponse) {
                        Log.d(TAG, "onSuccess: $t")
                        if (t.header.result == "true") {
                            _updateProfileResponse.postValue(Event(Resource.Success(t)))
                        } else {
                            Log.d(TAG, "onError: Register gagal")
                            _updateProfileResponse.postValue(Event(Resource.Error(t.header.resultMessage)))
                        }
                    }

                    override fun onError(e: Throwable) {
                        try {
                            e.printStackTrace()
                            when (e) {
                                is HttpException -> {
                                    Log.d(TAG, "onError: HTTP Excep")
                                    _updateProfileResponse.postValue(Event(Resource.Error(e.message())))
                                }
                                is SocketTimeoutException -> {
                                    _updateProfileResponse.postValue(Event(Resource.Error("Timeout")))
                                }
                                is IOException -> {
                                    _updateProfileResponse.postValue(Event(Resource.Error("Error Connection")))
                                }
                                else -> {
                                    Log.d(TAG, "onError: Else")
                                    _updateProfileResponse.postValue(Event(Resource.Error(e.message!!)))
                                }
                            }

                        } catch (e: Exception) {
                            Log.d(TAG, "onError: catch")
                            e.printStackTrace()
                            _updateProfileResponse.postValue(Event(Resource.Error(e.message!!)))
                        }
                    }

                })

        )

    }

    fun uploadImageKK(body: RequestBodies.UploadImageBody) {
        _uploadImgResponse.postValue(Event(Resource.Loading()))
        disposable.add(
            appRepository.uploadImgKK(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<RegisterResponse>() {
                    override fun onSuccess(t: RegisterResponse) {
                        Log.d(TAG, "onSuccess: $t")
                        if (t.header.result == "true") {
                            _uploadImgResponse.postValue(Event(Resource.Success(t)))
                        } else {
                            Log.d(TAG, "onError: Register gagal")
                            _uploadImgResponse.postValue(Event(Resource.Error(t.header.resultMessage)))
                        }
                    }

                    override fun onError(e: Throwable) {
                        try {
                            e.printStackTrace()
                            when (e) {
                                is HttpException -> {
                                    Log.d(TAG, "onError: HTTP Excep")
                                    _uploadImgResponse.postValue(Event(Resource.Error(e.message())))
                                }
                                is SocketTimeoutException -> {
                                    _uploadImgResponse.postValue(Event(Resource.Error("Timeout")))
                                }
                                is IOException -> {
                                    _uploadImgResponse.postValue(Event(Resource.Error("Error Connection")))
                                }
                                else -> {
                                    Log.d(TAG, "onError: Else")
                                    _uploadImgResponse.postValue(Event(Resource.Error(e.message!!)))
                                }
                            }

                        } catch (e: Exception) {
                            Log.d(TAG, "onError: catch")
                            e.printStackTrace()
                            _updateProfileResponse.postValue(Event(Resource.Error(e.message!!)))
                        }
                    }

                })

        )

    }

    fun uploadImageKTP(body: RequestBodies.UploadImageBody) {
        _uploadImgResponseKTP.postValue(Event(Resource.Loading()))
        disposable.add(
            appRepository.uploadImgKTP(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<RegisterResponse>() {
                    override fun onSuccess(t: RegisterResponse) {
                        Log.d(TAG, "onSuccess: $t")
                        if (t.header.result == "true") {
                            _uploadImgResponseKTP.postValue(Event(Resource.Success(t)))
                        } else {
                            Log.d(TAG, "onError: Register gagal")
                            _uploadImgResponseKTP.postValue(Event(Resource.Error(t.header.resultMessage)))
                        }
                    }

                    override fun onError(e: Throwable) {
                        try {
                            e.printStackTrace()
                            when (e) {
                                is HttpException -> {
                                    Log.d(TAG, "onError: HTTP Excep")
                                    _uploadImgResponseKTP.postValue(Event(Resource.Error(e.message())))
                                }
                                is SocketTimeoutException -> {
                                    _uploadImgResponseKTP.postValue(Event(Resource.Error("Timeout")))
                                }
                                is IOException -> {
                                    _uploadImgResponseKTP.postValue(Event(Resource.Error("Error Connection")))
                                }
                                else -> {
                                    Log.d(TAG, "onError: Else")
                                    _uploadImgResponseKTP.postValue(Event(Resource.Error(e.message!!)))
                                }
                            }

                        } catch (e: Exception) {
                            Log.d(TAG, "onError: catch")
                            e.printStackTrace()
                            _uploadImgResponseKTP.postValue(Event(Resource.Error(e.message!!)))
                        }
                    }

                })

        )

    }

    fun uploadImageBPJS(body: RequestBodies.UploadImageBody) {
        _uploadImgResponseBPJS.postValue(Event(Resource.Loading()))
        disposable.add(
            appRepository.uploadImgBPJS(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<RegisterResponse>() {
                    override fun onSuccess(t: RegisterResponse) {
                        Log.d(TAG, "onSuccess: $t")
                        if (t.header.result == "true") {
                            _uploadImgResponseBPJS.postValue(Event(Resource.Success(t)))
                        } else {
                            Log.d(TAG, "onError: Register gagal")
                            _uploadImgResponseBPJS.postValue(Event(Resource.Error(t.header.resultMessage)))
                        }
                    }

                    override fun onError(e: Throwable) {
                        try {
                            e.printStackTrace()
                            when (e) {
                                is HttpException -> {
                                    Log.d(TAG, "onError: HTTP Excep")
                                    _uploadImgResponseBPJS.postValue(Event(Resource.Error(e.message())))
                                }
                                is SocketTimeoutException -> {
                                    _uploadImgResponseBPJS.postValue(Event(Resource.Error("Timeout")))
                                }
                                is IOException -> {
                                    _uploadImgResponseBPJS.postValue(Event(Resource.Error("Error Connection")))
                                }
                                else -> {
                                    Log.d(TAG, "onError: Else")
                                    _uploadImgResponseBPJS.postValue(Event(Resource.Error(e.message!!)))
                                }
                            }

                        } catch (e: Exception) {
                            Log.d(TAG, "onError: catch")
                            e.printStackTrace()
                            _uploadImgResponseBPJS.postValue(Event(Resource.Error(e.message!!)))
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