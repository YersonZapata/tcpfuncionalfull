package com.zapata.yerson.tcpfuncional;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;



public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    //About the ui controls
    private EditText edit_ip = null;
    private EditText edit_port = null;
    private Button btn_connect = null;
    private EditText edit_send = null;
    private Button btn_send = null;
    private Button btn_sendlist=null;
    private Button btn_open=null;
    private TextView mensaje;


    //About the socket
    Handler handler;
    ClientThread clientThread;



    /** Called when the activity is first created.*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_ip = (EditText) this.findViewById(R.id.edit_ip);
        edit_port = (EditText) this.findViewById(R.id.edit_port);
        edit_send = (EditText) this.findViewById(R.id.edit_send);
        btn_connect = (Button) this.findViewById(R.id.btn_connect);
        btn_open =(Button) this.findViewById(R.id.btn_open);
        btn_sendlist = (Button) this.findViewById(R.id.btn_sendlist);
        btn_send = (Button) this.findViewById(R.id.btn_send);
        mensaje = (TextView) findViewById(R.id.msg2);

        init();

        //Click here to connect
        btn_connect.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String ip = edit_ip.getText().toString();
                String port = edit_port.getText().toString();

                Log.d(TAG, ip + port);

                clientThread = new ClientThread(handler, ip, port);
                new Thread(clientThread).start();
                Log.d(TAG, "clientThread is start!!");
                if(clientThread.isConnect)
                {
                    btn_connect.setText(R.string.btn_disconnect);
                }
            }});

        //Click here to Send Msg to Server
        btn_send.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                try
                {
                    Message msg = new Message();
                    msg.what = 0x852;
                    msg.obj = edit_send.getText().toString();
                    clientThread.sendHandler.sendMessage(msg);
                    edit_send.setText("");
                }
                catch (Exception e)
                {
                    Log.d(TAG, e.getMessage());
                    e.printStackTrace();
                }
            }});

        btn_sendlist.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Message msg = new Message();
                    msg.what = 0x852;
                    msg.obj = "EnviarLista666\n";
                    clientThread.sendHandler.sendMessage(msg);

                }
                catch (Exception e)
                {
                    Log.d(TAG, e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        btn_open.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Date horaActual=new Date();
                SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy 'at' hh:mm:ss a");
                try
                {

                    Message msg = new Message();
                    msg.what = 0x852;
                    msg.obj = "AbrirPuerta555"+"Yerson: "+ft.format(horaActual)+"AbrirPuerta666\n";
                    clientThread.sendHandler.sendMessage(msg);

                }
                catch (Exception e)
                {
                    Log.d(TAG, e.getMessage());
                    e.printStackTrace();
                }

            }
        });

    }


    private void init()
    {

        edit_ip.setText("192.168.1.15");
        edit_port.setText("80");

        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                if(msg.what == 0x123)
                {

                    mensaje.setText(msg.obj.toString()+"\n");
                }
            }
        };
    }


    public boolean onDestory(){
        return true;

    }

}