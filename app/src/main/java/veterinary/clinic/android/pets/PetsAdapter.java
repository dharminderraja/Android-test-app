package veterinary.clinic.android.pets;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import veterinary.clinic.android.R;
import veterinary.clinic.android.WebViewActivity;
import veterinary.clinic.android.imageloading.ImageLoader;
import veterinary.clinic.android.model.PetModel;

public class PetsAdapter extends RecyclerView.Adapter<PetsAdapter.PetItemViewHolder> {

    private List<PetModel> pets = null;
    private ImageLoader imageLoader = null;

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public void setPets(List<PetModel> petModels) {
        this.pets = petModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PetItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_pet, parent, false);
        return new PetItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PetItemViewHolder holder, int position) {
        holder.bind(pets.get(position));
    }

    @Override
    public int getItemCount() {
        return pets == null ? 0 : pets.size();
    }


    class PetItemViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTV;
        private ImageView petImageView;

        PetItemViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.petTitleTV);
            petImageView = itemView.findViewById(R.id.petImageView);
        }

        void bind(final PetModel petModel) {
            titleTV.setText(petModel.getTitle());
            imageLoader.DisplayImage(petModel.getImageUrl(), R.mipmap.ic_launcher, petImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("content_url", petModel.getContentUrl());
                    context.startActivity(intent);
                }
            });
        }
    }
}
