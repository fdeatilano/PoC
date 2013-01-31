package poc;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import asg.cliche.Command;
import asg.cliche.ShellFactory;

public class CrudClient 
{
	private static String PROTOCOL="http";
	private static String CONTEXT="CrudPoC";
	private static String CONTROLLER_PATH="controller";
	private String _hostname="localhost";
	private String _port="8080";
	private static BasicHttpContext _localContext=new BasicHttpContext();
	
	
	private String getBasicAddress(){
		String result;
		if(_port==null){
			result = PROTOCOL+"://"+_hostname+"/"+CONTEXT+"/"+CONTROLLER_PATH+"/"; 
		}else{
			result = PROTOCOL+"://"+_hostname+":"+_port+"/"+CONTEXT+"/"+CONTROLLER_PATH+"/";
		}
		return result;
	}
	

	@Command
	public String setHostnamePort(String hostname, String port){
		_hostname=hostname;
		_port=port;
		return "Hostname set to: "+_hostname+"\nPort set to: "+_port;
	}
	
	@Command
    public String authenticate(String parameters) throws HttpException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(getBasicAddress()+"authenticate?"+parameters); 
		HttpResponse response = httpclient.execute(httpget, _localContext);
        return EntityUtils.toString(response.getEntity());
    }
	
	@Command
    public String get(String entity, String parameters) throws HttpException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		String strUrl = getBasicAddress()+entity;
		if (parameters!=null) {
			strUrl+="?"+parameters;
		}
		HttpGet httpget = new HttpGet(strUrl); 
		HttpResponse response = httpclient.execute(httpget, _localContext);
        return EntityUtils.toString(response.getEntity());
    }
	
	@Command
    public String get(String entity) throws HttpException, IOException {
		return get(entity,null);
    }
	
	@Command
    public String post(String entity, String parameters) throws HttpException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		String strUrl = getBasicAddress()+entity;
		if (parameters!=null) {
			strUrl+="?"+parameters;
		}
		HttpPost httppost = new HttpPost(strUrl); 
		HttpResponse response = httpclient.execute(httppost, _localContext);
        return EntityUtils.toString(response.getEntity());
    }
	
	@Command
    public String post(String entity) throws HttpException, IOException {
		return post(entity,null);
    }
	
	@Command
    public String put(String entity, String parameters) throws HttpException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		String strUrl = getBasicAddress()+entity;
		if (parameters!=null) {
			strUrl+="?"+parameters;
		}
		HttpPut httpput = new HttpPut(strUrl); 
		HttpResponse response = httpclient.execute(httpput, _localContext);
        return EntityUtils.toString(response.getEntity());
    }
	
	@Command
    public String put(String entity) throws HttpException, IOException {
		return put(entity,null);
    }
	
	@Command
    public String delete(String entity, String parameters) throws HttpException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		String strUrl = getBasicAddress()+entity;
		if (parameters!=null) {
			strUrl+="?"+parameters;
		}
		HttpDelete httpdelete = new HttpDelete(strUrl); 
		HttpResponse response = httpclient.execute(httpdelete, _localContext);
        return EntityUtils.toString(response.getEntity());
    }
	
	@Command
    public String delete(String entity) throws HttpException, IOException {
		return delete(entity,null);
    }


    public static void main(String[] args) throws IOException {
    	CookieStore cookieStore = new BasicCookieStore();
    	_localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
        ShellFactory.createConsoleShell("http-query", "", new CrudClient())
            .commandLoop();
    }
}
