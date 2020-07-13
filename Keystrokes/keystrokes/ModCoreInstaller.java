package keystrokes;

import net.minecraft.launchwrapper.*;
import java.lang.reflect.*;
import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.nio.charset.*;
import org.apache.commons.io.*;
import com.google.gson.*;
import java.util.*;

public class ModCoreInstaller
{
    private static final String VERSION_URL = "https://api.sk1er.club/modcore_versions";
    private static final String className = "club.sk1er.mods.core.ModCore";
    private static boolean errored;
    private static String error;
    private static File dataDir;
    private static boolean isRunningModCore;
    
    public static boolean isIsRunningModCore() {
        return ModCoreInstaller.isRunningModCore;
    }
    
    private static boolean isInitalized() {
        try {
            final LinkedHashSet<String> objects = new LinkedHashSet<String>();
            objects.add("club.sk1er.mods.core.ModCore");
            Launch.classLoader.clearNegativeEntries((Set)objects);
            final Field invalidClasses = LaunchClassLoader.class.getDeclaredField("invalidClasses");
            invalidClasses.setAccessible(true);
            final Object obj = invalidClasses.get(ModCoreInstaller.class.getClassLoader());
            ((Set)obj).remove("club.sk1er.mods.core.ModCore");
            return Class.forName("club.sk1er.mods.core.ModCore") != null;
        }
        catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException ex2) {
            final ReflectiveOperationException ex;
            final ReflectiveOperationException ignored = ex;
            ignored.printStackTrace();
            return false;
        }
    }
    
    public static boolean isErrored() {
        return ModCoreInstaller.errored;
    }
    
    public static String getError() {
        return ModCoreInstaller.error;
    }
    
    private static void bail(final String error) {
        ModCoreInstaller.errored = true;
        ModCoreInstaller.error = error;
    }
    
    private static JsonHolder readFile(final File in) {
        try {
            return new JsonHolder(FileUtils.readFileToString(in));
        }
        catch (IOException ex) {
            return new JsonHolder();
        }
    }
    
    public static void initializeModCore(final File gameDir) {
        if (!isIsRunningModCore()) {
            return;
        }
        try {
            final Class<?> modCore = Class.forName("club.sk1er.mods.core.ModCore");
            final Method instanceMethod = modCore.getMethod("getInstance", (Class<?>[])new Class[0]);
            final Method initialize = modCore.getMethod("initialize", File.class);
            final Object modCoreObject = instanceMethod.invoke(null, new Object[0]);
            initialize.invoke(modCoreObject, gameDir);
            System.out.println("Loaded ModCore Successfully");
        }
        catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException ex2) {
            final ReflectiveOperationException ex;
            final ReflectiveOperationException e = ex;
            e.printStackTrace();
            System.out.println("Did NOT ModCore Successfully");
        }
    }
    
    public static int initialize(final File gameDir, final String minecraftVersion) {
        if (isInitalized()) {
            return -1;
        }
        ModCoreInstaller.dataDir = new File(gameDir, "modcore");
        if (!ModCoreInstaller.dataDir.exists() && !ModCoreInstaller.dataDir.mkdirs()) {
            bail("Unable to create necessary files");
            return 1;
        }
        final JsonHolder jsonHolder = fetchJSON("https://api.sk1er.club/modcore_versions");
        String latestRemote = jsonHolder.optString(minecraftVersion);
        final boolean failed = jsonHolder.getKeys().size() == 0 || (jsonHolder.has("success") && !jsonHolder.optBoolean("success"));
        final File metadataFile = new File(ModCoreInstaller.dataDir, "metadata.json");
        final JsonHolder localMetadata = readFile(metadataFile);
        if (failed) {
            latestRemote = localMetadata.optString(minecraftVersion);
        }
        final File modcoreFile = new File(ModCoreInstaller.dataDir, "Sk1er Modcore-" + latestRemote + " (" + minecraftVersion + ").jar");
        if (!modcoreFile.exists() || (!localMetadata.optString(minecraftVersion).equalsIgnoreCase(latestRemote) && !failed)) {
            final File old = new File(ModCoreInstaller.dataDir, "Sk1er Modcore-" + localMetadata.optString(minecraftVersion) + " (" + minecraftVersion + ").jar");
            if (old.exists()) {
                old.delete();
            }
            if (!download("https://static.sk1er.club/repo/mods/modcore/" + latestRemote + "/" + minecraftVersion + "/ModCore-" + latestRemote + " (" + minecraftVersion + ").jar", latestRemote, modcoreFile, minecraftVersion, localMetadata)) {
                bail("Unable to download");
                return 2;
            }
        }
        addToClasspath(modcoreFile);
        if (!isInitalized()) {
            bail("Something went wrong and it did not add the jar to the class path. Local file exists? " + modcoreFile.exists());
            return 3;
        }
        ModCoreInstaller.isRunningModCore = true;
        return 0;
    }
    
    public static void addToClasspath(final File file) {
        try {
            final URL url = file.toURI().toURL();
            final ClassLoader classLoader = ModCoreInstaller.class.getClassLoader();
            final Method method = classLoader.getClass().getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(classLoader, url);
        }
        catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }
    
    private static boolean download(String url, final String version, final File file, final String mcver, final JsonHolder versionData) {
        url = url.replace(" ", "%20");
        System.out.println("Downloading ModCore  version " + version + " from: " + url);
        final JFrame frame = new JFrame("ModCore Initializer");
        final JProgressBar bar = new JProgressBar();
        final TextArea comp = new TextArea("", 1, 1, 3);
        frame.getContentPane().add(comp);
        frame.getContentPane().add(bar);
        final GridLayout manager = new GridLayout();
        frame.setLayout(manager);
        manager.setColumns(1);
        manager.setRows(2);
        comp.setText("Downloading Sk1er ModCore Library Version " + version + " for Minecraft " + mcver);
        comp.setSize(399, 80);
        comp.setEditable(false);
        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        final Dimension preferredSize = new Dimension(400, 225);
        bar.setSize(preferredSize);
        frame.setSize(preferredSize);
        frame.setResizable(false);
        bar.setBorderPainted(true);
        bar.setMinimum(0);
        bar.setStringPainted(true);
        frame.setVisible(true);
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        final Font font = bar.getFont();
        bar.setFont(new Font(font.getName(), font.getStyle(), font.getSize() * 4));
        comp.setFont(new Font(font.getName(), font.getStyle(), font.getSize() * 2));
        try {
            final URL u = new URL(url);
            final HttpURLConnection connection = (HttpURLConnection)u.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(true);
            connection.addRequestProperty("User-Agent", "Mozilla/4.76 (Sk1er Modcore Initializer)");
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true);
            final InputStream is = connection.getInputStream();
            final int contentLength = connection.getContentLength();
            final FileOutputStream outputStream = new FileOutputStream(file);
            final byte[] buffer = new byte[1024];
            System.out.println("MAX: " + contentLength);
            bar.setMaximum(contentLength);
            bar.setValue(0);
            int read;
            while ((read = is.read(buffer)) > 0) {
                outputStream.write(buffer, 0, read);
                bar.setValue(bar.getValue() + 1024);
            }
            outputStream.close();
            FileUtils.write(new File(ModCoreInstaller.dataDir, "metadata.json"), (CharSequence)versionData.put(mcver, version).toString());
        }
        catch (Exception e) {
            e.printStackTrace();
            frame.dispose();
            return false;
        }
        frame.dispose();
        return true;
    }
    
    public static JsonHolder fetchJSON(final String url) {
        return new JsonHolder(fetchString(url));
    }
    
    public static String fetchString(String url) {
        url = url.replace(" ", "%20");
        System.out.println("Fetching " + url);
        try {
            final URL u = new URL(url);
            final HttpURLConnection connection = (HttpURLConnection)u.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(true);
            connection.addRequestProperty("User-Agent", "Mozilla/4.76 (Sk1er ModCore)");
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true);
            final InputStream is = connection.getInputStream();
            return IOUtils.toString(is, Charset.defaultCharset());
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Failed to fetch";
        }
    }
    
    static {
        ModCoreInstaller.errored = false;
        ModCoreInstaller.dataDir = null;
        ModCoreInstaller.isRunningModCore = false;
    }
    
    static class JsonHolder
    {
        private JsonObject object;
        
        public JsonHolder(final JsonObject object) {
            this.object = object;
        }
        
        public JsonHolder(final String raw) {
            if (raw == null) {
                this.object = new JsonObject();
            }
            else {
                try {
                    this.object = new JsonParser().parse(raw).getAsJsonObject();
                }
                catch (Exception e) {
                    this.object = new JsonObject();
                    e.printStackTrace();
                }
            }
        }
        
        public JsonHolder() {
            this(new JsonObject());
        }
        
        @Override
        public String toString() {
            if (this.object != null) {
                return this.object.toString();
            }
            return "{}";
        }
        
        public JsonHolder put(final String key, final boolean value) {
            this.object.addProperty(key, Boolean.valueOf(value));
            return this;
        }
        
        public void mergeNotOverride(final JsonHolder merge) {
            this.merge(merge, false);
        }
        
        public void mergeOverride(final JsonHolder merge) {
            this.merge(merge, true);
        }
        
        public void merge(final JsonHolder merge, final boolean override) {
            final JsonObject object = merge.getObject();
            for (final String s : merge.getKeys()) {
                if (override || !this.has(s)) {
                    this.put(s, object.get(s));
                }
            }
        }
        
        private void put(final String s, final JsonElement element) {
            this.object.add(s, element);
        }
        
        public JsonHolder put(final String key, final String value) {
            this.object.addProperty(key, value);
            return this;
        }
        
        public JsonHolder put(final String key, final int value) {
            this.object.addProperty(key, (Number)value);
            return this;
        }
        
        public JsonHolder put(final String key, final double value) {
            this.object.addProperty(key, (Number)value);
            return this;
        }
        
        public JsonHolder put(final String key, final long value) {
            this.object.addProperty(key, (Number)value);
            return this;
        }
        
        private JsonHolder defaultOptJSONObject(final String key, final JsonObject fallBack) {
            try {
                return new JsonHolder(this.object.get(key).getAsJsonObject());
            }
            catch (Exception e) {
                return new JsonHolder(fallBack);
            }
        }
        
        public JsonArray defaultOptJSONArray(final String key, final JsonArray fallback) {
            try {
                return this.object.get(key).getAsJsonArray();
            }
            catch (Exception e) {
                return fallback;
            }
        }
        
        public JsonArray optJSONArray(final String key) {
            return this.defaultOptJSONArray(key, new JsonArray());
        }
        
        public boolean has(final String key) {
            return this.object.has(key);
        }
        
        public long optLong(final String key, final long fallback) {
            try {
                return this.object.get(key).getAsLong();
            }
            catch (Exception e) {
                return fallback;
            }
        }
        
        public long optLong(final String key) {
            return this.optLong(key, 0L);
        }
        
        public boolean optBoolean(final String key, final boolean fallback) {
            try {
                return this.object.get(key).getAsBoolean();
            }
            catch (Exception e) {
                return fallback;
            }
        }
        
        public boolean optBoolean(final String key) {
            return this.optBoolean(key, false);
        }
        
        public JsonObject optActualJSONObject(final String key) {
            try {
                return this.object.get(key).getAsJsonObject();
            }
            catch (Exception e) {
                return new JsonObject();
            }
        }
        
        public JsonHolder optJSONObject(final String key) {
            return this.defaultOptJSONObject(key, new JsonObject());
        }
        
        public int optInt(final String key, final int fallBack) {
            try {
                return this.object.get(key).getAsInt();
            }
            catch (Exception e) {
                return fallBack;
            }
        }
        
        public int optInt(final String key) {
            return this.optInt(key, 0);
        }
        
        public String defaultOptString(final String key, final String fallBack) {
            try {
                return this.object.get(key).getAsString();
            }
            catch (Exception e) {
                return fallBack;
            }
        }
        
        public String optString(final String key) {
            return this.defaultOptString(key, "");
        }
        
        public double optDouble(final String key, final double fallBack) {
            try {
                return this.object.get(key).getAsDouble();
            }
            catch (Exception e) {
                return fallBack;
            }
        }
        
        public List<String> getKeys() {
            final List<String> tmp = new ArrayList<String>();
            this.object.entrySet().forEach(e -> tmp.add(e.getKey()));
            return tmp;
        }
        
        public double optDouble(final String key) {
            return this.optDouble(key, 0.0);
        }
        
        public JsonObject getObject() {
            return this.object;
        }
        
        public boolean isNull(final String key) {
            return this.object.has(key) && this.object.get(key).isJsonNull();
        }
        
        public JsonHolder put(final String values, final JsonHolder values1) {
            return this.put(values, values1.getObject());
        }
        
        public JsonHolder put(final String values, final JsonObject object) {
            this.object.add(values, (JsonElement)object);
            return this;
        }
        
        public void put(final String blacklisted, final JsonArray jsonElements) {
            this.object.add(blacklisted, (JsonElement)jsonElements);
        }
        
        public void remove(final String header) {
            this.object.remove(header);
        }
    }
}
