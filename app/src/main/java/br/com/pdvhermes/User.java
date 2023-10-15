package br.com.pdvhermes;

public class User {

    public static final String TAG = "User table";

    private  int mId;
    private String mName;
    private String mEmail;
    private String mPassword;
    private String mNivelAcesso;

    @Override
    public String toString() {
        return "User{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mNivelAcesso='" + mNivelAcesso + '\'' +
                '}';
    }

    public User(int mId, String mName, String mEmail, String mPassword, String mNivelAcesso) {
        this.mId = mId;
        this.mName = mName;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mNivelAcesso = mNivelAcesso;
    }

    public User(String mName, String mEmail, String mPassword, String mNivelAcesso) {
        this.mName = mName;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mNivelAcesso = mNivelAcesso;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmNivelAcesso() {
        return mNivelAcesso;
    }

    public void setmNivelAcesso(String mNivelAcesso) {
        this.mNivelAcesso = mNivelAcesso;
    }
}