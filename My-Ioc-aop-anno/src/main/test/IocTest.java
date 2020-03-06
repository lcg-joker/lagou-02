import com.lcg.edu.factory.BeanFactory;
import com.lcg.edu.utils.PackageScan;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.List;

/**
 * @author lichenggang
 * @date 2020/3/2 10:06 下午
 * @description
 */
public class IocTest {


    @Test
    public void test1() {
        int maxValue = Integer.MAX_VALUE;
        int minValue = Integer.MIN_VALUE;
        System.err.println(maxValue);
        System.err.println(minValue);

    }


    @Test
    public void test2() throws ClassNotFoundException {
        //包名

        PackageScan packageScan = new PackageScan();
        String basePack = "com.lcg.edu";
        List<String> classNameList = packageScan.doScan(basePack);
        for (String s : classNameList) {
            System.out.println(s);
        }
    }


    @Test
    public void test3() {

        Object connectionUtils = BeanFactory.getBean("connectionUtils");


    }

}
