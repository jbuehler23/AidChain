package com.aidchain.aidchain_blockchain;

import com.aidchain.transaction.Transaction;
import com.aidchain.utils.DateDeserializer;
import com.aidchain.utils.Utils;
import com.aidchain.web.services.MineResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RestController
public class Blockchain {

    private List<Block> blockchain;
    private List<Transaction> currentTransactions;
    public static int DIFFICULTY = 2;
    private String nodeId;
    //new nodes can only be added once
    private Set<Node> nodes;

    /**
     * Create a new instance of a Blockchain, which creates a new Genesis Block
     * */
    public Blockchain() {
        blockchain = new ArrayList<>();
        blockchain.add(createGenesisBlock());

        currentTransactions = new ArrayList<>();
        nodes = new HashSet<>();
        //need to generate a new nodeId
        nodeId = UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * @return the newly created Genesis Block
     */
    private Block createGenesisBlock() {
        return new Block(blockchain.size(), Collections.singletonList(new Transaction("Genesis", "Genesis", new BigDecimal(0))),100, "0");
    }

    /**
     * @return latest block from the chain
     */
    private Block getLatestBlock() {
        return blockchain.get(blockchain.size() - 1);
    }

    /**
     *
     * @param sender Address for the sender
     * @param recipient Address for the recipient
     * @param amount BigDecimal for the amount
     * @return <int>The index of the Block that will hold this transaction</int>
     */
    public int createNewTransaction(String sender, String recipient, BigDecimal amount) {
        currentTransactions.add(new Transaction(sender, recipient, amount));
        return getLatestBlock() != null ? getLatestBlock().getIndex() + 1 : 0;
    }

    /**
     * Add a new Block to the chain
     * @param previousHash of the Block
     */
    private Block createNewBlock(int proof, Date time, String previousHash) {
        Block newBlock = new Block(blockchain.size(), time, currentTransactions, proof, previousHash);
        currentTransactions = new ArrayList<>();
        blockchain.add(newBlock);
        return newBlock;
    }

    /**
     * Simple Proof of Work Algorithm:
        Find a number p' such that hash(pp') contains leading zeroes of difficulty, where p is the previous p'
        p is the previous proof, and p' is the new proof
     * @param lastProof used
     * @return used proof
     */
    private int proofOfWork(int lastProof, String previousHash) {
        int proof = 0;
        while(!validProof(lastProof, proof, previousHash)) {
            proof++;
        }
        System.out.println("valid proof found for previous hash: " + previousHash);
        return proof;
    }

    /**
     * Validates the Proof: Does hash(last_proof, proof) contain 4 leading zeroes?
     * @param lastProof calculated
     * @param proof current proof
     * @return whether this is a valid proof
     */
    private boolean validProof(int lastProof, int proof, String previousHash) {
        String guess = lastProof + proof + previousHash;
        String result = DigestUtils.sha256Hex(guess);
        String target = new String(new char[DIFFICULTY]).replace('\0', '0');
        return result.startsWith(target);
    }

    /**
     * Add a new Block to the chain
     * @param newBlock to be added
     */
    @Deprecated
    public void addBlock(Block newBlock) {
        //Set the new block's previous hash to the latest block's on the chain's hash
        newBlock.setPreviousHash(getLatestBlock().getHash());
        //recalculate the hash
        newBlock.mineBlock(DIFFICULTY);
        //can't really add a new block this easily
        blockchain.add(newBlock);
    }

    /**
     * Verify integrity of blockchain
     * @return true if blockchain is OK
     */
    public boolean isBlockchainValid() {
        for (int i = 1; i < blockchain.size(); i ++) {
            //get the current block
            Block currentBlock = blockchain.get(i);
            Block previousBlock = blockchain.get(i - 1);

            //check they are properly linked together
            //check the has of the block is calculated correctly
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;
            }

            //check we point to correct previous hash on the current block
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }

        }
        return true;
    }
    public List<Block> getBlockchain() {
        return blockchain;
    }

    public int getDifficulty() {
        return DIFFICULTY;
    }

    public void setDifficulty(int difficulty) {
        DIFFICULTY = difficulty;
    }

    public void setBlockchain(List<Block> blockchain) {
        this.blockchain = blockchain;
    }

    public List<Transaction> getCurrentTransactions() {
        return currentTransactions;
    }

    public void setCurrentTransactions(List<Transaction> currentTransactions) {
        this.currentTransactions = currentTransactions;
    }
    /*
        RPC RESPONSES - may need to pull these out
    */
    /**
     * Mine a block, and enter address to send reward
     * @param recipientAddress to send reward to
     */
    @Deprecated
    public String mine(String recipientAddress) {
        if (DIFFICULTY < 2) {
            throw new RuntimeException("Mining difficulty can be less than 2.");
        }
        int proof = proofOfWork(getLatestBlock().getProof(), getLatestBlock().getPreviousHash());
        createNewTransaction("AidChain Wallet Address", recipientAddress, new BigDecimal(1.0));
        Block block = createNewBlock(proof, new Date(), getLatestBlock().getHash());
        MineResponse response = new MineResponse("New Block Forged", block.getIndex(), block.getTransactions(), block.getProof(), block.getPreviousHash());
        return Utils.getJson(recipientAddress);
    }

    /**
     * Get nodeId on networkd
     * @return nodeId
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     * Set nodeId on the network
     * @param nodeId to set
     */
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    /**
     * Mine for the registered NodeId on the network,
     * and generate the response back once new block has been forged
     *
     */
    public MineResponse mine() {
        if (DIFFICULTY < 2) {
            throw new RuntimeException("Mining difficulty can be less than 2.");
        }
        int proof = proofOfWork(getLatestBlock().getProof(), getLatestBlock().getPreviousHash());
        //send a 0 to denote this node has generated a block
        createNewTransaction("0", nodeId, new BigDecimal(1.0));
        Block block = createNewBlock(proof, new Date(), getLatestBlock().getHash());
        return new MineResponse("New Block Forged", block.getIndex(), block.getTransactions(), block.getProof(), block.getPreviousHash());
    }

    /**
     * Add a new node to the list of nodes on the network
     * @param address address to be added to the network. For example, "http://192.168.0.5:5000"
     */
    public void registerNode(String address) throws URISyntaxException {
        nodes.add(new Node(new URI(address)));
    }

    /**
     * Determine if a given blockchain is valid
     * @param chain A blockchain
     * @return True if valid, False if not
     */
    private boolean isValidChain(List<Block> chain) {
        Block currentBlock;
        Block lastBlock = chain.get(0);
        int currentIndex = 1;
        while (currentIndex < chain.size()) {
            currentBlock = chain.get(currentIndex);
            System.out.println(Utils.getJson(lastBlock));
            System.out.println(Utils.getJson(currentBlock));
            //check the hash of the block is correct
            if (!currentBlock.getPreviousHash().equals(lastBlock.calculateHash())) {
                return false;
            }
            //check the Proof of Work is correct
            if (validProof(lastBlock.getProof(), currentBlock.getProof(), lastBlock.getPreviousHash())) {
                return false;
            }
            lastBlock = currentBlock;
            currentIndex++;
        }
        return true;
    }

    /**
     * Consesus Algorithm to resolve conflicts
     * by replacing our chain with the longest one in the network
     * @return True if our chain was replaced, False if not
     */
    public boolean resolveConflicts() throws URISyntaxException, IOException {
        List<Block> newChain = null;
        //only looking for chains longer than ours
        int maxLength = blockchain.size();

        for (Node node : nodes) {
            URI uri = new URI(node.getAddress() + "/chain");
            HttpClient http = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(uri);
            HttpResponse response = http.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
                //get blockchain json from the http response
                Blockchain blockchain = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create().fromJson(new InputStreamReader(response.getEntity().getContent()), Blockchain.class);
                List<Block> chain = blockchain.getBlockchain();
                int length = blockchain.getBlockchain().size();
                //check if the length is longer and the chain is valid from the response
                if (length > maxLength && isValidChain(chain)) {
                    maxLength = length;
                    newChain = chain;
                }

            }

        }
        //resolve our chain if we discovered a new, valid chain longer than ours
        if (newChain != null) {
            blockchain = newChain;
            return true;
        }
        return false;
    }

    public Set<Node> getNodes() {
        return nodes;
    }
}