package qa.framework.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import qa.framework.report.Report;

public class FileManager {

	private static Logger log = LoggerHelper.getLogger(FileManager.class);

	private static FileManager objFileManager = null;

	private FileManager() {

	}

	// Singleton
	public synchronized static FileManager getFileManagerObj() {
		return (objFileManager == null) ? new FileManager() : objFileManager;
	}

	/**
	 * public util to delete all the files in a folder
	 * 
	 * @author BathriYO
	 * @param folderPath
	 */
	public static void deleteAllFilesInFolder(String folderPath) {
		String status = "FAIL";
		try {
			File folder = new File(folderPath);

			/* checking if given path if of directory and folder exists or not */
			if (folder.exists() && folder.isDirectory()) {
				File[] listFiles = folder.listFiles();

				/* checking if file with in the folder exists or not */
				if (listFiles.length > 0) {
					for (File file : listFiles) {
						file.delete();
					}
				}

			}

			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Delete All Files");
			Report.printStatus(status);
		}
	}

	/**
	 * public util to delete file
	 * 
	 * @author BathriYO
	 * @param filePath
	 */
	public static void deleteFile(String filePath) {
		String status = "FAIL";
		try {
			File file = new File(filePath);
			file.delete();
			status = "PASS";
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		} finally {
			Report.printOperation("Delete File");
			Report.printStatus(status);
		}
	}

	/**
	 * public util to get absolute download filepath specified in config.properties
	 * 
	 * @return absolutepath
	 */
	public static String downloadFolderFilePath() {
		PropertyFileUtils properties = new PropertyFileUtils(System.getProperty("user.dir") + "/config.properties");
		File folder = new File(properties.getProperty("downloadFolder"));
		if (!folder.exists()) {
			folder.mkdir();
		}
		return folder.getAbsolutePath();
	}

	/**
	 * public util to get absolute download filepath user specified
	 * 
	 * @return string folderpath
	 */
	public static String downloadFolderFilePath(String folderPath) {
		File folder = new File(folderPath);
		if (!folder.exists()) {
			folder.mkdir();
		}
		return folder.getAbsolutePath();
	}

	/**
	 * public utils to check if file exists or not
	 * 
	 * @author BathriYO
	 * @param filePath
	 * @return boolean
	 */
	public static boolean isFileExists(String filePath) {
		return new File(filePath).exists();
	}

	/**
	 * public file util to check if file exist or not
	 * 
	 * @author BathriYO
	 * @param filepath
	 * @return float
	 */
	public static float getFileSize(String filePath) {
		return new File(filePath).length();

	}

	/**
	 * write to file
	 * 
	 * @param filePath
	 * @param writestr
	 */
	public static void write(String filePath, String writestr) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			writer.write(writestr);
			writer.close();

		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
	}

	/**
	 * Append character string in existing file
	 * 
	 * @author BathriYO
	 * @param filePath
	 * @param writestr
	 */
	public static void append(String filePath, String writestr) {
		try {
			Files.write(Paths.get(filePath), writestr.getBytes(), StandardOpenOption.APPEND);
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
	}

	/**
	 * Read file and convert it to string
	 * 
	 * @param filePath
	 * @return String
	 */
	public static String readFile(String filePath) {
		StringBuffer content = new StringBuffer();
		BufferedReader bufferedReader = null;
		if (isFileExists(filePath)) {
			try {
				bufferedReader = new BufferedReader(new FileReader(filePath));
				String line = null;

				while ((line = bufferedReader.readLine()) != null) {
					content.append(line).append("/n");
				}
			} catch (Exception e) {
				ExceptionHandler.handleException(e);
				e.printStackTrace();
			} finally {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					ExceptionHandler.handleException(e);
					e.printStackTrace();
				}
			}
		}
		return content.toString();
	}

	/**
	 * creating a new file in a given format
	 * 
	 * @param filePath
	 * @return boolean
	 */
	public static boolean createFile(String filePath) {
		File file = new File(filePath);
		try {
			return file.createNewFile();
		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}
		return false;
	}

	/**
	 * Search file if exists in specific directory
	 * 
	 * @author 10650956
	 * @param dirPath  : String
	 * @param fileName : String
	 * @return String
	 * @throws FileNotFoundException
	 */
	public static String searchFile(String dirPath, String fileName) {

		File dir = new File(dirPath);

		File[] listFiles = dir.listFiles();

		String filePath = null;

		for (File file : listFiles) {

			if (file.isDirectory()) {

				filePath = searchFile(file.getAbsolutePath(), fileName);

				if (filePath != null) {
					break;
				}

			} else {
				if (file.getName().equals(fileName)) {

					filePath = file.getAbsolutePath();
					break;
				}
			}
		}

		return filePath;
	}

	/**
	 * Code from String to Base64
	 * 
	 * @author 10650956
	 * @param value : String
	 * @return String
	 */
	public static String base64Encode(String value) {
		return Base64.getEncoder().encodeToString(value.getBytes());
	}

	/**
	 * Decode from Base64 to String
	 * 
	 * @author 10650956
	 * @param encodedValue : String
	 * @return String
	 */
	public static String base64Decode(String encodedValue) {
		byte[] decodedBytes = Base64.getDecoder().decode(encodedValue);
		return new String(decodedBytes);
	}

	/**
	 * Copy a file from source to destination
	 * 
	 * @author 10650956
	 * @param src
	 * @param dest
	 */
	public static void copy(String src, String dest) {
		try {
			com.google.common.io.Files.copy(new File(src), new File(dest));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Convert BufferedImage to byte[]
	 * 
	 * @author 10650956
	 * @param image
	 * @return byte[]
	 */
	public static byte[] bufferImageToByte(BufferedImage image) {
		ByteArrayOutputStream baos = null;
		byte[] imageInByte = null;
		try {

			baos = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", baos);
			baos.flush();
			imageInByte = baos.toByteArray();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return imageInByte;

	}

	/**
	 * Upload file using auto it
	 * 
	 * @author 10650956
	 * @param absoluteFilePath
	 * @throws IOException
	 */
	public static void fileUploadUsingAutoIt(String absoluteFilePath) throws IOException {
		/*
		 * wrapping filePath into double quote to over come space in between directory
		 * or file name
		 */
		File file = new File(absoluteFilePath);
		File parentFile = file.getParentFile();
		String parent = parentFile.getAbsolutePath();

		/* adding '\' at the end of parent dir path */
		parent = "\"" + parent + "\\" + "\"";
		log.info("Upload file Parent path: " + parent);

		String fileName = file.getName();
		fileName = "\"" + fileName + "\"";
		log.info("Upload file name: " + fileName);

		// doesn't work
		// Runtime.getRuntime().exec("./src/main/resources/exes/FileUpload(x64).exe"+"
		// "+parent+" "+fileName);

		List<String> cmds = Arrays.asList("cmd.exe", "/C", "start", "./src/main/resources/exes/FileUpload(x64).exe",
				parent, fileName);

		ProcessBuilder builder = new ProcessBuilder(cmds);
		Process start = builder.start();
		Action.pause(10000);
		start.destroyForcibly();

	}

	/**
	 * Get file created time
	 * 
	 * @author 10650956
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	public static Date getFileCreatedDate(String filepath) throws IOException {

		Path file = Paths.get(filepath);

		BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

		return CalendarUtils.millisecondToDate(attr.creationTime().to(TimeUnit.MILLISECONDS));
	}

	/**
	 * Get file last modified time
	 * 
	 * @author 10650956
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	public static Date getFileModifiedDate(String filepath) throws IOException {

		Path file = Paths.get(filepath);

		BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

		return CalendarUtils.millisecondToDate(attr.lastModifiedTime().to(TimeUnit.MILLISECONDS));
	}

	/**
	 * Note: to close cmd.exe add 'exit 0' as next line command
	 * 
	 * @author 10650956
	 * @param batchFilePath
	 * @return
	 * @throws IOException
	 */
	public static Process runBatchFile(String batchFilePath) throws IOException {
		String[] command = { "cmd.exe", "/C", "Start", batchFilePath };
		File bat = new File(batchFilePath);
		String dirPath = bat.getParent();

		ProcessBuilder pb = new ProcessBuilder(command);
		pb.directory(new File(dirPath));
		return pb.start();

	}

	/* Creates zip of a directory */

	public void zipDir(String dirPath, String zipFileName) {

		try {

			boolean isZipRequired = false;

			File dir = new File(dirPath);

			/* determining if there are files in the dir, provided */

			for (File file : dir.listFiles()) {
				if (!file.getName().endsWith(".zip")) {
					isZipRequired = true;
					break;
				}
			}
			/* Zip will only happen if there are files in the dir. */

			if (isZipRequired) {

				FileOutputStream fos = new FileOutputStream(dirPath + "/" + zipFileName + ".zip");
				ZipOutputStream zipOut = new ZipOutputStream(fos);
				File[] allFiles = dir.listFiles();
				byte[] bytes = new byte[1024];

				for (File file : allFiles) {

					if (!file.getName().endsWith(".zip")) {
						FileInputStream fis = new FileInputStream(file);
						ZipEntry zipEntry = new ZipEntry(file.getName());

						zipOut.putNextEntry(zipEntry);

						int length;
						while ((length = fis.read(bytes)) >= 0) {
							zipOut.write(bytes, 0, length);

						}
						fis.close();

					}

				}
				zipOut.closeEntry();
				zipOut.close();
				fos.close();

				log.info("Directory zipped.");
			}

		} catch (Exception e) {

			log.info("Excpetion", ((Throwable) e));

		}
	}

}
