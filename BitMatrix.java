import java.util.*;


public class BitMatrix {
    private boolean[][] matrix;
    private int[] physicalToId;
    private int size;

    public BitMatrix(int size, Vector mem) {
        this.size = size;
        matrix = new boolean[size][size];
        physicalToId = new int[size];

        for (int i = 0; i < size; ++i) {
            java.util.Arrays.fill(matrix[i], false);
        }

        for (int i = 0; i < mem.size(); ++i) {
            Page page = (Page) mem.elementAt(i);
            if (page.physical != -1) {
                physicalToId[page.physical] = i;
            }
        }
    }

    public void setPageReference(int physical) {
        for (int j = 0; j < size; ++j) {
            matrix[physical][j] = true;
        }

        for (int i = 0; i < size; ++i) {
            matrix[i][physical] = false;
        }
    }

    public void updatePhysical(int physical, int id) {
        physicalToId[physical] = id;
    }

    public int getLRU() {
        int lowestIndex = 0;
        int lowestValue = size;
        
        for (int i = 0; i < size; ++i) {
            int currentValue = 0;
            for (int j = 0; j < size; ++j) {
                currentValue += matrix[i][j] ? 1 : 0;
            }

            if (currentValue < lowestValue) {
                lowestIndex = i;
                lowestValue = currentValue;
            }
        }

        return physicalToId[lowestIndex];
    }


    public void outToConsole() {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                System.out.print((matrix[i][j] ? 1 : 0) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}