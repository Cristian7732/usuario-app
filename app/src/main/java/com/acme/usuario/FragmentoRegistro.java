package com.acme.usuario;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentoRegistro extends AppCompatActivity {

    Button botonRegistroGuardar;
    EditText nombreEditText, emailEditText;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.fragment_registro);

        nombreEditText = (EditText) findViewById(R.id.txtNombre);
        emailEditText = (EditText) findViewById(R.id.txtCorreo);
        botonRegistroGuardar = (Button) findViewById(R.id.btnGuardar);

        botonRegistroGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nombreEditText.getText().toString();
                String email = emailEditText.getText().toString();

                //String url = "http://10.0.2.2:8098/api/saveUser"; // url local
                String url = "https://my-usuario-acme.onrender.com/api/saveUser"; // Reemplazar por la url desplegada en Render

                RequestQueue queue = Volley.newRequestQueue(FragmentoRegistro.this);

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(FragmentoRegistro.this, "Datos guardados exitosamente", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FragmentoRegistro.this, "Error al guardar los datos " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("name", name);
                        params.put("email", email);
                        return params;
                    }

                    @Override
                    public byte[] getBody() {
                        return new JSONObject(getParams()).toString().getBytes();
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }
                };
                queue.add(request);
            }
        });

    }
}
