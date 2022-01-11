package com.example.projetjeuxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class P2P extends AppCompatActivity {

    TextView connectionStatus, test;
    Button discoverButton;
    ListView listView;
    Button playButton;

    WifiP2pManager manager;
    WifiP2pManager.Channel channel;

    BroadcastReceiver receiver;
    IntentFilter intentFilter;

    List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    String[] deviceNameArray;
    WifiP2pDevice[] deviceArray;

    Socket socket;

    public static ServerClass serverClass;
    public static ClientClass clientClass;

    public static boolean isHost;

    public static ArrayList<String> list_game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p2_p);

        String[] permissions = { "android.permission.ACCESS_FINE_LOCATION",
                "android.permission.ACCESS_COARSE_LOCATION",
                "android.permission.ACCESS_WIFI_STATE",
                "android.permission.CHANGE_WIFI_STATE",
                "android.permission.CHANGE_NETWORK_STATE",
                "android.permission.INTERNET",
                "android.permission.ACCESS_NETWORK_STATE"};
        this.requestAllPermissions(permissions);

        initWork();
        exqListener();
    }

    private void requestAllPermissions(String[] permissions) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 80);
        }
    }

    private void exqListener() {
        discoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        connectionStatus.setText("Discovery Started");
                    }

                    @Override
                    public void onFailure(int i) {
                        connectionStatus.setText("Discovery Failed");
                    }
                });

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final WifiP2pDevice device = deviceArray[i];
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = device.deviceAddress;
                manager.connect(channel, config, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        connectionStatus.setText("Connected: " + device.deviceName);
                    }

                    @Override
                    public void onFailure(int i) {
                        connectionStatus.setText("Not Connected");
                    }
                });
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String msg = typeMsg.getText().toString();
                chooseActivity();
                /*String list = "";

                for(int i = 0; i<3; i++){
                    list += list_game.get(i);
                }
                */String list = list_game.get(0)+list_game.get(1)+list_game.get(2);
                serverClass.write(list.getBytes());

/*                serverClass.write("ok".getBytes());
                Intent intent = new Intent(P2P.this, launchActivity());
                startActivity(intent);*/
                //serverClass.write(msg.getBytes());

            }
        });
    }

    @SuppressLint("WrongViewCast")
    private void initWork(){
        connectionStatus = findViewById(R.id.connection_status);
        discoverButton = findViewById(R.id.buttonDiscover);
        listView = findViewById(R.id.listView);
        playButton = findViewById(R.id.playButton);

        test = findViewById(R.id.test);

        list_game = new ArrayList<String>();

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        receiver = new WifiDirectBroadcastReceiver(manager, channel, this);

        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
    }

    WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
            if(!wifiP2pDeviceList.equals(peers)){
                peers.clear();
                peers.addAll(wifiP2pDeviceList.getDeviceList());

                deviceNameArray = new String[wifiP2pDeviceList.getDeviceList().size()];
                deviceArray = new WifiP2pDevice[wifiP2pDeviceList.getDeviceList().size()];

                int index = 0;
                for(WifiP2pDevice device : wifiP2pDeviceList.getDeviceList()){
                    deviceNameArray[index] = device.deviceName;
                    deviceArray[index] = device;
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, deviceNameArray);
                listView.setAdapter(adapter);

                if(peers.size() == 0){
                    connectionStatus.setText("No Device Found");
                    return;
                }
            }
        }
    };

    WifiP2pManager.ConnectionInfoListener connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
            final InetAddress groupOwnerAddress = wifiP2pInfo.groupOwnerAddress;
            if(wifiP2pInfo.groupFormed && wifiP2pInfo.isGroupOwner){
                connectionStatus.setText("Host");
                isHost = true;
                serverClass = new ServerClass();
                playButton.setVisibility(View.VISIBLE);
                serverClass.start();
            }else if(wifiP2pInfo.groupFormed){
                connectionStatus.setText("Client");
                isHost = false;
                clientClass = new ClientClass(groupOwnerAddress);
                clientClass.start();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause(){
        super.onPause();
        unregisterReceiver(receiver);
    }

    public class ServerClass extends Thread{
        ServerSocket serverSocket;
        private InputStream inputStream;
        private OutputStream outputStream;

        public void write(byte[] bytes){
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run(){
            try {
                serverSocket = new ServerSocket(8888);
                socket = serverSocket.accept();
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    byte[] buffer = new byte[1024];
                    int bytes;

                    while(socket!=null){
                        try {
                            bytes = inputStream.read(buffer);
                            if(bytes>0){
                                int finalBytes = bytes;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        String tempMsg = new String(buffer, 0, finalBytes);
                                        if(tempMsg.equals("stop")){
                                            MainActivity.duelScoreServer += Jeu6.score;
                                        }
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }


    public class ClientClass extends Thread{
        String hostAdd;
        private InputStream inputStream;
        private OutputStream outputStream;

        public ClientClass(InetAddress hostAddress){
            hostAdd = hostAddress.getHostAddress();
            socket = new Socket();
        }

        public void write(byte[] bytes){
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run(){
            try {
                socket.connect(new InetSocketAddress(hostAdd, 8888), 500);
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    byte[] buffer = new byte[1024];
                    int bytes;

                    while(socket!=null){
                        try {
                            bytes = inputStream.read(buffer);
                            if(bytes>0){
                                int finalBytes = bytes;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        String tempMsg = new String(buffer, 0, finalBytes);
                                        if(tempMsg.equals("stop")){
                                            //Corresponding "stop" sent by Jeu6
                                            MainActivity.duelScoreClient += Jeu6.score;
                                        }

                                        if(tempMsg.equals("431") || tempMsg.equals("432") || tempMsg.equals("451") || tempMsg.equals("452") || tempMsg.equals("461") || tempMsg.equals("462")) {
                                            for(int i = 0; i<3; i++){
                                                list_game.add(""+tempMsg.charAt(i));
                                            }
                                            test.setText(tempMsg);
                                        }

                                        //initialization of games
                                        if(list_game.size() == 3 && tempMsg.equals("ok")){
                                            Intent intent = new Intent(P2P.this, launchActivity());
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    public void chooseActivity() {

        ArrayList<String> tactile = new ArrayList<String>();
        ArrayList<String> capteur = new ArrayList<String>();

        while (!list_game.isEmpty()) {
            list_game.remove(0);
        }

        // adding elements
        capteur.add("1"); //balle de golf
        capteur.add("2"); //kart
        tactile.add("3"); //cible
        //capteur.add("4"); //quiz
        //tactile.add("5"); //Comment envoyer la réponse de l'adversaire en P2P //jeu du morpion
        //tactile.add("6"); //flèche

        String tactile_game = tactile.get(new Random().nextInt(tactile.size()));
        String capteur_game = capteur.get(new Random().nextInt(capteur.size()));

        list_game.add("4"); //quiz
        list_game.add(tactile_game);
        list_game.add(capteur_game);
        //Log.d("liste des jeux ", list_game.get(0) + " " + list_game.get(1) + list_game.get(2));

        test.setText(list_game.get(0)+list_game.get(1)+list_game.get(2));
    }

    public Class launchActivity(){

        Class activity = null;
        String current_game = list_game.get(0);
        list_game.remove(current_game);

        switch (current_game) {
            case "1":
                // E.g., if the output is 1, the activity we will open is ActivityOne.class
                activity = Game1.class;
                break;
            case "2":
                activity = Game2.class;
                break;
            case "3":
                activity = Game3.class;
                break;
            case "4":
                activity = Jeu4.class;
                break;
            case "5":
                activity = Game5.class;
                break;
            case "6":
                activity = Game6.class;
                break;
            default:
                activity = MainActivity.class;
                break;
        }

        return activity;
    }
}