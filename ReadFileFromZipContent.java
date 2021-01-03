package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReadContentYJFileInZipFile {
	private static Logger log = LogManager.getLogger();

	private ReadContentYJFileInZipFile() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Method to read the file contents in the zip folder Creates
	 * InputStream for the zip entry and read the file using BufferedReader
	 * 
	 * @param fileName - String - Zip file name
	 * @return textContent - String - Returns the content from the content.yj
	 * @throws InterruptedException 
	 */
	public static String readZipFileContent(String fileName) throws InterruptedException {
		String textContent = "";
		String downloadLocation = ReadProperties.getProperties("FileLocationToSaveZipFiles");

		File file = new File(System.getProperty("user.dir") + File.separator + downloadLocation + File.separator + fileName);
		long expirationTimeMillis = System.currentTimeMillis() + 200 * 1000;
		Date expiration = new Date(expirationTimeMillis);
		while (!file.exists() && new Date().before(expiration)) {
			Thread.sleep(30000);
			file = new File(System.getProperty("user.dir") + File.separator + downloadLocation + File.separator + fileName);
		}
		try (ZipFile zipFile = new ZipFile(file)) {
			InputStream stream = zipFile.getInputStream(zipFile.getEntry("fileName"));
			textContent = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)).lines()
					.collect(Collectors.joining("\n"));
			log.info("Successfully collected the text from content.yj file");
		} catch (IOException e) {
			log.error("Exception occurred while reading the content.yj file in Zip ", e);
		}

		return textContent;
	}

}
