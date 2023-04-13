package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CrimeDataLoader {
    public static List<Crime> loadCrimeData(String fileUrl) {
        List<Crime> crimes = new ArrayList<>();
        try {
            URL url = new URL(fileUrl);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = reader.readLine(); // skip the header row
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                String id = row[0];
                String longitude = row[1];
                String latitude = row[2];
                Crime crime = new Crime(id,longitude, latitude);
                crimes.add(crime);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return crimes;
    }
}
