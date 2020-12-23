package com.example.translateapp;

import java.util.ArrayList;
import java.util.Map;

public class LanguagesResponse {
    Map<String, Language> translation;

    @Override
    public String toString() {

        // перечень языков объединяем в одну строку
        String languages = "";
        for (String l : translation.keySet()) {
            Language value = translation.get(l);
            String curLang ="\n"+ l + ":\t" + value.name ;
            languages += curLang;
        }
        return languages;
    }

    public Map<String, Language> getTranslation() {
        return translation;
    }

    public ArrayList<String> getLanguages() {
        ArrayList<String> langs = new ArrayList<String>();

        for (String l : translation.keySet()) {
            Language value = translation.get(l);
            String curLang = l + ":\t" + value.name;
            langs.add(curLang);
        }
        return langs;
    }
}

/* формат ответа от API
{"translation":
  {
   "af":{"name":"Африкаанс","nativeName":"Afrikaans","dir":"ltr"},
   "ar":{"name":"Арабский","nativeName":"العربية","dir":"rtl"},
   "as":{"name":"Assamese","nativeName":"Assamese","dir":"ltr"},
   ..
  }
 */
