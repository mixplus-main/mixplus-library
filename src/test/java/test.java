



import com.mixplus.library.system.CPU;
import com.mixplus.library.system.OperatingSystem;

public class test {
    static void main(String[] args) {
        OperatingSystem.start();
        for (int i = 0; i < 50; i++) {
            System.out.println(CPU.getUsage());
        }
    }
}
