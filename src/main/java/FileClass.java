import java.io.File;
import java.io.IOException;

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
            System.out.println("Hey hey! File already exists!");
        return !file.exists();
    }
}
