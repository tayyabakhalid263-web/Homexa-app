package com.homexa.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.homexa.app.R;
import com.homexa.app.models.Provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.ProviderViewHolder> {

    public interface OnProviderClickListener {
        void onProviderClick(Provider provider);
    }

    private final List<Provider> providers = new ArrayList<>();
    private final OnProviderClickListener listener;
    private final boolean compact;

    public ProviderAdapter(OnProviderClickListener listener, boolean compact) {
        this.listener = listener;
        this.compact = compact;
    }

    public void submitList(List<Provider> items) {
        providers.clear();
        if (items != null) {
            providers.addAll(items);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProviderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = compact ? R.layout.item_service_card : R.layout.item_provider_card;
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ProviderViewHolder(view, compact);
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderViewHolder holder, int position) {
        holder.bind(providers.get(position));
    }

    @Override
    public int getItemCount() {
        return providers.size();
    }

    class ProviderViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageAvatar;
        private final TextView textName;
        private final TextView textCategory;
        private final TextView textPrice;
        private final TextView textRating;
        private final TextView textExperience;
        private final MaterialButton buttonViewDetails;

        ProviderViewHolder(@NonNull View itemView, boolean compactMode) {
            super(itemView);
            if (compactMode) {
                imageAvatar = itemView.findViewById(R.id.imageServiceAvatar);
                textName = itemView.findViewById(R.id.textServiceName);
                textCategory = itemView.findViewById(R.id.textServiceCategory);
                textPrice = itemView.findViewById(R.id.textServicePrice);
                textRating = itemView.findViewById(R.id.textRatingValue);
                textExperience = null;
                buttonViewDetails = null;
            } else {
                imageAvatar = itemView.findViewById(R.id.imageProviderAvatar);
                textName = itemView.findViewById(R.id.textProviderName);
                textCategory = itemView.findViewById(R.id.textProviderCategory);
                textPrice = itemView.findViewById(R.id.textProviderPrice);
                textRating = itemView.findViewById(R.id.textRatingValue);
                textExperience = itemView.findViewById(R.id.textProviderExperience);
                buttonViewDetails = itemView.findViewById(R.id.buttonViewDetails);
            }
        }

        void bind(Provider provider) {
            imageAvatar.setImageResource(provider.getImageResId());
            textName.setText(provider.getName());
            textCategory.setText(provider.getCategory());
            textPrice.setText(String.format(Locale.getDefault(), "PKR %d", provider.getStartingPrice()));
            if (textRating != null) {
                textRating.setText(String.format(Locale.getDefault(), "%.1f", provider.getRating()));
            }
            if (textExperience != null) {
                textExperience.setText(String.format(Locale.getDefault(),
                        "%d years experience", provider.getExperienceYears()));
            }
            View.OnClickListener openDetails = v -> {
                if (listener != null) {
                    listener.onProviderClick(provider);
                }
            };
            itemView.setOnClickListener(openDetails);
            if (buttonViewDetails != null) {
                buttonViewDetails.setClickable(true);
                buttonViewDetails.setFocusable(true);
                buttonViewDetails.setOnClickListener(openDetails);
            }
        }
    }
}
