// Blockchain (Java / Java Developer) -
// Graduate Project Completed By Iván Luna, September 6, 2023. -
// For Hyperskill (Jet Brains Academy). Course: Java Developer.

package blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Block implements Serializable {
    private static int numCreated = 0;
    private static Block previous = null;
    private static final long serialVersionUID = 5L;
    private String previousHash;
    private ArrayList<String> data;
    private int id;
    private int magicNumber;
    private long timeStamp;
    private String thisHash;
    private int secondsToGenerate;

    public Block(Block block, int numZeroes, ArrayList<String> data) {
        long start = System.currentTimeMillis();
        Block.numCreated = block == null ? 0 : block.id;
        Block.previous = block;
        ++numCreated;
        this.id = numCreated;
        if (previous != null) {
            this.previousHash = previous.thisHash;
        } else {
            this.previousHash = "0";
        }
        this.data = data;
        this.timeStamp = new Date().getTime();
        magicNumber = 0;
        String thisHash;
        do {
            ++magicNumber;
            thisHash = StringUtil.applySha256(getContents());
        } while (!thisHash.startsWith("0".repeat(numZeroes)));
        this.thisHash = thisHash;
        previous = this;
        secondsToGenerate = (int) (System.currentTimeMillis() - start) / 1000;
    }

    public String getHash() {
        return thisHash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getContents() {
        if (data == null)
            return id + previousHash + magicNumber;
        else
            return id + previousHash + magicNumber + String.join("", data);
    }

    public int getSecondsToGenerate() {
        return secondsToGenerate;
    }

    @Override
    public String toString() {
        return "Id: " + id
                + "\nTimestamp: " + timeStamp
                + "\nMagic number: " + magicNumber
                + "\nHash of the previous block:\n"
                + previousHash
                + "\nHash of the block:\n"
                + thisHash
                + "\nBlock data:"
                + (data != null && !data.isEmpty() ? "\n" + String.join("\n", data) : " no messages")
                + "\nBlock was generating for " + secondsToGenerate + " seconds";
    }
}