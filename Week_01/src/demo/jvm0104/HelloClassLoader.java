package demo.jvm0104;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) {
        try {
            Class<?> helloClass = new HelloClassLoader().findClass("Hello");
            Object obj = helloClass.newInstance();
            Method method = helloClass.getMethod("hello");
            method.invoke(obj);
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return defineClass(name, helloXlassBytes(), 0, helloXlassBytes().length);
    }

    public byte[] helloXlassBytes() {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get("src/demo/jvm0104/Hello.xlass"));

            byte[] newBytes = new byte[bytes.length];

            for (int i = 0; i < bytes.length; i++) {
                newBytes[i] = (byte) (255 - bytes[i]);
            }
            return newBytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
