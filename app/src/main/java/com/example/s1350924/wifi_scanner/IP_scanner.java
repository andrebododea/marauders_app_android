package com.example.s1350924.wifi_scanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by andrebododea on 2/10/17.
 */

public class IP_scanner {

    // http://www.flattermann.net/2011/02/android-howto-find-the-hardware-mac-address-of-a-remote-host/

    public static ArrayList<String> getMacFromArpCache() {

        String[] names = {"Dan's phone","Joel's laptop","Joel's phone","Meghan's laptop","Meghan's phone","Stephan's phone","James' laptop ","Connie's phone","Dan's laptop","Josh's laptop","Angus' phone","Andrew's phone","Patrick's laptop","Simon Rovder's phone","Andre Bododea's phone","Will's phone","Chris' phone","Kostas' phone","Patrick's phone","Suave's laptop","Gavin Waite's phone","Ben's laptop"};

        String[] known_macs = {"44:80:EB:77:41:02","C4:8E:8F:F2:E8:B9","AC:0D:1B:FC:AB:D4","20:C9:D0:CF:94:55","B0:9F:BA:6C:D8:67","40:B8:37:F7:09:5C","40:F0:2F:90:AC:CD ","10:A5:D0:28:59:9D","10:08:B1:3E:22:E5","10:08:B1:84:A8:39","C0:EE:FB:6C:66:F1","64:BC:0C:64:78:0E","4A:00:05:E3:7F:F0","9C:35:EB:66:2C:9F","E0:98:61:74:98:03","D8:96:95:23:50:65","C0:EE:FB:50:E9:DC","20:6E:9C:74:8B:C8","48:DB:50:4A:64:D7","40:B8:9A:1F:1E:63","A4:B8:05:5C:90:10","E8:2A:EA:06:F7:52" };

        ArrayList<String> matched_people = new ArrayList<String>();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                count++;
                String[] splitted = line.split(" +");
                if (splitted != null && splitted.length >= 4) {
                    String mac = mac = splitted[3];
                    System.out.println(mac);
                    for(int i = 0; i < names.length; i++){
                        if(mac.equals(known_macs[i].toUpperCase())){
                            matched_people.add(names[i]);
                        }
                    }
                }
            }

            System.out.println("Line count: "+count);
            return matched_people;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
