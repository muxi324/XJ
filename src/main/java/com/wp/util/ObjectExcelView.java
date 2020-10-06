package com.wp.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* 导入到EXCEL
* 类名称：ObjectExcelView.java
* 类描述： 
* @author FH
* 作者单位： 
* 联系方式：
* @version 1.0
 */
public class ObjectExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
									  HSSFWorkbook workbook, HttpServletRequest request,
									  HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();
		//String filename = Tools.date2Str(date, "yyyyMMddHHmmss");
		String filename = (String) model.get("filename");

		HSSFSheet sheet;
		HSSFCell cell;
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));

		sheet = workbook.createSheet("sheet1");

		List<String> titles = (List<String>) model.get("titles");
		int len = titles.size();
		HSSFCellStyle headerStyle = workbook.createCellStyle(); //标题样式
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont headerFont = workbook.createFont();    //标题字体
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerFont.setFontHeightInPoints((short) 11);
		headerStyle.setFont(headerFont);
		headerStyle.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);//下边框
		headerStyle.setBorderLeft(HSSFCellStyle.BORDER_DOTTED);//左边框
		headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		short width = 11, height = 25 * 20;
		sheet.setDefaultColumnWidth(width);
		// 产生表格标题行
		HSSFRow rowm = sheet.createRow(0);
		HSSFCell cellTiltle = rowm.createCell(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (len - 1)));


		cellTiltle.setCellStyle(headerStyle);
		String header = (String) model.get("headerName");
		cellTiltle.setCellValue(header);

		for (int i = 0; i < len; i++) { //设置标题
			String title = titles.get(i);
			cell = getCell(sheet, 2, i);
			cell.setCellStyle(headerStyle);
			setText(cell, title);
		}
		sheet.getRow(2).setHeight(height);
		int rowNum=sheet.getLastRowNum();
		System.out.println(rowNum+"--00000000000000");
		//sheet.addMergedRegion(new CellRangeAddress((rowNum+3), (rowNum+3), 0, (len - 1)));
		HSSFCellStyle contentStyle = workbook.createCellStyle(); //内容样式
		contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中
		contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
		contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		List<PageData> varList = (List<PageData>) model.get("varList");
		int varCount = varList.size();
		CellRangeAddress cra =new CellRangeAddress(0, 1, 0, (len-1)); // 起始行, 终止行, 起始列, 终止列
		sheet.addMergedRegion(cra);
		// 使用RegionUtil类为合并后的单元格添加边框
		RegionUtil.setBorderBottom(1, cra, sheet,workbook); // 下边框
		RegionUtil.setBorderLeft(1, cra, sheet,workbook); // 左边框
		RegionUtil.setBorderRight(1, cra, sheet,workbook); // 有边框
		RegionUtil.setBorderTop(1, cra, sheet,workbook); // 上边框
		for (int i = 0; i < varCount; i++) {
			PageData vpd = varList.get(i);
			for (int j = 0; j < len; j++) {
				String varstr = vpd.getString("var" + (j + 1)) != null ? vpd.getString("var" + (j + 1)) : "";
				cell = getCell(sheet, i + 3, j);       // 注意i+3，标题占2行，表头占1行
				cell.setCellStyle(contentStyle);
				setText(cell, varstr);
			}

		}

	}
}

