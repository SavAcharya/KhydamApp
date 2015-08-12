package test;

public class JsonParser {
	/*static String content;

public static void readfile(Context context,String reqObj) {
	// TODO Auto-generated method stub
	
	InputStream is=null;
	
	BufferedReader input=null;
	try {
		is=context.getResources().getAssets().open("login_res.json",Context.MODE_WORLD_READABLE);
		input=new BufferedReader(new InputStreamReader(is));
		 content=input.readLine();
		String line=null;
		while((line=input.readLine())!=null){
			content+="\n"+line;
		}
		Log.d("FileRead", content);
		readJson(content,reqObj);
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public static void readJson(String inputJSON,String resObj){
	{
"status" : "Success",
"responseData" : 
  {
  "resCode" : "201"
  }
}
	JSONObject mainJsonObject;
	try {
		mainJsonObject = new JSONObject(inputJSON);
		JSONObject resJsonObject = mainJsonObject.getJSONObject(resObj);
		
		
		if (resObj.equalsIgnoreCase("login")) {
			String status=resJsonObject.getString("status");
			JSONObject resData=resJsonObject.getJSONObject("responseDats");
			String userId=resData.getString("userId");
			String authCode=resData.getString("authCode");
			String userName=resData.getString("userName");
			
			Log.d("Login", status+"  " +userId+"  " +userName+" "+authCode);
			
		} else if (resObj.equalsIgnoreCase("signup")) {
			String status=resJsonObject.getString("status");
			if (status.equalsIgnoreCase("Success")){
			JSONObject resData=resJsonObject.getJSONObject("responseData");
			String resCode=resData.getString("resCode");
			Log.d("signup", status+"  " +resCode);
			}else{
				Log.d("signup","status fail "+ status);
			}
			
			
		} else if (resObj.equalsIgnoreCase("otp")) {
			
			
			String status=resJsonObject.getString("status");
			if (status.equalsIgnoreCase("Success")){
			JSONObject resData=resJsonObject.getJSONObject("responseDats");
			String userId=resData.getString("userId");
			String authCode=resData.getString("authCode");
			
			
			Log.d("otp", status+"  " +userId+" "+authCode);
			}else {
				Log.d("otp","otp fail "+ status);
			}
		}else if (resObj.equalsIgnoreCase("recipeDetails")) {
			String json=resJsonObject.toString();
		ServiceResponceParser.recipeDetailsParser(json);
		
		}
		
		else if (resObj.equalsIgnoreCase("getCatagory")) {
			
			ArrayList<CatagoryBean> catagoryList=new ArrayList<CatagoryBean>();
			String status=resJsonObject.getString("status");
			if (status.equalsIgnoreCase("Success")){
			JSONObject resData=resJsonObject.getJSONObject("responseDats");
			JSONArray recipeCatagoryArray=new JSONArray();
		recipeCatagoryArray=resData.getJSONArray("recipeCatagory");
		if (recipeCatagoryArray != null) {
			for (int i = 0; i < recipeCatagoryArray.length(); i++) {
				CatagoryBean mapData = getParsedCatagoryResult((JSONObject) recipeCatagoryArray.get(i));
				catagoryList.add(mapData);
			}
		}
			
			}else {
				Log.d("otp","otp fail "+ status);
			}
		}
		
		
		
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
}
public static CatagoryBean getParsedCatagoryResult(JSONObject jsonResult){

	CatagoryBean catagoryBean = new CatagoryBean();
	try {	
		catagoryBean.setCatagoryId(jsonResult.getString("recipeCatagoryId"));
		Log.d("Catagory","ID "+ jsonResult.getString("recipeCatagoryId"));
		catagoryBean.setCatagoryName(jsonResult.getString("recipeCatagoryName"));
		Log.d("Catagory","Name "+ jsonResult.getString("recipeCatagoryName"));
		
	} 
	catch (JSONException e) {
		e.printStackTrace();
	}
	return catagoryBean;
}
	*/
}
