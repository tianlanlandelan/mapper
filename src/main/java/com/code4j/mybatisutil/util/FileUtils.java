package com.code4j.mybatisutil.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangkaile
 * @date 2021-06-22 20:13:37
 * 文件工具类，读写文件
 */
public class FileUtils {

    /**
     * 写文件
     * @param filePath 文件全路径，如果文件不存在，将创建新文件
     * @param isAppend  是否追加写文件 false 不追加，直接覆盖原文件；true 追加写，在原文件末尾追加
     * @param list  要保存的字符串列表，列表中的每一项单独写入一行
     * @return boolean
     */
    public static boolean write2File(String filePath,boolean isAppend, List<String> list){
        OutputStreamWriter writer = null;
        BufferedWriter bufferedWriter = null;
        try {
            File file = new File(filePath);
            if(!file.exists()){
                file.createNewFile();
            }
            writer = new OutputStreamWriter(new FileOutputStream(file,isAppend),"UTF-8");
            bufferedWriter = new BufferedWriter(writer);
            for (String str : list){
                bufferedWriter.write(str);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            return true;
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                bufferedWriter.close();
                writer.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 读文件
     * @param filePath  文件全路径
     * @return  List<String> 读取的文件内容
     */
    public static List<String> readFromFile(String filePath){
        InputStreamReader read = null;
        BufferedReader bufferedReader = null;

        String line = null;
        List<String> list = new ArrayList<>();
        try {
            File file = new File(filePath);
            if(!file.exists() || file.isDirectory()){
                return list;
            }

            read = new InputStreamReader(new FileInputStream(file));
            bufferedReader = new BufferedReader(read);

            while ((line = bufferedReader.readLine()) != null){
                list.add(line);
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return list;
        }finally {
            try {
                bufferedReader.close();
                read.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
