package agent;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class ClassTransformAgent implements ClassFileTransformer {

    /**
     * main函数之前执行
     * @param agentArgs
     * @param inst
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassTransformAgent());
    }

    /**
     * 加载到类加载器之前会执行这个函数
     * ClassFileTransformer
     * @param loader
     * @param className
     * @param classBeingRedefined
     * @param protectionDomain
     * @param classfileBuffer
     * @return
     * @throws IllegalClassFormatException
     */
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
//        System.out.println("classLoader : " + ( loader != null ? loader.toString() : "")
//                        + " className : " + className
//                        + " classBeingRedefined:" + (classBeingRedefined != null ? classBeingRedefined.getName() : "" )
//                        + " protectionDomain :"+protectionDomain.toString()
//                        + " classFileBuffer:" + classfileBuffer.length );
        /**
         * 判断如果是JDK动态代理生成的类
         * 或者是 CGLIB生成的类
         * 则进行
         */
        //if(className.indexOf("$Proxy") >= 0 || className.indexOf("EnhancerByCGLIB") >= 0){
            int lastIndexOf = className.lastIndexOf("/") + 1;
            String fileName = className.substring(lastIndexOf) + ".class";
            exportClazzToFile("/data/generatedProxyClass/",fileName,classfileBuffer);
            System.out.println(className+" Exported success");
        //}
        return classfileBuffer;
    }

    /**
     *
     * @param dirPath
     *目录以/结尾，且必须存在
     * @param fileName
     * @param data
     */
    private void exportClazzToFile(String dirPath, String fileName, byte[] data) {
        try {
            File file = new File(dirPath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();
        } catch (Exception e) {
            System.out.println("exception occured while doing some file operation");
            e.printStackTrace();
        }
    }
}
