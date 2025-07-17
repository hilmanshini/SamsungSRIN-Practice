package bombing4;

import static utils.Reader.readInt;

@SuppressWarnings("ClassEscapesDefinedScope")
public class Test {

    public void doTest(TestCase testCase) {
        int testCount = readInt();
        for (int testIndex = 0; testIndex < testCount; testIndex++) {
            testCase.doTest(testIndex);
        }
    }
}

interface TestCase {
    void doTest(int index);
}
