package com.example.maricalara.allseasons.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Environment;
import android.os.ResultReceiver;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ajts.androidmads.library.SQLiteToExcel;
import com.example.maricalara.allseasons.Model.DBHelper;
import com.example.maricalara.allseasons.R;
import com.example.maricalara.allseasons.WifiP2PReceiverClass.ClientService;
import com.example.maricalara.allseasons.WifiP2PReceiverClass.WiFiClientBroadcastReceiver;

import java.io.File;
import java.util.ArrayList;

public class SyncSettingsClient extends AppCompatActivity {

    //data variable
    String directory_path;

    public final int fileRequestID = 98;
    public final int port = 7950;


    private WifiP2pManager wifiManager;
    private WifiP2pManager.Channel wifichannel;
    private BroadcastReceiver wifiClientReceiver;

    private IntentFilter wifiClientReceiverIntentFilter;

    private boolean connectedAndReadyToSendFile;

    private boolean filePathProvided;
    private File fileToSend;
    private boolean transferActive;

    private Intent clientServiceIntent;
    private WifiP2pDevice targetDevice;
    private WifiP2pInfo wifiInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_settings_client);


        wifiManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);

        wifichannel = wifiManager.initialize(this, getMainLooper(), null);
        wifiClientReceiver = new WiFiClientBroadcastReceiver(wifiManager, wifichannel, this);

        wifiClientReceiverIntentFilter = new IntentFilter();
        ;
        wifiClientReceiverIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        wifiClientReceiverIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        wifiClientReceiverIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        wifiClientReceiverIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        connectedAndReadyToSendFile = false;
        filePathProvided = false;
        fileToSend = null;
        transferActive = false;
        clientServiceIntent = null;
        targetDevice = null;
        wifiInfo = null;

        registerReceiver(wifiClientReceiver, wifiClientReceiverIntentFilter);

        setClientFileTransferStatus("Client is currently idle");

        //setTargetFileStatus("testing");

        Button btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportDB();
                searchForPeers();
                sendFile();
            }
        });
    }


    public void setTransferStatus(boolean status) {
        connectedAndReadyToSendFile = status;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void setNetworkToReadyState(boolean status, WifiP2pInfo info, WifiP2pDevice device) {
        wifiInfo = info;
        targetDevice = device;
        connectedAndReadyToSendFile = status;
    }

    private void stopClientReceiver() {
        try {
            unregisterReceiver(wifiClientReceiver);
        } catch (IllegalArgumentException e) {
            //This will happen if the server was never running and the stop button was pressed.
            //Do nothing in this case.
        }
    }

    public void searchForPeers() {

        //Discover peers, no call back method given
        // wifiManager.discoverPeers(wifichannel, null);

        wifiManager.discoverPeers(wifichannel, new WifiP2pManager.ActionListener() {

            public void onSuccess() {
                Toast.makeText(SyncSettingsClient.this, "Discovery Initiated",
                        Toast.LENGTH_SHORT).show();


            }

            public void onFailure(int reasonCode) {
                Toast.makeText(SyncSettingsClient.this, "Discovery Failed. Reason: " + reasonCode,
                        Toast.LENGTH_SHORT).show();
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
                Toast.makeText(SyncSettingsClient.this, "Database Exporting! \n" + directory_path, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCompleted(String filePath) {
                Toast.makeText(SyncSettingsClient.this, "Database Exported!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(SyncSettingsClient.this, "Database Export Fail \n!" + e, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void sendFile() {

        //Only try to send file if there isn't already a transfer active
        if (!transferActive) {
            if (!filePathProvided) {
                setClientFileTransferStatus("Select a file to send before pressing send");
            } else if (!connectedAndReadyToSendFile) {
                setClientFileTransferStatus("You must be connected to a server before attempting to send a file");
            } else if (wifiInfo == null) {
                setClientFileTransferStatus("Missing Wifi P2P information");
            } else {
                //Get fileExtension
                String fileXtent = getFileExtension(fileToSend);

                //Launch client service
                clientServiceIntent = new Intent(this, ClientService.class);
                clientServiceIntent.putExtra("fileXtent", fileXtent);
                clientServiceIntent.putExtra("fileToSend", fileToSend);
                clientServiceIntent.setType("*/*");
                clientServiceIntent.putExtra("port", Integer.valueOf(port));

                //clientServiceIntent.putExtra("targetDevice", targetDevice);
                clientServiceIntent.putExtra("wifiInfo", wifiInfo);
                clientServiceIntent.putExtra("clientResult", new ResultReceiver(null) {
                    @Override
                    protected void onReceiveResult(int resultCode, final Bundle resultData) {

                        if (resultCode == port) {
                            if (resultData == null) {
                                //Client service has shut down, the transfer may or may not have been successful. Refer to message
                                transferActive = false;
                            } else {
                                final TextView client_status_text = (TextView) findViewById(R.id.file_transfer_status);

                                client_status_text.post(new Runnable() {
                                    public void run() {
                                        client_status_text.setText((String) resultData.get("message"));
                                    }
                                });
                            }
                        }

                    }
                });

                transferActive = true;
                startService(clientServiceIntent);
                //end
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //fileToSend
        if (resultCode == Activity.RESULT_OK && requestCode == fileRequestID) {
            //Fetch result

            File targetDir = new File(directory_path);
            //File targetDir = (File) data.getExtras().get("file");

            if (targetDir.isFile()) {
                if (targetDir.canRead()) {
                    fileToSend = targetDir;
                    filePathProvided = true;

                    setTargetFileStatus(targetDir.getName() + " selected for file transfer");

                } else {
                    filePathProvided = false;
                    setTargetFileStatus("You do not have permission to read the file " + targetDir.getName());
                }

            } else {
                filePathProvided = false;
                setTargetFileStatus("You may not transfer a directory, please select a single file");
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Continue to listen for wifi related system broadcasts even when paused
        //stopClientReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Kill thread that is transferring data

        //Unregister broadcast receiver
        stopClientReceiver();
    }

    public void setClientWifiStatus(String message) {
        TextView connectionStatusText = (TextView) findViewById(R.id.client_wifi_status_text);
        connectionStatusText.setText(message);
    }

    public void setClientStatus(String message) {
        TextView clientStatusText = (TextView) findViewById(R.id.client_status_text);
        clientStatusText.setText(message);
    }

    public void setClientFileTransferStatus(String message) {
        TextView fileTransferStatusText = (TextView) findViewById(R.id.file_transfer_status);
        fileTransferStatusText.setText(message);
    }

    public void setTargetFileStatus(String message) {
        TextView targetFileStatus = (TextView) findViewById(R.id.selected_filename);
        targetFileStatus.setText(message);
    }

    public void displayPeers(final WifiP2pDeviceList peers) {
//Dialog to show errors/status
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("WiFi Direct File Transfer");

        //Get list view
        ListView peerView = (ListView) findViewById(R.id.peers_listview);

        //Make array list
        ArrayList<String> peersStringArrayList = new ArrayList<String>();

        //Fill array list with strings of peer names
        for (WifiP2pDevice wd : peers.getDeviceList()) {
            peersStringArrayList.add(wd.deviceName);
        }

        //Set list view as clickable
        peerView.setClickable(true);

        //Make adapter to connect peer data to list view
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, peersStringArrayList.toArray());

        //Show peer data in listview
        peerView.setAdapter(arrayAdapter);


        peerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
                //Get string from textview
                TextView tv = (TextView) view;

                WifiP2pDevice device = null;
                //Search all known peers for matching name
                for (WifiP2pDevice wd : peers.getDeviceList()) {
                    if (wd.deviceName.equals(tv.getText()))
                        device = wd;
                }

                if (device != null) {
                    //Connect to selected peer
                    connectToPeer(device);
                } else {
                    dialog.setMessage("Failed");
                    dialog.show();
                }
            }
            // TODO Auto-generated method stub
        });
    }

    public void connectToPeer(final WifiP2pDevice wifiPeer) {
        this.targetDevice = wifiPeer;

        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = wifiPeer.deviceAddress;
        wifiManager.connect(wifichannel, config, new WifiP2pManager.ActionListener() {
            public void onSuccess() {
                Toast.makeText(SyncSettingsClient.this, "Connection to " + targetDevice.deviceName + " Successful",
                        Toast.LENGTH_SHORT).show();
            }

            public void onFailure(int reason) {

                Toast.makeText(SyncSettingsClient.this, "Connection to " + targetDevice.deviceName + " Failed",
                        Toast.LENGTH_SHORT).show();
                //setClientStatus("Connection to " + targetDevice.deviceName + " failed");

            }
        });

    }

    public String getFileExtension(File file) {
        Uri fileUri = Uri.fromFile(file);
        String extension = null;
        try {
            extension = MimeTypeMap.getFileExtensionFromUrl(fileUri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return extension;
    }

}