package veterinary.clinic.android;

import android.view.View;
import android.widget.Button;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

// @RunWith is required only if you use a mix of JUnit3 and JUnit4.
@RunWith(AndroidJUnit4.class)
@SmallTest
public class HomeActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void ensureButtons() throws Exception {
        ensureChatButton();

        ensureCallButton();

        performScrollAndClickOnList();

        performBack();
    }

    private void ensureChatButton() throws InterruptedException {
        Thread.sleep(1000);

        MainActivity activity = rule.getActivity();

        View viewById = activity.findViewById(R.id.chatBtn);

        assertThat(viewById, notNullValue());
        assertThat(viewById, instanceOf(Button.class));

        onView(withId(R.id.chatBtn)).perform(click());

        Thread.sleep(1000);

        pressBack();
    }

    private void ensureCallButton() throws Exception {
        MainActivity activity = rule.getActivity();

        View viewById = activity.findViewById(R.id.callBtn);

        assertThat(viewById, notNullValue());
        assertThat(viewById, instanceOf(Button.class));

        onView(withId(R.id.callBtn)).perform(click());

        Thread.sleep(1000);

        pressBack();
    }

    private void performScrollAndClickOnList() throws InterruptedException {
        onView(withId(R.id.petsRecyclerView))
                .perform(swipeUp());

        Thread.sleep(1000);

        onView(withId(R.id.petsRecyclerView))
                .perform(swipeDown());

        Thread.sleep(1000);

        onView(withId(R.id.petsRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        Thread.sleep(5000);
    }

    private void performBack() {
        pressBack();
    }
}
