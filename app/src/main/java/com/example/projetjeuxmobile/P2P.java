package com.example.projetjeuxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
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
            //String list = "342"; //chooseActivity();
            @Override
            public void onClick(View view) {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if(isHost){
                            list_game.add("3");
                            //list_game.add("4");
                            list_game.add("2");
                            serverClass.write("start".getBytes());
                            Intent intent = new Intent(getBaseContext(), launchActivity());
                            startActivity(intent);
                        }else{
                            //clientClass.write(list.getBytes());
                        }
                    }
                });
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
                                        String[] newTempMsg = tempMsg.split(":");
                                        switch(tempMsg){
                                            case "stop":
                                                MainActivity.duelScoreServer += Jeu6.score;
                                                break;
                                            case "pret":
                                                Intent intent = new Intent(P2P.this, launchActivity());
                                                startActivity(intent);
                                                break;
                                            case "termine":
                                                Score.scoreJoueur2.setText("Joueur 2 : " + MainActivity.duelScoreClient);
                                                break;
                                        }

                                        if(newTempMsg[0].equals("termine")){
                                            MainActivity.duelScoreClient = Integer.parseInt(newTempMsg[1]);
                                            Log.d("tempMsg",""+newTempMsg[1]);
                                            //Score.playerOneScore.setText("Joueur 11 : " + MainActivity.duelScoreServer );
                                            Score.playerTwoScrore.setText("Joueur 22 : " + newTempMsg[1] );
                                            //comparer le score reçu avec le score du serveur
                                        }

                                        //MainActivity.duelScoreClient = Integer.parseInt(tempMsg);
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
                                        //test.setText(tempMsg);
                                        String[] newTempMsg = tempMsg.split(":");

                                        if(tempMsg.equals("341")){
                                            list_game.add("3");
                                            list_game.add("4");
                                            list_game.add("1");
                                            Intent intent = new Intent(getBaseContext(), launchActivity());
                                            startActivity(intent);
                                        }else if(tempMsg.equals("342")){
                                            list_game.add("3");
                                            list_game.add("4");
                                            list_game.add("2");
                                            Intent intent = new Intent(getBaseContext(), launchActivity());
                                            startActivity(intent);
                                        }else if(tempMsg.equals("641")){
                                            list_game.add("6");
                                            list_game.add("4");
                                            list_game.add("1");
                                            Intent intent = new Intent(getBaseContext(), launchActivity());
                                            startActivity(intent);
                                        }else if(tempMsg.equals("642")){
                                            list_game.add("6");
                                            list_game.add("4");
                                            list_game.add("2");
                                            Intent intent = new Intent(getBaseContext(), launchActivity());
                                            startActivity(intent);
                                        }else if(tempMsg.equals("start")){
                                            list_game.add("3");
                                            //list_game.add("4");
                                            list_game.add("2");
                                            Intent intent = new Intent(getBaseContext(), launchActivity());
                                            startActivity(intent);
                                        }else if(newTempMsg[0].equals("termine")){
                                            MainActivity.duelScoreServer = Integer.parseInt(newTempMsg[1]);
                                            Log.d("tempMsg", newTempMsg[1]+"");
                                            Score.playerOneScore.setText("Joueur 11 : " + newTempMsg[1] );
                                            //Score.playerTwoScrore.setText("Joueur 22 : " + MainActivity.duelScoreClient );
                                            //comparer le score reçu avec le score du client
                                        }


                                        /*switch(tempMsg) {
                                            case "stop":
                                                //Corresponding "stop" sent by Jeu6
                                                MainActivity.duelScoreClient += Jeu6.score;
                                                break;
                                            case "341":
                                                test.setText("j'ai recu");
                                                list_game.add("3");
                                                list_game.add("4");
                                                list_game.add("1");
                                                break;
                                            case "342":
                                                list_game.add("3");
                                                list_game.add("4");
                                                list_game.add("2");
                                                break;
                                            case "541":
                                                list_game.add("5");
                                                list_game.add("4");
                                                list_game.add("1");
                                                break;
                                            case "542":
                                                list_game.add("5");
                                                list_game.add("4");
                                                list_game.add("2");
                                                break;
                                            case "641":
                                                list_game.add("6");
                                                list_game.add("4");
                                                list_game.add("1");
                                                break;
                                            case "642":
                                                list_game.add("6");
                                                list_game.add("4");
                                                list_game.add("2");
                                                break;
                                            case "btn_0":
                                                if(P2P.isHost ==true){
                                                    Jeu5.buttons[0].setText("X");
                                                    Jeu5.buttons[0].setTextColor(Color.parseColor("#FFC34A"));
                                                    Jeu5.gameState[Jeu5.gameStatePointer] = 0;
                                                    Jeu5.tour ++;
                                                } else if(P2P.isHost ==true){
                                                    Jeu5.buttons[0].setText("0");
                                                    Jeu5.buttons[0].setTextColor(Color.parseColor("#70FFEA"));
                                                    Jeu5.gameState[Jeu5.gameStatePointer] = 0;
                                                    Jeu5.tour ++;

                                                }
                                                break;case "btn_1":
                                                if(P2P.isHost ==true){
                                                    Jeu5.buttons[1].setText("X");
                                                    Jeu5.buttons[1].setTextColor(Color.parseColor("#FFC34A"));
                                                    Jeu5.gameState[Jeu5.gameStatePointer] = 0;
                                                    Jeu5.tour ++;
                                                } else if(P2P.isHost ==true){
                                                    Jeu5.buttons[1].setText("0");
                                                    Jeu5.buttons[1].setTextColor(Color.parseColor("#70FFEA"));
                                                    Jeu5.gameState[Jeu5.gameStatePointer] = 0;
                                                    Jeu5.tour ++;

                                                }
                                                break;case "btn_2":
                                                if(P2P.isHost ==true){
                                                    Jeu5.buttons[2].setText("X");
                                                    Jeu5.buttons[2].setTextColor(Color.parseColor("#FFC34A"));
                                                    Jeu5.gameState[Jeu5.gameStatePointer] = 0;
                                                    Jeu5.tour ++;
                                                } else if(P2P.isHost ==true){
                                                    Jeu5.buttons[2].setText("0");
                                                    Jeu5.buttons[2].setTextColor(Color.parseColor("#70FFEA"));
                                                    Jeu5.gameState[Jeu5.gameStatePointer] = 0;
                                                    Jeu5.tour ++;

                                                }
                                                break;case "btn_3":
                                                if(P2P.isHost ==true){
                                                    Jeu5.buttons[3].setText("X");
                                                    Jeu5.buttons[3].setTextColor(Color.parseColor("#FFC34A"));
                                                    Jeu5.gameState[Jeu5.gameStatePointer] = 0;
                                                    Jeu5.tour ++;
                                                } else if(P2P.isHost ==true){
                                                    Jeu5.buttons[3].setText("0");
                                                    Jeu5.buttons[3].setTextColor(Color.parseColor("#70FFEA"));
                                                    Jeu5.gameState[Jeu5.gameStatePointer] = 0;
                                                    Jeu5.tour ++;

                                                }
                                                break;case "btn_4":
                                                if(P2P.isHost ==true){
                                                    Jeu5.buttons[4].setText("X");
                                                    Jeu5.buttons[4].setTextColor(Color.parseColor("#FFC34A"));
                                                    Jeu5.gameState[Jeu5.gameStatePointer] = 0;
                                                    Jeu5.tour ++;
                                                } else if(P2P.isHost ==true){
                                                    Jeu5.buttons[4].setText("0");
                                                    Jeu5.buttons[4].setTextColor(Color.parseColor("#70FFEA"));
                                                    Jeu5.gameState[Jeu5.gameStatePointer] = 0;
                                                    Jeu5.tour ++;

                                                }
                                                break;case "btn_5":
                                                if(P2P.isHost ==true){
                                                    Jeu5.buttons[5].setText("X");
                                                    Jeu5.buttons[5].setTextColor(Color.parseColor("#FFC34A"));
                                                    Jeu5.gameState[Jeu5.gameStatePointer] = 0;
                                                    Jeu5.tour ++;
                                                } else if(P2P.isHost ==true){
                                                    Jeu5.buttons[5].setText("0");
                                                    Jeu5.buttons[5].setTextColor(Color.parseColor("#70FFEA"));
                                                    Jeu5.gameState[Jeu5.gameStatePointer] = 0;
                                                    Jeu5.tour ++;

                                                }
                                                break;case "btn_6":
                                                if(P2P.isHost ==true){
                                                    Jeu5.buttons[6].setText("X");
                                                    Jeu5.buttons[6].setTextColor(Color.parseColor("#FFC34A"));
                                                    Jeu5.gameState[Jeu5.gameStatePointer] = 0;
                                                    Jeu5.tour ++;
                                                } else if(P2P.isHost ==true){
                                                    Jeu5.buttons[6].setText("0");
                                                    Jeu5.buttons[6].setTextColor(Color.parseColor("#70FFEA"));
                                                    Jeu5.gameState[Jeu5.gameStatePointer] = 0;
                                                    Jeu5.tour ++;

                                                }
                                                break;case "btn_7":
                                                if(P2P.isHost ==true){
                                                    Jeu5.buttons[7].setText("X");
                                                    Jeu5.buttons[7].setTextColor(Color.parseColor("#FFC34A"));
                                                    Jeu5.gameState[Jeu5.gameStatePointer] = 0;
                                                    Jeu5.tour ++;
                                                } else if(P2P.isHost ==true){
                                                    Jeu5.buttons[7].setText("0");
                                                    Jeu5.buttons[7].setTextColor(Color.parseColor("#70FFEA"));
                                                    Jeu5.gameState[Jeu5.gameStatePointer] = 0;
                                                    Jeu5.tour ++;

                                                }
                                                break;case "btn_8":
                                                if(P2P.isHost ==true){
                                                    Jeu5.buttons[8].setText("X");
                                                    Jeu5.buttons[8].setTextColor(Color.parseColor("#FFC34A"));
                                                    Jeu5.gameState[Jeu5.gameStatePointer] = 0;
                                                    Jeu5.tour ++;
                                                } else if(P2P.isHost ==true){
                                                    Jeu5.buttons[8].setText("0");
                                                    Jeu5.buttons[8].setTextColor(Color.parseColor("#70FFEA"));
                                                    Jeu5.gameState[Jeu5.gameStatePointer] = 0;
                                                    Jeu5.tour ++;

                                                }
                                                break;

                                            case "ok":
                                                //initialization of games
                                                test.setText("j'ai reçu");
                                            Intent intent = new Intent(P2P.this, launchActivity());
                                            startActivity(intent);
                                                break;
                                            case "termine":
                                                Scrore.scoreJoueur1.setText("Joueur 1 : " + MainActivity.duelScoreServer);
                                                break;

                                        }

                                        clientClass.write("pret".getBytes());
                                        Intent intent = new Intent(P2P.this, launchActivity());
                                        startActivity(intent);*/

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

    public String chooseActivity() {

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
        //tactile.add("5");  //jeu du morpion
        tactile.add("6"); //flèche

        String tactile_game = tactile.get(new Random().nextInt(tactile.size()));
        String capteur_game = capteur.get(new Random().nextInt(capteur.size()));

        list_game.add(tactile_game);
        list_game.add("4"); //quiz
        list_game.add(capteur_game);
        //Log.d("liste des jeux ", list_game.get(0) + " " + list_game.get(1) + list_game.get(2));

        Log.d("listejeux",list_game.get(0)+list_game.get(1)+list_game.get(2));
        return list_game.get(0)+list_game.get(1)+list_game.get(2);
    }

    public static Class launchActivity(){


        Class activity = null;
        String current_game = list_game.get(0);

        if(list_game.size() == 1){
            list_game.add("0");
        }

        Log.d("launchSizeBefore", list_game.size() + " " + list_game.get(0));
        list_game.remove(0);
        Log.d("launchSizeAfter", list_game.size() + " " + list_game.get(0));

        switch (current_game) {
            case "0":
                activity = Score.class;
                break;
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
                activity = Jeu6.class;
                break;
            default:
                activity = Score.class;
                break;
        }

        return activity;
    }
}