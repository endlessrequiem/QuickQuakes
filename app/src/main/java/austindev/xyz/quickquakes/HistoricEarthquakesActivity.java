package austindev.xyz.quickquakes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class HistoricEarthquakesActivity extends AppCompatActivity {

    EarthquakeAdapter historicAdapter;
    SharedPreferences currLocation;
    SharedPreferences currMagnitude;
    SharedPreferences currTime;
    SharedPreferences currDate;
    SharedPreferences currUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historic_earthquake_activity);

        setTitle("Historical Quakes");
        ListView historicEarthquakeListView = findViewById(R.id.list);

        TextView mEmptyStateTextView = findViewById(R.id.empty_view);
        historicEarthquakeListView.setEmptyView(mEmptyStateTextView);

        historicAdapter = new EarthquakeAdapter(this, new ArrayList<>());
        historicEarthquakeListView.setAdapter(historicAdapter);

        listPopulate();



        currLocation = this.getSharedPreferences(
                getString(R.string.location_key), Context.MODE_PRIVATE);
        currMagnitude = this.getSharedPreferences(
                getString(R.string.magnitude_key),Context.MODE_PRIVATE);
        currTime = this.getSharedPreferences(
                getString(R.string.time_key),Context.MODE_PRIVATE);
        currDate = this.getSharedPreferences(
                getString(R.string.date_key),Context.MODE_PRIVATE);
        currUrl = this.getSharedPreferences(
                getString(R.string.url_key),Context.MODE_PRIVATE);

        historicEarthquakeListView.setOnItemClickListener((adapterView, view, position, l) -> {
            //get info from current item
            historicEarthquakeListItem(position);
        });

    }



    public void historicEarthquakeListItem(int position) {
        Earthquake currentEarthquake = historicAdapter.getItem(position);

        String earthquakeLocation = currentEarthquake.getLocation();

        // Convert the String URL into a URI object (to pass into the Intent constructor)
        String earthquakeUri = currentEarthquake.getUrl();

        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        String earthquakeMagnitude = magnitudeFormat.format(currentEarthquake.getMagnitude());

        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy", Locale.getDefault());
        String earthquakeDate = dateFormat.format(currentEarthquake.getTimeInMilliseconds());

        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        String earthquakeTime = timeFormat.format(currentEarthquake.getTimeInMilliseconds());

        SharedPreferences.Editor currLocationEditor = currLocation.edit();
        currLocationEditor.putString(getString(R.string.location_key), String.valueOf(earthquakeLocation));
        currLocationEditor.apply();

        SharedPreferences.Editor currMagnitudeEditor = currMagnitude.edit();
        currMagnitudeEditor.putString(getString(R.string.magnitude_key), earthquakeMagnitude);
        currMagnitudeEditor.apply();

        SharedPreferences.Editor currTimeEditor = currTime.edit();
        currTimeEditor.putString(getString(R.string.time_key), String.valueOf(earthquakeTime));
        currTimeEditor.apply();

        SharedPreferences.Editor currDateEditor = currDate.edit();
        currDateEditor.putString(getString(R.string.date_key), String.valueOf(earthquakeDate));
        currDateEditor.apply();

        SharedPreferences.Editor currUrlEditor = currUrl.edit();
        currUrlEditor.putString(getString(R.string.url_key), String.valueOf(earthquakeUri));
        currUrlEditor.apply();

        Intent detail = new Intent(HistoricEarthquakesActivity.this, EarthquakeDetailActivity.class);

        // Send the intent to launch a new activity
        startActivity(detail);
    }

    private void listPopulate() {
        Earthquake valdivia = new Earthquake(9.6,
                getString(R.string.valdivia),
                -259980526000L,
                "https://en.wikipedia.org/wiki/1960_Valdivia_earthquake");

        historicAdapter.add(valdivia);

        Earthquake alaska = new Earthquake(9.2,
                getString(R.string.pwsAlaska),
                -120867824000L,
                "https://en.wikipedia.org/wiki/1964_Alaska_earthquake");

        historicAdapter.add(alaska);
    }

    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        this.overridePendingTransition(0, 0);
        return true;
    }
}
