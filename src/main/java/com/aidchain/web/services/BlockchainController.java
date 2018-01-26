package com.aidchain.web.services;

import com.aidchain.aidchain_blockchain.Blockchain;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
public class BlockchainController {
    private Blockchain blockchain = new Blockchain();
    static {{
        Blockchain.DIFFICULTY = 4;
    }}

    /**
     * Try to mine a new block
     * @return Block information containing transactions that has been successfully mined
     */
    @RequestMapping(method = RequestMethod.GET, value = "/mine")
    public MineResponse mineResponse() {
        return blockchain.mine();
    }

    /**
     * Create a new transaction
     * @param sender Address that is sending funds
     * @param recipient Address that is receiving funds
     * @param amount amount to send (BigDecimal)
     * @return response denoting the index of the Block this transaction will be added to
     */
    @RequestMapping(method = RequestMethod.POST, value = "/transactions/new")
    public TransactionResponse createTransaction(@RequestParam(value = "sender") String sender,
                                                 @RequestParam(value = "recipient") String recipient,
                                                 @RequestParam(value = "amount") String amount) {
        BigDecimal amountBigDecimal = new BigDecimal(amount);
        int index = blockchain.createNewTransaction(sender, recipient, amountBigDecimal);
        return new TransactionResponse("Your transaction will be added to the block.", index);
    }

    /**
     * Return the blockchain and size
     * @return the Blockchain and Size
     */
    @RequestMapping(method = RequestMethod.GET, value = "/chain")
    public BlockchainInspectionResponse inspectFullChain() {
        return new BlockchainInspectionResponse(blockchain.getBlockchain(), blockchain.getBlockchain().size());
    }

    /**
     * Register Nodes on the network
     * @param nodes Nodes to register
     * @return a response whether successful or failure.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/nodes/register")
    public NodeRegisterResponse registerNodes(@RequestParam(value = "nodes[]") String[] nodes) {
        Set<String> nodesInRequest = new HashSet<>(Arrays.asList(nodes));
        if (nodesInRequest.size() == 0) {
            return new NodeRegisterResponse("Error: Please supply a valid list of nodes", HttpStatus.BAD_REQUEST);
        }

        for(String address : nodesInRequest) {
            try {
                blockchain.registerNode(address);
            } catch (URISyntaxException e) {
                return new NodeRegisterResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return new NodeRegisterResponse("New nodes have been added.", blockchain.getNodes(), HttpStatus.CREATED);
    }

    /**
     * A conflict is when one node has a different chain to another node.
     * Therefore, the longest valid chain is authoritative.
     * The longest chain on the network is the default one.
     * We must run this Consensus algorithm amongst all nodes in the network
     * @return consesus response message
     */
    @RequestMapping(method = RequestMethod.GET, value = "/nodes/resolve")
    public ConsesusResponse consesus() {
        try {
            boolean replaced = blockchain.resolveConflicts();
            if (replaced) {
                return new ConsesusResponse("Our chain was replaced", blockchain, HttpStatus.OK);
            }
            else {
                return new ConsesusResponse("Our chain is authoritative", blockchain, HttpStatus.OK);
            }
        } catch (URISyntaxException | IOException e) {
            return new ConsesusResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
