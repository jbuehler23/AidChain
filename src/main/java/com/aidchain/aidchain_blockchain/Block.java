package com.aidchain.aidchain_blockchain;

import com.aidchain.transaction.Transaction;
import com.aidchain.utils.Utils;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;
import java.util.List;

public class Block {
    private int index;
    private String hash;
    private String previousHash;
    private List<Transaction> transactions;
    private Date timeStamp;
    private int proof;


    Block(int index, List<Transaction> transactions, int proof, String previousHash) {
        this.index = index;
        this.previousHash = previousHash;
        this.transactions = transactions;
        this.timeStamp = new Date();
        this.hash = calculateHash();
        this.proof = proof;
    }

    /**
     * Use this constructor when wanting to use the Proof-of-Work algorithm
     * @param index to use
     * @param time timestamp to use
     * @param currentTransactions current transactions on the block
     * @param proof proof of the block
     * @param previousHash hash of the previous block
     */
    Block(int index, Date time, List<Transaction> currentTransactions, int proof, String previousHash) {
        this.index = index;
        this.timeStamp = time;
        this.transactions = currentTransactions;
        this.proof = proof;
        this.previousHash = previousHash;
        this.hash = calculateHash();
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setProof(int proof) {
        this.proof = proof;
    }

    /**
     * calculate new hash based on block contents, using Apache Commons SHA-256 Hashing
     */
    public String calculateHash() {
        //TODO: convert to use Transaction() instead of string
        return DigestUtils.sha256Hex(previousHash + timeStamp.toString() + Utils.getJson(transactions) + Integer.toString(proof));
//        return Utils.applySha256(
//                previousHash +
//                        timeStamp.toString() +
//                        Integer.toString(nonce) +
//                        transaction.getData()
//        );
    }


    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            this.hash = this.calculateHash();
            this.proof++;
        }
        System.out.println("Block mined: " + this.hash);
    }

    //    public void mineBlock(int difficulty) {
//        //create String with difficulty * "0"
//        String target = new String(new char[difficulty]).replace('\0', '0');
//        while (!hash.substring(0, difficulty).equals(target)) {
//            nonce++;
//            hash = calculateHash();
//        }
//        System.out.println("Block successfully mined!!! : " + hash);
//    }

    /**
     *
     * @return hash
     */
    public String getHash() {
        return hash;
    }

    /**
     *
     * @param hash to set
    */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     *
     * @return previousHash
     */
    public String getPreviousHash() {
        return previousHash;
    }

    /**
     *
     * @param previousHash to set
     */
    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public int getIndex() {
        return index;
    }

    public int getProof() {
        return proof;
    }
}
