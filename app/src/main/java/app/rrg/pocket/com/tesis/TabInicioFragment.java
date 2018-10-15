package app.rrg.pocket.com.tesis;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import app.rrg.pocket.com.tesis.Entities.Nivel;
import app.rrg.pocket.com.tesis.Utilidades.NivelDB;

public class TabInicioFragment extends Fragment{
    private static final String TAG = "TabInicioFragment";
    private final int REQUEST_ACCESS_FINE = 0;
    Context thisContext;
    private NivelDB dbN;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabinicio_fragment, container,false);
        thisContext = container.getContext();
        dbN = new NivelDB(thisContext);

        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO},REQUEST_ACCESS_FINE);
        }
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_ACCESS_FINE);
        }

        Button nivel1 = (Button) view.findViewById(R.id.buttonNivel1);
        final Button nivel2 = (Button) view.findViewById(R.id.buttonNivel2);
        final Button nivel3 = (Button) view.findViewById(R.id.buttonNivel3);

        final Nivel mNivel2 = dbN.buscarNivel("Nivel 2");
        final Nivel mNivel3 = dbN.buscarNivel("Nivel 3");

        if(mNivel2.getBloqueado() == 1) {
            nivel2.setText("");
            nivel2.setBackground(thisContext.getDrawable(R.drawable.btn_circle_block));
        }

        if(mNivel3.getBloqueado() == 1) {
            nivel3.setText("");
            nivel3.setBackground(thisContext.getDrawable(R.drawable.btn_circle_block));
        }


        nivel1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Nivel1Activity.class);
                getActivity().startActivity(intent);
            }
        });

        nivel2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(mNivel2.getBloqueado() == 1){
                    Toast.makeText(thisContext, "Nivel 2 bloqueado, avanza más para desbloquearlo!", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getActivity(), Nivel2Activity.class);
                    getActivity().startActivity(intent);
                }
            }
        });

        nivel3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(mNivel3.getBloqueado() == 1){
                    Toast.makeText(thisContext, "Nivel 3 bloqueado, avanza más para desbloquearlo!", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getActivity(), Nivel3Activity.class);
                    getActivity().startActivity(intent);
                }
            }
        });

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_ACCESS_FINE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext(), "Permiso concedido", Toast.LENGTH_SHORT);
            }else {
                Toast.makeText(getContext(), "Permiso denegado", Toast.LENGTH_SHORT);
            }
        }
    }
}
