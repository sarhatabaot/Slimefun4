package me.mrCookieSlime.Slimefun.Setup;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;


public class Localization extends Config{

    public static Localization LANG;

    public Localization(Plugin plugin) {
        super(plugin.getDataFolder() + "/en_US.yml");
        LANG = this;
    }


    public static void setup(Plugin plugin){
        new Localization(plugin);

        LANG.save();
    }

    public void saveDefaultConfiguration(){

    }

    /**
     *
     * @param name has to be the exact name defined in en_US.yml
     * @return localized name
     */
    public String getName(String name) throws NullPointerException{
        return getConfiguration().getString(name);
    }

    public String getLore(String name, int line){
        return getConfiguration().getString(name+".lore."+line);
    }

    public void setLocalizedNames(){
        for(SlimefunItem sfItem:SlimefunItem.all){
            //sets localized names from correct localization file
        }
    }


}
