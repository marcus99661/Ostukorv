package com.github.marcus99661.ostukorv.Data;

import java.util.ArrayList;
import java.util.HashMap;

public class EnumMap {

    HashMap<Categories, HashMap<String, ArrayList<String>>> data;

    public enum Categories {
        IT,
        Telefonid,
        Helitehnika,
        Kodumasinad,
        Moobel,
        Koogitehnika,
        Ilu,
        Meelelahutus,
        Vaba;
    }

    public EnumMap() {
        data.put(Categories.IT, getIT());

    }

    private static HashMap<String, ArrayList<String>> getIT() {
        HashMap<String, ArrayList<String>> main = new HashMap<>();
        ArrayList<String> temp = new ArrayList<>();
        temp.add("test1");
        temp.add("test2");
        main.put("Price", temp);



        return main;
    }
}
