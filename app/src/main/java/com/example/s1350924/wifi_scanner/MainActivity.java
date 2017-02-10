package com.example.s1350924.wifi_scanner;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pintAllIPsInMySubnet();
                wifiAction();
                macCheck();
            }
        });


    }

    private void wifiAction(){

        // Create a wifi manager instance
        WifiManager wifiManager;
        wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);

        // Check the state of the Wifi and turn on if necessary
        if(wifiManager.getWifiState() == WifiManager.WIFI_STATE_DISABLED){
            wifiManager.setWifiEnabled(true);
        }

        wifiManager.startScan();

    }


    private void pintAllIPsInMySubnet(){
        String   s_dns1 ;
        String   s_dns2;
        String   s_gateway;
        String   s_ipAddress;
        String   s_leaseDuration;
        String   s_serverAddress;
        TextView info;
        DhcpInfo d;
        WifiManager wifii;
        wifii= (WifiManager) getSystemService(Context.WIFI_SERVICE);
        d= wifii.getDhcpInfo();

        s_dns1=String.valueOf(d.dns1);
        s_dns2=String.valueOf(d.dns2);
        s_gateway=String.valueOf(d.gateway);
        s_ipAddress=String.valueOf(d.ipAddress);
        s_leaseDuration=String.valueOf(d.leaseDuration);
        String s_netmask=String.valueOf(d.netmask);

        System.out.println("HEY! "+s_netmask);

        // s_netmask is the subnet mask of your current ip Address
        // Go through all 256 possibilities in your subnet
        InetAddress n;
        for (int i=1;i<255;i++){
            String host = s_netmask + "." + i;
            try {
                if (InetAddress.getByName(host).isReachable(10000)) {
                    System.out.println(host + " is reachable");
                }
            } catch(Exception e){

            }
        }


    }

    private void macCheck(){
        ArrayList<String> matched_people = IP_scanner.getMacFromArpCache();
        if(matched_people != null && matched_people.size() != 0) {
            int size = matched_people.size();
            String[] outputArr = new String[size];
            for (int i = 0; i < size; i++) {
                outputArr[i] = matched_people.get(i);
            }

            ListView lv;
            // link the list view, lv, to the xml object "listView"
            lv = (ListView) findViewById(R.id.listView);
            lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, outputArr));


        }else{
            Toast.makeText(this, "Soz m8, nae cunt's around", Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
