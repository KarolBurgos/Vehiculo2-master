package com.example.linux.vehiculo2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.linux.vehiculo2.api.DatosApi;
import com.example.linux.vehiculo2.models.Vehiculos;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String TAG="DATOS COLOMBIA";
    private Retrofit retrofit;
    private ListaVehiculos listaVehiculos;
    private RecyclerView recyclerView;
    private int ofset;
    private boolean cargar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        listaVehiculos = new ListaVehiculos(this);
        recyclerView.setAdapter(listaVehiculos);

        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0)
                {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();


                    if (cargar) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG, ".");
                            cargar = false;
                            ofset += 1;
                            obtenerDatosReportesVehiculos();
                        }
                    }

                }
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.datos.gov.co/resource/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        cargar = true;
        ofset = 0;

        obtenerDatosReportesVehiculos();
    }

    private void obtenerDatosReportesVehiculos() {
        DatosApi service = retrofit.create(DatosApi.class);
        Call<ArrayList<Vehiculos>> reporteRespuestaCall = service.obtenerListaVehiculos();

        reporteRespuestaCall.enqueue(new Callback<ArrayList<Vehiculos>>() {
            @Override
            public void onResponse(Call<ArrayList<Vehiculos>> call, Response<ArrayList<Vehiculos>> response) {
                if(response.isSuccessful())
                {
                    ArrayList listado = response.body();
                    listaVehiculos.agregar(listado);
                }
                else
                {
                    Log.e(TAG, "onResponse: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Vehiculos>> call, Throwable t) {
                Log.e(TAG," onFailure: "+t.getMessage());
            }
        });
    }
    public void acercaDe(View view){
        Intent i=new Intent (this,AcercaDe.class);
        startActivity(i);
    }
    public void ejecutar(View view){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setMessage("¿Salir?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();

    }
}
