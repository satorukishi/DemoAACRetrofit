package com.kishi.satoru.demoaacretrofit.repositories

import android.arch.lifecycle.LiveData
import com.kishi.satoru.demoaacretrofit.entities.EnderecoResponse

interface EnderecoRepository {
    fun buscarEndereco(cep: String): LiveData<EnderecoResponse>
}
