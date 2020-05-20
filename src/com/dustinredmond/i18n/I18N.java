package com.dustinredmond.i18n;

import java.util.HashMap;
import java.util.Locale;

/**
 * TODO: Add translations for locales other than en-US
 */
public class I18N {
    public static String get(String key) {

        Locale currentLocale = Locale.getDefault();
        String language = currentLocale.getLanguage();
        if (language.equals(new Locale("en").getLanguage())) {
            return americanEnglish().get(key);
        } else {
            System.out.printf("Language: %s | No matching translation for text key: %s\n", language, key);
            // as default, better than no Strings at all
            return americanEnglish().get(key);
        }
    }

    // NOTE: Each language group will have corresponding map
    private static HashMap<String,String> americanEnglish() {
        HashMap<String,String> map = new HashMap<>();

        // MainWindow & UI
        map.put("application.title", "ClipBoard++");
        map.put("application.greeting", "Welcome to ClipBoard++");
        map.put("application.welcome", "" +
                "Thanks for using ClipBoard++. It looks like this is your first time using the application. " +
                "To get started, you can add a new clip via the \"New\" button or by directly pasting text into " +
                "the table. Clips can be copied again later via Ctrl+C or by double clicking the table item." +
                "\n\n\n" +
                "https://github.com/dustinkredmond/clipboard++\n" +
                "Created By: Dustin K. Redmond\n" +
                "Email: dustin@dustinredmond.com");
        map.put("option.new", "New");
        map.put("option.edit", "Edit");
        map.put("option.delete", "Delete");

        // CreateNewWindow
        map.put("new.contents", "Clipboard Contents:");
        map.put("new.notes", "Notes:");
        map.put("new.submit", "Submit");

        // EditWindow
        map.put("edit.created", "Created:");
        map.put("edit.contents", "Clipboard Contents:");
        map.put("edit.notes", "Notes:");
        map.put("edit.save", "Save");

        // Title Case words
        map.put("title.edit", "Edit");
        map.put("title.delete", "Delete");

        // TableColumn names
        map.put("table.created", "Created");
        map.put("table.clip", "Clip");
        map.put("table.notes", "Notes");
        map.put("table.empty", "No clipboard items saved. Either add with \"New\" button, or Paste (Ctrl+V) into table.");

        return map;
    }


}
