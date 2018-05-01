package com.example.sergio.dp2aux;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.Object_opcionViewHolder>{

    private List<MenuClientActivity.Object_opcion> opciones;

    public RVAdapter(List<MenuClientActivity.Object_opcion> opciones) {
        this.opciones = opciones;
    }

    public static class Object_opcionViewHolder extends RecyclerView.ViewHolder  {
        CardView cv;
        TextView nombre_opcion;
        ImageView icono;
        public View view;

        public Object_opcionViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            cv = (CardView)itemView.findViewById(R.id.cv);
            nombre_opcion = (TextView)itemView.findViewById(R.id.nombre_opcion);
            icono = (ImageView)itemView.findViewById(R.id.icono_opcion);

            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    // item clicked
                    _ejecutar_funcion(nombre_opcion.getText().toString(),view );
                }
            });
        }

        private void _ejecutar_funcion(String funcionalidad, View view){
            if(funcionalidad.equals("Ruleta de Promociones")){
                _entrar_actividad( view, RouletteActivity.class);
            }else if(funcionalidad.equals("Tomate una Foto")){
                _entrar_actividad( view, TakePictureFBActivity.class);
            }
        }

        private void _entrar_actividad(View view, Class clase){
            view.getContext().startActivity(new Intent(view.getContext(),clase));
        }


    }

    @Override
    public Object_opcionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_menu_client, viewGroup, false);
        Object_opcionViewHolder pvh = new Object_opcionViewHolder(v);
        return pvh;

    }

    @Override
    public int getItemCount() {
        return opciones.size();
    }

    @Override
    public void onBindViewHolder(Object_opcionViewHolder object_opcionViewHolder, int i) {
        object_opcionViewHolder.nombre_opcion.setText(opciones.get(i).name);
        object_opcionViewHolder.icono.setImageURI(opciones.get(i).uri);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}