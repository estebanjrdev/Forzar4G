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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Intent valorar_Aplis = new Intent();
    final int PERMISSION_CALL = 101;
    Button mayor, ambas, btnSim, btnImei, btnActivar, btnSoporta, btnrequsito;
    TextView modelo, version;
    EditText txtImei;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_main);
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        mayor = findViewById(R.id.btnmas11);
        ambas = findViewById(R.id.btnambas);
        btnActivar = findViewById(R.id.btnactivar4g);
        btnImei = findViewById(R.id.btnIMEI);
        btnSim = findViewById(R.id.btnsim4g);
        btnSoporta = findViewById(R.id.btnmovil4g);
        btnrequsito = findViewById(R.id.btnrequisitos);
        txtImei = findViewById(R.id.EditTextimei);
        modelo = findViewById(R.id.txtmodelo);
        version = findViewById(R.id.txtversion);
        mayor.startAnimation(anim);
        ambas.startAnimation(anim);
        btnrequsito.startAnimation(anim);
        btnSoporta.startAnimation(anim);
        btnImei.startAnimation(anim);
        btnActivar.startAnimation(anim);
        btnSim.startAnimation(anim);
        ambas.setBackgroundResource(R.drawable.boton_ripple);
        mayor.setBackgroundResource(R.drawable.boton_ripple);
        btnActivar.setBackgroundResource(R.drawable.boton_ripple);
        btnSim.setBackgroundResource(R.drawable.boton_ripple);
        btnImei.setBackgroundResource(R.drawable.boton_ripple);
        btnSoporta.setBackgroundResource(R.drawable.boton_ripple);
        btnrequsito.setBackgroundResource(R.drawable.boton_ripple);
        modelo.setText("Modelo: " + obtenerNombreDeDispositivo());
        String txtversion = Build.VERSION.RELEASE;
        version.setText("Versión de Android: " + txtversion);
        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("¿Es 4G mi línea?");
                builder.setMessage("Para saber si es 4G su línea debe enviar un mensaje sin costo al 2266 con el texto SIM");
                builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendSMS("SIM");
                    }
                });
                builder.show();
            }
        });
        btnrequsito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Requisitos");
                builder.setMessage("1-Tener una línea USIM.\n\n\u20222-Tener un teléfono que soporte la red 4G en la frecuencia de los 1800 MHz, banda 3.\n\n\u20223-Tener activada la red 4G en su teléfono.\n\n\u20224-Estar en zona bajo cobertura 4G.");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        btnActivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Activar 4G");
                builder.setMessage("Para activar la red 4G debe enviar un mensaje sin costo al 2266 con el texto LTE");
                builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendSMS("LTE");
                    }
                });
                builder.show();
            }
        });
        btnImei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("IMEI");
                builder.setMessage("Para obtener el IMEI debe marcar en la aplicación Teléfono *#06#");
                builder.setPositiveButton("Ver IMEI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"));
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });
        btnSoporta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String imei = txtImei.getText().toString();
                if (!imei.equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("¿Es 3G o 4G mi móvil?");
                    builder.setMessage("Para saber que redes soporta su móvil debe enviar un mensaje sin costo al 2266 con los 8 primeros dígitos de su IMEI");
                    builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendSMS(imei);
                        }
                    });
                    builder.show();
                    txtImei.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Ponga el IMEI", Toast.LENGTH_SHORT).show();

                }
            }
        });
        mayor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
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
        ambas.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View p1) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_CALL);

                } else {
                    try {
                        Intent intent1 = new Intent("android.intent.action.MAIN");
                        intent1.setComponent(new ComponentName("com.android.settings", "com.android.settings.RadioInfo"));
                        startActivity(intent1);
                    } catch (Exception e) {
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
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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
                LinearLayout share = v.findViewById(R.id.layoutshare);
                ImageView apklis = v.findViewById(R.id.contactoImageView5);
                sitio.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent("android.intent.action.VIEW");
                                intent.setData(Uri.parse((String) "https://www.apklis.cu/developer/estebanmc"));
                                startActivity(intent);
                            }
                        }
                );
                apklis.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MainActivity.this.valorar_Aplis.setAction("android.intent.action.VIEW");
                                MainActivity.this.valorar_Aplis.setData(Uri.parse("https://www.apklis.cu/application/com.ejrm.forzar4G"));
                                MainActivity.this.startActivity(MainActivity.this.valorar_Aplis);
                            }
                        }
                );
                share.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
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
                            public void onClick(View v) {
                                Intent intent = new Intent("android.intent.action.VIEW");
                                intent.setData(Uri.parse((String) "https://t.me/susoluciones"));
                                startActivity(intent);
                            }
                        }
                );
                email.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setData(Uri.parse("email"));
                                String[] s = {"susoluciones.software@gmail.com"};
                                intent.putExtra(Intent.EXTRA_EMAIL, s);
                                intent.putExtra(Intent.EXTRA_SUBJECT, "suSoluciones");
                                intent.putExtra(Intent.EXTRA_TEXT, "");
                                intent.setType("message/rfc822");
                                Intent chooser = Intent.createChooser(intent, "");
                                startActivity(chooser);
                            }
                        }
                );
                facebook.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent("android.intent.action.VIEW");
                                intent.setData(Uri.parse((String) "https://www.facebook.com/susoluciones"));
                                startActivity(intent);
                            }
                        }
                );
                build.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {

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
                Toast.makeText(MainActivity.this, "Versión 1.2", Toast.LENGTH_SHORT).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void sendSMS(String text) {
        String num = "2266";
        Uri sms_uri = Uri.parse("smsto:" + num);
        Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
        sms_intent.putExtra("sms_body", text);
        startActivity(sms_intent);
    }
}