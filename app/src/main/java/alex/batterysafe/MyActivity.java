package alex.batterysafe;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class MyActivity extends ActionBarActivity {

    Spinner spinBattery;
    EditText etOhm;
    TextView tvDisplay;
    String[] arBatteries = {"Sony Vtc 4 18650 2500mah 30amp","LG LGDBHE21865 2500mah 20amp", "Awr 2200 mah 10amp"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        spinBattery = (Spinner) findViewById(R.id.spinBattery);
        etOhm = (EditText) findViewById(R.id.etOhm);
        tvDisplay = (TextView) findViewById(R.id.tvDisplay);
        BatteryListener listener = new BatteryListener();
        etOhm.setText("1.5");
        etOhm.setOnClickListener(listener);
        spinBattery.setOnItemSelectedListener(listener);

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

            double dAmps = 0;

            int dischargeRate = 0;
            double dOhm = Double.parseDouble(etOhm.getText().toString());
            long id = spinBattery.getSelectedItemId();

            if (id == 0 ) dischargeRate = 30;
            if (id == 1 ) dischargeRate = 20;
            if (id == 2 ) dischargeRate = 10;
            dAmps = 4.2/dOhm;


            if(dOhm <= 0){
                dOhm = 1.5;
                etOhm.setText("1.5");
                CharSequence text = "Resistance cannot be Zero";
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            }

            if(dAmps < dischargeRate ){
                tvDisplay.setText("This setup is safe.\nPulls " + String.valueOf(dAmps).substring(0,4) + " Amps at 4.2 volts");
                tvDisplay.setBackgroundColor(Color.GREEN);


            }else{
                tvDisplay.setText("This setup is UNsafe.\nPulls "  .valueOf(dAmps).substring(0,4) + " Amps at 4.2 volts");
                tvDisplay.setBackgroundColor(Color.RED);
            }
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
