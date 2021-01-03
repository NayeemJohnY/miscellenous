package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.reporter.converters.ExtentHtmlReporterConverter;

public class ExcelReporter {

	private static Logger log = LogManager.getLogger();
	private Workbook workbook;
	private List<String> listOfHeaders;
	private static final String FONTNAME = "Cambria";

	public ExcelReporter() {
		listOfHeaders = Arrays.asList("Tests", "Status");
		initializeWorkbookAndAddReportHeaders();
	}

	/**
	 * Get the list of Test in HTML Report using ExtentHtmlReporterConverter
	 * @return List<Test>
	 */
	private List<Test> getListOfTestFromHTMLReport() {
		ExtentHtmlReporterConverter converter = new ExtentHtmlReporterConverter(HTMLReporter.HTML_REPORT_PATH);
		return converter.parseAndGetModelCollection();
	}

	/**
	 * Method to parse the HTML report content to the Excel
	 */
	public void parseHTMLReportToExcel() {
		List<Test> listOfTests = getListOfTestFromHTMLReport();
		addTestDetailsToWorkbook(listOfTests);
		writeReportStepsToExcel();
	}

	/**
	 * Method to initialize the workbook and add Report Headers
	 */
	private void initializeWorkbookAndAddReportHeaders() {
		workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		Row row = sheet.createRow(0);

		// Create cell style for headers
		CellStyle cellStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName(FONTNAME);
		font.setBold(true);
		cellStyle.setFont(font);
		cellStyle.setFillForegroundColor(HSSFColorPredefined.GREY_40_PERCENT.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// Add Headers
		for (String header : listOfHeaders) {
			setValueInCell(row, header, cellStyle);
		}

		log.info("Initialized workbook with report headers");
	}

	/**
	 * Method to write the report steps to the Excel File
	 */
	private void writeReportStepsToExcel() {
		String outputFilePath = new File(HTMLReporter.HTML_REPORT_PATH).getParent() + "/ExcelReport.xlsx";

		try (FileOutputStream outputStream = new FileOutputStream(outputFilePath)) {
			workbook.write(outputStream);
			workbook.close();
		} catch (IOException e) {
			log.error("Exception occurred while writing report steps to the Excel!", e);
			throw new IllegalStateException("Exception occurred while writing report steps to the Excel Workbook!", e);
		}

		log.info("Excel Report created at location : {}", outputFilePath);
	}

	/**
	 * Method to add test details - test Name, status and logs to workbook
	 * @param listOfTests - List<Test>
	 */
	private void addTestDetailsToWorkbook(List<Test> listOfTests) {
		Sheet sheet = workbook.getSheetAt(0);
		for (Test parentTest : listOfTests) {
			// Add test case header
			addTestHeader(parentTest, sheet);
			// Map logs to the parent test
			addLogsForTest(parentTest.getName(), parentTest.getLogContext().getAll(), sheet);
			for (Test testNode : parentTest.getNodeContext().getAll()) {
				// Add nested node test case header
				addTestHeader(testNode, sheet);
				// Map logs to the parent test. since parent test has details of CGI and type of item
				addLogsForTest(parentTest.getName(), testNode.getLogContext().getAll(), sheet);
			}
		}

		log.info("Added Test Details to the Workbook");

		// Adjust the columns width to fit with contents
		for (int i = 0; i < listOfHeaders.size(); i++) {
			if (listOfHeaders.get(i).equals("Test Logs")) {
				sheet.setColumnWidth(2, 100 * 256); // Adjust the column width for logs at max 100 characters in line.
			} else {
				sheet.autoSizeColumn(i);
			}
		}
	}

	/**
	 * Method to add logs for the test
	 * @param testName   - Test
	 * @param reportLogs - List<Log>
	 * @param sheet      - Sheet
	 */
	private void addLogsForTest(String testName, List<Log> reportLogs, Sheet sheet) {
		for (Log reportLog : reportLogs) {
			Row row = sheet.createRow(sheet.getLastRowNum() + 1);

			// Add Test Name
			setValueInCell(row, testName);

			// Add Status
			setStatus(row, reportLog.getStatus());

			// Add log details
			String logDetails = reportLog.getDetails().replaceAll("\\<\\/*br\\/*\\>", "\n").replaceAll("\\<.*?\\>", "")
					.replace("&amp;", "&");
			setValueInCell(row, logDetails);

			// Add report link
			addReportLink(row);
		}
	}

	/**
	 * Method to set the status with the color coding
	 * @param row    - Row
	 * @param status - Status
	 */
	private void setStatus(Row row, Status status) {
		Font font = workbook.createFont();

		// Set font color based on Status
		switch (status) {
		case PASS:
			font.setColor(HSSFColorPredefined.GREEN.getIndex());
			break;

		case FAIL:
			font.setColor(HSSFColorPredefined.RED.getIndex());
			break;

		case WARNING:
			font.setColor(HSSFColorPredefined.GOLD.getIndex());
			break;

		case ERROR:
			font.setColor(HSSFColorPredefined.DARK_RED.getIndex());
			break;

		case SKIP:
			font.setColor(HSSFColorPredefined.ORANGE.getIndex());
			break;

		default:
			font.setColor(HSSFColorPredefined.BLUE.getIndex());
			break;
		}

		font.setFontName(FONTNAME);
		font.setBold(true);
		CellStyle statusStyle = workbook.createCellStyle();
		statusStyle.setFont(font);
		setValueInCell(row, status.toString().toUpperCase(), statusStyle);
	}

	/**
	 * Add a hyperlink for the extent HTML report
	 * @param row - Row
	 */
	private void addReportLink(Row row) {

		// Create an hyperlink
		Hyperlink link;
		String linkText = "";

		// For Jenkins : The linkText and link will be the report URL of current job build
		if (System.getenv("JENKINS_URL") != null) {
			link = workbook.getCreationHelper().createHyperlink(HyperlinkType.URL);
			if (System.getenv("BUILD_URL") != null) {
				linkText = System.getenv("BUILD_URL") + "ReportName";
				link.setAddress(linkText);
			}
		} else {
			// For Local : The linkText and link will be the File URL of the current result.hmtl under test_results
			link = workbook.getCreationHelper().createHyperlink(HyperlinkType.FILE);
			linkText = new File(HTMLReporter.HTML_REPORT_PATH).getAbsolutePath();
			link.setAddress(new File(HTMLReporter.HTML_REPORT_PATH).getName());
		}

		// Create cell style for hyperlink
		CellStyle hlinkstyle = workbook.createCellStyle();
		Font hlinkfont = workbook.createFont();
		hlinkfont.setUnderline(Font.U_SINGLE);
		hlinkfont.setFontName(FONTNAME);
		hlinkfont.setColor(HSSFColorPredefined.BLUE.getIndex());
		hlinkstyle.setFont(hlinkfont);

		// Set value in cell with hyperlink style and add hyperlink
		setValueInCell(row, linkText, hlinkstyle);
		Cell cell = row.getCell(row.getLastCellNum() - 1);
		cell.setHyperlink(link);

	}

	/**
	 * Method to add value in a cell with cell style
	 * @param row       - Row
	 * @param value     - String
	 * @param cellStyle - CellStyle
	 */
	private void setValueInCell(Row row, String value, CellStyle cellStyle) {
		int cellNum = row.getLastCellNum() == -1 ? 0 : row.getLastCellNum();
		Cell cell = row.createCell(cellNum, CellType.STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}

	/**
	 * Method to add value in a cell
	 * @param row   - Row
	 * @param value - String
	 */
	private void setValueInCell(Row row, String value) {
		int cellNum = row.getLastCellNum() == -1 ? 0 : row.getLastCellNum();
		Cell cell = row.createCell(cellNum, CellType.STRING);

		// Set Cell with style
		CellStyle cellStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName(FONTNAME);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cell.setCellStyle(cellStyle);

		cell.setCellValue(value);
	}

	/**
	 * Method to add the header for the Test
	 * @param test  - Test
	 * @param sheet - Sheet
	 */
	private void addTestHeader(Test test, Sheet sheet) {
		Row row = sheet.createRow(sheet.getLastRowNum() + 1);

		// Set cell Style to bold and update font Name
		CellStyle cellStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName(FONTNAME);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);

		// Set cell foreground color based on test level
		if (test.getLevel() == 0) {
			cellStyle.setFillForegroundColor(HSSFColorPredefined.LIGHT_ORANGE.getIndex());
		} else {
			cellStyle.setFillForegroundColor(HSSFColorPredefined.AQUA.getIndex());
		}
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		setValueInCell(row, test.getName(), cellStyle);
	}
}
