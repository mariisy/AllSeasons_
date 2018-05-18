package com.example.maricalara.allseasons.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ajts.androidmads.library.SQLiteToExcel;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.R;
import com.example.maricalara.allseasons.WifiP2PReceiverClass.ServerService;
import com.example.maricalara.allseasons.WifiP2PReceiverClass.WiFiServerBroadcastReceiver;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SyncSettingsActivity extends AppCompatActivity implements WifiP2pManager.ChannelListener {

    private Toolbar toolbar;

    //UI
    Button btnExport, btnStartSend, btnStartRecieve, btnBrowse;

    //get Date String
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    Date d = new Date();
    String dayOfTheWeek = sdf.format(d);
    String dateForTheDay = DateFormat.getDateInstance().format(date);
    String strDate = dayOfTheWeek + "_" + dateForTheDay;

    //data variable
    String directory_path;

    //Wifi P2P
    public final int fileRequestID = 55;
    public final int port = 7950;


    private WifiP2pManager wifiManager;
    private WifiP2pManager.Channel wifichannel;
    private BroadcastReceiver wifiServerReceiver;

    private IntentFilter wifiServerReceiverIntentFilter;

    private String path;
    private File downloadTargetFile;

    private Intent serverServiceIntent;

    private boolean serverThreadActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_settings);

        //inflate toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sync and Update");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Block auto opening keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        wifiManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        wifichannel = wifiManager.initialize(this, getMainLooper(), null);
        wifiServerReceiver = new WiFiServerBroadcastReceiver(wifiManager, wifichannel, this);

        wifiServerReceiverIntentFilter = new IntentFilter();
        ;
        wifiServerReceiverIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        wifiServerReceiverIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        wifiServerReceiverIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        wifiServerReceiverIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        //set status to stopped
        TextView serverServiceStatus = (TextView) findViewById(R.id.server_status_text);
        serverServiceStatus.setText("Server has stopped");

        path = "/";
        downloadTargetFile = new File(path);

        serverServiceIntent = null;
        serverThreadActive = false;

        setServerFileTransferStatus("No File being transfered");

        registerReceiver(wifiServerReceiver, wifiServerReceiverIntentFilter);


        //inflate UI



        btnStartSend = (Button) findViewById(R.id.btnStartSend);
        btnStartSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopServer(null);
                Intent clientStartIntent = new Intent(SyncSettingsActivity.this, SyncSettingsClient.class);
                startActivity(clientStartIntent);
            }
        });

        btnStartRecieve = (Button) findViewById(R.id.btnStartRecieve);
        btnStartRecieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverStart();
            }
        });

        btnBrowse = (Button) findViewById(R.id.btnBrowse);
        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent clientStartIntent = new Intent(SyncSettingsActivity.this, SyncSettingsBrowse.class);
                startActivityForResult(clientStartIntent, fileRequestID);
            }
        });
    }

    private void exportDB() {
        directory_path = Environment.DIRECTORY_DOCUMENTS;
        //  Environment.getRootDirectory().getPath();
        //   .getExternalStorageDirectory().getPath() + "/DBBackup/";
        SQLiteToExcel sqliteToExcel = new SQLiteToExcel(getApplicationContext(), DBHelper.DATABASE_NAME);
        sqliteToExcel.exportAllTables("FarmDB.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {
                Toast.makeText(SyncSettingsActivity.this, "Database Exporting! \n" + directory_path, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCompleted(String filePath) {
                Toast.makeText(SyncSettingsActivity.this, "Database Exported!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(SyncSettingsActivity.this, "Database Export Fail \n!" + e, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    *
    *
    all method that follows are used for the Wifi P2P transfer*
    *
    *
    */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK && requestCode == fileRequestID) {
            //Fetch result
            File targetDir = (File) data.getExtras().get("file");

            if (targetDir.isDirectory()) {
                if (targetDir.canWrite()) {
                    downloadTargetFile = targetDir;
                    TextView filePath = (TextView) findViewById(R.id.server_file_path);
                    filePath.setText(targetDir.getPath());
                    setServerFileTransferStatus("Download directory set to " + targetDir.getName());

                } else {
                    setServerFileTransferStatus("You do not have permission to write to " + targetDir.getName());
                }

            } else {
                setServerFileTransferStatus("The selected file is not a directory. Please select a valid download directory.");
            }

        }
    }

    public void serverStart(){
        //If server is already listening on port or transfering data, do not attempt to start server service
        if (!serverThreadActive) {
            //Create new thread, open socket, wait for connection, and transfer file

            serverServiceIntent = new Intent(SyncSettingsActivity.this, ServerService.class);
            serverServiceIntent.putExtra("saveLocation", downloadTargetFile);
            serverServiceIntent.putExtra("port", new Integer(port));
            serverServiceIntent.putExtra("serverResult", new ResultReceiver(null) {
                @Override
                protected void onReceiveResult(int resultCode, final Bundle resultData) {

                    if (resultCode == port) {
                        if (resultData == null) {
                            //Server service has shut down. Download may or may not have completed properly.
                            serverThreadActive = false;


                            final TextView server_status_text = (TextView) findViewById(R.id.server_status_text);
                            server_status_text.post(new Runnable() {
                                public void run() {
                                    server_status_text.setText("Server has stopped");
                                }
                            });


                        } else {
                            final TextView server_file_status_text = (TextView) findViewById(R.id.server_file_transfer_status);

                            server_file_status_text.post(new Runnable() {
                                public void run() {
                                    server_file_status_text.setText((String) resultData.get("message"));
                                }
                            });
                        }
                    }

                }
            });

            serverThreadActive = true;
            startService(serverServiceIntent);

            //Set status to running
            TextView serverServiceStatus = (TextView) findViewById(R.id.server_status_text);
            serverServiceStatus.setText("Server Running");

        } else {
            //Set status to already running
            TextView serverServiceStatus = (TextView) findViewById(R.id.server_status_text);
            serverServiceStatus.setText("The server is already running");

        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void stopServer(View view) {


        //stop download thread
        if (serverServiceIntent != null) {
            //stopService(serverServiceIntent);
            serverThreadActive = false;
            wifiManager.clearServiceRequests(wifichannel, new WifiP2pManager.ActionListener() {
                public void onSuccess() {
                    TextView serverServiceStatus = (TextView) findViewById(R.id.server_status_text);
                    serverServiceStatus.setText("The server has stopped");
                }

                public void onFailure(int i) {
                    TextView serverServiceStatus = (TextView) findViewById(R.id.server_status_text);
                    serverServiceStatus.setText("Disconnect failed. Reason : " + i);
                    Log.d("wifidirect", "Disconnect failed. Reason :" + i);

                }
            });


            /*
            wifiManager.removeGroup(wifichannel, new ActionListener() {

                public void onFailure(int reasonCode) {

                     TextView serverServiceStatus = (TextView) findViewById(R.id.server_status_text);
                    serverServiceStatus.setText("Disconnect failed. Reason : " + reasonCode);
                    Log.d("wifidirect", "Disconnect failed. Reason :" + reasonCode);

                }

                public void onSuccess() {
                    TextView serverServiceStatus = (TextView) findViewById(R.id.server_status_text);
                    serverServiceStatus.setText("The server has stopped");
                }
            });*/

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //stopServer(null);
        //unregisterReceiver(wifiServerReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopServer(null);

        //stopService(serverServiceIntent);

        //Unregister broadcast receiver
        try {
            unregisterReceiver(wifiServerReceiver);
        } catch (IllegalArgumentException e) {
            // This will happen if the server was never running and the stop
            // button was pressed.
            // Do nothing in this case.
        }
    }

    public void setServerWifiStatus(String message) {
        TextView server_wifi_status_text = (TextView) findViewById(R.id.server_wifi_status_text);
        server_wifi_status_text.setText(message);
    }

    public void setServerStatus(String message) {
        TextView server_status_text = (TextView) findViewById(R.id.server_status_text_2);
        server_status_text.setText(message);
    }


    public void setServerFileTransferStatus(String message) {
        TextView server_status_text = (TextView) findViewById(R.id.server_file_transfer_status);
        server_status_text.setText(message);
    }

    @Override
    public void onChannelDisconnected() {

    }
}
