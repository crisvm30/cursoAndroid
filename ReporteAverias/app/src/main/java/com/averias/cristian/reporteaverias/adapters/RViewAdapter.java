package com.averias.cristian.reporteaverias.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.averias.cristian.reporteaverias.R;

import com.averias.cristian.reporteaverias.entities.Post;
import com.averias.cristian.reporteaverias.ui.DetalleAveriaActivity;

import java.util.List;

public class RViewAdapter extends RecyclerView.Adapter<RViewAdapter.ViewHolder>{



    private List<Post> myPosts;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public View view;
        Post post;
        public ViewHolder(View v){
            super(v);
            view = v;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(),DetalleAveriaActivity.class);
            intent.putExtra("idPost",post.id);


            v.getContext().startActivity(intent);
        }

        public void setPost(Post post) {
            this.post = post;
        }
    }

    public RViewAdapter(List<Post> posts){
        myPosts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.averia_item, parent, false);

        ViewHolder vh = new ViewHolder(row);
        row.setOnClickListener(vh);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        TextView nombreAveria,descripcion,tipo,ubicacion;

        nombreAveria = (TextView) holder.view.findViewById(R.id.nombre_averia);
        descripcion = (TextView) holder.view.findViewById(R.id.descripcion_averia);
        tipo = (TextView) holder.view.findViewById(R.id.tipo_averia);
        ubicacion =  (TextView) holder.view.findViewById(R.id.ubicacion_averia);

       holder.setPost(myPosts.get(position));

        nombreAveria.setText(myPosts.get(position).nombre);
        descripcion.setText(myPosts.get(position).descripcion);
        tipo.setText(myPosts.get(position).tipo);
        ubicacion.setText(myPosts.get(position).ubicacion.getLat() + ":" + myPosts.get(position).ubicacion.getLon());

    }

    @Override
    public int getItemCount() {
        return myPosts.size();
    }



}
