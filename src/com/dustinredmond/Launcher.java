package com.dustinredmond;

import com.dustinredmond.view.UI;

public class Launcher {
    // Launch from class that doesn't extend JavaFX Application
    // makes for easier compatibility with differing JDKs
    public static void main(String[] args) {
        new UI().startUi(args);
    }
}
