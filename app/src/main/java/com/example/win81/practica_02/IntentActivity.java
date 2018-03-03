package com.example.win81.practica_02;

import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntentActivity extends AppCompatActivity implements View.OnClickListener {

    private static boolean esNum(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }
    @Override public void onClick(View v){
        Intent i = new Intent(Intent.ACTION_VIEW);
        EditText editText= (EditText) findViewById(R.id.editText);
        String mensaje= editText.getText().toString();
        EditText editText1=(EditText) findViewById(R.id.editText2);
        String contenido= editText1.getText().toString();
        final TextView editText2= (TextView) findViewById(R.id.editText3);
        String mensaje2= editText.getText().toString();



        switch (v.getId()){

            case R.id.buttonCallPhone:             //content://contacts/people/1
                if (esNum(mensaje)==true){
                    i.setData(Uri.parse("tel:"+mensaje));
                }
                else {
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Pon un número para hacer la llamada", Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.NO_GRAVITY, 11,490 );

                    toast1.show();
                    break;

                }

            startActivity(i);
            break;
            case R.id.buttonMaps:
                String[] spl=mensaje.split(",");
                if (esNum(spl[0]) && esNum(spl[1])){
                    i.setData(Uri.parse("geo:"+mensaje));
                    startActivity(i);
                }
                else{
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Pon coordenadas con formato n,n", Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.NO_GRAVITY, 11,490 );
                    toast1.show();
                }


            break;
            case  R.id.buttonAlert:
                Calendar date = Calendar.getInstance();
                int h = date.get(Calendar.HOUR_OF_DAY);
                int m = date.get(Calendar.MINUTE) + 2;
                i = new Intent(AlarmClock.ACTION_SET_ALARM).putExtra(AlarmClock.EXTRA_MESSAGE,mensaje).putExtra(AlarmClock.EXTRA_HOUR, h)
                        .putExtra(AlarmClock.EXTRA_MINUTES, m);
                if(i.resolveActivity(getPackageManager()) != null){
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"No se puede asignar la alarma", Toast.LENGTH_SHORT).show();
                }
            break;
            case R.id.buttonSms:
                if (esNum(mensaje)==true){

                i = new Intent(Intent.ACTION_VIEW);
                i.putExtra("sms_body", "Mensaje");
                i.putExtra("address", new String(mensaje));
                i.setType("vnd.android-dir/mms-sms");
                startActivity(i);
                }
                else{
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Pon un número a cual mandarle el mensaje", Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.NO_GRAVITY, 11,490 );

                    toast1.show();

                }
             break;
             case R.id.buttonCamera:
                 i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                 startActivity(i);


                 break;
            case R.id.button2:
                i=new Intent((MediaStore.ACTION_VIDEO_CAPTURE));

                startActivity(i);

                break;


            case R.id.buttonMail:
                if(validarEmail(mensaje)==true){
                    Toast toast2 = Toast.makeText(getApplicationContext(), "Recuerda escribir todo en minúsculas", Toast.LENGTH_SHORT);
                    toast2.setGravity(Gravity.NO_GRAVITY, 11,490 );
                    i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT,"Email de Ulises Orduña Pérez");
                    i.putExtra(Intent.EXTRA_TEXT,contenido);
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{mensaje});
                    try{
                        startActivity(
                                Intent.createChooser(i, "Quien puede enviar un Email?"));
                    }catch (android.content.ActivityNotFoundException e){
                        e.printStackTrace();
                    }
                    break;
                }
                else{
                    Toast toast2 = Toast.makeText(getApplicationContext(), "Escribe una direección de correo válida >:v", Toast.LENGTH_SHORT);
                    toast2.setGravity(Gravity.NO_GRAVITY, 11,490 );

                }
                break;
            //case R.id.buttonCalendar:

              //  i=new Intent(Intent.CATEGORY_APP_CALENDAR.);
                //startActivity(i);
            case R.id.buttonTemp:
                if(esNum(mensaje)==true){
                    long lg=Long.parseLong(mensaje);


                    new CountDownTimer(lg*1000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            editText2.setText("Segundos restantes: " + millisUntilFinished / 1000);
                        }

                        public void onFinish() {
                            editText2.setText("terminado!");
                        }
                    }.start();
                }
                else {
                    Toast toast4 = Toast.makeText(getApplicationContext(), "Pon el tiempo del temporizador", Toast.LENGTH_SHORT);
                    toast4.setGravity(Gravity.NO_GRAVITY, 11,490 );
                }




        } }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        findViewById(R.id.buttonAlert).setOnClickListener(this);
        findViewById(R.id.buttonCallPhone).setOnClickListener(this);
        findViewById(R.id.buttonCamera).setOnClickListener(this);
        findViewById(R.id.buttonMail).setOnClickListener(this);
        findViewById(R.id.buttonMaps).setOnClickListener(this);
        findViewById(R.id.buttonPhone).setOnClickListener(this);
        findViewById(R.id.buttonSms).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.buttonTemp).setOnClickListener(this);
        findViewById(R.id.buttonCalendar).setOnClickListener(this);
    }

    public void btnWeb(View v){
        TextView textView=(TextView) findViewById(R.id.editText);
        String text=textView.getText().toString();
        if (URLUtil.isValidUrl(text)){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String urlWeb = text ;
            intent.setData(Uri.parse(urlWeb));
            startActivity(intent);
        }
        else{
            Toast toast1 = Toast.makeText(getApplicationContext(), "Esribe una URL válida", Toast.LENGTH_SHORT);
            toast1.setGravity(Gravity.NO_GRAVITY, 11,490 );

            toast1.show();
        }
    }
     public void google(View v){

            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.google.com.mx/webhp"));
            startActivity(intent);
    }

    public static boolean validarEmail(String email){

        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }


}

