import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

public class ExtractLinks extends LinkParser {
    @Override
    public ArrayList getLink() throws IOException {
        Document document = org.jsoup.Jsoup.connect(url).get();
        Elements links = document.select("a[href]");
        //first list
        ArrayList<String> setList = new ArrayList<String>();
        //final list
        ArrayList<ArrayList> listOfLinks = new ArrayList<ArrayList>();
        for (Element lin : links){
            if(lin.attr("abs:href").contains(domain)) {
                if (!lin.attr("abs:href").contains("/?C=") ) {
                    setList.add(lin.attr("abs:href"));
                    setList.add(name + "/" + lin.text());
                    listOfLinks.add(setList);
                    setList = new ArrayList();
                }
            }
        }
        // System.out.println(listOfLinks.get(2).get(0));
        // System.out.println(linkList.get(1));
        return listOfLinks;
    }
}
