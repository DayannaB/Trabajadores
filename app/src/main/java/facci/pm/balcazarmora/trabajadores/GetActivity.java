package facci.pm.balcazarmora.trabajadores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetActivity extends AppCompatActivity {

    private TextView tituloNombre, ciudad;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private ArrayList<Horario> horarioArrayList;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);
        tituloNombre = (TextView) findViewById(R.id.tituloNombres);
        ciudad = (TextView) findViewById(R.id.ciudad);
        recyclerView = (RecyclerView)findViewById(R.id.RecyclerHorarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        horarioArrayList = new ArrayList<>();
        adapter = new Adapter(horarioArrayList);
        progressBar = (ProgressBar)findViewById(R.id.progress_circular);
        getDatosPersonal();
        getHorarios();
    }

    private void getHorarios() {
        JsonArrayRequest jsonObjReq = new JsonArrayRequest
                (Request.Method.GET, Constant.URLCHECKIN, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Horario horario = new Horario(
                                        jsonObject.getString("fecha"),
                                        jsonObject.getString("hora")+":" + jsonObject.getString("minuto")+":"+jsonObject.getString("segundo")
                                );
                                horarioArrayList.add(horario);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        recyclerView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", error.getMessage());
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(GetActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjReq);
    }

    private void getDatosPersonal() {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest
                (Request.Method.GET, Constant.URLPERSONAL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            tituloNombre.setText(response.getString("titulo") + " " + response.getString("nombres") + " " + response.getString("apellidos"));
                            ciudad.setText(response.getString("ciudad"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", error.getMessage());
                        Toast.makeText(GetActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjReq);
    }
}