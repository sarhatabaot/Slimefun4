package me.mrCookieSlime.Slimefun.Setup;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class Localization {
    private File file;
    private Config config;
    public static Localization LANG;

    //FIXME: Has to load onLoad(), before items are initialized.

    public Localization(Plugin plugin) {
        this.file = new File("plugins/" + plugin.getDescription().getName().replace(" ", "_") + "/localization/en_US.yml");
        this.config = new Config(file);
        LANG = this;
    }

    /**
     *
     * @param name has to be the exact name defined in en_US.yml
     * @return localized name
     */
    public String getName(String name) {
        return config.getString(name);
    }

    public String getLore(String name, int line){
        return config.getString(name+".lore."+line);
    }


}
