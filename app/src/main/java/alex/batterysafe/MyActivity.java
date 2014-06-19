package alex.batterysafe;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MyActivity extends ActionBarActivity {

    Spinner spinBattery;
    View vMain;
    EditText etOhm;
    TextView tvDisplay, tvAmps, tvVolts;
    String[] arBatteries;
            //= {"Sony Vtc 4 18650 2500mah 30amp","LG LGDBHE21865 2500mah 20amp", "Awr 2200 mah 10amp"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        vMain =  findViewById(R.id.vMain);
        spinBattery = (Spinner) findViewById(R.id.spinBattery);
        etOhm = (EditText) findViewById(R.id.etOhm);
        tvDisplay = (TextView) findViewById(R.id.tvDisplay);
        tvAmps = (TextView) findViewById(R.id.tvAmps);
        tvVolts = (TextView) findViewById(R.id.tvVolts);
        BatteryListener listener = new BatteryListener();
        etOhm.setText("1.5");
        etOhm.setOnClickListener(listener);
        spinBattery.setOnItemSelectedListener(listener);

        Resources res = getResources();
        arBatteries = res.getStringArray(R.array.batteries);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arBatteries);
        spinBattery.setAdapter(adapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class BatteryListener implements View.OnClickListener, AdapterView.OnItemSelectedListener {

        @Override
        public void onClick(View view) {

            String sAmps;
            double dAmps = 0;
            long id = spinBattery.getSelectedItemId();
            String battery = arBatteries[(int)id];

            //Obtain amp rating from description
            //TODO Need to remake this not taking decimal places
            double dischargeRate = Double.parseDouble(battery.substring(battery.length() - 5, battery.length() - 3));
            double dOhm = Double.parseDouble(etOhm.getText().toString());


            dAmps = 4.2/dOhm;
            sAmps = String.valueOf(dAmps);

            if(sAmps.length()>=5){
                sAmps = sAmps.substring(0,4);

            }


            if(dOhm <= 0){
                dOhm = 1.5;
                etOhm.setText("1.5");
                CharSequence text = "Resistance cannot be Zero";
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            }

            if(dAmps <=  dischargeRate ){
                tvDisplay.setText("This setup is safe.");
                vMain.setBackgroundColor(Color.parseColor("#99FF99"));

            }else{
                tvDisplay.setText("This setup is UNsafe.");
                vMain.setBackgroundColor(Color.parseColor("#FF9999"));
            }
            tvAmps.setText(sAmps + " Amps");
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            this.onClick(view);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

}
