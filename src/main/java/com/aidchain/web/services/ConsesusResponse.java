package com.aidchain.web.services;

import com.aidchain.aidchain_blockchain.Blockchain;
import org.springframework.http.HttpStatus;


public class ConsesusResponse {
    private String message;
    private Blockchain blockchain;
    private HttpStatus status;

    ConsesusResponse(String message, Blockchain blockchain, HttpStatus status) {
        this.message = message;
        this.blockchain = blockchain;
        this.status = status;
    }

    ConsesusResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Blockchain getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
