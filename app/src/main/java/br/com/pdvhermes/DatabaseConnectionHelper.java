package br.com.pdvhermes;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionHelper {

    public static final String TAG = "Connection Database";

    private static String mStringConnectionUrl;

    private static String mStringServerIpName = "ns272.hostgator.com.br";

    private static String mStringUserName = "pdvher45_adm";

    private static String mStringPassword = "iA8HKEm?(b}}";

    private static String mStringDataBase = "pdvher45_PDV";

    private static String mStringPort = "3306";

    public static Connection getConnection(Context mContext){

        Connection mConnection = null;

        try{

            StrictMode.ThreadPolicy mPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(mPolicy);

            //    Class.forName("net.sourceforge.jtds.jdbc.Driver");

            //  mStringConnectionUrl = "jdbc:jtds:sqlserver://" + mStringServerIpName +
            //          ";databaseName=" + mStringDataBase +
            //         ";user=" + mStringUserName +
            //         ";password=" + mStringPassword + ";" ;

            Class.forName("com.mysql.cj.jdbc.Driver");
            mStringConnectionUrl = "jdbc:mysql://" + mStringServerIpName + ":" + mStringPort + "/" + mStringDataBase + "?user=" + mStringUserName + "&password=" + mStringPassword;


            mConnection = DriverManager.getConnection(mStringConnectionUrl);;

            Log.i(TAG , "Connection Successful");

        } catch (ClassNotFoundException e) {
            String mMessage = "Class Not Found" + e.getMessage();
            Log.e(TAG , mMessage);
        } catch (SQLException e) {
            String mMessage = "Database Fail" + e.getMessage();
            Log.e(TAG , mMessage);
        } catch (Exception e) {
            String mMessage = "Failure Unknown" + e.getMessage();
            Log.e(TAG , mMessage);
        }

        return mConnection;

    }
}

