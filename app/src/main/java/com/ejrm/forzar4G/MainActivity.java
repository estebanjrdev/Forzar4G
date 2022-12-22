package com.ejrm.forzar4G;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Intent valorar_Aplis = new Intent();
    final int PERMISSION_CALL =101;
    Button mayor,ambas;
    TextView modelo,version;
    Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_main);
        anim= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        mayor = findViewById(R.id.btnmas11);
        ambas  = findViewById(R.id.btnambas);
        modelo = findViewById(R.id.txtmodelo);
        version = findViewById(R.id.txtversion);
        mayor.startAnimation(anim);
        ambas.startAnimation(anim);
        ambas.setBackgroundResource(R.drawable.boton_ripple);
        mayor.setBackgroundResource(R.drawable.boton_ripple);
        modelo.setText("Modelo: "+obtenerNombreDeDispositivo());
        String txtversion = Build.VERSION.RELEASE;
        version.setText("Versión de Android: "+txtversion);
        mayor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View p1)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("¿Como forzar la 4G/LTE?");
                builder.setMessage("1-Precionar el boton Forzar 4G/LTE.\n\n\u20222-Luego saldra el menu “Phone Info”.\n\n\u20223-Ir y precionar la opción “Set Preferred Network Type“.\n\n\u20224-Por último seleccionar la opción “LTE only“.\n\n\u2022Siempre debe tener en cuenta que solo funciona si su dispositivo admite la red 4G.");

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        ambas.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View p1)
            {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_CALL);

                }else{
                    try{
                        Intent intent1 = new Intent("android.intent.action.MAIN");
                        intent1.setComponent(new ComponentName("com.android.settings", "com.android.settings.RadioInfo"));
                        startActivity(intent1);
                    }catch(Exception e){
                        Intent intent = new Intent("android.intent.action.MAIN");
                        intent.setClassName("com.android.phone", "com.android.phone.settings.RadioInfo");
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private String obtenerNombreDeDispositivo() {
        String fabricante = Build.MANUFACTURER;
        String modelo = Build.MODEL;
        if (modelo.startsWith(fabricante)) {
            return primeraLetraMayuscula(modelo);
        } else {
            return primeraLetraMayuscula(fabricante) + " " + modelo;
        }
    }
    private String primeraLetraMayuscula(String cadena) {
        if (cadena == null || cadena.length() == 0) {
            return "";
        }
        char primeraLetra = cadena.charAt(0);
        if (Character.isUpperCase(primeraLetra)) {
            return cadena;
        } else {
            return Character.toUpperCase(primeraLetra) + cadena.substring(1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.itemshare:
                AlertDialog.Builder build = new AlertDialog.Builder(MainActivity.this);
                build.setTitle("Contáctenos en");
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();

                View v = inflater.inflate(R.layout.contacto, null);

                build.setView(v);
                LinearLayout sitio = v.findViewById(R.id.layoutweb);
                LinearLayout facebook = v.findViewById(R.id.layoutfacebook);
                LinearLayout email = v.findViewById(R.id.layoutemail);
                LinearLayout telegram = v.findViewById(R.id.layouttelegram);
                LinearLayout share =  v.findViewById(R.id.layoutshare);
                ImageView apklis = v.findViewById(R.id.contactoImageView5);
                sitio.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent = new Intent("android.intent.action.VIEW");
                                intent.setData(Uri.parse((String)"https://www.apklis.cu/developer/estebanmc"));
                                startActivity(intent);
                            }
                        }
                );
                apklis.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                MainActivity.this.valorar_Aplis.setAction("android.intent.action.VIEW");
                                MainActivity.this.valorar_Aplis.setData(Uri.parse("https://www.apklis.cu/application/com.ejrm.forzar4G"));
                                MainActivity.this.startActivity(MainActivity.this.valorar_Aplis);
                            }
                        }
                );
                share.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.SEND");
                                intent.putExtra("android.intent.extra.TEXT", "\u00a1Hola!\n Te estoy invitando a que uses Forzar 4G para que puedas tener una mejor conexión. Yo la uso. \n\nDesc\u00e1rgala de: https://www.apklis.cu/application/com.ejrm.forzar4G");
                                intent.setType("text/plain");
                                startActivity(intent);
                            }
                        }
                );
                telegram.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent = new Intent("android.intent.action.VIEW");
                                intent.setData(Uri.parse((String)"https://t.me/susoluciones"));
                                startActivity(intent);
                            }
                        }
                );
                email.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setData(Uri.parse("email"));
                                String[] s={"susoluciones.software@gmail.com"};
                                intent.putExtra(Intent.EXTRA_EMAIL, s);
                                intent.putExtra(Intent.EXTRA_SUBJECT, "suSoluciones");
                                intent.putExtra(Intent.EXTRA_TEXT, "");
                                intent.setType("message/rfc822");
                                Intent chooser= Intent.createChooser(intent, "");
                                startActivity(chooser);
                            }
                        }
                );
                facebook.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent = new Intent("android.intent.action.VIEW");
                                intent.setData(Uri.parse((String)"https://www.facebook.com/susoluciones"));
                                startActivity(intent);
                            }
                        }
                );
                build.setPositiveButton("Cerrar", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface p1, int p2) {
                        p1.cancel();
                    }
                });

                build.show();

                return true;
            case R.id.iteminfo:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Acerca de Forzar 4G/LTE");
                builder.setMessage("Esta aplicación nos permite forzar (Solo 4G/LTE) en el dispositivo siempre que este soporte está red.\n\n\u2022Lo que nos permite tener una conexión más rapida y estable.\n\n\u2022Funciona para todas las versiones de android que admiten la 4G.");

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return true;
            case R.id.itemversion:
                Toast.makeText(MainActivity.this,"Versión 1.2",Toast.LENGTH_SHORT).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}