package austindev.xyz.quickquakes;

import android.widget.EditText;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


public class EarthquakeActivityTest {

    @Rule
    public ActivityScenarioRule<EarthquakeActivity> activityRule =
            new ActivityScenarioRule<>(EarthquakeActivity.class);

    @Test
    public void RefreshButtonTest() {
        onView(withId(R.id.action_refresh)).perform(click());
    }

    @Test
    public void minMagSettingsTest() {
        onView(withId(R.id.action_settings)).perform(click());
        onView(withText(R.string.settings_min_magnitude_label)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("3"));
        onView(withText("OK")).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
    }

    @Test
    public void mostRecentOrderSettingsTest() {
        onView(withId(R.id.action_settings)).perform(click());
        onView(withText(R.string.settings_order_by_label)).perform(click());
        onView(withText("Most Recent")).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
    }

    @Test
    public void minMagnitudeOrderSettingsTest() {
        onView(withId(R.id.action_settings)).perform(click());
        onView(withText(R.string.settings_order_by_label)).perform(click());
        onView(withText("Magnitude")).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
    }

    @Test
    public void listItemTest(){
        onView(withId(R.id.list)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
    }
}