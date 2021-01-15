
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HelloClassLoader extends ClassLoader {

    public static final String CLASS_FILE_PATH = "E:\\IdeaProjects\\JavaLearn\\dutt-base\\src\\main\\java\\cn\\dutt\\Hello.xlass";

    public static void main(String[] args) {

        try {
            Object helloObject = new HelloClassLoader().findClass("Hello").newInstance();
            Method[] methods = helloObject.getClass().getMethods();

            for(int i=0;i<methods.length;i++){
                Method temp = methods[i];
                if("hello".equals(temp.getName())){
                    temp.invoke(helloObject,null);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = new byte[1];
        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(CLASS_FILE_PATH));
            bytes = convertByteArray(fileBytes);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return defineClass(name,bytes,0,bytes.length);

    }

    private byte[] convertByteArray(byte[] bytes){
        byte[] temp = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            temp[i] = (byte) (255-bytes[i]);
        }
        return temp;
    }
}
