package alex.batterysafe;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class MyActivity extends ActionBarActivity {

    Button btnCompute;
    Spinner spinBattery;
    EditText etOhm;
    TextView tvDisplay;
    String[] arBatteries = {"Sony Vtc 4 18650 2500mah 30amp","LG LGDBHE21865 2500mah 20amp", "Awr 2200 mah 10amp"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        btnCompute = (Button) findViewById(R.id.btnCompute);
        spinBattery = (Spinner) findViewById(R.id.spinBattery);
        etOhm = (EditText) findViewById(R.id.etOhm);
        tvDisplay = (TextView) findViewById(R.id.tvDisplay);
        etOhm.setText("1.5");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arBatteries);
        spinBattery.setAdapter(adapter);

        btnCompute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int dischargeRate = 0;
                double dOhm = 1.5;
                long id = spinBattery.getSelectedItemId();

                if (id == 0 ) dischargeRate = 30;
                if (id == 1 ) dischargeRate = 20;
                if (id == 2 ) dischargeRate = 10;

                if(etOhm.getText() == null){
                   dOhm = 0;
                }else{
                    dOhm = Double.parseDouble(etOhm.getText().toString());

                }
                if(4.2/dOhm < dischargeRate ){
                    tvDisplay.setText("This setup is safe");

                }else{
                    tvDisplay.setText("This setup is UNsafe");
                }
            }
        });

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


}
