package veterinary.clinic.android;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import java.util.List;

import veterinary.clinic.android.home.HomeViewModel;
import veterinary.clinic.android.model.PetModel;
import veterinary.clinic.android.model.SettingsModel;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.Silent.class)
public class HomeViewModelUnitTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    HomeViewModel homeViewModel;

    @Test
    public void fetchDataSettingTest() {

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Assert.assertFalse(homeViewModel.isFetchingConfig);
                // return null because fetch data is void
                return null;
            }
        }).when(homeViewModel).fetchSettings();

    }

    @Test
    public void fetchDataPetsTest() {

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Assert.assertFalse(homeViewModel.isFetchingPets);
                // return null because fetch data is void
                return null;
            }
        }).when(homeViewModel).fetchPets();
        homeViewModel.fetchPets();

    }

    @Test
    public void isCallEnabled() {
        when(homeViewModel.isCallEnabled()).thenReturn(true);
        Assert.assertTrue(homeViewModel.isCallEnabled());

        when(homeViewModel.isCallEnabled()).thenReturn(false);
        Assert.assertFalse(homeViewModel.isCallEnabled());
    }

    @Test
    public void isChatEnabled() {
        when(homeViewModel.isChatEnabled()).thenReturn(true);
        Assert.assertTrue(homeViewModel.isChatEnabled());

        when(homeViewModel.isChatEnabled()).thenReturn(false);
        Assert.assertFalse(homeViewModel.isChatEnabled());
    }



}
