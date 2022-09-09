package qa.framework.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemProcess {
	
	
	/**
	 * Run batch file using code
	 * 
	 * @author Yogesh Bathri
	 * @param batchFileName
	 * @return
	 * @throws IOException
	 */
	public static Process runBatchFile(String batchFileName) throws IOException {
		
		String userDir = System.getProperty("user.dir");
		String batchFilePath = FileManager.searchFile(userDir, batchFileName);
		
		File batch= new File(batchFilePath);
		String dirPath = batch.getParent();
		
		String[] command = {"cmd.exe","/c","Start","/b",batchFilePath};
		
		ProcessBuilder pb = new ProcessBuilder(command);
		pb.directory(new File(dirPath));
		return pb.start();
		
	}
	
	/**
	 * Write console output in the file.
	 * 
	 * @author Yogesh Bathri
	 * @param p
	 * @param filePath
	 * @throws IOException
	 */
	public static void writeInputStream(Process p, String filePath) throws IOException {
		StringBuilder strBuilder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while((line=reader.readLine())!=null) {
			strBuilder.append(line).append("\n");
			
		}
		
		FileManager.write(filePath, strBuilder.toString());
	}
	
	
	
	/**
	 * @author 10650956
	 * @param processName
	 * @throws IOException
	 */
	public static synchronized void killProcess(String processName) throws IOException {
		Process p = Runtime.getRuntime().exec("tasklist");
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;

		while ((line = reader.readLine()) != null) {
			if (line.contains(processName)) {
				Runtime.getRuntime().exec("taskkill /F /IM" + processName);
			}
		}
	}
	
	/**
	 * Kill java process by identifying Process id using fully qualified class name
	 * @param fullyQualifiedClassName
	 * @throws IOException
	 */
	public static void killJavaProcess(String fullyQualifiedClassName) throws IOException {
		
		String javaHome = System.getenv("JAVA_HOME");
		String javaBin = javaHome+"\\"+"bin";
		String[] command= {"cmd.exe","/C","jps -l"};
		ProcessBuilder pb = new ProcessBuilder(command);
		
		pb.directory(new File(javaBin));
		Process process = pb.start();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;

		while ((line = reader.readLine()) != null) {
			
			if (line.contains(fullyQualifiedClassName)) {
				String processId = line.split(" ")[0];
				try{
					Runtime.getRuntime().exec("taskkill /F /PID" + processId);	
				}catch(Exception e) {
					
				}
			}
		}

		
	}
	
}
