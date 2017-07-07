package cn.sdk.util.export.excel;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.sdk.util.export.IExportFile;

/**
 * 导出  单sheet excel
 * @author gaoxigang
 *
 */
public abstract class AbstractExportExcel4SimpleSheet implements IExportFile{
	private HttpServletResponse response;
	private String fileName;
	protected List<?> list;
	protected HSSFWorkbook wb = new HSSFWorkbook();
	protected HSSFSheet sheet;
	public AbstractExportExcel4SimpleSheet(HttpServletResponse response,String fileName, String sheetName,List<?> list) {
		this.fileName = fileName;
		this.response = response;
		this.list = list;
		try {
			sheet = wb.createSheet(new String(sheetName.getBytes(),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			sheet = wb.createSheet("");
		}
	}
	@Override
	public void export() throws Exception {
		setDefaultSheet();
		createHead();
		createBody();
		fileName = URLEncoder.encode(fileName, "UTF-8");
		response.reset();
		OutputStream outputStream = response.getOutputStream();
		response.addHeader("Content-Disposition", "attachment;filename="
				+ fileName);
		response.setContentType("application/x-msdownload");
		wb.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}
	
	public HSSFCellStyle getDefaultStyle(){
		HSSFCellStyle cellStyle = wb.createCellStyle(); 
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐 
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐 
		// 设置单元格字体 
		HSSFFont font = wb.createFont(); 
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
		font.setFontName("宋体"); 
		font.setFontHeight((short) 300); 
		cellStyle.setFont(font); 
		return cellStyle;
	}
	
	private void setDefaultSheet(){
		sheet.setDefaultColumnWidth(30); 
	}
	
	public abstract void createHead();
	public abstract void createBody();
}
