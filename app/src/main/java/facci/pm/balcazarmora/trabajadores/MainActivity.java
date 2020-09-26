package facci.pm.balcazarmora.trabajadores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText cedula, fecha, hora, minutos, segudos;
    private Button post, get;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cedula = (EditText) findViewById(R.id.cedula);
        fecha = (EditText) findViewById(R.id.fecha);
        hora = (EditText) findViewById(R.id.hora);
        minutos = (EditText) findViewById(R.id.minutos);
        segudos = (EditText) findViewById(R.id.segundos);
        progressBar = (ProgressBar) findViewById(R.id.progress_circular);

        post = (Button) findViewById(R.id.post);
        get = (Button) findViewById(R.id.get);

        post.setOnClickListener(this);
        get.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post:
                if (cedula.getText().toString().trim().isEmpty() ||
                        fecha.getText().toString().trim().isEmpty() ||
                        hora.getText().toString().trim().isEmpty() ||
                        minutos.getText().toString().trim().isEmpty() ||
                        segudos.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, getString(R.string.campos), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getString(R.string.enviado), Toast.LENGTH_SHORT).show();
                    Post();
                }
                break;

            case R.id.get:
                startActivity(new Intent(this, GetActivity.class));
                break;
        }
    }

    private void Post() {
        progressBar.setVisibility(View.VISIBLE);
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("cedula", cedula.getText().toString().trim());
        postParam.put("fecha", fecha.getText().toString().trim());
        postParam.put("hora", hora.getText().toString().trim());
        postParam.put("minuto", minutos.getText().toString().trim());
        postParam.put("segundo", segudos.getText().toString().trim());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constant.URLCHECKIN, new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("RESPONSe", response.toString());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "EXITO", Toast.LENGTH_SHORT).show();
                cedula.setText("");
                fecha.setText("");
                hora.setText("");
                minutos.setText("");
                segudos.setText("");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.getMessage());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjReq);
    }
}