package com.study.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
    /**
     * 创建一个带有UTF-8编码的BufferedReader
     * @param filePath 文件路径
     * @return BufferedReader对象
     * @throws IOException 如果文件不存在或创建失败
     */
    public static BufferedReader createReader(String filePath) throws IOException {
        // 确保文件存在
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        }
        
        return new BufferedReader(
            new InputStreamReader(
                new FileInputStream(filePath), 
                StandardCharsets.UTF_8
            )
        );
    }

    /**
     * 创建一个带有UTF-8编码的BufferedWriter
     * @param filePath 文件路径
     * @return BufferedWriter对象
     * @throws IOException 如果创建文件失败
     */
    public static BufferedWriter createWriter(String filePath) throws IOException {
        // 确保目录存在
        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());
        
        return new BufferedWriter(
            new OutputStreamWriter(
                new FileOutputStream(filePath), 
                StandardCharsets.UTF_8
            )
        );
    }
}
