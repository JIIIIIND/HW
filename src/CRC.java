import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;

public class CRC
{
    //11000000000000101
    ArrayList<Boolean> keyValue;
    ArrayList<BitSet> bitList;
    ArrayList<Boolean> dataList;
    ArrayList<Boolean> resultList;
    int index;

    public CRC()
    {
        resultList = new ArrayList<>();
        bitList = new ArrayList<>();
        dataList = new ArrayList<>();

        keyValue = new ArrayList<>();
        keyValue.add(true);
        keyValue.add(true);
        keyValue.add(false);
        keyValue.add(false);
        keyValue.add(false);
        keyValue.add(false);
        keyValue.add(false);
        keyValue.add(false);
        keyValue.add(false);
        keyValue.add(false);
        keyValue.add(false);
        keyValue.add(false);
        keyValue.add(false);
        keyValue.add(false);
        keyValue.add(true);
        keyValue.add(false);
        keyValue.add(true);

        index = 0;
    }

    public void startCRC()
    {
        ReadFile(this.getClass().getResource("").getPath() + "input.txt");
        Operation();
    }

    private void ReadFile(String path)
    {
        try
        {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            int count = 0;
            int character;

            while ((character = fileReader.read()) != -1)
            {
                BitSet bit = new BitSet(8);
                String binary = Integer.toBinaryString(character);
                int evenParity = 0;
                int startPos = 7-binary.length();
                for(int i = 0; i < binary.length(); i++)
                {
                    if(binary.charAt(i) == '1')
                    {
                        bit.set(i+startPos,true);
                        evenParity++;
                    }
                    else
                        bit.set(i+startPos,false);
                }
                if(evenParity%2 == 1)
                    bit.set(7,true);
                else
                    bit.set(7,false);

                bitList.add(bit);
            }
            for(int i = 0; i < bitList.size(); i++)
            {
                for(int j = 0; j < 8; j++)
                    dataList.add(bitList.get(i).get(j));
            }
            for(int i = 0; i < 16; i++)
                dataList.add(false);

            System.out.println(ListToBinary(dataList));
        }
        catch(FileNotFoundException e) { System.out.println(e); }
        catch(IOException e) { System.out.println(e); }
    }

    private void Operation()
    {
        while(CheckData())
        {
            XOR();
        }
        String value = ListToBinary(resultList);

        //파일 저장
    }

    private boolean CheckData()
    {
        if(dataList.size() == index)
        {
            while(resultList.size() < 16)
                resultList.add(0,false);
            return false;
        }
        else
        {
            while(resultList.size() < 17)
            {
                if(dataList.size() == index)
                {
                    while(resultList.size() < 16)
                        resultList.add(0,false);
                    return false;
                }
                int i = resultList.size();
                resultList.add(i,dataList.get(index));
                index++;
            }
            return true;
        }
    }

    private void XOR()
    {
        for(int i = 0; i < keyValue.size(); i++)
            resultList.set(i, resultList.get(i) ^ keyValue.get(i));

        while(resultList.get(0) == false)
            resultList.remove(0);
    }

    private String ListToBinary(ArrayList<Boolean> list)
    {
        String value = new String();
        for(int i = 0; i < list.size(); i++)
        {
            if( list.get(i) == true)
                value += "1";
            else
                value += "0";
        }
        return value;
    }
}
