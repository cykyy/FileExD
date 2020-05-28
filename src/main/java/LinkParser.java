import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
public class LinkParser {
    protected String domain;
    protected String url;
    protected String name;

    public void setLink(String domain, String url){
        this.domain=domain;
        this.url=url;
    }

    public void setLink(String domain, String url, String name){
        this.domain=domain;
        this.url=url;
        this.name=name;
    }

    public ArrayList getLink() throws IOException {
        Document document = org.jsoup.Jsoup.connect(url).get();
        Elements links = document.select("a[href]");
        //first list
        ArrayList<String> setList = new ArrayList<String>();
        //final list
        ArrayList<ArrayList> listOfLinks = new ArrayList<ArrayList>();
        for (Element lin : links){
            if(lin.attr("abs:href").contains(domain)) {
                //the next if condition is for supporting Fancyindex-Theme. Uncomment if anything goes wrong.
                if (!lin.attr("abs:href").contains("/?C=") ) {
                    setList.add(lin.attr("abs:href"));
                    setList.add(lin.text());
                    listOfLinks.add(setList);
                    setList = new ArrayList();
                }
            }
        }
        return listOfLinks;
    }
}
