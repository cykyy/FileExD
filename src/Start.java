import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Start {
    public static void main(String [] args) {
        Scanner sc = new Scanner(System.in);
        Scanner input1 = new Scanner(System.in);
        String domain;
        String url;
        String name;
        LinkParser obj1 = new LinkParser();
        ExtractLinks obj2 = new ExtractLinks();
        List <ArrayList> rootLinks = new ArrayList<ArrayList>() ;
        List <ArrayList> layerTwoLinks = new ArrayList<ArrayList>();
        List <ArrayList> layerThreeLinks = new ArrayList<ArrayList>();
        List <ArrayList> fileOnlyLinkOne = new ArrayList<ArrayList>();
        Mime mimeObj1 = new Mime();
        FileClass fileObj1 = new FileClass();
        Download downloadObj1 = new Download();

        boolean choice = true;

        while (choice) {
            System.out.println("Welcome to IFM Downloader! This tool simply download files/folders recursively from index based file manager sites. \nCurrently it supports H5ai and Fancyindex based sites. But the tool is upgradable and any contribution is always welcome. Thanks.");
            System.out.println();
            System.out.println("Choose from the Following Options: ");
            System.out.println("--------------------------------------");
            System.out.println("1. Download Files");
            System.out.println("2. Exit");
            System.out.println("--------------------------------------\n");

            System.out.print("You have choosed: ");
            int option = sc.nextInt();

            switch(option){
                case 1:
                    System.out.println("#####################");
                    //Rest of the code goes here...

                    System.out.print("Please enter the URL: ");
                    url=input1.nextLine();
                    domain=url;
                    System.out.println();
                    System.out.print("Where do you want to save files? (eg: Windows: D:\\My Folder, Linux/Mac: /Users/david/My Folder) : ");
                    String rootFolderName = input1.nextLine();

                    //setting and getting for next iteration
                    try {
                        obj1.setLink(domain, url);
                        if (mimeObj1.getMimeTypeIsFile(url)){
                            System.out.println("The inserted link is a file! Please enter a folder link to proceed.");
                            break;
                        }
                        rootLinks = obj1.getLink();
                        //System.out.println(rootLinks);

                    } catch (NullPointerException | IOException rootExcep){
                        rootExcep.printStackTrace();
                    }

                    for (int counter = 0; counter < rootLinks.size(); counter++) {
                        //System.out.println("On loop: "+counter); //Loop Counter
                        //automation code starts from here
                        boolean flag = true;
                        while(flag){
                            url =(String) rootLinks.get(counter).get(0);
                            name = (String) rootLinks.get(counter).get(1);
                            System.out.println("Extracting sub-link: "+url);
                            // if a link mime type is a file, add to list and break the most inner loop to continue to check the next link
                            if (mimeObj1.getMimeTypeIsFile(url)) {
                                fileOnlyLinkOne.add(rootLinks.get(counter));
                                break;
                            }

                            obj2.setLink(domain,url,name);
                            //on loop getting more links
                            try {
                                layerTwoLinks = obj2.getLink();
                            }catch (NullPointerException | IOException l2Excp){
                                l2Excp.printStackTrace();
                            }
                            //System.out.println("Scraped Array Links:  "+layerTwoLinks);
                            //mime type coding
                            try {
                                for (int counterTwo = 1; counterTwo < layerTwoLinks.size(); counterTwo++) {
                                    //System.out.print(layerTwoLinks.get(counterTwo)); //printing list with location+name
                                    boolean checkMime = mimeObj1.getMimeTypeIsFile((String) layerTwoLinks.get(counterTwo).get(0));
                                    if (checkMime)
                                        fileOnlyLinkOne.add(layerTwoLinks.get(counterTwo));
                                    else
                                        layerThreeLinks.add(layerTwoLinks.get(counterTwo));
                                }
                            } catch (NullPointerException mimeExcp2){
                                mimeExcp2.printStackTrace();
                                System.out.println("Error Occurred");
                            }

                            if(!layerThreeLinks.equals(0))
                                rootLinks.addAll(counter+1, layerThreeLinks);
                            layerThreeLinks = new ArrayList<>();
                            //flag turns false and stops the loop if not before
                            flag = false;
                        }
                    }
                    System.out.println("");
                    //System.out.println(fileOnlyLinkOne); //The full list of file links to create and download on the system

                    //working with file
                    for(int i = 0; i < fileOnlyLinkOne.size(); i++) {
                        boolean checkFileExist;
                        String addressSetter = rootFolderName+"/"+fileOnlyLinkOne.get(i).get(1);
                        System.out.println("Link: " + fileOnlyLinkOne.get(i).get(0));
                        System.out.println("File name: " + fileOnlyLinkOne.get(i).get(1));
                        try {
                            fileObj1.setAddress(addressSetter);
                            checkFileExist = fileObj1.makeFolderFile();

                            //Downloading part, Just enable in-case you want to download.
                            if (checkFileExist) {
                                downloadObj1.setAddress(addressSetter, (String) fileOnlyLinkOne.get(i).get(0));
                                downloadObj1.downloadFileFromUrlUsingNio();
                            } else
                                System.out.println("File already attempted to download before, skipping!");
                        }catch (NullPointerException | IOException setterAdd2){
                            setterAdd2.printStackTrace();
                        }
                    }
                    //code ends
                    System.out.println("#####################");
                    break;
                case 2:
                    System.out.println("********************");
                    System.out.println("Exit");
                    choice = false;
                    System.out.println("********************");
                    break;

                default:
                    System.out.println("Invalid");
                    break;
            }
        }
    }
}
