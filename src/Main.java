import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;

public class Main {

	static final String PAGE_TO_CREATE = "Video viewer.html";

	public static void main(String[] args) throws IOException {

		String sourceFile = chooseSource();

		System.out.println("You chose to use this file: " + sourceFile);

		createHTML(sourceFile);

		openInBrowser();

	}

	private static void openInBrowser() {

		try {
			File htmlFile = new File(PAGE_TO_CREATE);
			Desktop.getDesktop().browse(htmlFile.toURI());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String chooseSource() {

		JFileChooser chooser = new JFileChooser();

		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		// FileNameExtensionFilter filter = new FileNameExtensionFilter(
		// "folders and video", "mp4", "webm", "flv", "wmv");
		// chooser.setFileFilter(filter);

		int returnVal = chooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
		}

		return chooser.getSelectedFile().getAbsolutePath();

	}

	private static void createHTML(String sourceRoute) {

		FileWriter fw;
		try {
			fw = new FileWriter(PAGE_TO_CREATE);

			fw.write("<!DOCTYPE html> <html><body style=\"background-color: #cccccc;\">");
			System.out.println("<!DOCTYPE html> <html><body style=\"background-color: #cccccc;\">");

			printVideos(sourceRoute, fw);

			fw.write("</body> </html>");
			System.out.println("</body> </html>");

			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void printVideos(String sourceRoute, FileWriter fw) throws IOException {

		/*//Example
		 * <!DOCTYPE html> <html> <body style="background-color: #cccccc;">
		 * 
		 * <video width="400" controls> <source src="output.webm"
		 * type="video/webm"> <source src="mov_bbb.ogg" type="video/ogg"> Your
		 * browser does not support HTML5 video. </video>
		 * 
		 * </body> </html>
		 */

		File folder = new File(sourceRoute);

		if (folder.isDirectory()) {

			File[] files = folder.listFiles();

			for (int i = 0; i < files.length; i++) {

				if (files[i].isFile()) {

					String path = files[i].getAbsolutePath();

					String extension = getExtension(files[i].getAbsolutePath());

					String videoHTML = "<video width=\"400\" controls>" + "" + " <source src=\"" + path + "\""
							+ " type=\"video/" + extension + "\">"

							+ " <source src=\"mov_bbb.ogg\" type=\"video/ogg\"> " + " </video>";
					
					System.out.println(videoHTML);
					fw.write(videoHTML);
				}

			}

		}

	}

	private static String getExtension(String absolutePath) {

		String extension = null;

		int i = absolutePath.lastIndexOf('.');
		if (i > 0) {
			extension = absolutePath.substring(i + 1);
		}

		return extension;
	}

}
