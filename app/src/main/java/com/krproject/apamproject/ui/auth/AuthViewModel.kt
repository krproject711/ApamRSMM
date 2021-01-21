package com.krproject.apamproject.ui.auth

import android.app.Application
import androidx.lifecycle.*
import com.krproject.apamproject.data.network.RequestBodies
import com.krproject.apamproject.data.network.Resource
import com.krproject.apamproject.data.response.LoginResponse
import com.krproject.apamproject.repository.AppRepository
import com.krproject.apamproject.ui.base.MyApplication
import com.krproject.apamproject.util.Event
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class AuthViewModel(
    app: Application,
    private val appRepository: AppRepository
) : AndroidViewModel(app) {


    private val _loginResponse: MutableLiveData<Event<Resource<LoginResponse>>> = MutableLiveData()
    val loginResponse: LiveData<Event<Resource<LoginResponse>>>
        get() = _loginResponse

    fun loginUser(body: RequestBodies.LoginBody) = viewModelScope.launch {
        login(body)
    }

//    }


    private suspend fun login(body: RequestBodies.LoginBody) {
        _loginResponse.postValue(Event(Resource.Loading()))

        try{
            val response = appRepository.loginUser(body)
            _loginResponse.postValue(handleResponse(response))
        }catch (t: Throwable){
            when (t) {
                is IOException -> {
                    _loginResponse.postValue(
                        Event(Resource.Error(
                            getApplication<MyApplication>().getString(
                                com.krproject.apamproject.R.string.network_failure
                            )
                        ))
                    )
                }
                else -> {
                    _loginResponse.postValue(
                        Event(Resource.Error(
                            getApplication<MyApplication>().getString(
                                com.krproject.apamproject.R.string.conversion_error
                            ) + t.message
                        ))
                    )
                }
            }
        }

    }

//    suspend fun login (
//        email: String,
//        password: String
//    ) {
//        val response = repository.login(email, password)
//        _loginResponse.postValue(handleResponse(response))
//    }
//
//    fun saveAuthToken(token: String) = viewModelScope.launch{
//        repository.saveAuthToken(token)
//    }

    private fun handleResponse(response: Response<LoginResponse>): Event<Resource<LoginResponse>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }
}