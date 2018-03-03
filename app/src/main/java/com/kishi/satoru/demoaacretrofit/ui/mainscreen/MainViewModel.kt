package com.kishi.satoru.demoaacretrofit.ui.mainscreen

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.kishi.satoru.demoaacretrofit.entities.EnderecoResponse
import com.kishi.satoru.demoaacretrofit.repositories.EnderecoRepository
import com.kishi.satoru.demoaacretrofit.repositories.EnderecoRepositoryImpl

// Se for trabalhar com Room, utilizar AndroidViewModel
class MainViewModel : ViewModel() {

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val enderecoRepository: EnderecoRepository
    private val mApiResponse: MediatorLiveData<EnderecoResponse> = MediatorLiveData()
    val apiResponse: LiveData<EnderecoResponse>
        get() = mApiResponse

    init {
        enderecoRepository = EnderecoRepositoryImpl()
    }

    fun pesquisarEndereco(cep: String): LiveData<EnderecoResponse> {
        isLoading.postValue(true)

        mApiResponse.addSource(enderecoRepository.buscarEndereco(cep)) { api ->
            {
                mApiResponse.value = api
                isLoading.postValue(false)
            }
        }
        return mApiResponse
    }
}