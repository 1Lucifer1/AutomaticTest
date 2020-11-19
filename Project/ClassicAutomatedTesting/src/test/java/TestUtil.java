import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

public class TestUtil {

    @Test
    public void CMDTest() throws IOException {
        Set<String> classLevelData = readFile( "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\0-CMD\\data\\selection-class.txt");
        Set<String> methodLevelData = readFile("D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\0-CMD\\data\\selection-method.txt");

        MainEntry.main(new String[]{"-c", "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\0-CMD\\target",
                "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\0-CMD\\data\\change_info.txt"});
        Set<String> classLevelRes = readFile("selection-class.txt");
        assertTrue(cmpRes(classLevelRes, classLevelData));

        MainEntry.main(new String[]{"-m", "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\0-CMD\\target",
                "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\0-CMD\\data\\change_info.txt"});
        Set<String> methodLevelRes = readFile("selection-method.txt");
        assertTrue(cmpRes(methodLevelRes, methodLevelData));
    }

    @Test
    public void ALUTest() throws IOException {
        Set<String> classLevelData = readFile( "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\1-ALU\\data\\selection-class.txt");
        Set<String> methodLevelData = readFile("D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\1-ALU\\data\\selection-method.txt");

        MainEntry.main(new String[]{"-c", "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\1-ALU\\target",
                "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\1-ALU\\data\\change_info.txt"});
        Set<String> classLevelRes = readFile("selection-class.txt");
        assertTrue(cmpRes(classLevelRes, classLevelData));

        MainEntry.main(new String[]{"-m", "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\1-ALU\\target",
                "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\1-ALU\\data\\change_info.txt"});
        Set<String> methodLevelRes = readFile("selection-method.txt");
        assertTrue(cmpRes(methodLevelRes, methodLevelData));
    }

    @Test
    public void DataLogTest() throws IOException {
        Set<String> classLevelData = readFile( "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\2-DataLog\\data\\selection-class.txt");
        Set<String> methodLevelData = readFile("D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\2-DataLog\\data\\selection-method.txt");

        MainEntry.main(new String[]{"-c", "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\2-DataLog\\target",
                "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\2-DataLog\\data\\change_info.txt"});
        Set<String> classLevelRes = readFile("selection-class.txt");
        assertTrue(cmpRes(classLevelRes, classLevelData));

        MainEntry.main(new String[]{"-m", "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\2-DataLog\\target",
                "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\2-DataLog\\data\\change_info.txt"});
        Set<String> methodLevelRes = readFile("selection-method.txt");
        assertTrue(cmpRes(methodLevelRes, methodLevelData));
    }

    @Test
    public void BinaryHeapTest() throws IOException {
        Set<String> classLevelData = readFile( "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\3-BinaryHeap\\data\\selection-class.txt");
        Set<String> methodLevelData = readFile("D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\3-BinaryHeap\\data\\selection-method.txt");

        MainEntry.main(new String[]{"-c", "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\3-BinaryHeap\\target",
                "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\3-BinaryHeap\\data\\change_info.txt"});
        Set<String> classLevelRes = readFile("selection-class.txt");
        assertTrue(cmpRes(classLevelRes, classLevelData));

        MainEntry.main(new String[]{"-m", "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\3-BinaryHeap\\target",
                "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\3-BinaryHeap\\data\\change_info.txt"});
        Set<String> methodLevelRes = readFile("selection-method.txt");
        assertTrue(cmpRes(methodLevelRes, methodLevelData));
    }

    @Test
    public void NextDayTest() throws IOException {
        Set<String> classLevelData = readFile( "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\4-NextDay\\data\\selection-class.txt");
        Set<String> methodLevelData = readFile("D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\4-NextDay\\data\\selection-method.txt");

        MainEntry.main(new String[]{"-c", "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\4-NextDay\\target",
                "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\4-NextDay\\data\\change_info.txt"});
        Set<String> classLevelRes = readFile("selection-class.txt");
        assertTrue(cmpRes(classLevelRes, classLevelData));

        MainEntry.main(new String[]{"-m", "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\4-NextDay\\target",
                "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\4-NextDay\\data\\change_info.txt"});
        Set<String> methodLevelRes = readFile("selection-method.txt");
        assertTrue(cmpRes(methodLevelRes, methodLevelData));
    }

    @Test
    public void MoreTriangleTest() throws IOException {

        Set<String> classLevelData = readFile( "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\5-MoreTriangle\\data\\selection-class.txt");
        Set<String> methodLevelData = readFile("D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\5-MoreTriangle\\data\\selection-method.txt");

        MainEntry.main(new String[]{"-c", "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\5-MoreTriangle\\target",
                "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\5-MoreTriangle\\data\\change_info.txt"});
        Set<String> classLevelRes = readFile("selection-class.txt");
        assertTrue(cmpRes(classLevelRes, classLevelData));

        MainEntry.main(new String[]{"-m", "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\5-MoreTriangle\\target",
                "D:\\GitHub\\Git\\AutomaticTest\\Data\\ClassicAutomatedTesting\\5-MoreTriangle\\data\\change_info.txt"});
        Set<String> methodLevelRes = readFile("selection-method.txt");
        assertTrue(cmpRes(methodLevelRes, methodLevelData));
    }

    private boolean cmpRes(Set<String> res, Set<String> data) {
        System.out.println("res.size is "+res.size());
        System.out.println("data.size is "+data.size());
        if (res.size() != data.size()) {
            return false;
        }
        for (String s : res) {
            if (!data.contains(s)) {
                return false;
            }
        }
        return true;
    }

    private Set<String> readFile(String path) throws IOException {
        File file = new File(path);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        Set<String> changedMethods = new HashSet<String>();
        String s;
        while ((s = bufferedReader.readLine()) != null) {
            if(s.equals("\n") || s.equals("\r\n") || s.equals("")) continue;
            changedMethods.add(s.trim());
        }
        return changedMethods;
    }

}

