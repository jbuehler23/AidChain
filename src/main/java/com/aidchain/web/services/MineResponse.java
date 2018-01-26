package com.aidchain.web.services;

import com.aidchain.transaction.Transaction;

import java.util.List;

public class MineResponse {
    public MineResponse(String message, int index, List<Transaction> transactions, int proof, String previousHash) {
        this.message = message;
        this.index = index;
        this.transactions = transactions;
        this.proof = proof;
        this.previousHash = previousHash;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public int getProof() {
        return proof;
    }

    public void setProof(int proof) {
        this.proof = proof;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    private String message;
    private int index;
    private List<Transaction> transactions;
    private int proof;
    private String previousHash;



}
