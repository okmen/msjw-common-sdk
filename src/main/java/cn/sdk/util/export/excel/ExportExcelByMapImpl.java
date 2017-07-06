package cn.sdk.util.export.excel;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import cn.sdk.util.FiledUtil;
public class ExportExcelByMapImpl extends AbstractExportExcel{
	private LinkedHashMap<String, String> map;
	public ExportExcelByMapImpl(HttpServletResponse response, String fileName,
			String sheetName, List<?> list,LinkedHashMap<String, String> map) {
		super(response, fileName, sheetName, list);
		this.map = map;
	}

	@Override
	public void createHead(HSSFSheet sheet) {
		HSSFRow row = sheet.createRow((int) 0);
		int k = 0;
		for (String key : map.keySet()) {
			HSSFCell cell = row.createCell((short) k);
			cell.setCellValue(key);
			k++;
		}
	}

	@Override
	public void createBody(HSSFSheet sheet, List list) {
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);

				Object t = (Object) list.get(i);

				int cellIndex = 0;
				for (String key : map.keySet()) {
					HSSFCell cell = row.createCell((short) cellIndex);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					Object value = FiledUtil.getProperty(t, map.get(key));
					cell.setCellValue(null == value ? "" : value.toString());
					cellIndex++;
				}
			}
		}
	}
}
