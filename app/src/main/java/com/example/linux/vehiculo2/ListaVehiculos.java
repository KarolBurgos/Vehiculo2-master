package com.example.linux.vehiculo2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.linux.vehiculo2.models.Vehiculos;
import java.util.ArrayList;

public class ListaVehiculos extends RecyclerView.Adapter<ListaVehiculos.ViewHolder>

{
        //Atributos
        private ArrayList<Vehiculos> dataset;
        private Context context;

        public ListaVehiculos(Context context)
        {
            this.context = context;
            dataset = new ArrayList<>();

        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.
                        activity_lista_vehiculos, parent, false);
                        return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position)
        {
                Vehiculos a=dataset.get(position);
                holder.clas.setText(a.getExtranjero().toString());
                holder.extrang.setText(a.getClase().toString());
                holder.oficia.setText(a.getOficial().toString());
                holder.partic.setText(a.getParticular().toString());
                holder.publi.setText(a.getPublico().toString());
                holder.ttal.setText(a.getTotal().toString());
        }

        @Override
        public int getItemCount()
        {
                return dataset.size();
        }

        public void agregar(ArrayList<Vehiculos> list)
        {
                dataset.addAll(list);
                notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            //Cajas de texto
            private TextView clas;
            private TextView extrang;
            private TextView oficia;
            private TextView partic;
            private TextView publi;
            private TextView ttal;

            public ViewHolder(View itemView)
            {
                super(itemView);
                clas=(TextView)itemView.findViewById(R.id.ed_clase);
                extrang=(TextView)itemView.findViewById(R.id.ed_extrangero);
                oficia=(TextView)itemView.findViewById(R.id.ed_oficial);
                partic=(TextView)itemView.findViewById(R.id.ed_particular);
                publi=(TextView)itemView.findViewById(R.id.ed_publico);
                ttal=(TextView)itemView.findViewById(R.id.total);
            }
        }
}

