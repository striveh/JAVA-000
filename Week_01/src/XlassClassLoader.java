import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;

public class XlassClassLoader extends ClassLoader {
    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class clz = new XlassClassLoader().xlassToClass("Hello");
        Object xlassIns = clz.newInstance();
        Method method = clz.getMethod("hello");
        method.invoke(xlassIns);
    }

    Class xlassToClass(String xlassName) throws IOException {
        File xlassFile = new File(this.getResource(xlassName+".xlass").getPath());
        byte[] xlassByte = Files.readAllBytes(xlassFile.toPath());
        for (int i= 0; i < xlassByte.length; i++){
            xlassByte[i]= (byte) (255- xlassByte[i]);
        }
        return defineClass(xlassName,xlassByte,0, xlassByte.length);
    }
}
