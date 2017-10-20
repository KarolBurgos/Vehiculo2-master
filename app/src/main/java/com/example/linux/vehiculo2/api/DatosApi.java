package com.example.linux.vehiculo2.api;

import com.example.linux.vehiculo2.models.Vehiculos;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by linux on 17/10/17.
 */

public interface DatosApi {
    @GET("rvmt-7x4r.json")
    Call<ArrayList<Vehiculos>> obtenerListaVehiculos();
}
