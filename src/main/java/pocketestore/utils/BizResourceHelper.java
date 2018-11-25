package pocketestore.utils;

import java.io.*;

public class BizResourceHelper {
    private static String rootPathKey = "PES_Root";

    public static String getProductImagePath() throws Exception {
        String imageWebFolder = "images/product";
        String productImageFolderPath = System.getProperty(rootPathKey) + imageWebFolder;
        try {
            File targetDir = new File(productImageFolderPath);
            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }
        } catch (Exception ex) {
            throw ex;
        }
        return productImageFolderPath;
    }

    public static boolean deleteProductImage(String imgPath) {
        try {
            String productFolder = BizResourceHelper.getWebRootPath();
            String imgFullPath = productFolder + "/" + imgPath;
            File imgFile = new File(imgFullPath);
            if (imgFile.exists()) {
                imgFile.delete();
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static String getWebRootPath() {
        return System.getProperty(rootPathKey);
    }
}
