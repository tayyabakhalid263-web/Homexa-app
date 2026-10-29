package com.homexa.app.data;

import com.homexa.app.models.Provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * In-memory cache of providers (filled from SQLite at app start).
 */
public final class ProviderStore {

    private static final List<Provider> PROVIDERS = new ArrayList<>();

    private ProviderStore() {
    }

    public static void replaceAll(List<Provider> providers) {
        PROVIDERS.clear();
        if (providers != null && !providers.isEmpty()) {
            PROVIDERS.addAll(providers);
        } else {
            PROVIDERS.addAll(SampleData.getProviders());
        }
    }

    public static List<Provider> getAllProviders() {
        return new ArrayList<>(PROVIDERS);
    }

    public static List<Provider> getProvidersByCategory(String category) {
        List<Provider> filtered = new ArrayList<>();
        if (category == null || category.trim().isEmpty()) {
            return getAllProviders();
        }
        for (Provider provider : PROVIDERS) {
            if (provider.getCategory().equalsIgnoreCase(category.trim())) {
                filtered.add(provider);
            }
        }
        return filtered;
    }

    public static Provider getProviderById(int id) {
        for (Provider provider : PROVIDERS) {
            if (provider.getId() == id) {
                return provider;
            }
        }
        return null;
    }

    public static List<Provider> searchProviders(String query, String category) {
        List<Provider> source = getProvidersByCategory(category);
        if (query == null || query.trim().isEmpty()) {
            return source;
        }
        String needle = query.trim().toLowerCase(Locale.getDefault());
        List<Provider> filtered = new ArrayList<>();
        for (Provider provider : source) {
            if (provider.getName().toLowerCase(Locale.getDefault()).contains(needle)
                    || provider.getCategory().toLowerCase(Locale.getDefault()).contains(needle)) {
                filtered.add(provider);
            }
        }
        return filtered;
    }

    public static List<Provider> getPopularProviders() {
        List<Provider> popular = new ArrayList<>();
        List<Provider> all = getAllProviders();
        int limit = Math.min(5, all.size());
        for (int i = 0; i < limit; i++) {
            popular.add(all.get(i));
        }
        return popular;
    }
}
