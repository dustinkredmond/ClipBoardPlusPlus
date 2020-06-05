package com.dustinredmond.i18n;

import java.util.HashMap;
import java.util.Locale;

/**
 * TODO: Add translations for locales other than en-US
 * other languages should have their own map and should be 
 * added to the get method below
 */
public class I18N {

    /**
     * Determine the Locale of the user and return
     * a localized String given the key
     * @param key Key for translation lookup
     * @return localized version of String
     */
    public static String get(String key) {

        Locale currentLocale = Locale.getDefault();
        String language = currentLocale.getLanguage();
        if (language.equals(new Locale("en").getLanguage())) { // don't compare String values per Java docs
            return americanEnglish().get(key);
        } else {
            System.out.printf("Language: %s | No matching translation for text key: %s\n", language, key);
            // as default use English, better than no Strings at all
            return americanEnglish().get(key);
        }
    }

    private static HashMap<String,String> americanEnglish() {
        HashMap<String,String> map = new HashMap<>();

        // MainWindow & UI
        map.put("application.title", "ClipBoard++");
        map.put("application.greeting", "Welcome to ClipBoard++");
        map.put("application.welcome", "" +
                "Thanks for using ClipBoard++. It looks like this is your first time using the application. " +
                "To get started, you can add a new clip via the \"New\" button. As long as the application " +
                "remains open, ClipBoard++ will run in the background and each time you use the system clipboard, " +
                "the text will be automatically added to the ClipBoard++ table. " +
                "Clips can be copied again later via Ctrl+C or by double clicking the table item." +
                "\n\n\n" +
                "https://github.com/dustinkredmond/ClipBoardPlusPlus\n" +
                "Created By: Dustin K. Redmond\n" +
                "Email: dustin@dustinredmond.com");
        map.put("option.new", "New");
        map.put("option.edit", "Edit");
        map.put("option.delete", "Delete");
        map.put("application.exit", "Are you sure you wish to exit?");

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

        // Menu Names
        map.put("menu.about", "About");
        map.put("menu.options", "Options");

        // MenuItems
        map.put("mi.about", "About this program");
        map.put("mi.help", "Help");
        map.put("mi.alwaysOnTop", "Set always on top");
        map.put("mi.exit", "Exit");

        // About menu
        map.put("mi.about.content", "ClipBoard++ is licensed under the GNU General Public License V3.0\n" +
                "Clipboard icon by Icons8 | https://icons8.com/\n\n" +
                "https://github.com/dustinkredmond/ClipBoardPlusPlus\nCreated: " +
                "2020-05-20 by Dustin K. Redmond");

        // Help menu
        map.put("mi.help.content", "ClipBoard++ is created in such a way that its use should be very straightforward. " +
                "If you run into any issues, please file an Issue or submit a Pull Request on the GitHub page.\n" +
                "\nhttps://github.com/dustinkredmond/ClipBoardPlusPlus");
        return map;
    }
}
