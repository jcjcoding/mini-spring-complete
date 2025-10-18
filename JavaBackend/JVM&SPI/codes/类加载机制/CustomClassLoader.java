import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CustomClassLoader extends ClassLoader{
    private final String customClassPath;

    public CustomClassLoader(String customClassPath){
        super();
        this.customClassPath=customClassPath;
    }
    /**
     * @param className
     * @return 加载后的class对象
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException{
        System.out.println("===== 我的findClass被调用了！正在加载：" + className + " ====="); // 新增日志
        try{
            String classFilePath = customClassPath+className.replace(".","/")+".class";
            
            byte[] classBytes = loadClassBytesFromFile(classFilePath);
            return defineClass(className,classBytes,0,classBytes.length);
        }catch(IOException e){
            throw new ClassNotFoundException("类加载失败:"+className,e);
        }
    }
    
    
    /**
     * @param .class的绝对路径
     * @return .class文件的二进制字节数组
     * @throws IOException
     */
    private byte[] loadClassBytesFromFile(String classFilePath) throws IOException{
        try(InputStream in = new FileInputStream(classFilePath);
        ByteArrayOutputStream out = new ByteArrayOutputStream()){
            //输入流和输出流
            byte[] buffer = new byte[1024];
            //缓冲区 在读取数据中很常用
            int len;
            while((len = in.read(buffer)) != -1){
                out.write(buffer,0,len);
            }
            return out.toByteArray();
        }
    }

    public static void main(String[] args) {
        try {
            //创建加载器
            String customPath = "C:/temp/";
            CustomClassLoader customLoader = new CustomClassLoader(customPath);

            //加载类
            String className = "TestClass"; 
            Class<?> testClass = customLoader.loadClass(className);

            //验证加载结果：输出类信息+加载器信息
            System.out.println("=== 类加载结果 ===");
            System.out.println("加载的类名：" + testClass.getName());
            System.out.println("加载该类的加载器：" + testClass.getClassLoader().getClass().getSimpleName());
            System.out.println("加载器的父加载器（系统类加载器）：" + testClass.getClassLoader().getParent().getClass().getSimpleName());

            //反射调用TestClass的方法（因为TestClass不在当前classpath，无法直接new实例）
            System.out.println("\n=== 反射调用方法 ===");
            //创建实例
            Object testInstance = testClass.getDeclaredConstructor().newInstance();
            //获取sayHello方法
            java.lang.reflect.Method sayHelloMethod = testClass.getMethod("sayHello");
            //调用方法并获取返回值
            String result = (String) sayHelloMethod.invoke(testInstance);
            System.out.println("方法调用结果：" + result);
            // 尝试用自定义加载器加载核心类java.lang.String
            Class<?> stringClass = customLoader.loadClass("java.lang.String");
            System.out.println("\n=== 核心类加载验证 ===");
            System.out.println("java.lang.String的加载器：" + stringClass.getClassLoader());

        } catch (Exception e) {
            // 捕获所有异常（类加载失败、反射异常等），打印详细信息
            e.printStackTrace();
        }
    }
}

