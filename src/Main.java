public class Main {

    public static void main(String[] args) {
	// write your code here
        checksum check = new checksum();

        check.StartChecksum(check.getClass().getResource("").getPath());

    }
}
