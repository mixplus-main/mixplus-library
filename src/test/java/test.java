import com.mixplus.library.system.CPU;
import com.mixplus.library.system.Memory;
import unit.DataUnit;
import unit.Percentage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class test {
    private static final ScheduledExecutorService service =
            Executors.newSingleThreadScheduledExecutor();



    static void main(String[] args) {
        System.out.println("Total");
        System.out.println("BYTE" + Memory.getTotal(DataUnit.BYTE));
        System.out.println("KB" + Memory.getTotal(DataUnit.KB));
        System.out.println("MB" + Memory.getTotal(DataUnit.MB));
        System.out.println("GB" + Memory.getTotal(DataUnit.GB));
        System.out.println("TB" + Memory.getTotal(DataUnit.TB));

        System.out.println("Total  : " + Memory.getTotal(DataUnit.BYTE) + " BYTE");
        System.out.println("Used   : " + Memory.getUsed(DataUnit.BYTE) + " BYTE");
        System.out.println("Free   : " + Memory.getAvailable(DataUnit.BYTE) + " BYTE");
        System.out.println("Usage  : " + Memory.getUsage() + "%");


    }
}
