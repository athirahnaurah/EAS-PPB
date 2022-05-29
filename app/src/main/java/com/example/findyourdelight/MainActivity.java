package com.example.findyourdelight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findyourdelight.activities.CreateMenuActivity;
import com.example.findyourdelight.activities.LoginActivity;
import com.example.findyourdelight.adapter.MenuAdapter;
import com.example.findyourdelight.api.RestClient;
import com.example.findyourdelight.models.MenuResponse;
import com.example.findyourdelight.models.ResultItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<ResultItem> menuItem;
    private MenuAdapter menuAdapter;
    private RecyclerView rvListMenu;
    private FloatingActionButton fabCreateMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvListMenu = findViewById(R.id.rvListMenu);
        fabCreateMenu = findViewById(R.id.fabAddMenu);
        fabCreateMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateMenuActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
        getMenu();

    }

    private void getMenu(){
        RestClient.getService().getAllMenu().enqueue(new Callback<MenuResponse>() {
            @Override
            public void onResponse(Call<MenuResponse> call, Response<MenuResponse> response) {
                String responseCode = "Response Code: " + response.code();
                if (response.isSuccessful()){
                    Toast.makeText(MainActivity.this, responseCode, Toast.LENGTH_SHORT).show();

                    menuItem = response.body().getResult();

                    menuAdapter = new MenuAdapter(menuItem, MainActivity.this);
                    rvListMenu.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rvListMenu.setAdapter(menuAdapter);
                }
            }

            @Override
            public void onFailure(Call<MenuResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}