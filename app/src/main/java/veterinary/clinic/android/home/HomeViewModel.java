package veterinary.clinic.android.home;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import veterinary.clinic.android.api.OkHttpProvider;
import veterinary.clinic.android.model.PetModel;
import veterinary.clinic.android.model.SettingsModel;

public class HomeViewModel extends BaseObservable {

    private SettingsModel settingsModel;
    private HomeRepository homeRepository;
    private OnPetsUpdatedLister onPetsUpdatedLister;

    @Bindable
    public boolean isFetchingConfig = true;

    @Bindable
    public boolean isFetchingPets = true;

    @Bindable
    public boolean showPetError = false;

    @Bindable
    public boolean showConfigError = false;


    public HomeViewModel() {
        homeRepository = new HomeRepository(new OkHttpProvider());
    }

    void setOnPetsUpdatedLister(OnPetsUpdatedLister onPetsUpdatedLister) {
        this.onPetsUpdatedLister = onPetsUpdatedLister;
    }


    public void setSettingsModel(SettingsModel settingsModel) {
        isFetchingConfig = false;
        this.settingsModel = settingsModel;
        notifyPropertyChanged(BR.callEnabled);
        notifyPropertyChanged(BR.chatEnabled);
        notifyPropertyChanged(BR.workHoursString);
        notifyPropertyChanged(BR.isFetchingConfig);
    }

    public void setPets(List<PetModel> petModels) {
        isFetchingPets = false;
        notifyPropertyChanged(BR.isFetchingPets);
        onPetsUpdatedLister.onPetsUpdated(petModels);
    }

    @Bindable
    public boolean isChatEnabled() {
        if (settingsModel != null) {
            return settingsModel.isChatEnabled();
        } else {
            return false;
        }
    }

    @Bindable
    public boolean isCallEnabled() {
        if (settingsModel != null) {
            return settingsModel.isCallEnabled();
        } else {
            return false;
        }
    }

    @Bindable
    public String getWorkHoursString() {
        if (settingsModel != null) {
            return "Office Hours: " + settingsModel.getWorkHours();
        } else {
            return "";
        }
    }

    public void fetchSettings() {
        homeRepository.getSettingsFromAPI(new HomeRepository.OnSettingsListener() {
            @Override
            public void onSettings(SettingsModel settingsModel) {
                setSettingsModel(settingsModel);
            }

            @Override
            public void onFailed() {
                setConfigError();
            }
        });
    }

    public void fetchPets() {
        homeRepository.getPetsFromAPI(new HomeRepository.OnPetsListener() {
            @Override
            public void onPets(List<PetModel> petsModel) {
                setPets(petsModel);
            }

            @Override
            public void onFailed() {
                setPetsError();
            }
        });
    }

    boolean checkIfValidTime() {
        try {

            int hourOfTheDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

            String workHoursString = settingsModel.getWorkHours();
            String[] timeSplits = workHoursString.split(" ");

            boolean workDay = getWeekDaysOff(timeSplits[0].split("-"));

            String[] startingHourMin = timeSplits[1].split(":");
            int startingHour = Integer.parseInt(startingHourMin[0]);

            String[] endingHourMin = timeSplits[3].split(":");
            int endingHour = Integer.parseInt(endingHourMin[0]);


            return hourOfTheDay > startingHour && hourOfTheDay < endingHour && workDay;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    boolean getWeekDaysOff(String[] workDays){
        HashMap<String, Integer> allWeekDays = new HashMap<String, Integer>();
        allWeekDays.put("Su", Calendar.SUNDAY);
        allWeekDays.put("M", Calendar.MONDAY);
        allWeekDays.put("T", Calendar.TUESDAY);
        allWeekDays.put("W", Calendar.WEDNESDAY);
        allWeekDays.put("Th", Calendar.THURSDAY);
        allWeekDays.put("F", Calendar.FRIDAY);
        allWeekDays.put("S", Calendar.SATURDAY);

        Calendar myDate = Calendar.getInstance();
        int dow = myDate.get (Calendar.DAY_OF_WEEK);
        return ((dow >= allWeekDays.get(workDays[0])) && (dow <= allWeekDays.get(workDays[1])));
    }

    void setPetsError() {
        showPetError = true;
        isFetchingPets = false;
        notifyPropertyChanged(BR.showPetError);
        notifyPropertyChanged(BR.isFetchingPets);
    }

    void setConfigError() {
        isFetchingConfig = false;
        showConfigError = true;
        notifyPropertyChanged(BR.showConfigError);
        notifyPropertyChanged(BR.isFetchingConfig);
    }

    public interface OnPetsUpdatedLister {
        void onPetsUpdated(List<PetModel> pets);
    }
}
