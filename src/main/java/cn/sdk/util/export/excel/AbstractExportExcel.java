package cn.sdk.util.export.excel;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.sdk.util.export.IExportFile;
public abstract class AbstractExportExcel implements IExportFile{
	private HttpServletResponse response;
	private String fileName;
	private String sheetName;
	private List<?> list;
	public AbstractExportExcel(HttpServletResponse response,String fileName, String sheetName,List<?> list) {
		this.fileName = fileName;
		this.sheetName = sheetName;
		this.response = response;
		this.list = list;
	}
	@Override
	public void export() throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(new String(sheetName.getBytes(),"utf-8"));
		createHead(sheet);
		createBody(sheet,list);
		fileName = URLEncoder.encode(fileName, "UTF-8");
		response.reset();
		OutputStream outputStream = response.getOutputStream();
		wb.write(outputStream);
		response.addHeader("Content-Disposition", "attachment;filename="
				+ fileName);
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		outputStream.flush();
	}
	public abstract void createHead(HSSFSheet sheet);
	public abstract void createBody(HSSFSheet sheet,List list);
}
