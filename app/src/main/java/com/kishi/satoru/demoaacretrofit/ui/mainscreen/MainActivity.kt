package com.kishi.satoru.demoaacretrofit.ui.mainscreen

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.kishi.satoru.demoaacretrofit.R
import kotlinx.android.synthetic.main.activity_main.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var mainViewModel: MainViewModel
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        btPesquisar.setOnClickListener {
            mainViewModel.pesquisarEndereco(etCEP.text.toString())
        }

        mainViewModel.apiResponse.observe(this, Observer {
            api ->
            if (api?.erro == null) {
                Log.i("TAG", "SUCESSO")
                tvEndereco.text = "Logradouro: ${api!!.endereco!!.logradouro}\n" +
                        "Complemento: ${api!!.endereco!!.complemento}\n" +
                        "Bairro: ${api!!.endereco!!.bairro}\n" +
                        "Cidade: ${api!!.endereco!!.localidade} - ${api!!.endereco!!.uf}"
            } else {
                Log.i("TAG", "ERRO${api.erro}")
            }
        })

        mainViewModel.isLoading.observe(this, Observer {isLoading ->
            if (isLoading!!) {
                loading.visibility = View.VISIBLE
            } else {
                loading.visibility = View.GONE
            }
        })

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = supportFragmentManager
//                .findFragmentById(R.id.mapView) as SupportMapFragment
//        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

}
