/**
 * @filename			FileUtils.java
 * @function			文件操作通用类
 * @author				skyz <skyzhw@gmail.com>
 * @datetime			Jun 29, 2007
 * @lastmodify			Mar	1,  2008
 */
package cn.sdk.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.lang3.StringUtils;

/**
 * 文件操作通用类
 * 
 * @author skyz <skyzhw@gmail.com>
 * @version 1.0
 */
public final class FileUtil {
	private final static String EMPTYSTRING="";
	/**
	 * 判断一个文件是否存在
	 * @param filePath 文件路径
	 * @return
	 */
	public static boolean isExists(String filePath){
		if(StringUtils.isNotEmpty(filePath)){
			File file=new File(filePath);
			return file.exists();
		}
		return false;
	}
	/**
	 * 判断一个文件是否是目录
	 * @param filePath 文件路径
	 * @return
	 */
	public static boolean isDir(String filePath){
		if(StringUtils.isNotEmpty(filePath)){
			File file=new File(filePath);
			return file.isDirectory();
		}
		return false;
	}
	/**
	 * 判断一个文件是否是文件
	 * @param filePath 文件路径
	 * @return
	 */
	public static boolean isFile(String filePath){
		if(StringUtils.isNotEmpty(filePath)){
			File file=new File(filePath);
			return file.isFile();
		}
		return false;
	}		
	/**
	 * 获取传入的文件名称的扩展名。
	 * 
	 * @param fileName
	 *            要获取其扩展名的文件名称。
	 * @return 传入的文件名称的扩展名。
	 * @comment 如果文件名称不具有后缀或为空，则返回空字符串。
	 */
	public static String getExtension(String fileName){
		// 如果文件名称为null或者空字符串，则抛出异常。
		if (StringUtils.isNotEmpty(fileName)) {
			// 如果文件名称不具有后缀，则返回空字符串。
			int index = fileName.lastIndexOf('.');
			if (index >=0) {
				return fileName.substring(index).toLowerCase();
			}
		}
		return EMPTYSTRING;
	}
	/**
	 * 修改文件名称的后缀。
	 * 
	 * @param fileName
	 *            要修改的文件名称。
	 * @param newExtension
	 *            要修改为的后缀。
	 * @return 修改后的文件名称。
	 * @comment 如果文件名称没有后缀，则返回新的文件名称加上新的后缀；否则返回将文件名称的后缀修改为新的后缀后的文件名称。
	 */
	public static String changeExtension(String fileName, String newExtension){
		if (StringUtils.isEmpty(fileName)) {
			return EMPTYSTRING;
		}
		if (StringUtils.isEmpty(newExtension)) {
			return EMPTYSTRING;
		}
		if (newExtension.equals(".")) {
			return EMPTYSTRING;
		}

		String extension = newExtension.charAt(0) == '.' ? newExtension
				: '.' + newExtension;
		int index = fileName.lastIndexOf('.');
		if (index >= 0) {
			return fileName.substring(0, index) + extension;
		} else {
			return fileName + extension;
		}
	}	
	/**
	 * 移除文件名称后缀。
	 * 
	 * @param fileName
	 *            要移除其后缀的文件名称。
	 * @return 移除了后缀的文件名称。
	 */
	public static String removeExtension(String fileName){
		if (StringUtils.isNotEmpty(fileName)) {
			int index = fileName.lastIndexOf('.');
			if (index >= 0) {
				return fileName.substring(0, index);
			} else {
				return fileName;
			}
		}
		return EMPTYSTRING;
	}
	/**
	 * 返回本地文件夹路径
	 * 
	 * @param folderPath
	 *            文件夹路径。
	 * @param fileName
	 *            文件名称。
	 * @return 文件夹路径与文件名称组合后的新路径。
	 */
	public static String getLocalFilePath(String folderPath, String fileName){
		if(StringUtils.isNotEmpty(folderPath) && StringUtils.isNotEmpty(fileName)){
			return getLocalFilePath(folderPath+"/"+fileName);
		}		
		return EMPTYSTRING;
	}
	/**
	 * 返回本地文件夹路径
	 * 
	 * @param folderPath
	 *            文件夹路径。
	 * @param fileName
	 *            文件名称。
	 * @return 文件夹路径与文件名称组合后的新路径。
	 * @throws NullPointerException
	 */
	public static String getLocalFilePath(String fileName){
		if(StringUtils.isNotEmpty(fileName)){
			String separator = "/";
			fileName=fileName.replace("\\",separator);
			fileName=fileName.replace("//", separator);
			return fileName;
		}
		return EMPTYSTRING;
	}
	public static String getFilePath(String fileName){
		if(StringUtils.isNotEmpty(fileName)){
			String separator = "/";
			fileName=getLocalFilePath(fileName);
			return fileName.substring(0,fileName.lastIndexOf(separator));
		}
		return EMPTYSTRING;
	}
	public static String getFileName(String fileName){
		if(StringUtils.isNotEmpty(fileName)){
			String separator = System.getProperty("file.separator");
			if (StringUtils.isEmpty(separator)) {
				separator = "/";
			}
			fileName=getLocalFilePath(fileName);
			return fileName.substring(fileName.lastIndexOf(separator)+1);
		}
		return EMPTYSTRING;
	}
	/**
	 * 取得在文件夹下所有文件列表
	 * 
	 * @param filePath
	 *            文件夹路径
	 * @return string[]文件数组列表
	 */
	public static String[] getFileList(String filePath) {
		String list[] = null;
		if(StringUtils.isNotEmpty(filePath)){
			File file = new File(filePath);
			if (file.isDirectory()) {
				list = file.list();
			}
			file = null;
		}
		return list;
	}
	/**
	 * 取得在文件夹下所有文件列表
	 * 
	 * @param filePath
	 *            文件夹路径
	 * @return string[]文件数组列表
	 */
	public static String[] getFileList(String filePath,FilenameFilter fileFilter) {
		String list[] = null;
		if(StringUtils.isNotEmpty(filePath) && fileFilter!=null){
			File file = new File(filePath);
			if (file.isDirectory()) {
				list = file.list(fileFilter);
			}
			file = null;
		}
		return list;
	}
	/**
	 * 返回当前磁盘可用空间
	 * @param filePath
	 * @return
	 */
	public static long getDiskFreeSpace(String filePath){
		if(StringUtils.isNotEmpty(filePath)){
			File file=new File(filePath);
			return file.getFreeSpace();
		}
		return -1;
	}
	/**
	 * 返回当前磁盘总空间
	 * @param filePath
	 * @return
	 */
	public static long getDiskTotalSpace(String filePath){
		if(StringUtils.isNotEmpty(filePath)){
			File file=new File(filePath);
			return file.getTotalSpace();
		}
		return -1;
	}	
	
	/**
	 * 返回文件大小
	 * @param filePath
	 * @return
	 */
	public static long getFileSize(String filePath){
		if(StringUtils.isNotEmpty(filePath)){
			File file=new File(filePath);
			if(file.exists() && file.isFile() && file.canRead()){
				return file.length();
			}
		}
		return -1;
	}
	/**
	 * 修改文件属性
	 * @param fileName
	 * @param mod
	 * @category 修改文件拥有者
	 */
	public static boolean chown(String fileName,String own){
        try{
			Runtime rt = Runtime.getRuntime();
			rt.exec("chown "+own+" "+fileName, null);
			return true;
        }catch(Exception e){
        	return false;
        }
	}	
	/**
	 * 修改文件权限
	 * @param fileName
	 * @param mod
	 * @category 修改文件权限，权限一般为777,666等数字，此数字由1(执行),2(写),4(读)组合而成
	 */
	public static boolean chmod(String fileName,int mod){
        try{
			Runtime rt = Runtime.getRuntime();
			rt.exec("chmod "+mod+" "+fileName, null);
			return true;
        }catch(Exception e){
        	return false;
        }
	}	
	/**
	 * 创建单一目录
	 * 
	 * @param path
	 *            要创建的目录
	 */
	public static boolean mkdir(String path) {
		path=getFilePath(path);
		if(StringUtils.isNotEmpty(path)){
			File file = new File(path);
			if (!file.exists()) {
				return file.mkdir();
			}
			file = null;
		}
		return true;
	}

	/**
	 * 创建多级目录
	 * 
	 * @param path
	 *            要创建的目录路径
	 */
	public static boolean mkdirs(String path) {
		path=getFilePath(path);
		if(StringUtils.isNotEmpty(path)){
			File file = new File(path);
			if (!file.exists()) {
				return file.mkdirs();
			}
			file = null;
		}
		return true;
	}

	/**
	 * 搬移文件
	 * 
	 * @param from
	 *            要搬移的文件路径
	 * @param to
	 *            目标文件路径
	 */
	public static boolean mv(String from, String to) {
		if(StringUtils.isNotEmpty(from) && StringUtils.isNotEmpty(to)){
			File fromFile = new File(from);
			File toFile = new File(to);
			try {
				mkdirs(toFile.getParent());
				return fromFile.renameTo(toFile);
			} catch (Exception e) {
				return false;
			}finally{
				fromFile = null;
				toFile = null;
			}	
		}
		return true;
	}

	/**
	 * 搬移文件,可控制是否强制搬移
	 * 
	 * @param from
	 *            要搬移的文件路径
	 * @param to
	 *            目标文件路径
	 * @param force
	 *            是否强制搬移 true(强制) false(不强制)
	 */
	public static boolean mv(String from, String to, boolean force) {
		if(StringUtils.isNotEmpty(from) && StringUtils.isNotEmpty(to)){
			File toFile = new File(to);
			if (force && toFile.exists()) {
				toFile.delete();
				toFile = null;
			}
		}
		return mv(from, to);
	}

	/**
	 * 复制文件
	 * 
	 * @param from
	 *            源文件
	 * @param to
	 *            目录文件
	 */
	public static boolean cp(String from, String to) {
		if(StringUtils.isNotEmpty(from) && StringUtils.isNotEmpty(to)){
			File fromFile = new File(from);
			File toFile = new File(to);
			try {
				mkdirs(toFile.getParent());
				FileInputStream fis = new FileInputStream(fromFile);
				BufferedInputStream bufferIn = new BufferedInputStream(fis, 2024);
				FileOutputStream fos = new FileOutputStream(toFile);
				BufferedOutputStream bufferOut = new BufferedOutputStream(fos, 2024);
				int c;
				while ((c = bufferIn.read()) != -1) {
					bufferOut.write(c);
				}
				bufferIn.close();
				fis.close();
				bufferOut.close();
				fos.close();
				return true;
			}catch(Exception e) {
				return false;
			}finally{
				fromFile = null;
				toFile = null;
			}
		}
		return true;
	}

	/**
	 * 复制文件,可控制是否强制搬移
	 * 
	 * @param from
	 *            源文件
	 * @param to
	 *            目录文件
	 * @param force
	 *            是否强制搬移 true(强制) false(不强制)
	 */
	public static boolean  cp(String from, String to, boolean force) {
		if(StringUtils.isNotEmpty(to)){
			File toFile = new File(to);
			if (force && toFile.exists()) {
				if(toFile.delete()){
					return cp(from, to);
				}else{
					return false;
				}
			}
			toFile = null;
		}
		return true;
	}
	/**
	 * 删除磁盘文件
	 * 
	 * @param fileName
	 *            要删除的文件名
	 */
	public static boolean delete(String fileName) {
		if(StringUtils.isEmpty(fileName)){
			return true;
		}else{
			File file = new File(fileName);
			if (file.exists()) {
				return file.delete();
			}
			file = null;
		}
		return true;
	}	
	/**
	 * 将对象序列化入文件
	 * @param fileName
	 * @param obj
	 * @return
	 */
	public static boolean writeObjectToFile(String fileName,Object obj){
		if(StringUtils.isEmpty(fileName) || obj==null){
			return false;
		}
		mkdirs(getFilePath(fileName));
		try{
			ObjectOutputStream in = new ObjectOutputStream(new FileOutputStream(fileName));
	    	in.writeObject(obj);
	        in.close();
	        return true;
		}catch(Exception e){
			return false;
		}
	}
	/**
	 * 将序列化的文件读入对象
	 * @param fileName
	 * @return
	 */
	public static Object readObjectFromFile(String fileName){
		Object obj=null;
		if(StringUtils.isEmpty(fileName)){
			return obj;
		}
		try{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
	    	obj=in.readObject();
	        in.close();
		}catch(Exception e){
		}
		return obj;
	}
}
