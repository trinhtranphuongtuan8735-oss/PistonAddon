package com.example.addon;

import com.example.addon.modules.PistonCrystal;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;

public class AddonTemplate extends MeteorAddon {
    public static final Category CATEGORY = new Category("Piston Addon");

    @Override
    public void onInitialize() {
        // Dang ky module PistonCrystal
        Modules.get().add(new PistonCrystal());
    }

    @Override
    public void registerCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "com.example.addon";
    }
}
