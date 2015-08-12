package in.sayes.android.khadyam.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import org.jetbrains.annotations.NotNull;

public class ConnectivitySpeedtest {
	public static final int NETWORK_TYPE_EHRPD=14; // Level 11
	public static final int NETWORK_TYPE_EVDO_B=12; // Level 9
	public static final int NETWORK_TYPE_HSPAP=15; // Level 13
	public static final int NETWORK_TYPE_IDEN=11; // Level 8
	public static final int NETWORK_TYPE_LTE=13; // Level 11
	Context mContext;
	public ConnectivitySpeedtest(Context c){
		this.mContext=c;
	}
	
	public static boolean isConnectedFast(@NotNull Context context){
	    @NotNull ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo info = cm.getActiveNetworkInfo();
	    return (info != null && info.isConnected() && ConnectivitySpeedtest.isConnectionFast(info.getType(),info.getSubtype()));
	}
	public static boolean isConnectionFast(int type, int subType){
	    if(type==ConnectivityManager.TYPE_WIFI){
	        System.out.println("CONNECTED VIA WIFI");
	        return true;
	    }else if(type==ConnectivityManager.TYPE_MOBILE){
	        switch(subType){
	        case TelephonyManager.NETWORK_TYPE_1xRTT:
	            return false; // ~ 50-100 kbps
	        case TelephonyManager.NETWORK_TYPE_CDMA:
	            return false; // ~ 14-64 kbps
	        case TelephonyManager.NETWORK_TYPE_EDGE:
	            return false; // ~ 50-100 kbps
	        case TelephonyManager.NETWORK_TYPE_EVDO_0:
	            return true; // ~ 400-1000 kbps
	        case TelephonyManager.NETWORK_TYPE_EVDO_A:
	            return true; // ~ 600-1400 kbps
	        case TelephonyManager.NETWORK_TYPE_GPRS:
	            return false; // ~ 100 kbps
	        case TelephonyManager.NETWORK_TYPE_HSDPA:
	            return true; // ~ 2-14 Mbps
	        case TelephonyManager.NETWORK_TYPE_HSPA:
	            return true; // ~ 700-1700 kbps
	        case TelephonyManager.NETWORK_TYPE_HSUPA:
	            return true; // ~ 1-23 Mbps
	        case TelephonyManager.NETWORK_TYPE_UMTS:
	            return true; // ~ 400-7000 kbps
	        // NOT AVAILABLE YET IN API LEVEL 7
	        case ConnectivitySpeedtest.NETWORK_TYPE_EHRPD:
	            return true; // ~ 1-2 Mbps
	        case ConnectivitySpeedtest.NETWORK_TYPE_EVDO_B:
	            return true; // ~ 5 Mbps
	        case ConnectivitySpeedtest.NETWORK_TYPE_HSPAP:
	            return true; // ~ 10-20 Mbps
	        case ConnectivitySpeedtest.NETWORK_TYPE_IDEN:
	            return false; // ~25 kbps
	        case ConnectivitySpeedtest.NETWORK_TYPE_LTE:
	            return true; // ~ 10+ Mbps
	        // Unknown
	        case TelephonyManager.NETWORK_TYPE_UNKNOWN:
	            return false;
	        default:
	            return false;
	        }
	    }else{
	        return false;
	    }
}
}
