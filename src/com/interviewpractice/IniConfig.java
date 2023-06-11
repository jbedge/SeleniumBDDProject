package com.interviewpractice;

import org.ini4j.Ini;
import org.ini4j.Profile;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class IniConfig {
private File fileToParse;
private Ini ini;
    public IniConfig(String filePath){
        fileToParse = new File("src/com/interviewpractice/Data.ini");
    }

    public IniConfig loadConfig() throws IOException {
            ini = new Ini(fileToParse);
            Set<String> sections=ini.keySet();
            for (String section:sections){
                Profile.Section s=ini.get(section);
                s.putAll(ini.get("Default"));
            }
            return this;
    }

    public Profile.Section getSection(String sectionName){
        return ini.get(sectionName);
    }

    public static void main(String[] args) throws IOException {
        System.out.println(new IniConfig(null).loadConfig().getSection("Default").getAll("env"));
    }


}
