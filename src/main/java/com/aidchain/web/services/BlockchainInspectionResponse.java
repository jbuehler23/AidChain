package com.aidchain.web.services;

import com.aidchain.aidchain_blockchain.Block;

import java.util.List;

public class BlockchainInspectionResponse {

    private List<Block> blockchain;
    private int length;

    BlockchainInspectionResponse(List<Block> blockchain, int length) {
        this.blockchain = blockchain;
        this.length = length;
    }

    public List<Block> getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(List<Block> blockchain) {
        this.blockchain = blockchain;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

}
