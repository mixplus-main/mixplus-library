import com.mixplus.library.system.Disk;
import com.mixplus.library.unit.DataUnit;

import java.nio.file.Path;

public class test {
    static void main(String[] args) {
        Path path = Path.of("C:/");
        System.out.println("Total: " + Disk.getTotal(path, DataUnit.BYTE) + "BYTE");
        System.out.println("Used: " + Disk.getUsed(path, DataUnit.BYTE) + "BYTE");
        System.out.println("Usage: " + Disk.getUsage(path) + "%");
    }
}
