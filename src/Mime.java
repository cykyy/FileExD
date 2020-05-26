import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Mime {

    public boolean getMimeTypeIsFile(String uri) {
        try {
            URLConnection urlConnection = new URL(uri).openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
            String mimeType = urlConnection.getContentType();
            //System.out.println("######Mime Type: "+mimeType);
            if (mimeType!=null) {
                if (mimeType.contains("text/html")) {
                    //System.out.println(" :::It's a folder:::");
                    return false;
                } else {
                    //System.out.println(" :::It's a file::: ");
                    return true;
                }
            } else {
                System.out.println("Mime is Null!");
                return true;
            }
        } catch (NullPointerException | MalformedURLException mimeException){
            mimeException.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
