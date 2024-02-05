package kameib.localizator.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kameib.localizator.Localizator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModListGetter {
    
    public static List<String> getModList() {
        List<String> modList = new ArrayList<>();
        File modsFolder = new File("mods");

        if (modsFolder.exists() && modsFolder.isDirectory()) {
            File[] modFiles = modsFolder.listFiles();

            if (modFiles != null) {
                String modId;
                for (File modFile : modFiles) {
                    if (modFile.isFile() && modFile.getName().endsWith(".jar")) {
                        if (modFile.getName().contains(Localizator.NAME)) {
                            continue;
                        }
                        modId = getModIdFromJar(modFile);
                        if (modId != null) {
                            modList.add(modId);
                        }
                    }
                }
            } else {
                System.out.println("No mods found in the mods folder.");
            }
        } else {
            System.out.println("Mods folder does not exist or is not a directory.");
        }
        return modList;
    }

    private static String getModIdFromJar(File modFile) {
        try (JarFile jarFile = new JarFile(modFile)) {
            JarEntry entry = jarFile.getJarEntry("mcmod.info");
            if (entry != null) {
                // Use a library like Gson to parse JSON
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(readInputStream(jarFile.getInputStream(entry)));

                if (element.isJsonArray()) {
                    JsonArray modsArray = element.getAsJsonArray();
                    if (modsArray.size() > 0) {
                        JsonObject modInfo = modsArray.get(0).getAsJsonObject();
                        if (modInfo.has("modid")) {
                            return modInfo.get("modid").getAsString();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            resultStringBuilder.append(new String(buffer, 0, length));
        }
        return resultStringBuilder.toString();
    }
}
