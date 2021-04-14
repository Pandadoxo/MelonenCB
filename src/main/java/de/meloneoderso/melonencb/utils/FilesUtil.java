// -----------------------
// Coded by Pandadoxo
// on 06.04.2021 at 10:52 
// -----------------------

package de.meloneoderso.melonencb.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import de.meloneoderso.melonencb.MelonenCB;
import de.meloneoderso.melonencb.config.ResetConfig;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class FilesUtil {

    private static final File resetConfig = new File(MelonenCB.getInstance().getDataFolder(), "resetConfig.json");

    public FilesUtil() {
        create();
        load();
    }

    public void create() {
        try {
            if (!resetConfig.exists()) {
                resetConfig.getParentFile().mkdirs();
                resetConfig.createNewFile();
            }
        } catch (IOException ignored) {
        }
    }

    public void load() {
        try {
            JsonReader resetR = new JsonReader(new FileReader(resetConfig));
            MelonenCB.setResetConfig(new Gson().fromJson(resetR, ResetConfig.class));
            if (MelonenCB.getResetConfig() == null) MelonenCB.setResetConfig(new ResetConfig());
            resetR.close();
        } catch (IOException ignored) {
        }
    }

    public void save() {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        try {
            PrintWriter resetW = new PrintWriter(resetConfig, "UTF-8");
            resetW.println(gson.toJson(MelonenCB.getResetConfig()));
            resetW.flush();
            resetW.close();
        } catch (IOException ignored) {
        }
    }

}
