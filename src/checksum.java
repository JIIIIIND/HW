import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.BitSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class checksum
{

    ArrayList<BitSet> bitList;
    ArrayList<BitSet> mainList;
    ArrayList<Integer> intList;

    public checksum()
    {
        bitList = new ArrayList<>();
        intList = new ArrayList<>();
        mainList = new ArrayList<>();
;    }

    public void ReadFile(String path)
    {
        try
        {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);

            int character;
            while((character = fileReader.read()) != -1)
            {
                BitSet bit = new BitSet(8);

                String binary = Integer.toBinaryString(character);
                System.out.println(binary);
                int evenParity = 0;

                for(int i = 0; i < binary.length(); i++)
                {
                    if(binary.charAt(i) == '1')
                    {
                        bit.set(i+1,true);
                        evenParity++;
                    }
                    else
                        bit.set(i+1,false);
                }
                if(evenParity%2 == 1)
                    bit.set(0,true);
                else
                    bit.set(0,false);

                bitList.add(bit);
                intList.add(new BigInteger(bit.toByteArray()).intValue());
            }
            for(int i = 0; i < bitList.size(); i++)
            {
                if(bitList.size() > i+1)
                {
                    System.out.println(bitList.get(i+1).length());
                    int size = bitList.get(i).length();
                    for(int j = 0; j < bitList.get(i+1).length(); j++)
                    {
                        System.out.println(j+":"+bitList.get(i+1).get(j));
                        bitList.get(i).set(size+j,bitList.get(i+1).get(j));
                    }
                }
                bitList.remove(i+1);
            }

        }
        catch(FileNotFoundException e) {System.out.println("File Not Found");}
        catch(IOException e) {System.out.println("IOException");}
    }
}
