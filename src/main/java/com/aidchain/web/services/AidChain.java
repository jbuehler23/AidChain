package com.aidchain.web.services;

import com.aidchain.aidchain_blockchain.Block;
import com.aidchain.aidchain_blockchain.Blockchain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(scanBasePackages = {"com.aidchain"})
public class AidChain {
    private static List<Block> blockchain = new ArrayList<>();
    private static int difficulty = 1;
    //fire up our server now
    public static void main(String[] args) {
        SpringApplication.run(AidChain.class, args);
    }

//    public static void main(String[] args) {
//
//        Blockchain aidChain = new Blockchain();
//        aidChain.setDifficulty(6);
//
////        System.out.println("Trying to Mine block 1... ");
////        aidChain.addBlock(new Block(1, Collections.singletonList(new Transaction("Joe", "Bob", new BigDecimal(10.00))), ""));
////        System.out.println("Trying to Mine block 2... ");
////        aidChain.addBlock(new Block(2, Collections.singletonList(new Transaction("Bill", "Steve", new BigDecimal(30.00))), ""));
//
//        System.out.println("Trying to Mine first block... ");
//        aidChain.mine("joe");
//        System.out.println("Trying to Mine second block... ");
//        aidChain.mine("joe1");
//        System.out.println("Trying to Mine third block... ");
//        aidChain.mine("joe2");
//        System.out.println("Trying to Mine fourth block... ");
//        aidChain.mine("joe3");
//
////        aidChain.mine("JOE");
//        System.out.println("----------------------BLOCK CHAIN STRUCTURE----------------------");
//        System.out.println(Utils.getJson(aidChain));
//
//        System.out.println("Is blockchain valid? " + aidChain.isBlockchainValid());
////
////        //try and add a new transaction to the block at index 1, and then check integrity of the chain
////        aidChain.getBlockchain().get(1).setTransactions(Collections.singletonList(new Transaction("Joe", "Bob", new BigDecimal(10000.0000))));
////        //try changing the hash of that block in the chain instead?
////        aidChain.getBlockchain().get(1).setHash(aidChain.getBlockchain().get(1).calculateHash());
////
////        System.out.println("Is blockchain valid? " + aidChain.isBlockchainValid());
//
////        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(aidChain));
//
//
//
////        System.out.println("Trying to Mine block 1... ");
////        addBlock(new Block(new Transaction("Hi im the first block"), "0"));
////
////        System.out.println("Trying to Mine block 2... ");
////        addBlock(new Block(new Transaction("I am the second block"), blockchain.get(blockchain.size() - 1).getHash()));
////
////        System.out.println("Trying to Mine block 3... ");
////        addBlock(new Block(new Transaction("I am the third block"), blockchain.get(blockchain.size() - 1).getHash()));
////
////        //get the previous block's hash in the list
//////        //TODO: fire on a new thread?
//////        for (int i = 0; i <= blockchain.size() - 1; i++) {
//////            System.out.println("Trying to mine block " + (i+1) + "...");
//////            blockchain.get(i).mineBlock(difficulty);
//////        }
////
////        System.out.println(isBlockchainValid() ? "\nBlockchain is VALID." : "\nBlockchain is NOT VALID.");
////
////        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
////        System.out.println("\n----The AidBlockchain----");
////        System.out.println(blockchainJson);
////
//////        Block genesisBlock = new Block(new Transaction("I am the first block"), "0");
//////        System.out.println("Hash for genesis: " + genesisBlock.getHash());
//////
//////        Block secondBlock = new Block(new Transaction("I am the second block"), genesisBlock.getHash());
//////        System.out.println("Hash for block 2: " + secondBlock.getHash());
//////
//////        Block thirdBlock = new Block(new Transaction("I am the third block"), genesisBlock.getHash());
//////        System.out.println("Hash for block 3: " + thirdBlock.getHash());
//    }
//
//    public static Boolean isBlockchainValid() {
//        Block currentBlock;
//        Block previousBlock;
//        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
//        //iterate through block and check hashes
//        for (int i = 1; i < blockchain.size(); i++) {
//            //skip genesis block
//            currentBlock = blockchain.get(i);
//            previousBlock = blockchain.get(i - 1);
//
//            //compare stored hash with calculated hash
//            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
//                System.out.println("Current hashes not equal.");
//                return false;
//            }
//
//            //compare previous hash with stored previous hash
//            if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
//                System.out.println("Previous hashes not equal.");
//                return false;
//            }
//
//            //check if hash is solved
//            if (!currentBlock.getHash().substring(0, difficulty).equals(hashTarget)) {
//                System.out.println("This block hasn't been mined");
//                return false;
//            }
//        }
//        return true;
//    }

//    public static void addBlock(Block newBlock) {
//        newBlock.mineBlock(difficulty);
//        blockchain.add(newBlock);
//    }

}
