/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.io.IOException;
import jakarta.servlet.ServletContext;

/**
 *
 * @author zainr
 */


public class FileUploadUtil {

    private static final String UPLOAD_DIRECTORY = "C:/Users/zainr/Documents/NetBeansProjects/Mega_City_Cab Booking System/web/Uploads/";

    public static String uploadFile(Part filePart, String folderName) throws IOException {
        String fileName = filePart.getSubmittedFileName();

        if (fileName == null || fileName.isEmpty()) {
            return null; // Ensure null is returned if no file is uploaded
        }

        // Create folder if it doesn't exist
        File uploadDir = new File(UPLOAD_DIRECTORY + folderName);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        // Save the file
        String filePath = uploadDir + File.separator + fileName;
        filePart.write(filePath);

        // Log upload path for debugging
        System.out.println("File uploaded to: " + filePath);

        // Return relative path for database storage
        return "Uploads/" + folderName + "/" + fileName;
    }
}
