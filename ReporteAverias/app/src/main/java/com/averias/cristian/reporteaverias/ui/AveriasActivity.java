package com.averias.cristian.reporteaverias.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.averias.cristian.reporteaverias.R;
import com.averias.cristian.reporteaverias.entities.Post;
import com.averias.cristian.reporteaverias.service.AveriaServicio;
import com.averias.cristian.reporteaverias.service.GestorAveriaServicio;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AveriasActivity extends AppCompatActivity implements ListaFragment.ListaListener,MapaFragment.MapaListener{

    @BindView(R.id.pager)
    ViewPager viewPager;

    @BindView(R.id.pager_tab)
    PagerTabStrip pagerTab;

    @BindView(R.id.loading_icon)
    ProgressBar progressBar;

    PagerAdapter pagerAdapter;

    List<Post> listaPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_averias);
        ButterKnife.bind(this);

        obtenerTodasAverias();

    }


    private void obtenerTodasAverias(){
        AveriaServicio servicio  = GestorAveriaServicio.generarServicios();

        Call<List<Post>> llamadaPost = servicio.obtenerAverias();

        llamadaPost.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()){
                    listaPosts = response.body();

                    pagerAdapter = new MyPageAdapter(getSupportFragmentManager());
                    viewPager.setAdapter(pagerAdapter);
                    viewPager.setOffscreenPageLimit(2);
                    progressBar.setVisibility(View.GONE);
                }else{
                    Toast.makeText(AveriasActivity.this, "Hubo un error a la hora de obtener las averías!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(AveriasActivity.this, "Hubo un error a la hora de obtener las averías!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public List<Post> getListaPosts() {
        return listaPosts;
    }

    @Override
    public List<Post> getListaPostsMapa() {
        return listaPosts;
    }

    private class MyPageAdapter extends FragmentPagerAdapter{

        ListaFragment listaFragment;
        MapaFragment mapaFragment;

        public MyPageAdapter(FragmentManager fragmentManager){
            super(fragmentManager);

            listaFragment = new ListaFragment();
            mapaFragment  = new MapaFragment();

        }

        @Override
        public Fragment getItem(int position) {

            if(position == 0){
                return listaFragment;
            }else{

                return mapaFragment;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0){
                return "Lista";
            }else{
                return "Mapa";
            }
        }
    }

}
