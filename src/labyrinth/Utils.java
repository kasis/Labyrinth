/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author melkonyan
 */
public class Utils {
    static Properties load(String file) throws IOException {
        Properties prop = new Properties();
        InputStream in = new FileInputStream(file);
        if (in == null) {
            throw new IOException("Could not open properties file.");
        }
        prop.load(in);
        in.close();
        return prop;   
    }
}
