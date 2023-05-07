package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class ReadUrl {
    public static String read(String urlString) {
        try {
            URL url = new URL(urlString);
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line;
            line = br.readLine();
            StringBuilder allBuilder = new StringBuilder();
            while (line != null) {
                line = br.readLine();
                allBuilder.append(line);
            }
            String all = new String(allBuilder);
            String[] array = all.split("%%%%%");
            return array[1];
        } catch(IOException e) {
            Main.logM("Exception at readUrl25:");
            Main.logN(e.toString());
            Main.exit(2, "readUrl25");
            return null;
        }
    }
}
