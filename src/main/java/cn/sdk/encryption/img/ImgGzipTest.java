package cn.sdk.encryption.img;

import java.io.File;

public class ImgGzipTest {
	public static void main(String[] args) {
		/**
		 * d://3.jpg 源图片
		 * d://31.jpg 目标图片
		 * 压缩宽度和高度都是1000
		 * 
		 */
		/*System.out.println("压缩图片开始...");
		File srcfile = new File("d://1496280063541t6bMH.jpg");
		System.out.println("压缩前srcfile size:" + srcfile.length());*/
		
		String imgsPath = "D:/img";
		File file = new File(imgsPath);
		if(file.exists()){
			String[] file1s = file.list();
			for(String file1 : file1s){
				File file2 = new File(imgsPath +"/"+ file1);
				if(file2.isDirectory()){
					String[] imgFiles = file2.list();
					for(String imgFile : imgFiles){
						String imgPath = file2  +"/"+ imgFile;
						ImgGzip.reduceImg(imgPath, imgPath, 806, 454,null);
					}
				}
			}
		}
		
		/*ImgGzip.reduceImg("d://1496280063541t6bMH.jpg", "d://1496280063541t6bMH.jpg", 806, 454,null);
		File distfile = new File("d://1496280063541t6bMH.jpg");
		System.out.println("压缩后distfile size:" + distfile.length());*/
	
	}
}
