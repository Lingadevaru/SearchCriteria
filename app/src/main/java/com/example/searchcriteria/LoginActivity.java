package com.example.searchcriteria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import com.example.jsonobjectrequestsample.NewActivity;
//import com.example.jsonobjectrequestsample.MyApplication;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.loopj.android.http.AsyncHttpClient;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.json.JSONException;
import org.json.JSONObject;

import static com.loopj.android.http.AsyncHttpClient.log;

public class LoginActivity extends AppCompatActivity {

    private EditText login_editText, password_editText;
    String token;
    TextView token_name;
    Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Gson gson = new Gson();

        login_editText = findViewById(R.id.login_edit);
        password_editText = findViewById(R.id.password_edit);
        login_button = findViewById(R.id.btn_login);
        token_name = findViewById(R.id.token);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://www.greenwaveps.com/bi.api/api/v1/Auth/GetToken";
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();

                String login_name = login_editText.getText().toString();
                String password_name = password_editText.getText().toString();

                params.put("login", login_name);
                params.put("password", password_name);

                client.post(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        log.d("success","Response available");

                        try {
                            String messager = response.getString("token");
                            String message = gson.toJson(response);
                            //log.i("response",message);
                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                            //get JSONObject from JSON file
                            JSONObject obj =new JSONObject(message);

                            // fetch JSONObject named nameValuePairs
                            JSONObject nameValuePairs = obj.getJSONObject("nameValuePairs");

                            //get token
                            token = nameValuePairs.getString("token");

                            //set token
                            token_name.setText("Token: "+token);

                            Intent i = new Intent(LoginActivity.this, MainActivity.class);

                            // pass token string through Intent
                            i.putExtra("TOKEN_STRING", token);

                            startActivity(i);



                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        log.e("error", String.valueOf(t));
                    }
                });
            }
        });
    }
}