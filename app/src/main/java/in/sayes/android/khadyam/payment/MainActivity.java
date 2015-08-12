package in.sayes.android.khadyam.payment;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.emv.pgsdk.EmvMerchant;
import com.emv.pgsdk.EmvOrder;
import com.emv.pgsdk.EmvPGService;
import com.emv.pgsdk.EmvPaymentTransactionCallback;
import com.emv.pgsdk.EmvStatusQuery;
import com.emv.pgsdk.EmvStatusQueryCallback;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MainActivity extends Activity {
	
	String ME_TxnID,ItemsPurchased,PayMedium,ItemQuantity,order_id,cust_id,email,mobile,cust_name,Amount;
	String ShipAddress1, ShipAddress2, ShipAddress3, ShipCity, ShipState, ShipZipCode, ShipCountry;
	String BillAddress1, BillAddress2, BillAddress3, BillCity, BillState, BillZipCode, BillCountry;
	@NotNull
    String TxnDate, ME_ReturnURL="https://www.emvantage.com/ResponseForMobileApp.aspx";
	SharedPreferences sp, sp2;
	SharedPreferences.Editor  ed;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("ProjectPref",0);
		
		ed=sp.edit();
		order_id="appoid"+System.currentTimeMillis();
		ME_TxnID="43212234";
		ItemsPurchased="Mobile Phone";
		PayMedium="1";
		ItemQuantity="1";
		cust_id="Customer001";
		Amount="1.00";
		email="pankaj@emvantage.com";
		mobile="9999999999";
		cust_name="Pankaj";
		
		ShipAddress1="ShipAddress1";
		ShipAddress2="ShipAddress3";
		ShipAddress3="ShipAddress3";
		ShipCity="ShipCity";
		ShipState="ShipState";
		ShipZipCode="110018";
		ShipCountry="IND";
		
		BillAddress1="ShipAddress1";
		BillAddress2="BillAddress2";
		BillAddress3="BillAddress3";
		BillCity="BillCity";
		BillState="BillState";
		BillZipCode="110018";
		BillCountry="IND";
		Calendar c = Calendar.getInstance();        
        @NotNull SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TxnDate = df.format(c.getTime());		
		Log.d("pgactivitycamouny", Amount);
		// User can also call status enquiry from outside transaction thread like this
		
		StatusEnquiry("oid1406875175313", "899871495953317", "43212234", Amount, "01-08-2014");
		@Nullable EmvPGService Service = null;
		
		//Service = EmvPGService.getProductionService();
		Service = EmvPGService.getStagingService();//getProductionService for production and getStagingService for Testing
		//Create new order Object having all order information.
		
		@NotNull EmvOrder Order = new EmvOrder(order_id, ME_TxnID, Amount, "356", "356", "", "", "", "", "",
				cust_id, PayMedium, ItemsPurchased, ItemQuantity, 
				ShipAddress1, ShipAddress2, ShipAddress3, ShipCity, ShipState, ShipZipCode, ShipCountry, 
				BillAddress1, BillAddress2, BillAddress3, BillCity, BillState, BillZipCode, BillCountry, 
				cust_name, email, mobile, ME_ReturnURL, TxnDate, "Android App");
		
		//Create new Merchant Object having all merchant configuration.
	@NotNull EmvMerchant Merchant = new EmvMerchant("899871495953317", "91F86049E6318BC5761DAA76FAEB0873", "F2C10F3A02BF9C151B3422A6732DAF40", "RFU", "SALE",
				"1.5", "RFU" );
		
		//Set EmvOrder, EmvMerchant and EmvClientCertificate Object. Call this method and set both objects before
		// starting transaction.
		Service.initialize(Order, Merchant,null);
		Service.startPaymentTransaction(MainActivity.this, true, new EmvPaymentTransactionCallback() {

			@Override
			public void someUIErrorOccurred(String arg0) {
				// TODO Auto-generated method stub
				Log.d("someUIErrorOccurred", arg0);
				String message=arg0;
				
				ShowAlertDialog(message);
			}

			@Override
			public void onTransactionSuccess(@NotNull Bundle arg0) {
				// TODO Auto-generated method stub
				Log.d("onTransactionSuccess", arg0.toString());
				String amount=arg0.getString("TxnAmount");
				String status=arg0.getString("TxnStatus");
				String trans_res=arg0.getString("TxnRespMessage");
				String ME_TxnID=arg0.getString("ME_TXNID");
				String TxnID=arg0.getString("TxnID");
				ShowAlertDialog(TxnID + " " + trans_res);
				new CompleteOrder().execute("0",ME_TxnID,order_id,Amount);
				StatusEnquiry(arg0.getString("ME_OrderID"), arg0.getString("MerchantID"), ME_TxnID, amount, arg0.getString("TxnDate"));
			}

			@Override
			public void onTransactionFailure(@NotNull String arg0, @NotNull Bundle arg1) {
				// TODO Auto-generated method stub
				Log.d("onTransactionFailure", arg0.toString()+arg1.toString());
				@NotNull String message=arg0;
				//ShowAlertDialog(message);
				
			}

			@Override
			public void onErrorLoadingWebPage(int arg0, @NotNull String arg1, @NotNull String arg2) {
				// TODO Auto-generated method stub
				Log.d("onErrorLoadingWebPage", arg2.toString()+arg1.toString());
				ShowAlertDialog(arg2);
				
			}

			@Override
			public void networkNotAvailable() {
				// TODO Auto-generated method stub
				ShowAlertDialog("Please check your connection settings. Network Unavailable!");
				
			}

			@Override
			public void clientAuthenticationFailed(@NotNull String arg0) {
				// TODO Auto-generated method stub
				Log.d("onErrorLoadingWebPage", arg0.toString());
				ShowAlertDialog(arg0);
				
			}
		});
		//Start the Payment Transaction. Before starting the transaction ensure that initialize method is called.
		
		//Start the Payment Transaction. Before starting the transaction ensure that initialize method is called.
	}
	private class CompleteOrder extends AsyncTask<String,ArrayList<HashMap<String,String>>,ArrayList<HashMap<String,String>>>
	{
		private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			super.onPreExecute();
			this.dialog.setMessage("Please Wait..."); 
			this.dialog.show(); 
			this.dialog.setCanceledOnTouchOutside(false);
		}

		@NotNull
        @Override
		protected ArrayList<HashMap<String,String>> doInBackground(String...arg0) {
			// TODO Auto-generated method stub
			//process your order here
			@NotNull HashMap<String, String> mMap = new HashMap<String, String>();
			@NotNull ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
			
			mMap.put("Status","1");
			mMap.put("Balance","yes");
			list.add(mMap);
			return list;
			//return AfterGetResponseFromPG.CompleteOrder(arg0[0], arg0[1], arg0[2], arg0[3], "");
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(@NotNull ArrayList<HashMap<String,String>> result) {
			super.onPostExecute(result);
			if (this.dialog.isShowing()) 
			{
				this.dialog.dismiss();
			}
			// TODO Auto-generated method stub
			//Toast.makeText(PaymentActivity.this, result, Toast.LENGTH_LONG).show();
			HashMap<String,String> user_information= result.get(0);
			String Status=user_information.get("Status");
			Log.d("currentbalanceu",user_information.get("Balance"));
			if(Status.equals("Error")){
				Toast.makeText(MainActivity.this,"Server problem",Toast.LENGTH_SHORT).show();
			}

			else {
				if(Status.equals("try again"))
				{
					Toast.makeText(MainActivity.this,"Please Try Again",Toast.LENGTH_SHORT).show();	

				}
				else
				{

					
					ed.putString("balance",user_information.get("Balance").toString());
					ed.commit();
					ShowAlertDialog(Status);
					//AlertDialogUtil.showAlertDialog(MainActivity.this,Status,"Transaction Status", intent);

				}
			}



		}
	}

	public void StatusEnquiry(String inOrderId, String inMerchantId, String inTxnId, String inTxnAmount, String inTxnDate)
	{
		ShowAlertDialog("Transaction Enquiry Begin");
		@Nullable EmvPGService Service = null;
		Service = EmvPGService.getStagingService();
		@NotNull EmvStatusQuery statusQryobj=new EmvStatusQuery(inOrderId, inMerchantId, inTxnId, inTxnAmount, inTxnDate);
		Service.TransactionEnquiry(MainActivity.this, statusQryobj, null, new EmvStatusQueryCallback() {

			@Override
			public void onStatusQueryCompleted(@NotNull Bundle arg0) {
				// TODO Auto-generated method stub
				try
				{
					Log.d("onStatusQueryCompleted", arg0.toString());
					String amount=arg0.getString("TxnAmount");
					String status=arg0.getString("TxnStatus");
					String trans_res=arg0.getString("RESPONSE_MESSAGE");
					String trans_no=arg0.getString("TxnID");
					//ShowAlertDialog("Transaction Enquiry Completed Status : "+trans_no + " " + trans_res);
				}
				catch(Exception e)
				{
					Log.d("onStatusQueryCompleted", e.toString());
					//ShowAlertDialog("Transaction Enquiry Exception onStatusQueryCompleted "+ e.toString());
				}
				
			}

			@Override
			public void onStatusQueryFailed(String arg0) {
				// TODO Auto-generated method stub
				Log.d("someUIErrorOccurred", arg0);
				String message=arg0;
				
				//ShowAlertDialog("Transaction Enquiry Failure : "+message);
				
			}

		});
	}
	
	public void ShowAlertDialog(String msg)
	{
		@NotNull AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	    alertDialogBuilder.setMessage(msg);
	    @NotNull AlertDialog alertDialog = alertDialogBuilder.create();
	    alertDialog.show();
	}
	
}
