package com.rl.solenis.db.migration.utility;

import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;

public class VPNConnectionManager
{
    private static final Logger logger;
    
    static {
        logger = Logger.getLogger(VPNConnectionManager.class);
    }
    
    public static void connectVPN() {
        Process process = null;
        BufferedReader reader = null;
        try {
            process = new ProcessBuilder(new String[] { "sh", "vpnconnect.sh" }).start();
            process.waitFor();
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                if (line.equalsIgnoreCase("VPN CONNECTED")) {
                    VPNConnectionManager.logger.info("VPN Connected Successfully..");
                }
                else if (line.equalsIgnoreCase("VPN AREADY CONNECTED")) {
                    VPNConnectionManager.logger.info("VPN Already Connected..");
                }
                else {
                    VPNConnectionManager.logger.error("Unable to connect vpn,  Please check again");
                    System.exit(0);
                }
            }
        }
        catch (IOException | InterruptedException e) {
            VPNConnectionManager.logger.error("Unable to connect..." + e);
            System.exit(0);
        }
    }
    
    public static void disconnectVPN() {
        Process process = null;
        BufferedReader reader = null;
        try {
            process = new ProcessBuilder(new String[] { "sh", "vpndisconnect.sh" }).start();
            process.waitFor();
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                if ("VPN DISCONNECTED".equalsIgnoreCase(line)) {
                    VPNConnectionManager.logger.info("VPN Disconnected Successfully...");
                }
                else if ("VPN AREADY DISCONNECTED".equalsIgnoreCase(line)) {
                    VPNConnectionManager.logger.info("VPN already disconnected..");
                }
                else {
                    VPNConnectionManager.logger.error("Unable to Disconnect VPN, please check again....");
                }
            }
        }
        catch (IOException | InterruptedException e) {
            logger.error("Unable to disConnectVPN..." + e);
            System.exit(0);
        }
    }
}
