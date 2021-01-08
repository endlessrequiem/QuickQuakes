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
        //Only need 9 or 10
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

        Earthquake indonesia = new Earthquake(9.1,
                getString(R.string.indonesia),
                1104080333000L,
                "https://en.wikipedia.org/wiki/2004_Indian_Ocean_earthquake_and_tsunami");

        historicAdapter.add(indonesia);

        Earthquake tohoku = new Earthquake(8.8,
                getString(R.string.tohoku2011),
                1299883560000L,
                "https://en.wikipedia.org/wiki/2011_T%C5%8Dhoku_earthquake_and_tsunami");

        historicAdapter.add(tohoku);

        Earthquake ecuadorColombia = new Earthquake(8.8,
                getString(R.string.ecuadorColumbia),
                -2017029600000L,
                "https://en.wikipedia.org/wiki/1906_Ecuador%E2%80%93Colombia_earthquake");

        historicAdapter.add(ecuadorColombia);

        Earthquake kurilIsland = new Earthquake(8.5,
                getString(R.string.kurilIsland),
                -196220521000L,
                "https://en.wikipedia.org/wiki/1963_Kuril_Islands_earthquake");

        historicAdapter.add(kurilIsland);

        Earthquake sichuan = new Earthquake(8.0,
                getString(R.string.sichuan),
                1228516081000L,
                "https://en.wikipedia.org/wiki/2008_Sichuan_earthquake");

        historicAdapter.add(sichuan);

        Earthquake sf1906 = new Earthquake(7.9,
                getString(R.string.sanFrancisco),
                -2010394080000L,
                "https://en.wikipedia.org/wiki/1906_San_Francisco_earthquake");

        historicAdapter.add(sf1906);

        Earthquake sf1989 = new Earthquake(6.9,
                getString(R.string.sanFrancisco),
                624672255000L,
                "https://en.wikipedia.org/wiki/1989_Loma_Prieta_earthquake");

        historicAdapter.add(sf1989);

        Earthquake hanshin = new Earthquake(6.9,
                getString(R.string.hanshin),
                790264013000L,
                "https://en.wikipedia.org/wiki/Great_Hanshin_earthquake");

        historicAdapter.add(hanshin);
    }

    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        this.overridePendingTransition(0, 0);
        return true;
    }
}
