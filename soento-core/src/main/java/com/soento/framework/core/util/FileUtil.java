package com.soento.framework.core.util;

import com.soento.framework.core.consts.Constants;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public final class FileUtil extends FileUtils {
    /**
     * 返回文件名
     *
     * @param file 文件
     * @return 主文件名
     */
    public static String getName(File file) {
        if (file.isDirectory()) {
            return file.getName();
        }
        return getName(file.getName());
    }

    /**
     * 返回文件名
     *
     * @param fileName 完整文件名
     * @return 主文件名
     */
    public static String getName(String fileName) {
        if (StringUtil.isBlank(fileName) || false == fileName.contains(Constants.DOT)) {
            return fileName;
        }
        return StringUtil.substring(fileName, 0, fileName.lastIndexOf(Constants.DOT));
    }

    /**
     * 创建文件夹，如果存在直接返回此文件夹<br>
     * 此方法不对File对象类型做判断，如果File不存在，无法判断其类型
     *
     * @param dirPath 文件夹路径，使用POSIX格式，无论哪个平台
     * @return 创建的目录
     */
    public static File mkDir(String dirPath) {
        if (dirPath == null) {
            return null;
        }
        final File dir = getFile(dirPath);
        return mkDir(dir);
    }

    /**
     * 创建文件夹，会递归自动创建其不存在的父文件夹，如果存在直接返回此文件夹<br>
     * 此方法不对File对象类型做判断，如果File不存在，无法判断其类型
     *
     * @param dir 目录
     * @return 创建的目录
     */
    public static File mkDir(File dir) {
        if (false == dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 创建文件及其父目录，如果这个文件存在，直接返回这个文件<br>
     * 此方法不对File对象类型做判断，如果File不存在，无法判断其类型
     *
     * @param fullFilePath 文件的全路径，使用POSIX风格
     * @return 文件，若路径为null，返回null
     */
    public static File mkFile(String fullFilePath) {
        if (fullFilePath == null) {
            return null;
        }
        return mkFile(getFile(fullFilePath));
    }

    /**
     * 创建文件及其父目录，如果这个文件存在，直接返回这个文件<br>
     * 此方法不对File对象类型做判断，如果File不存在，无法判断其类型
     *
     * @param file 文件对象
     * @return 文件，若路径为null，返回null
     */
    public static File mkFile(File file) {
        if (false == file.exists()) {
            mkDir(file.getParent());
            try {
                file.createNewFile();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return file;
    }

    /**
     * 获得输入流
     *
     * @param file 文件
     * @return 输入流
     */
    public static BufferedInputStream getInputStream(File file) {
        try {
            return new BufferedInputStream(new FileInputStream(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得输入流
     *
     * @param path 文件路径
     * @return 输入流
     */
    public static BufferedInputStream getInputStream(String path) {
        return getInputStream(getFile(path));
    }

    /**
     * 获得一个输出流对象
     *
     * @param file 文件
     * @return 输出流对象
     */
    public static BufferedOutputStream getOutputStream(File file) {
        try {
            return new BufferedOutputStream(new FileOutputStream(mkFile(file)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得一个输出流对象
     *
     * @param path 输出到的文件路径，绝对路径
     * @return 输出流对象
     */
    public static BufferedOutputStream getOutputStream(String path) {
        return getOutputStream(mkFile(path));
    }

    /**
     * 路径标准化<br>
     * <ol>
     * <li>1. 统一用 /</li>
     * <li>2. 多个 / 转换为一个 /</li>
     * <li>3. 去除两边空格</li>
     * <li>4. .. 和 . 转换为绝对路径</li>
     * <li>5. 去掉前缀，例如file:</li>
     * </ol>
     *
     * @param path 原路径
     * @return 修复后的路径
     */
    public static String normalize(String path) {
        if (path == null) {
            return null;
        }
        String pathToUse = path.replaceAll("[/\\\\]{1,}", "/").trim();

        int prefixIndex = pathToUse.indexOf(Constants.COLON);
        String prefix = "";
        if (prefixIndex != -1) {
            prefix = pathToUse.substring(0, prefixIndex + 1);
            if (prefix.contains("/")) {
                prefix = "";
            } else {
                pathToUse = pathToUse.substring(prefixIndex + 1);
            }
        }
        if (pathToUse.startsWith(Constants.SLASH)) {
            prefix = prefix + Constants.SLASH;
            pathToUse = pathToUse.substring(1);
        }

        String[] paths = StringUtil.split(pathToUse, Constants.SLASH);
        List<String> pathElements = new LinkedList();
        int tops = 0;

        for (int i = paths.length - 1; i >= 0; i--) {
            String element = paths[i];
            if (Constants.DOT.equals(element)) {
                // 当前目录，丢弃
            } else if (Constants.DOUBLE_DOT.equals(element)) {
                tops++;
            } else {
                if (tops > 0) {
                    // Merging path element with element corresponding to top path.
                    tops--;
                } else {
                    // Normal path element found.
                    pathElements.add(0, element);
                }
            }
        }

        // Remaining top paths need to be retained.
        for (int i = 0; i < tops; i++) {
            pathElements.add(0, Constants.DOUBLE_DOT);
        }

        return prefix + StringUtil.join(pathElements, Constants.SLASH);
    }

    /**
     * 获得相对子路径
     *
     * @param rootDir  绝对父路径
     * @param filePath 文件路径
     * @return 相对子路径
     */
    public static String relativePath(String rootDir, String filePath) {
        return relativePath(rootDir, getFile(filePath));
    }

    /**
     * 获得相对子路径
     *
     * @param rootDir 绝对父路径
     * @param file    文件
     * @return 相对子路径
     */
    public static String relativePath(String rootDir, File file) {
        String subPath = null;
        try {
            subPath = file.getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (StringUtil.isNotEmpty(rootDir) && StringUtil.isNotEmpty(subPath)) {
            rootDir = normalize(rootDir);
            subPath = normalize(subPath);

            if (subPath != null && subPath.toLowerCase().startsWith(subPath.toLowerCase())) {
                subPath = subPath.substring(rootDir.length() + 1);
            }
        }
        return subPath;
    }

}
