package com.aidchain.web.services;

import com.aidchain.aidchain_blockchain.Node;
import org.springframework.http.HttpStatus;

import java.util.Set;

public class NodeRegisterResponse {

    private String message;
    private Set<Node> totalNodes;

    NodeRegisterResponse(String message, Set<Node> totalNodes, HttpStatus status) {
        this.message = message;
        this.totalNodes = totalNodes;
        this.status = status;
    }

    private HttpStatus status;

    NodeRegisterResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<Node> getTotalNodes() {
        return totalNodes;
    }

    public void setTotalNodes(Set<Node> totalNodes) {
        this.totalNodes = totalNodes;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

}
