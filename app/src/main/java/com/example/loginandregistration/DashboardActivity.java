package com.example.loginandregistration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    private SessionHandler session;
    private String dashboard_url = "http://192.168.43.149/pugacs_test/show_dashboard.php";
    private TextView textResult;
    private TextView textResultStatus, textNo, textNo2, textDepen, textDepento;
    //private EditText etDashUsername;
    private String username;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        TextView welcomeText = findViewById(R.id.welcomeText);
        TextView sessionText = findViewById(R.id.sessionText);
        TextView sBatchText = findViewById(R.id.sBatchText);
        TextView sMajorText = findViewById(R.id.sMajorText);
        textResult = findViewById(R.id.textResult);
        textResultStatus = findViewById(R.id.textResultStatus);
        textNo = findViewById(R.id.textNo);
        textNo2 = findViewById(R.id.textNo2);
        textDepen= findViewById(R.id.textDepen);
        textDepento = findViewById(R.id.textResultDepento);

        //etDashUsername = findViewById(R.id.etDashboardUsername);

        mQueue = Volley.newRequestQueue(this);

        welcomeText.setText("Welcome " + user.getSName());
        sessionText.setText("Your session will expire on " + user.getSessionExpiryDate());
        sBatchText.setText("Batch  = " + user.getSBatch());
        sMajorText.setText("Major = " + user.getSMajor());

        username = user.getUsername();


        Button logoutBtn = findViewById(R.id.btnLogout);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(i);
                finish();

            }
        });

        /*parseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });*/
        final JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put("username", username);
            //request.put(KEY_PASSWORD, password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, dashboard_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("activity");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject activities = array.getJSONObject(i);
                                String list = activities.getString("step");
                                String status = activities.getString("status");
                                String depen = activities.getString("depen");
                                String depento = activities.getString("depento");
                                String no = activities.getString("no");

                                textResult.append(list+"\n");
                                textResultStatus.append(status+"\n");
                                textNo.append(no+"\n");
                                textNo2.append(list+"\n");
                                textDepento.append(no+"\n");
                                textDepen.append(depen+"("+depento+")\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //pDialog.dismiss();

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
        //mQueue.add(jsArrayRequest);
    }

    /*private void jsonParse() {
        //String url = "https://api.myjson.com/bins/egkmc";

        }*/
}

