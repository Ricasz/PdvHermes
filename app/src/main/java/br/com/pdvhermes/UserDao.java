package br.com.pdvhermes;

import android.content.Context;
import android.util.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private static final String TAG = "UserDao";

    public static int insertUser(User mUser, Context mContext){
        int vResponse = 0;
        String mSql;

        try {
            mSql = "INSERT INTO usuario (nome, email, senha, nivel_acesso) VALUES (?, ?, ?, ?)";

            PreparedStatement mPreparedStatement = DatabaseConnectionHelper.getConnection(mContext).prepareStatement(mSql);
            mPreparedStatement.setString(1, mUser.getmName());
            mPreparedStatement.setString(2, mUser.getmEmail());
            mPreparedStatement.setString(3, mUser.getmPassword());
            mPreparedStatement.setString(4, mUser.getmNivelAcesso());

            vResponse = mPreparedStatement.executeUpdate();
        } catch (Exception mException) {
            Log.e(TAG, mException.getMessage());
            mException.printStackTrace();
        }

        return vResponse;
    }

    public static int updateUser(User mUser, Context mContext){
        int vResponse = 0;
        String mSql;

        try {
            mSql = "UPDATE usuario SET nome=?, email=?, senha=?, nivel_acesso=? WHERE id=?";
            PreparedStatement mPreparedStatement = DatabaseConnectionHelper.getConnection(mContext).prepareStatement(mSql);
            mPreparedStatement.setString(1, mUser.getmName());
            mPreparedStatement.setString(2, mUser.getmEmail());
            mPreparedStatement.setString(3, mUser.getmPassword());
            mPreparedStatement.setString(4, mUser.getmNivelAcesso());
            mPreparedStatement.setInt(5, mUser.getmId());

            vResponse = mPreparedStatement.executeUpdate();
        } catch (Exception mException) {
            Log.e(TAG, mException.getMessage());
            mException.printStackTrace();
        }

        return vResponse;
    }

    // ... [Métodos delete e list idênticos aos anteriores]

    public static String authenticateUser(User mUser, Context mContext) {
        String mResponse = "";
        String mSql = "SELECT id, nome, email, senha FROM usuario WHERE senha LIKE ? AND email LIKE ?";

        try {
            PreparedStatement mPreparedStatement = DatabaseConnectionHelper.getConnection(mContext).prepareStatement(mSql);
            mPreparedStatement.setString(1, mUser.getmPassword());
            mPreparedStatement.setString(2, mUser.getmEmail());

            ResultSet mResultSet = mPreparedStatement.executeQuery();
            while (mResultSet.next()) {
                mResponse = mResultSet.getString("nome");
            }
        } catch (Exception e) {
            mResponse = "Exception";
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return mResponse;
    }

}
