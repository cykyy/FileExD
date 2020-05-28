import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileClass {
    private String address;

    public void setAddress(String address){
        this.address=address;
    }

    public boolean makeFolderFile() throws IOException {
        File file = new File(address);
        if (!file.exists()) {
            if (file.getParentFile() != null) {
                if (!file.getParentFile().exists())
                    System.out.println("Creating parent folder...");
                file.getParentFile().mkdirs();
            }
            System.out.println("Creating file...");
            file.createNewFile();
            return file.exists();
        } else
            System.out.print("File already exists! ");
        return !file.exists();
    }

    public boolean isLocalMatchesRemote(String localAddr, String remoteAddr) throws IOException {
        File localFile = new File(localAddr);
        if (localFile.exists()){
            URL remoteUrl = new URL(remoteAddr);
            HttpURLConnection connectRemote = (HttpURLConnection) remoteUrl.openConnection();
            connectRemote.setRequestMethod("HEAD");
            long remoteFileSize = connectRemote.getContentLengthLong();
            String remoteFileSizeStr= Long.toString(remoteFileSize);

            long localFileSize=localFile.length();
            String localFileSizeStr=Long.toString(localFileSize);

            if (localFileSizeStr.equals(remoteFileSizeStr)) {
                return true;
            }
        connectRemote.disconnect();
        }
        System.out.println("Local file size do not matches with remote file size");
        return false;
    }
}
