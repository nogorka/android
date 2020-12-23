package com.example.translateapp;

import java.util.ArrayList;

class Trans {
    String text;
    String to;
}

public class TranslatedText {
    // TODO: указать необходимые поля хранения ответа от API при переводе текста

    ArrayList<Trans> translations;
    @Override
    public String toString() {
        String str = " ";
        for (Trans t : translations) {
            str += "\nto " + t.to + "\ttext" + t.text;
        }
        return str;
    }

    public String getTranslations() {
        return translations.get(0).text;
    }
}
