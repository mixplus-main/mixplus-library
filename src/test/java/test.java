import com.mixplus.library.system.CPU;
import com.mixplus.library.system.Disk;
import com.mixplus.library.unit.DataUnit;

import java.nio.file.Path;

public class test {
    static void main(String[] args) {
        System.out.println("CPU Name: " + CPU.getName());
        System.out.println("CORE: " + CPU.getPhysicalProcessorCount());
        System.out.println("Thread: " + CPU.getLogicalProcessorCount());
        System.out.println("Maximum CPU frequency: " + CPU.getMaxFrequency());
    }
}
