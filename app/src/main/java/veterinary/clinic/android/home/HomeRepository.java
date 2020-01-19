package veterinary.clinic.android.home;

import android.util.Log;


import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import veterinary.clinic.android.api.OkHttpProvider;
import veterinary.clinic.android.model.PetModel;
import veterinary.clinic.android.model.SettingsModel;

public class HomeRepository {

    private OkHttpProvider okHttpProvider;


    public HomeRepository(OkHttpProvider okHttpProvider) {
        this.okHttpProvider = okHttpProvider;
    }

    void getSettingsFromAPI(final OnSettingsListener listener) {
        Request request = okHttpProvider.buildRequest(Constant.allEnabled);
        okHttpProvider.getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFailed();
            }

            @Override
            public void onResponse(Response response) {
                SettingsModel settingsModel = parseAPIResponseToSettingModel(response);
                if (settingsModel != null) {
                    listener.onSettings(settingsModel);
                } else {
                    listener.onFailed();
                }
            }
        });
    }


    private SettingsModel parseAPIResponseToSettingModel(Response response) {
        try {

            String responseString = response.body().string();
            JSONObject jsonObject = new JSONObject(responseString);
            JSONObject settingJsonObj = (JSONObject) jsonObject.opt("settings");


            SettingsModel settingsModel = new SettingsModel();
            if (settingJsonObj != null) {
                settingsModel.setCallEnabled(settingJsonObj.optBoolean("isCallEnabled"));
                settingsModel.setChatEnabled(settingJsonObj.optBoolean("isChatEnabled"));
                settingsModel.setWorkHours(settingJsonObj.optString("workHours"));
                return settingsModel;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    void getPetsFromAPI(final OnPetsListener listener) {
        Request request = okHttpProvider.buildRequest("https://api.jsonbin.io/b/5e216cc05df640720836e76b");
        okHttpProvider.getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onFailed();
            }

            @Override
            public void onResponse(Response response) {
                Log.e("onResponse", response.toString());
                List<PetModel> petModels = parseAPIResponseToPetsList(response);
                if (petModels != null && !petModels.isEmpty()) {
                    listener.onPets(petModels);
                } else {
                    listener.onFailed();
                }
            }
        });
    }


    private List<PetModel> parseAPIResponseToPetsList(Response response) {
        try {
            String responseString = response.body().string();
            JSONArray petsJsonArray = new JSONObject(responseString).optJSONArray("pets");
            ArrayList<PetModel> petModels = new ArrayList<>();
            if (petsJsonArray != null) {
                for (int i = 0; i < petsJsonArray.length(); i++) {
                    JSONObject petJObj = (JSONObject) petsJsonArray.get(i);

                    PetModel petModel = new PetModel();
                    petModel.setContentUrl(petJObj.optString("content_url"));
                    petModel.setImageUrl(petJObj.optString("image_url"));
                    petModel.setTitle(petJObj.optString("title"));
                    petModel.setDateAdded(petJObj.optString("date_added"));
                    petModels.add(petModel);
                }
            }
            return petModels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class Constant {

        private static String allEnabled = "https://api.jsonbin.io/b/5e216ecf5df640720836e806";
        private static String allDisabled = "https://api.jsonbin.io/b/5e2195118d761771cc926fd1";
        private static String chatOnly = "https://api.jsonbin.io/b/5e2195045df640720836f4a1";
        private static String callOnly = "https://api.jsonbin.io/b/5e2194f78d761771cc926fbf";

    }

    public interface OnSettingsListener {
        void onSettings(SettingsModel settingsModel);
        void onFailed();
    }

    public interface OnPetsListener {
        void onPets(List<PetModel> petsModel);
        void onFailed();
    }

}
