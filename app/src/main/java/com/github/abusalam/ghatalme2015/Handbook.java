package com.github.abusalam.ghatalme2015;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.apache.http.conn.util.InetAddressUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


public class Handbook extends ActionBarActivity {

  public static final String TAG = Handbook.class.getSimpleName();
  public static final String API_HOST = "http://10.42.0.1";
  static final String API_URL = API_HOST + "/apps/android/api.php";

  private RequestQueue rQueue;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_handbook);

    rQueue = VolleyAPI.getInstance(this).getRequestQueue();

    ListView listView = (ListView) findViewById(R.id.listView);

    ArrayList<Page> pageList = new ArrayList<>();

    ArrayList<Integer> imgList = new ArrayList<>();
    imgList.add(R.mipmap.p01);
    imgList.add(R.mipmap.p02);
    imgList.add(R.mipmap.p03);
    imgList.add(R.mipmap.p04);
    imgList.add(R.mipmap.p05);
    imgList.add(R.mipmap.p06);
    imgList.add(R.mipmap.p07);
    imgList.add(R.mipmap.p08);
    imgList.add(R.mipmap.p09);
    imgList.add(R.mipmap.p10);
    imgList.add(R.mipmap.p11);
    imgList.add(R.mipmap.p12);
    imgList.add(R.mipmap.p13);
    imgList.add(R.mipmap.p14);
    imgList.add(R.mipmap.p15);
    imgList.add(R.mipmap.p16);
    imgList.add(R.mipmap.p17);
    imgList.add(R.mipmap.p18);
    imgList.add(R.mipmap.p19);

    for (int anImgList : imgList) {
      Page mPage = new Page(anImgList);
      pageList.add(mPage);
    }

    listView.setAdapter(new PageAdapter(Handbook.this, R.layout.page_view, pageList));
    if (savedInstanceState == null) {
      NetConnection IC = new NetConnection(getApplicationContext());
      if (IC.isDeviceConnected()) {
        String IMEI = null;
        String MobileNo = null;
        String IP = null;
        try {
          TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
          IMEI = tm.getDeviceId();
          MobileNo = tm.getLine1Number();
          IP = getLocalIpAddress();
        } catch (Exception e) {
          Log.e(TAG, "Error: " + e.getMessage());
        }
        sendLog(IMEI, MobileNo, IP);
        Log.e(TAG, "IMEI:" + IMEI + " MDN:" + MobileNo + " IP:" + IP);
      }
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    rQueue.cancelAll(TAG);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_handbook, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  /**
   * Obtain Local IP Address
   */
  public String getLocalIpAddress() {
    try {
      for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
        NetworkInterface intf = en.nextElement();
        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
          InetAddress inetAddress = enumIpAddr.nextElement();
          String ipv4;
          if (!inetAddress.isLoopbackAddress()
            && InetAddressUtils.isIPv4Address(ipv4 = inetAddress.getHostAddress())) {
            return ipv4;
          }
        }
      }
    } catch (SocketException ex) {
      Log.e("IP Error:", ex.toString());
    }
    return "IP Error";
  }

  private void sendLog(String IMEI, String mobileNo, String IP) {
    final JSONObject jsonPost = new JSONObject();

    try {
      jsonPost.put("API", "AL");
      jsonPost.put("IP", IP);
      jsonPost.put("IMEI", IMEI);
      jsonPost.put("MDN", mobileNo);
    } catch (JSONException e) {
      VolleyLog.d(TAG, "Error API:" + jsonPost.toString());
      return;
    }

    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
      Handbook.API_URL, jsonPost,
      new Response.Listener<JSONObject>() {

        @Override
        public void onResponse(JSONObject response) {
          VolleyLog.d(TAG, "AccessLog: " + response.toString());
        }
      }, new Response.ErrorListener() {

      @Override
      public void onErrorResponse(VolleyError error) {
        VolleyLog.d(TAG, "Error: ");
      }
    });

    // Adding request to request queue
    jsonObjReq.setTag(TAG);
    rQueue.add(jsonObjReq);
    Log.e(TAG, "API Call: " + jsonPost.toString());
  }

  public class PageAdapter extends ArrayAdapter<Page> {
    int mResource;

    public PageAdapter(Context context, int resource, List<Page> Pages) {
      super(context, resource, Pages);
      this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      LinearLayout pageView;

      Page mPage = getItem(position);

      if (convertView == null) {
        pageView = new LinearLayout(getContext());

        LayoutInflater li = (LayoutInflater) getContext()
          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(mResource, pageView, true);

      } else {
        pageView = (LinearLayout) convertView;
      }

      ImageView imgView = (ImageView) pageView.findViewById(R.id.imageView);

      //Picasso.with(getApplicationContext()).load(mPage.getImage()).into(imgView);
      imgView.setImageResource(mPage.getImage());

      return pageView;
    }
  }
}
