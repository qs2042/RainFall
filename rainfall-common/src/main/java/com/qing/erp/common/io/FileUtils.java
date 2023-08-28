package com.qing.erp.common.io;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileUtils {
    // 创建文件
    public static boolean createFile(String filePath) {
        File file = new File(filePath);
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 创建目录
    public static boolean createDir(String dirPath) {
        File dir = new File(dirPath);
        return dir.mkdir();
    }

    // 删除文件or目录
    public static boolean deleteFileOrDir(String path) {
        //Path file = Paths.get(path);
        //Files.delete(file);

        File f = new File(path);
        return f.delete();
    }

    // 重命名文件or目录
    public static boolean renameFile(File file, String newName) throws IOException {
        File newFile = new File(file.getParent(), newName);
        if (newFile.exists()) {
            return false;
        }
        return file.renameTo(newFile);
    }

    // 列出文件
    public static List<File> listFiles(File directory, boolean recursive) {
        List<File> fileList = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    if (recursive) {
                        fileList.addAll(listFiles(file, true));
                    }
                } else {
                    fileList.add(file);
                }
            }
        }
        return fileList;
    }

    // 检查文件or目录是否存在
    public static boolean isExists (String path) {
        File file = new File(path);
        return file.exists();
    }

    // 复制文件
    public static boolean copyFile(String sourcePath, String targetPath) {
        Path source = Paths.get(sourcePath);
        Path target = Paths.get(targetPath);
        try {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 移动文件
    public static boolean moveFile(String sourcePath, String targetPath) {
        Path source = Paths.get(sourcePath);
        Path target = Paths.get(targetPath);
        try {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 读取文件内容为字节数组
     */
    @SneakyThrows
    public static byte[] readByBytes(String filePath) {
        Path file = Paths.get(filePath);
        return Files.readAllBytes(file);
    }

    /**
     * 将字节数组写入文件
     */
    @SneakyThrows
    public static void write(String filePath, byte[] bytes) {
        Path file = Paths.get(filePath);
//        byte[] content = "Hello, world!".getBytes();
        Files.write(file, bytes, StandardOpenOption.CREATE);
    }

    /**
     * 构建路径
     */
    public static Path joinPaths(String... paths) {
        return Paths.get(paths[0], Arrays.copyOfRange(paths, 1, paths.length));
    }

    /**
     * 构建路径
     */
    public static String joinPathsPro(String... paths) {
        if (paths == null || paths.length == 0) {
            return "";
        }
        String joinedPath = paths[0];
        for (int i = 1; i < paths.length; i++) {
            joinedPath = Paths.get(joinedPath, paths[i]).toString();
        }
        return joinedPath;
    }

    /**
     * 规范化路径
     */
    public static Path normalizePath(String path) {
        return Paths.get(path).normalize();
    }

    // 比较两个文件的内容是否相等
    public static boolean contentEquals(File file1, File file2) throws IOException {
        long length1 = file1.length();
        long length2 = file2.length();
        if (length1 != length2) {
            return false;
        }
        try (FileInputStream inputStream1 = new FileInputStream(file1);
             FileInputStream inputStream2 = new FileInputStream(file2)) {
            byte[] buffer1 = new byte[1024];
            byte[] buffer2 = new byte[1024];
            int n;
            while ((n = inputStream1.read(buffer1)) != -1) {
                inputStream2.read(buffer2);
                if (!Arrays.equals(buffer1, buffer2)) {
                    return false;
                }
            }
            return true;
        }
    }

    // 计算文件哈希值：计算文件的MD5、SHA-1等哈希值。
    public static String calculateFileHash(File file, String algorithm) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        try (FileInputStream inputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int n;
            while ((n = inputStream.read(buffer)) != -1) {
                digest.update(buffer, 0, n);
            }
        }
        byte[] hashBytes = digest.digest();
        StringBuilder hashBuilder = new StringBuilder();
        for (byte b : hashBytes) {
            hashBuilder.append(String.format("%02x", b));
        }
        return hashBuilder.toString();
    }

    // 压缩文件：将一个或多个文件压缩成zip、tar、gz等格式。
    public static void zipFiles(File[] sourceFiles, File targetZipFile) throws IOException {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(targetZipFile))) {
            for (File sourceFile : sourceFiles) {
                ZipEntry zipEntry = new ZipEntry(sourceFile.getName());
                zipOutputStream.putNextEntry(zipEntry);
                try (FileInputStream inputStream = new FileInputStream(sourceFile)) {
                    byte[] buffer = new byte[1024];
                    int n;
                    while ((n = inputStream.read(buffer)) != -1) {
                        zipOutputStream.write(buffer, 0, n);
                    }
                }
                zipOutputStream.closeEntry();
            }
        }
    }

    // 解压文件：解压zip、tar、gz等格式的文件。
    public static void unzipFile(File zipFile, File targetDirectory) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                File targetFile = new File(targetDirectory, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    targetFile.mkdirs();
                } else {
                    targetFile.getParentFile().mkdirs();
                    try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {
                        byte[] buffer = new byte[1024];
                        int n;
                        while ((n = zipInputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, n);
                        }
                    }
                }
                zipInputStream.closeEntry();
            }
        }
    }
}
