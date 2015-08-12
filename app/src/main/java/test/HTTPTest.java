package test;

import in.sayes.android.khadyam.http.HttpSSLSocketFactory;
import in.sayes.android.khadyam.http.WebserviceKeyValues;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
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

public class HTTPTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		@NotNull List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair(WebserviceKeyValues.PHONE_NO, "9876132435"));
		postParameters.add(new BasicNameValuePair(WebserviceKeyValues.DEVICE_ID, "1122"));
		
		@NotNull String responce=postData("login",postParameters , "http://sayes.in/cookingclasses/webservice/index.php?action=");
	System.out.println(responce);
	
	
	}
	@NotNull
    public static String postData(String functionName,
			@NotNull List<NameValuePair> nameValuePair, String baseUrl) {

		@NotNull String downloadedString = "";

		

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
				//request.setEntity(new UrlEncodedFormEntity(postParameters));
				request.setEntity(formEntity);
				HttpResponse response = client.execute(request);
				in = new BufferedReader(new InputStreamReader(response
						.getEntity().getContent()));
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
					downloadedString = "-1";
				}
			} catch (Exception e) {
			}
		
		System.out.println("WebService downloaded string length"
				+ downloadedString.length());
		return downloadedString;
	}


@NotNull
public static HttpClient getNewHttpClient() {
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
}