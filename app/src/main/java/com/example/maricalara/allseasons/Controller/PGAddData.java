package com.example.maricalara.allseasons.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mari Calara on 24/08/2017.
 */

public class PGAddData {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> date1 = new ArrayList<String>();
        date1.add("Transaction");
        date1.add("Transaction");
        date1.add("Transaction");
        date1.add("Transaction");
        date1.add("Transaction Africa");

        List<String> date2 = new ArrayList<String>();
        date2.add("Transaction");
        date2.add("Transaction");
        date2.add("Transaction");
        date2.add("Transaction");
        date2.add("Transaction");

        List<String> date3 = new ArrayList<String>();
        date3.add("Transaction States");
        date3.add("Transaction");
        date3.add("Transaction");
        date3.add("Transaction");
        date3.add("Transaction");

        expandableListDetail.put("August 4, 2016", date1);
        expandableListDetail.put("September 5, 2016", date2);
        expandableListDetail.put("October 20, 2016", date3);
        return expandableListDetail;
    }
}
