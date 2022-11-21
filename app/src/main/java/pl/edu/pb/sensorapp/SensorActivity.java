package pl.edu.pb.sensorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SensorActivity extends AppCompatActivity {

    private SensorManager securityManager;
    private List<Sensor> sensorList;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_activity);
        securityManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorList = securityManager.getSensorList(Sensor.TYPE_ALL);
        ((RecyclerView) findViewById(R.id.sensor_recycler_view)).setAdapter(new SensorAdapter());
        textView = findViewById(R.id.text_menu);
        for(Sensor sensor:sensorList){
            Log.d("SensorActivity",sensor.getVendor() + sensor.getMaximumRange());
        }
        findViewById(R.id.location_sensor_button).setOnClickListener(v->{
            Intent intent = new Intent(SensorActivity.this,LocationActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        textView.setText(getString(R.string.sensor_count,sensorList.size()));
        return super.onOptionsItemSelected(item);
    }

    private class SensorAdapter extends RecyclerView.Adapter<SensorHolder> {

        @NonNull
        @Override
        public SensorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SensorHolder(getLayoutInflater().inflate(R.layout.sensor_list_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull SensorHolder holder, int position) {
            holder.textView.setText(sensorList.get(position).getName());
            if(sensorList.get(position).getType() == Sensor.TYPE_LIGHT ||
                    sensorList.get(position).getType() == Sensor.TYPE_AMBIENT_TEMPERATURE)
            {
                holder.textView.setBackgroundColor(Color.YELLOW);
                holder.itemView.setOnClickListener(v->
                {
                    Intent intent = new Intent(SensorActivity.this,SensorDetailsActivity.class);
                    intent.putExtra("Type",sensorList.get(position).getType());
                    startActivity(intent);
                });
            }else {
                holder.textView.setBackgroundColor(Color.WHITE);

            }
        }

        @Override
        public int getItemCount() {
            return sensorList.size();
        }
    }

    private class SensorHolder extends RecyclerView.ViewHolder {
        private TextView textView;


        public SensorHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_image_view);
        }
    }

}