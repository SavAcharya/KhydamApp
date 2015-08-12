package in.sayes.android.khadyam.http;

import in.sayes.android.khadyam.R;
import in.sayes.android.khadyam.common.AppConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

/**
 * Helps to make Http GET and Http POST request with server
 *
 * @author Sourav Bhattacharya
 * */
public class AsyncHttpRequest extends AsyncTask<Void, Void, Void> {
    public enum Type {
        POST, GET, POST_WITH_FILE
    };

    public final String SERVER_BUSY = "-1";
    public final String NO_INTERNET = "-2";

    @NotNull
    private static String TAG = "AsyncHttpRequest";
    @Nullable
    private DefaultHttpClient mHttpClient = null;
    @Nullable
    private HttpGet mGet = null;
    @Nullable
    private HttpPost mPost = null;
    @Nullable
    private String mResponse = null;
    @Nullable
    private Exception mError = null;
    private String mUrl = "";
    private int mRequestCode;
    private String mFunctionName = "";
    @Nullable
    private Context mContext = null;
    @Nullable
    private List<NameValuePair> mParams = null;

    private Type mType = Type.GET;
    private boolean mRunning = false;

    @Nullable
    RequestListener mRequestListener = null;
    private MultipartEntityBuilder mRequestEntry;

    public interface RequestListener {
        void onHTTPRequestCompleted(String response,int mRequestCode);

        void onHTTPRequestError(Exception e,int mRequestCode);

        void onHTTPRequestStarted(int mRequestCode);
    }

    public AsyncHttpRequest(Context context, String baseUrl,
                            String functionName, List<NameValuePair> params,int requestCode,
                            Type type, MultipartEntityBuilder reqEntity) {
        this.mParams = params;

        this.mType = type;
        this.mUrl = baseUrl;
        this.mFunctionName = functionName;
        this.mRequestCode=requestCode;
        this.mContext = context;
        this.mRequestEntry = reqEntity;

    }

    public AsyncHttpRequest() {

    }

    public void setRequestListener(@Nullable RequestListener listener) {
        if (listener != null)
            this.mRequestListener = listener;

    }

    public static boolean isConnected(@NotNull final Context ctx) {
        @NotNull ConnectivityManager cm = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    @Override
    protected void onCancelled() {
        if (mType == Type.GET && mGet != null)
            mGet.abort();
        else if (mType == Type.POST && mPost != null)
            mPost.abort();

        mParams = null;
        mPost = null;
        mGet = null;
        mError = null;
        if (mRequestListener != null)
            mRequestListener
                    .onHTTPRequestError(
                            new Exception(mContext
                                    .getString(R.string.async_task_error)),mRequestCode);
        mRequestListener = null;
        mRunning = false;
        super.onCancelled();
    }

    public boolean isRunning() {
        return mRunning;
    }

    @Nullable
    @Override
    protected Void doInBackground(Void... p) {
        if (AsyncHttpRequest.isConnected(mContext))

        {
            mRunning = true;
            try {
                if (mType == Type.GET) {
                    mResponse = doGet(mUrl, mParams);
                } else if (mType == Type.POST)
                    mResponse = doHttpPostRequest(mFunctionName, mParams, mUrl);
                else if (mType == Type.POST_WITH_FILE)
                    mResponse = postData(mFunctionName, mParams, mRequestEntry);
            } catch (Exception e) {
                e.printStackTrace();
                mError = e;
            }
        } else {
            mError = new Exception(
                    mContext.getString(R.string.async_task_no_internet));
        }
        mHandler.sendEmptyMessage(1);
        return null;
    }

    @NotNull
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            mRunning = false;
            if (mError != null && mRequestListener != null) {
                mRequestListener.onHTTPRequestError(mError,mRequestCode);
            } else if (mResponse != null && mRequestListener != null) {
                mRequestListener.onHTTPRequestCompleted(mResponse,mRequestCode);
            }
        }
    };
    @Nullable
    private ProgressDialog progressDialog;

    @Override
    protected void onPostExecute(Void result) {
        progressDialog.dismiss();
    }

    @Override
    protected void onPreExecute() {
        if (mRequestListener != null)
            mRequestListener.onHTTPRequestStarted(mRequestCode);
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancel(true);
            }
        });

        // if(mRequestCode!=000||mRequestCode!=001)
        // progressDialog.show();
        super.onPreExecute();
    }


    @NotNull
    public String doGet(String aUrl, @Nullable List<NameValuePair> params) {

        @NotNull String response = "";
        try {

            mHttpClient = new DefaultHttpClient();
            if (params != null) {
                @NotNull String paramsString = "";
                for (@NotNull NameValuePair nameValuePair : params) {

                    @NotNull String key = nameValuePair.getName().toString().trim();
                    @NotNull String value = nameValuePair.getValue().toString().trim();
                    if (paramsString.isEmpty())
                        paramsString = key + "=" + value;

                    paramsString = "&" + key + "=" + value;
                }

                aUrl = aUrl + "?" + paramsString;
            }
            mGet = new HttpGet(aUrl);
            HttpResponse resp = mHttpClient.execute(mGet);
            if (resp.getStatusLine().getStatusCode() != 200)
                throw new Exception(String.format((mContext
                        .getString(R.string.async_task_request)), resp
                        .getStatusLine().getStatusCode()));
            InputStream inStream = resp.getEntity().getContent();
            response = convertStreamToString(inStream);
            inStream.close();
            return response;
        } catch (UnknownHostException UNHE) {
            System.out.println("In Response UnknownHostException found.");
            Log.e(this.getClass().getName(), "", UNHE);
        } catch (HttpResponseException hrExc) {
            System.out.println("Response not found.");
            System.out.println(hrExc);
        } catch (IOException ioEx) {
            Log.e(this.getClass().getName(), "", ioEx);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return (response);
    }

    @NotNull
    public String postData(String functionName,
                           @NotNull List<NameValuePair> nameValuePair, String baseUrl,int b) {

        @NotNull String downloadedString = "";

        if (isConnected(mContext)) {

            @Nullable BufferedReader in = null;
            try {
                @NotNull final HttpParams httpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
                @NotNull HttpClient client = getNewHttpClient();// new
                // DefaultHttpClient(httpParams);
                @NotNull HttpPost request = new HttpPost(baseUrl + functionName);

                @NotNull List<NameValuePair> postParameters = new ArrayList<NameValuePair>();

                for (int i = 0; i < nameValuePair.size(); i++) {

                    postParameters
                            .add(new BasicNameValuePair(nameValuePair.get(i)
                                    .getName(), nameValuePair.get(i).getValue()));
                }

                @NotNull UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
                        postParameters, "UTF-8");

                request.setEntity(formEntity);
                HttpResponse response = client.execute(request);
                in = new BufferedReader(new InputStreamReader(response
                        .getEntity().getContent()), 8);
                @NotNull StringBuffer sb = new StringBuffer("");
                String line = "";
                String NL = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line + NL);
                }
                in.close();
                downloadedString = sb.toString();
                System.out.println("downloadedString  = " + downloadedString);
                if (downloadedString == null || downloadedString == ""
                        || downloadedString.length() == 0) {
                    downloadedString = SERVER_BUSY;
                }
            } catch (Exception e) {
            }
        } else {
            System.out
                    .println("Sorry,Please check your data package/wifi settings.");
            downloadedString = NO_INTERNET;
        }
        System.out.println("WebService downloaded string length"
                + downloadedString.length());
        return downloadedString;
    }

    @NotNull
    public String postData(String functionName,
                           @NotNull List<NameValuePair> nameValuePair, MultipartEntityBuilder reqEntity) {

        @NotNull String downloadedString = "";

        if (isConnected(mContext)) {

            @Nullable BufferedReader in = null;
            try {
                @NotNull final HttpParams httpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
                @NotNull HttpClient client = getNewHttpClient();// new
                // DefaultHttpClient(httpParams);
                @NotNull HttpPost request = new HttpPost(AppConstants.URL_BASE
                        + functionName);

                @NotNull List<NameValuePair> postParameters = new ArrayList<NameValuePair>();

                for (int i = 0; i < nameValuePair.size(); i++) {

                    postParameters
                            .add(new BasicNameValuePair(nameValuePair.get(i)
                                    .getName(), nameValuePair.get(i).getValue()));

                }

                @NotNull UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
                        postParameters, "UTF-8");
                request.setEntity((HttpEntity) reqEntity);
                request.setEntity(formEntity);

                HttpResponse response = client.execute(request);
                in = new BufferedReader(new InputStreamReader(response
                        .getEntity().getContent()), 8);
                @NotNull StringBuffer sb = new StringBuffer("");
                String line = "";
                String NL = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line + NL);
                }
                in.close();
                downloadedString = sb.toString();
                System.out.println("downloadedString  = " + downloadedString);
                if (downloadedString == null || downloadedString == ""
                        || downloadedString.length() == 0) {
                    downloadedString = SERVER_BUSY;
                }
            } catch (Exception e) {
            }
        } else {
            System.out
                    .println("Sorry,Please check your data package/wifi settings.");
            downloadedString = NO_INTERNET;
        }
        System.out.println("WebService downloaded string length"
                + downloadedString.length());
        return downloadedString;
    }

    @NotNull
    public HttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore
                    .getDefaultType());
            trustStore.load(null, null);

            @NotNull SSLSocketFactory sf = new HttpSSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            @NotNull HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            @NotNull SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory
                    .getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            @NotNull ClientConnectionManager ccm = new ThreadSafeClientConnManager(
                    params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }

    @NotNull
    private String convertStreamToString(@NotNull InputStream instream) {
        @NotNull String response = "";
        @NotNull BufferedReader reader = new BufferedReader(new InputStreamReader(
                instream));
        @NotNull StringBuilder sb = new StringBuilder();
        @Nullable String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                instream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        response = sb.toString();
        return response.trim();
    }
    @Nullable
    public String postData(String functionName,
                           @NotNull List<NameValuePair> nameValuePair, String baseUrl){
        @NotNull HttpClient httpclient = new DefaultHttpClient();
        @NotNull HttpPost httppost = new HttpPost(AppConstants.URL_BASE
                + functionName);
        @Nullable String downloadedString = null;
        try {
            @NotNull List<NameValuePair> postParameters = new ArrayList<NameValuePair>();

            for (int i = 0; i < nameValuePair.size(); i++) {

                postParameters
                        .add(new BasicNameValuePair(nameValuePair.get(i)
                                .getName(), nameValuePair.get(i).getValue()));

            }
            @NotNull UrlEncodedFormEntity param = new UrlEncodedFormEntity(nameValuePair);
            // httppost.setEntity(new UrlEncodedFormEntity(postParameters,HTTP.UTF_8));
            httppost.setEntity(param);

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            @NotNull BufferedReader in = new BufferedReader(new InputStreamReader(response
                    .getEntity().getContent()), 8);
            @NotNull StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            downloadedString = sb.toString();
            System.out.println("downloadedString  = " + downloadedString);
            if (downloadedString == null || downloadedString == ""
                    || downloadedString.length() == 0) {
                downloadedString = SERVER_BUSY;
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        return downloadedString;
    }

    @Nullable
    private String doHttpPostRequest(String functionName,
                                     @NotNull List<NameValuePair> nameValuePair, String baseUrl) throws Exception
    {
        @Nullable String response = null;
        @NotNull List postParameters = new ArrayList<NameValuePair>();



        for (int i = 0; i < nameValuePair.size(); i++) {

            postParameters
                    .add(new BasicNameValuePair(nameValuePair.get(i)
                            .getName(), nameValuePair.get(i).getValue()));

        }

        mHttpClient = new DefaultHttpClient();
        mPost = new HttpPost(baseUrl+functionName);
        mPost.setEntity(new UrlEncodedFormEntity(postParameters));
        HttpResponse resp = mHttpClient.execute(mPost);
        if (resp.getStatusLine().getStatusCode() != 200)
            throw new Exception(String.format((mContext.getString(R.string.async_task_request)), resp.getStatusLine().getStatusCode()));

        InputStream inStream = resp.getEntity().getContent();
        response = convertStreamToString(inStream);
        inStream.close();
        return response;
    }

}