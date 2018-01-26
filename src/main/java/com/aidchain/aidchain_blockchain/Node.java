package com.aidchain.aidchain_blockchain;

import java.net.URI;

public class Node {

    private URI address;

    Node(URI address) {
        this.address = address;
    }

    public URI getAddress() {
        return address;
    }

    public void setAddress(URI address) {
        this.address = address;
    }
}
