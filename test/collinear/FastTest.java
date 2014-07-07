import org.testng.annotations.Test;

@Test
public class FastTest {
    public void shouldOnlyGenerateTwoEntriesForInput8() {
        String[] args = new String[1];
        args[0] = "test/collinear/input8.txt";
        Fast.main(args);
    }
}