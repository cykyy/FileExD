import java.awt.desktop.SystemEventListener;
import java.lang.*;
import java.io.*;
public class FileReadWrite {
    private File file;				//to create a File
    private FileWriter writer;		//to write in a file
    private FileReader reader;		//to read from a file
    private BufferedReader bfr;		//to read file content as a String

    public void writeInFile(String writeText, String writeFileName, boolean setAppend) {
        try {
            file = new File(writeFileName);            //Declaring a file named named History.txt for creating.
            if (file.getParentFile() != null) {
                if (!file.getParentFile().exists())
                    System.out.println("Creating log/history parent folder...");
                file.getParentFile().mkdirs();
            }
            file.createNewFile();                               //If the file does not exists, creates and opens the file. else, just opens the file
            if (setAppend)
                writer = new FileWriter(file, true);        //creating the writer object to write in the file.
            else
                writer = new FileWriter(file);

            if ((readFromFile(writeFileName).isBlank()))
                writer.write(writeText + "\r" + "\n");      //writing a strings in the file. the "\r" and "\n" has been concat to go to a newline.
            else
                if (setAppend)
                    writer.write("\n"+writeText+"\r"+"\n");
                else
                    writer.write(writeText+"\r"+"\n");
            writer.flush();					                    //After writing, we need to flush to indicate that we have completed writing.
            writer.close();					                    //After flushing, we need to close the file to save our writing.
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public String readFromFile(String readFile)
    {
        String text="", temp;
        try
        {
            file = new File(readFile); //file address
            reader = new FileReader(file);			//creating the reader object to read from a file.
            bfr = new BufferedReader(reader);		//creating the BufferedReader object using the reader object to read the file content.
            //String text="", temp;					//declaring two string variables to read the file content and storing them.

            while((temp=bfr.readLine())!=null)		//reading one line from the file, storing it in the variable temp and checking whether it is null or not. It will be null at the end of reading from the file.
            {
                text=text+temp+"\n"+"\r";			//storing the temp string in text by concating it with text and "n" and "\r" is used to go to a newline.
            }

           // System.out.print(text);   				//printing the whole string in console.
            reader.close();							//closing the file.
        return text;
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
        return text;
    }

    public void finalizer( String content, String addr){
        String readContent = readFromFile(addr);
        String finalFile;
        finalFile = readContent.replace(content , "");
        writeInFile(finalFile.trim(),addr,false);
    }

}
