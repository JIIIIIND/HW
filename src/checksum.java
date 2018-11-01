
import java.io.*;
import java.util.ArrayList;
import java.io.PrintWriter;

public class checksum
{

    ArrayList<String> bitList;
    String result;

    public checksum()
    {
        bitList = new ArrayList<>();
    }

    public void StartChecksum()
    {
        ReadFile( "./src/input.txt");
        WriteFile("./src/output_checksum.txt");
    }

    private void WriteFile(String path)
    {
        try
        {
            PrintWriter printWriter = new PrintWriter(path);
            printWriter.print(result);
            printWriter.close();
        }
        catch(IOException e) {System.out.println("IOException"); }
    }

    private void ReadFile(String path)
    {
        try
        {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);

            int character;
            while((character = fileReader.read()) != -1)
            {
                String binary = Integer.toBinaryString(character);
                int evenParity = 0;

                for(int i = 0; i < binary.length(); i++)
                {
                    if(binary.charAt(i) == '1')
                        evenParity++;
                }
                if(binary.length() < 7)
                {
                    for(int i = binary.length(); i < 7; i++)
                        binary = "0" + binary;
                }
                if(evenParity%2 == 1)
                    binary = binary + "1";
                else
                    binary = binary + "0";

                bitList.add(binary);
            }

            MergeString(bitList);
            result = ChecksumCalculate(bitList);
            while(result.length() < 16)
                result = "0" + result;
        }
        catch(FileNotFoundException e) {System.out.println("File Not Found");}
        catch(IOException e) {System.out.println("IOException");}


    }

    private int BinaryStringToInt(String value)
    {
        int sum = 0;
        int count = 0;
        for(int i = value.length(); i > 0; i--)
        {
            if(value.charAt(i-1) == '1')
            {
                sum += Math.pow(2,count);
            }
            count++;
        }
        return sum;
    }

    private void MergeString(ArrayList<String> list)
    {
        int length = list.size();
        if(length > 2)
        {
            for(int i = 0; i < length; i += 2)
            {
                list.add(list.get(0)+list.get(1));
                list.remove(0);
                list.remove(0);
            }
        }
    }

    private String OnesComplements(String str)
    {
        String value = new String();
        for(int i = 0; i < str.length(); i++)
        {
            if(str.charAt(i) == '1')
                value += "0";
            else
                value += "1";
        }
        return value;
    }

    private String ChecksumCalculate(ArrayList<String> list)
    {
        int sum = 0;
        for(int i = 0; i < list.size(); i++)
        {

            sum += BinaryStringToInt(OnesComplements(list.get(i)));
            if(sum > 65536)
                sum -= 65535;
        }
        return (Integer.toBinaryString(sum));
    }

}
