package com.lzrd.util;

import com.lzrd.Exception.LzrdException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtility {


	public static boolean writeToFile(InputStream is,
			String uploadedFileLocation) throws LzrdException {
		File file = new File(uploadedFileLocation);
		OutputStream ops = null;
		try {
			ops = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			while ((is.read(buffer)) != -1) {
				ops.write(buffer);
			}
			ops.flush();
		} catch (FileNotFoundException e) {
            throw new LzrdException(e);
        } catch (IOException e) {
            throw new LzrdException(e);
        }finally {
            IOUtils.closeQuietly(ops);
            IOUtils.closeQuietly(is);
        }
        // System.out.println(uploadedFileLocation + "文件大小" + file.length());
		if (file.length() < 5) {
			file.delete();
			return false;
		}
		return true;
	}

    public static void writeStringToFile(String dir, String fileName, String scrStr) throws IOException {
        File desFile = new File(dir+ "/" + fileName);
        FileUtils.writeStringToFile(desFile, scrStr, Constant.CHARSET);
    }

    public static void copyDirToDir(String srcDir, String destDir) throws IOException {
        FileUtils.copyDirectory(new File(srcDir), new File(destDir));
    }

    public static void copyFileToDir(String srcFile, String destDir) throws IOException {
        FileUtils.copyFileToDirectory(new File(srcFile), new File(destDir));
    }

    public static void zip(String zipFileName, String inputFilePath) throws IOException {
        ZipOutputStream out = null;
        BufferedOutputStream bo = null;
        File inputFile = new File(inputFilePath);
        try {
            out = new ZipOutputStream(new FileOutputStream(
                    zipFileName));
            bo = new BufferedOutputStream(out);
            zip(out, inputFile, inputFile.getName(), bo);
        } finally {
            IOUtils.closeQuietly(bo);
            IOUtils.closeQuietly(out);
        }
    }

    private static void zip(ZipOutputStream out, File f, String base,
                     BufferedOutputStream bo) throws IOException { // 方法重载
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            if (fl.length == 0) {
                out.putNextEntry(new ZipEntry(base + "/")); // 创建zip压缩进入点base
            }
            for (int i = 0; i < fl.length; i++) {
                zip(out, fl[i], base + "/" + fl[i].getName(), bo); // 递归遍历子文件夹
            }
        } else {
            writeFileToZip(out, f, base, bo);
        }
    }

    private static void writeFileToZip(ZipOutputStream out, File f, String base, BufferedOutputStream bo) throws IOException {
        out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base
        FileInputStream in = null;
        BufferedInputStream bi = null;
        try {
            in = new FileInputStream(f);
            bi = new BufferedInputStream(in);
            int b;
            byte[] bytes = new byte[1024];
            while ((b = bi.read(bytes)) != -1) {
                bo.write(bytes, 0, b); // 将字节流写入当前zip目录
                bo.flush();
            }
        } finally {
            IOUtils.closeQuietly(bi);
            IOUtils.closeQuietly(in);
        }

    }

    public static void deleteDir(String filePath) throws IOException {
        File file = new File(filePath);
        if(file.exists() && file.isDirectory()){
            FileUtils.deleteDirectory(file);
        }
    }

}
