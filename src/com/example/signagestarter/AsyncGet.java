package com.example.signagestarter;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.lang.Exception;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;

public class AsyncGet extends AsyncTask<String, Integer, String> {

	private static final String url = "jdbc:mysql://192.168.0.243/answerbook";
    private static final String user = "ashida";
    private static final String pass = "ssas04u4";
    private AsyncCallback _asyncCallback = null;;
    String[] _textArray;

    public AsyncGet(AsyncCallback asyncCallback, String[] textArray) {
        this._asyncCallback = asyncCallback;
        this._textArray = textArray;
    }

    protected String doInBackground(String... urls) {
    	
    	testDB();
    	/*
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(urls[0]);
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                httpResponse.getEntity().writeTo(outputStream);
                
                XMLPerse(outputStream);
                
                return outputStream.toString();
            }
        } catch (Exception e) {
            return null;
        }a
        */
        return null;
    }

    private void XMLPerse(ByteArrayOutputStream outputStream) {
		// TODO Auto-generated method stub
    	
    	//XMLパーサーを生成する
    	XmlPullParserFactory factory;
    	try {
			factory = XmlPullParserFactory.newInstance();
		
	    	final XmlPullParser parser = factory.newPullParser();
	    	//XMLパーサに解析したい内容を設定する
	    	parser.setInput(new StringReader(outputStream.toString()));
	
	    	//XML文章の終わりまでループして解析する
	    	for(int eventType = parser.getEventType();eventType != XmlPullParser.END_DOCUMENT; eventType = parser.next()){
	    		String tagName;
	    		String tagText;
	
	    		switch(eventType){
	    		//TAGの始まり
	    		case XmlPullParser.START_TAG:
	    			//TAGの名前を取得する
	    			tagName = parser.getName();
	
	    			//TAGの名前がｔｉｔｌｅ
	    			if(tagName.equals("title")){
	    				//次の要素へ進む
	    				parser.next();
	
	    				//要素がTEXTだったら内容を取り出す
	    				if(parser.getEventType() == XmlPullParser.TEXT){
	    					tagText = parser.getText();
	    					Log.v("とれた", tagText);
	    				}
	    			}
	    		}
	    	}
    	} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	
	}

	protected void onPreExecute() {
        super.onPreExecute();
        this._asyncCallback.onPreExecute();
    }

    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        this._asyncCallback.onProgressUpdate(values[0]);
    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        this._asyncCallback.onPostExecute(result);
    }

    protected void onCancelled() {
        super.onCancelled();
        this._asyncCallback.onCancelled();
    }

    public void testDB() {
    	//TextView tv = (TextView)this.findViewById(R.id.text_view);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);
            /* System.out.println("Database connection success"); */
 
            String result = "Database connection success\n";
            Statement st = (Statement) con.createStatement();
            //ResultSet rs = (ResultSet) st.executeQuery("DESC answer");
            ResultSet rs = (ResultSet) st.executeQuery("select * from answer");
            ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
 
            while(rs.next()) {
            	// SELECT向け
            	result += rsmd.getColumnName(1) + ": " + rs.getInt(1) + "\n";
            	result += rsmd.getColumnName(2) + ": " + rs.getString(2) + "\n";
            	result += rsmd.getColumnName(3) + ": " + rs.getString(3) + "\n";
            	// DESC answer向け
            	//result += rsmd.getColumnName(1) + ": " + rs.getString(1) + "\n";
            	//result += rsmd.getColumnName(2) + ": " + rs.getString(2) + "\n";
            	//result += rsmd.getColumnName(3) + ": " + rs.getString(3) + "\n";
            }
            Log.v("でーたべーす", result);
            //tv.setText(result);
        }
        catch(Exception e) {
            e.printStackTrace();
            //tv.setText(e.toString());
        }   
 
    }
}