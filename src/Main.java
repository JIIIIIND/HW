public class Main {

    public static void main(String[] args) {
	// write your code here
        //checksum check = new checksum();

        //check.StartChecksum(check.getClass().getResource("").getPath());

        CRC crc = new CRC();
        //crc.ReadFile(crc.getClass().getResource("").getPath() + "input.txt");
        crc.ReadFile("C:\\Users\\jinwo\\OneDrive\\문서\\HW\\src\\input.txt");
    }

}
