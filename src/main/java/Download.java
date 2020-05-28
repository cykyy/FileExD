import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Download {
    private String address;
    private String url;

    public void setAddress(String address, String url){
        this.address=address;
        this.url=url;
    }

    public void downloadFileFromUrlUsingNio() {
        FileReadWrite frw = new FileReadWrite();
        URL urlObj = null;
        ReadableByteChannel rbcObj = null;
        FileOutputStream fOutStream  = null;

        // Checking If The File Exists At The Specified Location Or Not
        Path filePathObj = Paths.get(address);
        boolean fileExists = Files.exists(filePathObj);
        //log saving
        frw.writeInFile(address+" ::: "+url,"./History/failed_download.txt", true);
        if(fileExists) {
            try {
                urlObj = new URL(url);
                rbcObj = Channels.newChannel(urlObj.openStream());
                fOutStream = new FileOutputStream(address);
                System.out.println("Downloading file...");

                fOutStream.getChannel().transferFrom(rbcObj, 0, Long.MAX_VALUE);
                System.out.println("! File Successfully Downloaded From The Url !");
                System.out.println();
                frw.writeInFile(address+" ::: "+url,"./History/download_completed.txt", true);
                frw.finalizer(address+" ::: "+url,"./History/failed_download.txt"); //saving failed download
            } catch (IOException ioExObj) {
                System.out.println("Problem Occurred While Downloading The File= " + ioExObj.getMessage());
            } finally {
                try {
                    if(fOutStream != null){
                        fOutStream.flush(); //added by Ray later
                        fOutStream.close();
                    }
                    if(rbcObj != null) {
                        rbcObj.close();
                    }
                } catch (IOException ioExObj) {
                    System.out.println("Problem Occurred While Closing The Object= " + ioExObj.getMessage());
                }
            }
        } else {
            System.out.println("File Not Present! Please Check!");
        }
    }
}
