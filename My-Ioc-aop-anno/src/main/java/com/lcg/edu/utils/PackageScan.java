package com.lcg.edu.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lichenggang
 * @date 2020/3/5 6:42 上午
 * @description
 */
public class PackageScan {


    /**
     * 获取类文件集合
     *
     * @param file
     * @return
     */
    public static void scanFilePath(File file, List<String> classPaths) {
        if (file.isDirectory()) {
            //如果是文件夹就递归
            File[] files = file.listFiles();
            for (File file1 : files) {
                scanFilePath(file1, classPaths);
            }
        } else {
            //如果是是文件就判断是否是.java  .class文件
            if (file.getName().endsWith(".class")) {
                //如果是class文件我们就放入我们的集合中。
                classPaths.add(file.getPath());
            }
        }
    }

    /**
     * 扫描类得到Class集合
     *
     * @param basePack
     * @return
     * @throws ClassNotFoundException
     */
    public static List<String> doScan(String basePack) {

        List<String> classList = new ArrayList<>();
        //先把包名转换为路径,首先得到项目的classpath
        String classpath = PackageScan.class.getResource("/").getPath();
        System.out.println("classpath:" + classpath);
        //然后把我们的包名转换为路径名
        String basePackPath = basePack.replace(".", File.separator);
        //然后把classpath和basePack合并
        String searchPath = classpath + basePackPath;
        System.out.println("searchPath:" + searchPath);
        //文件路径集合
        File file = new File(searchPath);
        List<String> classPaths = new ArrayList<>();
        scanFilePath(file, classPaths);
        for (String s : classPaths) {
            String replace = s.replace(classpath, "").replace(File.separator, ".").replace(".class", "");
            if (!replace.contains("$")) {
                System.out.println(replace);
                classList.add(replace);
            }
        }
        return classList;
    }


    public static void main(String[] args) throws ClassNotFoundException {

        String basePack = "com.lcg.edu";
        List<String> classNameList = PackageScan.doScan(basePack);
        for (String s : classNameList) {
            System.out.println(s);
        }
    }
}
