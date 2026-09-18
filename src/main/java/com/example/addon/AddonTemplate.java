package com.example.addon;

import com.example.addon.modules.PistonCrystal;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddonTemplate extends MeteorAddon {
    public static final Logger LOG = LoggerFactory.getLogger("Addon");
    public static final Category CATEGORY = new Category("PistonPvP");

    @Override
    public void onInitialize() {
        LOG.info("Khoi tao PistonCrystal Addon!");
        Modules.get().add(new PistonCrystal());
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "com.example.addon";
    }
}
