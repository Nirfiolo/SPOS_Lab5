import java.util.*;


public class BitMatrix {
    private long[] matrix;
    private int[] physicalToId;
    private int size;
    private final int maxBitSize = 64;

    public BitMatrix(int size, Vector mem) {
        this.size = size;
        matrix = new long[size];
        physicalToId = new int[size];

        java.util.Arrays.fill(matrix, 0);
        
        for (int i = 0; i < mem.size(); ++i) {
            Page page = (Page) mem.elementAt(i);
            if (page.physical != -1) {
                physicalToId[page.physical] = i;
            }
        }
    }

    public void setPageReference(int physical) {
        for (int j = 0; j < size; ++j) {
            matrix[physical] = Long.MAX_VALUE >> (maxBitSize - size - 1);
        }

        for (int i = 0; i < size; ++i) {
            matrix[i] &= ~(1 << physical);
        }
    }

    public void updatePhysical(int physical, int id) {
        physicalToId[physical] = id;
    }

    public int getLRU() {
        int lowestIndex = 0;
        long lowestValue = Long.MAX_VALUE;
        
        for (int i = 0; i < size; ++i) {
            if (matrix[i] < lowestValue) {
                lowestIndex = i;
                lowestValue = matrix[i];
            }
        }

        return physicalToId[lowestIndex];
    }

    public void outToConsole() {
        for (int i = 0; i < size; ++i) {
            System.out.print("0".repeat(size - Long.toBinaryString(matrix[i]).length()) +
                Long.toBinaryString(matrix[i]));
            System.out.println();
        }
        System.out.println();
    }
}