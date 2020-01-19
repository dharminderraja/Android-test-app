package veterinary.clinic.android.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.arch.core.util.Function;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import veterinary.clinic.android.R;
import veterinary.clinic.android.databinding.FragmentHomeBinding;
import veterinary.clinic.android.imageloading.ImageLoader;
import veterinary.clinic.android.model.PetModel;
import veterinary.clinic.android.pets.PetsAdapter;

public class HomeFragment extends Fragment {

    private static HomeFragment _homeFragment;

    private FragmentHomeBinding binding;
    private PetsAdapter petsAdapter;
    private HomeViewModel homeViewModel;

    public static HomeFragment newInstance() {
        if (_homeFragment == null) {
            _homeFragment = new HomeFragment();
        }
        return _homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate
                (inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        setUpAdapter(view);
        setRetainInstance(true);
        homeViewModel = new HomeViewModel();
        homeViewModel.setOnPetsUpdatedLister(new HomeViewModel.OnPetsUpdatedLister() {
            @Override
            public void onPetsUpdated(final List<PetModel> pets) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            petsAdapter.setPets(pets);
                        }
                    });
                }
            }
        });
        binding.setModel(homeViewModel);

        homeViewModel.fetchSettings();
        homeViewModel.fetchPets();
    }

    private void initUI(View view) {
        view.findViewById(R.id.chatBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message;
                if (homeViewModel.checkIfValidTime()) {
                    message = getString(R.string.within_work_hour_msg);
                } else {
                    message = getString(R.string.outside_work_hour_msg);
                }
                buildAlertDialog(message);
            }
        });

        view.findViewById(R.id.callBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message;
                if (homeViewModel.checkIfValidTime()) {
                    message = getString(R.string.within_work_hour_msg);
                } else {
                    message = getString(R.string.outside_work_hour_msg);
                }
                buildAlertDialog(message);
            }
        });
    }

    private void buildAlertDialog(String message) {
        if (getContext() != null) {
            AlertDialog.Builder dialogBuilder =
                    new AlertDialog.Builder(getContext(), R.style.Theme_AppCompat_Light_Dialog);

            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_dialog, null);
            TextView messageTV = view.findViewById(R.id.messageText);


            messageTV.setText(message);
            dialogBuilder.setView(view);
            final AlertDialog dialog = dialogBuilder.show();

            view.findViewById(R.id.okBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

    }

    private void setUpAdapter(View view) {
        petsAdapter = new PetsAdapter();
        petsAdapter.setImageLoader(new ImageLoader(getContext()));
        RecyclerView petsRecyclerView = view.findViewById(R.id.petsRecyclerView);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(view.getContext().getResources().getDrawable(R.drawable.divider_drawable));
        petsRecyclerView.addItemDecoration(dividerItemDecoration);
        petsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        petsRecyclerView.setHasFixedSize(false);
        petsRecyclerView.setAdapter(petsAdapter);
    }
}
