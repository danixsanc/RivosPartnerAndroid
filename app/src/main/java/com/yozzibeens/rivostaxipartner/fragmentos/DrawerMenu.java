package com.yozzibeens.rivostaxipartner.fragmentos;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yozzibeens.rivostaxipartner.Main;
import com.yozzibeens.rivostaxipartner.R;
import com.yozzibeens.rivostaxipartner.actividades.Nav_Ayuda;
import com.yozzibeens.rivostaxipartner.actividades.Nav_Historial;
import com.yozzibeens.rivostaxipartner.actividades.Nav_Perfil;
import com.yozzibeens.rivostaxipartner.controlador.CabbieController;
import com.yozzibeens.rivostaxipartner.modelo.Cabbie;
import com.yozzibeens.rivostaxipartner.utilerias.Preferencias;


public class DrawerMenu extends Fragment {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View mContainer;
    public boolean menuOpen;
    private Context context;

    TextView txtCorreo;
    TextView txtNombre;
    LinearLayout CerrarSesion;
    View rootview;
    String correo;
    String nombre;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar, final ActionBar actionBar, Context prContext) {
        mContainer = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        context = prContext;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Log.d("VIVZ", "onDrawerOpened");
                getActivity().supportInvalidateOptionsMenu();
                menuOpen = true;
            }


            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.d("VIVZ", "onDrawerClosed");
                setHasOptionsMenu(true);
                getActivity().supportInvalidateOptionsMenu();
                menuOpen = false;
            }


            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                //toolbar_wizzard.setAlpha(1 - slideOffset / 2);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public void cerrarDrawer(){
        mDrawerLayout.closeDrawers();
    }

    public DrawerMenu() {
        // Required empty public constructor
    }

    public void invalidarMenu(android.view.Menu menu, int idMenu){
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mContainer);
        menu.findItem(idMenu).setVisible(!drawerOpen);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.drawer_menu, container, false);



        final Preferencias preferencias = new Preferencias(getActivity().getApplicationContext());
        boolean check = preferencias.getSesion();
        if (!check)
        {
            String Cabbie_Id = preferencias.getCabbie_Id();
            CabbieController cabbieController = new CabbieController(getActivity().getApplicationContext());
            Cabbie cabbie;
            cabbie = cabbieController.obtenerCabbiePorCabbieId(Cabbie_Id);
            correo = cabbie.getEmail();
            nombre = cabbie.getName();
            txtCorreo = (TextView) view.findViewById(R.id.txtCorreo);
            //txtCorreo.setTypeface(RobotoCondensed_Regular);
            txtNombre = (TextView) view.findViewById(R.id.txtNombre);
            //txtNombre.setTypeface(RobotoCondensed_Regular);
            txtCorreo.setText(correo);
            txtNombre.setText(nombre);


            CerrarSesion = (LinearLayout) view.findViewById(R.id.CerrarSesion);
            CerrarSesion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder dialog1 = new AlertDialog.Builder(getActivity());
                    dialog1.setMessage("¿Cerrar Sesion?");
                    dialog1.setCancelable(false);
                    dialog1.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            preferencias.setSesion(true);
                            CabbieController cabbieController = new CabbieController(getActivity().getApplicationContext());
                            cabbieController.eliminarTodo();
                            Intent intent = new Intent(getActivity(), Main.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    });
                    dialog1.setNegativeButton("No", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog1.show();



                }
            });


            LinearLayout solicitar;
            solicitar = (LinearLayout) view.findViewById(R.id.nav_solicitar);
            solicitar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDrawerLayout.closeDrawers();
                }
            });

            LinearLayout perfil;
            perfil = (LinearLayout) view.findViewById(R.id.nav_perfil);
            perfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), Nav_Perfil.class));
                }
            });

            LinearLayout historial;
            historial = (LinearLayout) view.findViewById(R.id.nav_historial);
            historial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), Nav_Historial.class));
                }
            });

            LinearLayout ayuda;
            ayuda = (LinearLayout) view.findViewById(R.id.nav_ayuda);
            ayuda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), Nav_Ayuda.class));
                }
            });

        }












        return view;
    }


}
