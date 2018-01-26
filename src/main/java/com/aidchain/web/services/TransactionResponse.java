package com.aidchain.web.services;

public class TransactionResponse {
    private String message;
    private int blockIndex;

    TransactionResponse(String message, int blockIndex) {
        this.message = message;
        this.blockIndex = blockIndex;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getBlockIndex() {
        return blockIndex;
    }

    public void setBlockIndex(int blockIndex) {
        this.blockIndex = blockIndex;
    }
}
