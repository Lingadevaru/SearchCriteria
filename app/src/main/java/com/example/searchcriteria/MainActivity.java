package com.example.searchcriteria;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    CheckBox entity, ipa, hp, lob, month, service, source;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        this.token = intent.getStringExtra("TOKEN_STRING");

        final TextView textView = findViewById(R.id.textView);

        textView.setText(token);

        //Spinner YEAR From
        Spinner YearSpinner = (Spinner) findViewById(R.id.yearFrom_spinner);
        YearSpinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> Yearadapter = ArrayAdapter.createFromResource(this,
                R.array.year_array, R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        Yearadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        YearSpinner.setAdapter(Yearadapter);

        //SPINNER MONTH From
        Spinner MonthSpinner = (Spinner) findViewById(R.id.monthFrom_spinner);
        MonthSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> Monthadapter = ArrayAdapter.createFromResource(this,
                R.array.month_array, R.layout.simple_spinner_item);
        Monthadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MonthSpinner.setAdapter(Monthadapter);

        //SPINNER YEAR To
        Spinner YearToSpinner = (Spinner) findViewById(R.id.yearTo_spinner);
        YearToSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> YearToadapter = ArrayAdapter.createFromResource(this,
                R.array.yearTo_array, R.layout.simple_spinner_item);
        YearToadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        YearToSpinner.setAdapter(YearToadapter);

        //SPINNER MONTH To
        Spinner MonthToSpinner = (Spinner) findViewById(R.id.monthTo_spinner);
        MonthToSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> MonthToadapter = ArrayAdapter.createFromResource(this,
                R.array.monthTo_array, R.layout.simple_spinner_item);
        MonthToadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MonthToSpinner.setAdapter(MonthToadapter);

        entity = findViewById(R.id.checkbox_Entity);
        ipa = findViewById(R.id.checkbox_IPA);
        hp = findViewById(R.id.checkbox_HP);
        lob = findViewById(R.id.checkbox_LOB);
        month = findViewById(R.id.checkbox_month);
        service = findViewById(R.id.checkbox_SERVICE);
        source = findViewById(R.id.checkbox_source);

        Button search_button = findViewById(R.id.btn_search);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuffer grp = new StringBuffer();

                if(entity.isChecked())
                    grp.append("company");

                if(hp.isChecked())
                    grp.append(","+"hp");

                if(ipa.isChecked())
                    grp.append(","+"ipa");

                if(lob.isChecked())
                    grp.append(","+"lob");

                if(month.isChecked())
                    grp.append(","+"ym");

                if(month.isChecked())
                    grp.append(","+"service");

                if(month.isChecked())
                    grp.append(","+"contSrc");

                String groups = grp.toString();
                //textView.setText(groups);

                String url = "https://www.greenwaveps.com/bi.api/api/v1/Reports/MMonth?yrStart=2019&mnthStart=1&yrEnd=2019&mnthEnd=11&grpCols="+groups;

                //textView.setText(url);

                AsyncHttpClient client = new AsyncHttpClient();

                client.addHeader("Authorization",token);

                client.get(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        Log.d("Response", "Success");
                        String str = new String(responseBody);
                        textView.setText(str);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                       Log.e("ERROR", error.toString());
                        //textView.setText("Error while calling API");

                    }
                });
            }
        });

    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

//        String item = parent.getItemAtPosition(pos).toString();
//        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();


    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        // TODO Auto-generated method stub
    }


}
