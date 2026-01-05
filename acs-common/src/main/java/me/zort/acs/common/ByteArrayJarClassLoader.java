package me.zort.acs.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * A ClassLoader that loads classes and resources from a JAR file provided as a byte array.
 */
public class ByteArrayJarClassLoader extends ClassLoader {

    private final Map<String, byte[]> classes = new HashMap<>();
    private final Map<String, byte[]> resources = new HashMap<>();

    public ByteArrayJarClassLoader(byte[] jarBytes, ClassLoader parent) throws IOException {
        super(parent);
        loadResources(jarBytes);
    }

    private void loadResources(byte[] jarBytes) throws IOException {
        try (JarInputStream jis = new JarInputStream(new ByteArrayInputStream(jarBytes))) {
            JarEntry e;
            while ((e = jis.getNextJarEntry()) != null) {
                byte[] data = jis.readAllBytes();
                if (e.getName().endsWith(".class")) {
                    String name = e.getName()
                            .replace('/', '.')
                            .replace(".class", "");
                    classes.put(name, data);
                } else {
                    resources.put(e.getName(), data);
                }
            }
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] b = classes.get(name);
        if (b == null) {
            throw new ClassNotFoundException(name);
        }

        return defineClass(name, b, 0, b.length);
    }

    @Override
    public InputStream getResourceAsStream(String name) {
        byte[] b = resources.get(name);
        if (b != null) {
            return new ByteArrayInputStream(b);
        }

        return super.getResourceAsStream(name);
    }

    @Override
    protected Enumeration<URL> findResources(String name) {
        if (!resources.containsKey(name)) {
            return Collections.emptyEnumeration();
        }

        try {
            URL url = new URL(null, "mem:///" + name, new URLStreamHandler() {
                @Override
                protected URLConnection openConnection(URL u) {
                    return new URLConnection(u) {
                        @Override public void connect() {}
                        @Override
                        public InputStream getInputStream() {
                            return new ByteArrayInputStream(resources.get(name));
                        }
                    };
                }
            });
            return Collections.enumeration(List.of(url));
        } catch (MalformedURLException e) {
            return Collections.emptyEnumeration();
        }
    }
}
