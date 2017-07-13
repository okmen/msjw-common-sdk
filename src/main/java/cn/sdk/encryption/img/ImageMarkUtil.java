package cn.sdk.encryption.img;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import cn.sdk.util.DateUtil2;

//import com.founder.cms.watermark.util.ImageMarkLogoUtil;

/**
 * 
 * 生成水印
 * 
 */
public class ImageMarkUtil {
	private static final String PICTRUE_FORMATE_JPG = "jpg";
	
	/** 水印透明度 */
	private static float alpha = 0.5f;
	/** 水印图片旋转角度 */
	private static double degree = 0f;
	private static int interval = 0;

	/**
	 * 设置水印参数，不设置就使用默认值
	 * 
	 * @param alpha
	 *            水印透明度
	 * @param degree
	 *            水印图片旋转角度 *
	 * @param interval
	 *            水印图片间隔
	 */
	public static void setImageMarkOptions(float alpha, int degree,
			int interval) {
		if (alpha != 0.0f) {
			ImageMarkUtil.alpha = alpha;
		}
		if (degree != 0f) {
			ImageMarkUtil.degree = degree;
		}
		if (interval != 0f) {
			ImageMarkUtil.interval = interval;
		}

	}

	/**
	 * 给图片添加水印图片
	 * 
	 * @param waterImgPath
	 *            水印图片路径
	 * @param srcImgPath
	 *            源图片路径
	 * @param targerPath
	 *            目标图片路径
	 */
	public static void waterMarkByImg(String waterImgPath, String srcImgPath,String targerPath) throws Exception {
		waterMarkByImg(waterImgPath, srcImgPath, targerPath, 0);
	}

	/**
	 * 给图片添加水印图片
	 * 
	 * @param waterImgPath
	 *            水印图片路径
	 * @param srcImgPath
	 *            源图片路径
	 * @param targerPath
	 *            目标图片路径
	 */
	public static void waterMarkByImg(String waterImgPath, String srcImgPath) {
		try {
			waterMarkByImg(waterImgPath, srcImgPath, srcImgPath, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 给图片添加水印图片、可设置水印图片旋转角度
	 * 
	 * @param waterImgPath
	 *            水印图片路径
	 * @param srcImgPath
	 *            源图片路径
	 * @param targerPath
	 *            目标图片路径
	 * @param degree
	 *            水印图片旋转角度
	 */
	public static void waterMarkByImg(String waterImgPath, String srcImgPath,
			String targerPath, double degree) throws Exception {
		OutputStream os = null;
		try {

			Image srcImg = ImageIO.read(new File(srcImgPath));

			BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
					srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

			// 1、得到画笔对象
			Graphics2D g = buffImg.createGraphics();

			// 2、设置对线段的锯齿状边缘处理
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg
					.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
			// 3、设置水印旋转
			if (0 != degree) {
				g.rotate(Math.toRadians(degree),
						(double) buffImg.getWidth() / 2, (double) buffImg
								.getHeight() / 2);
			}

			// 4、水印图片的路径 水印图片一般为gif或者png的，这样可设置透明度
			ImageIcon imgIcon = new ImageIcon(waterImgPath);

			// 5、得到Image对象。
			Image img = imgIcon.getImage();

			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));

			// 6、水印图片的位置
			for (int height = interval + imgIcon.getIconHeight(); height < buffImg
					.getHeight(); height = height +interval+ imgIcon.getIconHeight()) {
				for (int weight = interval + imgIcon.getIconWidth(); weight < buffImg
						.getWidth(); weight = weight +interval+ imgIcon.getIconWidth()) {
					g.drawImage(img, weight - imgIcon.getIconWidth(), height
							- imgIcon.getIconHeight(), null);
				}
			}
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			// 7、释放资源
			g.dispose();

			// 8、生成图片
			os = new FileOutputStream(targerPath);
			ImageIO.write(buffImg, "JPG", os);

			System.out.println("图片完成添加水印图片");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != os)
					os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取字符长度，一个汉字作为 1 个字符, 一个英文字母作为 0.5 个字符
	 * 
	 * @param text
	 * @return 字符长度，如：text="中国",返回 2；text="test",返回 2；text="中国ABC",返回 4.
	 */
	public static int getLength(String text) {
		int textLength = text.length();
		int length = textLength;
		for (int i = 0; i < textLength; i++) {
			if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
				length++;
			}
		}
		return (length % 2 == 0) ? length / 2 : length / 2 + 1;
	}
	
	/**
	 * 添加文字水印
	 * 
	 * @param targetImg
	 *            目标图片路径，如：C://myPictrue//1.jpg
	 * @param pressText
	 *            水印文字， 如：中国证券网
	 * @param fontName
	 *            字体名称， 如：宋体
	 * @param fontStyle
	 *            字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
	 * @param fontSize
	 *            字体大小，单位为像素
	 * @param color
	 *            字体颜色
	 * @param x
	 *            水印文字距离目标图片左侧的偏移量，如果x<0, 则在正中间
	 * @param y
	 *            水印文字距离目标图片上侧的偏移量，如果y<0, 则在正中间
	 * @param alpha
	 *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
	 */
	public static void pressText(String targetImg, String pressText, String fontName, int fontStyle, int fontSize,
			Color color, int x, int y, float alpha) {
		try {
			File file = new File(targetImg);

			Image image = ImageIO.read(file);
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufferedImage.createGraphics();
			g.drawImage(image, 0, 0, width, height, null);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setColor(color);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

			int width_1 = fontSize * getLength(pressText);
			int height_1 = fontSize;
			int widthDiff = width - width_1;
			int heightDiff = height - height_1;
			if (x < 0) {
				x = widthDiff / 2;
			} else if (x > widthDiff) {
				x = widthDiff;
			}
			if (y < 0) {
				y = heightDiff / 2;
			} else if (y > heightDiff) {
				y = heightDiff;
			}

			g.drawString(pressText, x, y + height_1);
			g.dispose();
			ImageIO.write(bufferedImage, PICTRUE_FORMATE_JPG, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {

		System.out.println("..添加水印图片开始...");
		/**
		 * watermarkPath 水印图片地址 加水印图片地址 上传成功后文件地址
		 */
		//修改默认参数
		ImageMarkUtil.setImageMarkOptions(0.0f, 0,60);
		String watermarkPath = "d:/22.jpg";  //测试水印图片
        String imgPath = "d:/14958633330244TG0d.jpg";   //测试需加水印图片
		ImageMarkUtil.waterMarkByImg(watermarkPath, imgPath);
		
		
		//添加文字水印
		pressText(watermarkPath, DateUtil2.getLastTimeInterval().get(1), "宋体", Font.BOLD, 30, Color.RED, -1, -1, 1.0f);
		//pressText(watermarkPath, "深圳南山区xxxxxx", "宋体", Font.BOLD, 30, Color.YELLOW, 10, 10, 1.0f);
		
		
		System.out.println("..添加水印图片结束...");
	}

}