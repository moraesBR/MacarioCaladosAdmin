package senac.macariocalcadosadmin.providers;

import android.content.SearchRecentSuggestionsProvider;

public class SapatoSuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "senac.macariocalcadosadmin.providers.SapatoSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SapatoSuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
