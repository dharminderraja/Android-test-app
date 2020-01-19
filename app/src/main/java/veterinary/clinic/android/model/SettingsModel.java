package veterinary.clinic.android.model;

public class SettingsModel {

    private boolean isChatEnabled;
    private boolean isCallEnabled;
    private String workHours;


    public boolean isChatEnabled() {
        return isChatEnabled;
    }

    public void setChatEnabled(boolean chatEnabled) {
        isChatEnabled = chatEnabled;
    }

    public boolean isCallEnabled() {
        return isCallEnabled;
    }


    public void setCallEnabled(boolean callEnabled) {
        isCallEnabled = callEnabled;
    }

    public String getWorkHours() {
        return workHours;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }
}
