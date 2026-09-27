/** 
 * Copyright (c) 2025, Jared Newell. All rights reserved.
 * Javacard Application
 */

package com.research.teasle;

import javacard.framework.ISO7816;
import javacard.framework.ISOException;
import javacard.framework.APDU;
import javacard.framework.Util;
import javacard.framework.Applet;
import javacard.framework.JCSystem;


/**
 * Index Applet
 * @author Jared Newell
 */

public class TEASLEIndex extends Applet {
	/*
	 * Builds different index types
	 * 				- CLA	(7816)	INS (Index type)		P1    Bytes (data size)	Version (eg. two)
	 * To query use - 0x00 0xC0		0x20					0x00  0x01				0x02
	 */
	
	// codes CLA byte command
	final static byte GET_RESPONSE = (byte) 0xC0;
	
	// codes INS byte command
	final static byte TEASL_CE = (byte) 0x20; // TEASL client-side compute efficient
	final static byte TEASL_CE_SIZE = (byte) 0x21; // TEASL client-side compute efficient storage usage
	final static byte TEASL_SE = (byte) 0x30; // TEASL client-side storage-side efficient
	final static byte TEASL_SE_SIZE = (byte) 0x31; // TEASL client-side compute efficient storage usage
	final static byte EASL = (byte) 0x40; // TEASL server-side or no index
	
	/**
	 * Installs this applet.
	 * 
	 * @param bArray
	 *            the array containing installation parameters
	 * @param bOffset
	 *            the starting offset in bArray
	 * @param bLength
	 *            the length in bytes of the parameter data in bArray
	 */
	public static void install(byte[] bArray, short bOffset, byte bLength) {
        new TEASLEIndex(bArray, bOffset, bLength);
    }

    /**
     * Only this class's install method should create the applet object.
     */
    protected TEASLEIndex(byte[] bArray, short bOffset, byte bLength) {
        register(bArray,((short)(bOffset + 1)), bArray[bOffset]);
    }

    /**
     * Processes an incoming APDU.
     * 
     * @see APDU
     * @param apdu
     *            the incoming APDU
     */
    //@Override
    public void process(APDU apdu) {
    	/**
    	 * Accept data version
    	 * Static - most recent block
    	 */
    	byte[] message = apdu.getBuffer();
    	
    	// C-APDU - Header (CLA, INS, P1, P2, P3) Body (Lc field, Data field, Le field)   		
    	
    	if (apdu.isISOInterindustryCLA()) {
    		// 0x00
    		if (message[ISO7816.OFFSET_INS] == GET_RESPONSE) {
		    	// 0xC0 - get response,   		
				switch (message[ISO7816.OFFSET_P1]) {
					case TEASL_CE:
			            teaslce(apdu);
			            return;
			        case TEASL_SE:
			        	teaslse(apdu);
			        	return;
			        case EASL:
			            easl(apdu);
			            return;
			        case TEASL_CE_SIZE:
			        	teaslceSize(apdu);
			        	return;
			        case TEASL_SE_SIZE:
			        	teaslseSize(apdu);
			        	return;
			        default:
			            ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
				}
    		}else
    			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
    	}else
    		ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);
    }
    
    public class equalNode<T>{
    	// stores values for equal node
    	public byte[] block = new byte[3];
    	public byte[] transaction = new byte[2];
    	
    	// block height, transaction number
    	public equalNode(byte[] block, byte[] transaction) {
    		this.block = block;
    		this.transaction = transaction;
    	}
    }
    
    public class teaslceNode<T>{
    	// define the compute efficient index node
    	    	
    	public byte version;
    	public teaslceNode<T> leftPointer, rightPointer;
    	public equalNode<T> equalPointer;
    	
    	public teaslceNode(byte version, equalNode<T> equal, teaslceNode<T> left, teaslceNode<T> right) {
    		this.version = version;
    		this.leftPointer = left;
    		this.rightPointer = right;
    		this.equalPointer = equal;
    	}
    }
      
    public class teaslseNode<T>{
    	// define the storage efficient parent index node
    	
    	public byte[] version;
    	public leafEqualNode equalPointer; 
    }
    
    
    public class teaslseIntNode extends teaslseNode {
    	// define the storage efficient intermediate index node
    	
    	public teaslseNode leftPointer;
    	public teaslseNode rightPointer;
    	
    	public teaslseIntNode(byte[] version, teaslseNode left, leafEqualNode equal, teaslseNode right) {
    		this.version = version;
    		this.equalPointer = equal;
    		this.leftPointer = left;
    		this.rightPointer = right;
    	}
    }
    
    public class teaslseLeafNode extends teaslseNode {
    	public leafEqualNode leftPointer;
    	public leafEqualNode rightPointer;
    	
    	public teaslseLeafNode(byte[] version, leafEqualNode left, leafEqualNode equal, leafEqualNode right) {
    		this.version = version;
    		this.equalPointer = equal;
    		this.leftPointer = left;
    		this.rightPointer = right;
    	}
    }
    
    
    public class leafEqualNode<T>{
    	// stores values for equal node 
    	
    	public byte[] block;
    	public byte[] transaction;
    	
    	// block height, transaction number
    	public leafEqualNode(byte[] block, byte[] transaction) {
    		this.block = block;
    		this.transaction = transaction;
    	}
    }
       
    public teaslceNode teaslceTree() {
    	
    	teaslceNode rootNode = null;
     	            		
		rootNode = new teaslceNode((byte) 0,null,null,null); // initialise
		    		        	     	
        // construct ternary tree
		
        byte node1Version = (byte) 0x01;
        byte[] node1Block = {(byte) 0x34,(byte) 0x6C,(byte) 0x76};
        byte[] node1Transaction = {(byte) 0x09,(byte) 0x0E};
        equalNode node1Equal = new equalNode(node1Block, node1Transaction);
        teaslceNode node1 = new teaslceNode(node1Version,node1Equal,null,null);
        
        byte node3Version = (byte) 0x03;
        byte[] node3Block = {(byte) 0x34,(byte) 0x6C,(byte) 0x83};
        byte[] node3Transaction = {(byte) 0x0C,(byte) 0xF7};
        equalNode node3Equal = new equalNode(node3Block,node3Transaction);
        teaslceNode node3 = new teaslceNode(node3Version,node3Equal,null,null);
        
        byte node2Version = (byte) 0x02;
        byte[] node2Block = {(byte) 0x34, (byte) 0x6C, (byte) 0x81};
        byte[] node2Transaction = {(byte) 0x07,(byte) 0x5B};
        equalNode node2Equal = new equalNode(node2Block,node2Transaction);
        teaslceNode node2 = new teaslceNode(node2Version,node2Equal,node1,node3);
        
        byte node5Version =  (byte) 0x05;
        byte[] node5Block = {(byte) 0x34,(byte) 0x6C,(byte) 0x93};
        byte[] node5Transaction = {(byte) 0x04,(byte) 0x07};
        equalNode node5Equal = new equalNode(node5Block,node5Transaction);
        teaslceNode node5 = new teaslceNode(node5Version,node5Equal,null,null);
        
        byte node7Version = (byte) 0x07;
        byte[] node7Block =  {(byte) 0x34,(byte) 0x6C,(byte) 0xA1};
        byte[] node7Transaction = {(byte) 0x01,(byte) 0xB6};
        equalNode node7Equal = new equalNode(node7Block,node7Transaction);
        teaslceNode node7 = new teaslceNode(node7Version,node7Equal,null,null);
        
        byte node6Version = (byte) 0x06;
        byte[] node6Block = {(byte) 0x34,(byte) 0x6C,(byte) 0x96};
        byte[] node6Transaction = {(byte) 0x04,(byte) 0xB4};
        equalNode node6Equal = new equalNode(node6Block,node6Transaction);
        teaslceNode node6 = new teaslceNode(node6Version,node6Equal,node5,node7);
        
        byte node4Version = (byte) 0x04;
        byte[] node4Block = {(byte) 0x34,(byte) 0x6C, (byte) 0x8B};
        byte[] node4Transaction = {(byte) 0x06, (byte) 0x09};
        equalNode node4Equal = new equalNode(node4Block,node4Transaction);
        teaslceNode node4 = new teaslceNode(node4Version,node4Equal,node2,node6);
        
        byte node15Version = (byte) 0x0F;
        byte[] node15Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xE6};
        byte[] node15Transaction = {(byte) 0x07,(byte) 0x4A};
        equalNode node15Equal = new equalNode(node15Block,node15Transaction);
        teaslceNode node15 = new teaslceNode(node15Version,node15Equal,null,null);
        
        byte node13Version = (byte) 0x0D;
        byte[] node13Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xDA};
        byte[] node13Transaction = {(byte) 0x06,(byte) 0xEC};
        equalNode node13Equal = new equalNode(node13Block,node13Transaction);
        teaslceNode node13 = new teaslceNode(node13Version,node13Equal,null,null);
        
        byte node14Version = (byte) 0x0E;
        byte[] node14Block = {(byte) 0x34 ,(byte) 0x6C,(byte) 0xDD};
        byte[] node14Transaction = {(byte) 0x0C,(byte) 0x96};
        equalNode node14Equal = new equalNode(node14Block,node14Transaction);
        teaslceNode node14 = new teaslceNode(node14Version,node14Equal,node13,node15);
        
        byte node9Version = (byte) 0x09;
        byte[] node9Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xB0};
        byte[] node9Transaction = {(byte) 0x0A,(byte) 0x94};
        equalNode node9Equal = new equalNode(node9Block,node9Transaction);
        teaslceNode node9 = new teaslceNode(node9Version,node9Equal,null,null);
        
        byte node11Version = (byte) 0x0B;
        byte[] node11Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xC9};
        byte[] node11Transaction = {(byte) 0x00,(byte) 0x8E};
        equalNode node11Equal = new equalNode(node11Block,node11Transaction);
        teaslceNode node11 = new teaslceNode(node11Version,node11Equal,null,null);
        
        byte node10Version = (byte) 0x0A;
        byte[] node10Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xB7};
        byte[] node10Transaction = {(byte) 0x0A,(byte) 0x29};
        equalNode node10Equal = new equalNode(node10Block,node10Transaction);
        teaslceNode node10 = new teaslceNode(node10Version,node10Equal,node9,node11);
        
        byte node12Version = (byte) 0x0C;
        byte[] node12Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xCE};
        byte[] node12Transaction = {(byte) 0x0A,(byte) 0xC4};
        equalNode node12Equal = new equalNode(node12Block,node12Transaction);
        teaslceNode node12 = new teaslceNode(node12Version,node12Equal,node10,node14);
        
        byte node8Version = (byte) 0x08;
        byte[] node8Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xA4};
        byte[] node8Transaction = {(byte) 0x09,(byte) 0x31};
        equalNode node8Equal = new equalNode(node8Block,node8Transaction);
        teaslceNode node8 = new teaslceNode(node8Version,node8Equal,node4,node12);
        
        byte node19Version = (byte) 0x13;
        byte[] node19Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xF8};
        byte[] node19Transaction = {(byte) 0x09,(byte) 0xD1};
        equalNode node19Equal = new equalNode(node19Block,node19Transaction);
        teaslceNode node19 = new teaslceNode(node19Version,node19Equal,null,null);
        
        byte node17Version = (byte) 0x11;
        byte[] node17Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xF2};
        byte[] node17Transaction = {(byte) 0x06,(byte) 0x85};
        equalNode node17Equal = new equalNode(node17Block,node17Transaction);
        teaslceNode node17 = new teaslceNode(node17Version,node17Equal,null,null);
        
        byte node18Version = (byte) 0x12;
        byte[] node18Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xF6};
        byte[] node18Transaction = {(byte) 0x04,(byte) 0x2D};
        equalNode node18Equal = new equalNode(node18Block,node18Transaction);
        teaslceNode node18 = new teaslceNode(node18Version,node18Equal,node17,node19);
        
        byte node20Version = (byte) 0x14;
        byte[] node20Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xFB};
        byte[] node20Transaction = {(byte) 0x0A,(byte) 0x71};
        equalNode node20Equal = new equalNode(node20Block,node20Transaction);
        teaslceNode node20 = new teaslceNode(node20Version,node20Equal,node18,null);
        
        byte node16Version = (byte) 0x10;
        byte[] node16Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xEC};
        byte[] node16Transaction = {(byte) 0x0C,(byte) 0x25};
        equalNode node16Equal = new equalNode(node16Block,node16Transaction);
        teaslceNode node16 = new teaslceNode(node16Version,node16Equal,node8,node20);
        rootNode = node16;
        
    	return rootNode;
    }
    
    public teaslseIntNode teaslseTree() {
    	
    	byte[] version = {0};
    	teaslseIntNode rootNode = null;
    	    	    		
		rootNode = new teaslseIntNode(version,null,null,null); // initialise;
			     	
        // construct ternary tree
		
        byte[] node123Version = {(byte) 0x01,(byte) 0x02,(byte) 0x03};
        
        byte[] node1Block = {(byte) 0x34,(byte) 0x6C,(byte) 0x76};
        byte[] node1Transaction = {(byte) 0x09,(byte) 0x0E}; 
        leafEqualNode node123Left = new leafEqualNode(node1Block,node1Transaction);
        
        byte[] node2Block = {(byte) 0x34,(byte) 0x6C,(byte) 0x81};
        byte[] node2Transaction = {(byte) 0x07,(byte) 0x5B};
        leafEqualNode node123Equal = new leafEqualNode(node2Block,node2Transaction);
        
        byte[] node3Block = {(byte) 0x34,(byte) 0x6C,(byte) 0x83};
        byte[] node3Transaction = {(byte) 0x0C,(byte) 0xF7};
        leafEqualNode node123Right = new leafEqualNode(node3Block,node3Transaction);
        
        teaslseLeafNode node123 = new teaslseLeafNode(node123Version,node123Left,node123Equal,node123Right);
        
        
        byte[] node567Version = {(byte) 0x05,(byte) 0x06,(byte) 0x07};
        
        byte[] node5Block = {(byte) 0x34,(byte) 0x6C,(byte) 0x93};
        byte[] node5Transaction = {(byte) 0x04,(byte) 0x07};
        leafEqualNode node567Left = new leafEqualNode(node5Block,node5Transaction);
        
        byte[] node6Block = {(byte) 0x34,(byte) 0x6C,(byte) 0x96};
        byte[] node6Transaction = {(byte) 0x04,(byte) 0xB4};
        leafEqualNode node567Equal = new leafEqualNode(node6Block,node6Transaction);
        
        byte[] node7Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xA1};
        byte[] node7Transaction = {(byte) 0x01,(byte) 0xB6};
        leafEqualNode node567Right = new leafEqualNode(node7Block,node7Transaction);
        
        teaslseLeafNode node567 = new teaslseLeafNode(node567Version,node567Left,node567Equal,node567Right);
        
         
        byte[] node4Version = {(byte) 0x04};
        
        byte[] node4Block = {(byte) 0x34,(byte) 0x6C,(byte) 0x8B};
        byte[] node4Transaction = {(byte) 0x06,(byte) 0x09};
        leafEqualNode node4Equal = new leafEqualNode(node4Block,node4Transaction);
        
        teaslseIntNode node4 = new teaslseIntNode(node4Version,node123,node4Equal,node567);
        
        
        byte[] node91011Version = {(byte) 0x09,(byte) 0x0A,(byte) 0x0B};
                
        byte[] node9Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xB0};
        byte[] node9Transaction = {(byte) 0x0A,(byte) 0x94};
        leafEqualNode node91011Left = new leafEqualNode(node9Block,node9Transaction);
        
        byte[] node10Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xB7};
        byte[] node10Transaction = {(byte) 0x0A,(byte) 0x29};
        leafEqualNode node91011Equal = new leafEqualNode(node10Block,node10Transaction);
        
        byte[] node11Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xC9};
        byte[] node11Transaction = {(byte) 0x00,(byte) 0x8E};
        leafEqualNode node91011Right = new leafEqualNode(node11Block,node11Transaction);
        
        teaslseLeafNode node91011 = new teaslseLeafNode(node91011Version,node91011Left,node91011Equal,node91011Right);
                    
        
        byte[] node131415Version = {(byte) 0x0D,(byte) 0x0E,(byte) 0x0F};
         
        byte[] node13Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xDA};
        byte[] node13Transaction = {(byte) 0x06,(byte) 0xEC};
        leafEqualNode node131415Left = new leafEqualNode(node13Block,node13Transaction);
        
        byte[] node14Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xDD};
        byte[] node14Transaction = {(byte) 0x0C,(byte) 0x96};
        leafEqualNode node131415Equal = new leafEqualNode(node14Block,node14Transaction);
        
        byte[] node15Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xE6};
        byte[] node15Transaction = {(byte) 0x07,(byte) 0x4A};
        leafEqualNode node131415Right = new leafEqualNode(node15Block,node15Transaction);
        
        teaslseLeafNode node131415 = new teaslseLeafNode(node131415Version,node131415Left,node131415Equal,node131415Right);
                
        
        byte[] node12Version = {(byte) 0x0C};
        
        byte[] node12Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xCE};
        byte[] node12Transaction = {(byte) 0x0A,(byte) 0xC4};
        leafEqualNode node12Equal = new leafEqualNode(node12Block,node12Transaction);
        
        teaslseIntNode node12 = new teaslseIntNode(node12Version,node91011,node12Equal,node131415);
        
        
        byte[] node8Version = {(byte) 0x08};
        
        byte[] node8Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xA4};
        byte[] node8Transaction = {(byte) 0x09,(byte) 0x31};
        leafEqualNode node8Equal = new leafEqualNode(node8Block,node8Transaction);
        teaslseIntNode node8 = new teaslseIntNode(node8Version,node4,node8Equal,node12);
        
        
        byte[] node171819Version = {(byte) 0x11,(byte) 0x12,(byte) 0x13};
        
        byte[] node17Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xF2};
        byte[] node17Transaction = {(byte) 0x06,(byte) 0x85};
        leafEqualNode node171819Left = new leafEqualNode(node17Block,node17Transaction);
        
        byte[] node18Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xF6};
        byte[] node18Transaction = {(byte) 0x04,(byte) 0x2D};
        leafEqualNode node171819Equal = new leafEqualNode(node18Block,node18Transaction);
        
        byte[] node19Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xF8};
        byte[] node19Transaction = {(byte) 0x09,(byte) 0xD1};
        leafEqualNode node171819Right = new leafEqualNode(node19Block,node19Transaction);
        
        teaslseLeafNode node171819 = new teaslseLeafNode(node171819Version,node171819Left,node171819Equal,node171819Right);
        
        
        byte[] node20Version = {(byte) 0x14};
        
        byte[] node20Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xFB};
        byte[] node20Transaction = {(byte) 0x0A,(byte) 0x71};
        leafEqualNode node20Equal = new leafEqualNode(node20Block,node20Transaction);
        
        teaslseIntNode node20 = new teaslseIntNode(node20Version,node171819,node20Equal,null);
        
        
        byte[] node16Version = {(byte) 0x10};
        
        byte[] node16Block = {(byte) 0x34,(byte) 0x6C,(byte) 0xEC};
        byte[] node16Transaction = {(byte) 0x0C,(byte) 0x25};
        leafEqualNode node16Equal = new leafEqualNode(node16Block,node16Transaction);
        
        teaslseIntNode node16 = new teaslseIntNode(node16Version,node8,node16Equal,node20);

        rootNode = node16;
        
    	return rootNode;
    }
    
    private void teaslce(APDU apdu) {
    	/**
    	 * Get version
    	 * Search ternary tree (node data block height and transaction number)
    	 * return (node data block height and transaction number)
    	 */
    	byte initialise = (byte) 0; 
    	
    	byte[] message = apdu.getBuffer();
    	
    	// extract the version to be queried
        byte byteCount = message[ISO7816.OFFSET_LC];
        byte byteOffset = (byte) (apdu.setIncomingAndReceive());
        if ((byteCount != 1) || (byteOffset != 1)) {
            ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
        }

        // get the version to be queried (search version)
        byte searchVersion = message[ISO7816.OFFSET_CDATA];
        
        byte[] emptyBlock;
        byte[] emptyTransaction;
        equalNode fallbackVersion;
        equalNode closestVersion;
        byte closestVersionNumber;
        
        // lookup the location of the version
        
        teaslceNode currentNode = teaslceTree(); // begin at the root node
        
        
    	emptyBlock = new byte[3];
    	emptyTransaction = new byte[2];
    	fallbackVersion = new equalNode(emptyBlock,emptyTransaction);
    	closestVersion = new equalNode(emptyBlock,emptyTransaction);
    	closestVersionNumber = (byte) 0x00;

        
        while (true){
        	if (currentNode.version >= searchVersion) {
        		fallbackVersion.block = currentNode.equalPointer.block;
        		fallbackVersion.transaction = currentNode.equalPointer.transaction;
        	}
        	if (currentNode.version == searchVersion || (currentNode.leftPointer == null && currentNode.rightPointer == null)) {
        		closestVersion.block = currentNode.equalPointer.block;
        		closestVersion.transaction = currentNode.equalPointer.transaction;
        		closestVersionNumber = currentNode.version;
        		break;	
        	}
        	else if (currentNode.version > searchVersion) {
        		if (currentNode.leftPointer != null) {
        			currentNode = currentNode.leftPointer;
        			continue;
        		}else
            		closestVersion.block = currentNode.equalPointer.block;
            		closestVersion.transaction = currentNode.equalPointer.transaction;
        			closestVersionNumber = currentNode.version;
        			break;
        	}else if (currentNode.version < searchVersion) {
        		if (currentNode.rightPointer != null) {
        			currentNode = currentNode.rightPointer;
        			continue;
        		}else {
            		closestVersion.block = currentNode.equalPointer.block;
            		closestVersion.transaction = currentNode.equalPointer.transaction;
        			closestVersionNumber = currentNode.version;
        			break;
        		}
        	}
        }
        if (closestVersionNumber < searchVersion) {
    		closestVersion.block = fallbackVersion.block;
    		closestVersion.transaction = fallbackVersion.transaction;	
        }
        
        // return data initialise
        apdu.setOutgoing();
        apdu.setOutgoingLength((byte) 5);

        // moves the return data (closest_version) into the message buffer
        message [0] = (byte) (closestVersion.block[0]);
        message [1] = (byte) (closestVersion.block[1]);
        message [2] = (byte) (closestVersion.block[2]);
        message [3] = (byte) (closestVersion.transaction[0]);
        message [4] = (byte) (closestVersion.transaction[1]);
        		
        // send the 1-byte data with an offset of 0
        apdu.sendBytes((short) 0, (short) 5);
    	
    }
    
    private void teaslse(APDU apdu) {
    	/**
    	 * Get version
    	 * Search ternary tree (node data block height and transaction number)
    	 * return (node data block height and transaction number)
    	 */
    	byte initialise = (byte) 0;
    	
    	byte[] message = apdu.getBuffer();
    	
    	// extract the version to be queried
        byte byteCount = message[ISO7816.OFFSET_LC];
        byte byteOffset = (byte) (apdu.setIncomingAndReceive());
        if ((byteCount != 1) || (byteOffset != 1)) {
            ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
        }

        // get the version to be queried (search version)
        byte searchVersion = message[ISO7816.OFFSET_CDATA];
        
        byte[] emptyBlock;
        byte[] emptyTransaction;
        byte closestVersionNumber;
        leafEqualNode fallbackVersion;
        leafEqualNode closestVersion;
        
        // lookup the location of the version
        
        teaslseIntNode currentNode = (teaslseIntNode) teaslseTree(); // begin at the root node
        teaslseLeafNode currentLeafNode = null;
        
        emptyBlock = new byte[3];
        emptyTransaction = new byte[2];
        closestVersionNumber = (byte) 0x00;
        fallbackVersion = new leafEqualNode(emptyBlock,emptyTransaction);
        closestVersion = new leafEqualNode(emptyBlock,emptyTransaction);
        initialise = (byte) 1;
        
	    
        while (true){ 	
            // closest version if not built from level 0
        	if (currentNode.version[0] >= searchVersion) {
        		// fallback Version
        		fallbackVersion.block = currentNode.equalPointer.block;
        		fallbackVersion.transaction = currentNode.equalPointer.transaction;
        	}
        	        	        	
        	// intermediate node
        	if (currentNode.version[0] == searchVersion) {
        		closestVersion.block = currentNode.equalPointer.block;
        		closestVersion.transaction = currentNode.equalPointer.transaction;
        		closestVersionNumber = currentNode.version[0];
        		break;	
        	}else if (currentNode.version[0] > searchVersion) {
    			if (currentNode.leftPointer instanceof teaslseIntNode) {
    				currentNode = (teaslseIntNode) currentNode.leftPointer;
    				continue;
    			}else {
    				break;
    			}
        	}else if (currentNode.version[0] < searchVersion) {
    			if (currentNode.rightPointer instanceof teaslseIntNode) {
    				currentNode = (teaslseIntNode) currentNode.rightPointer;
    				continue;
    			}else {
    				break;
    			}
        	}
        }
                
        if ((currentNode.leftPointer instanceof teaslseLeafNode) || (currentNode.rightPointer instanceof teaslseLeafNode)) {
        	// process the leaf node
        	byte leafSize;
	        if (searchVersion < currentNode.version[0] && currentNode.leftPointer != null) {
	        	// leaf node to left
	        	currentLeafNode = (teaslseLeafNode) currentNode.leftPointer;
	        	if (currentLeafNode.version[0] == searchVersion) {
	        		closestVersion.block = currentLeafNode.leftPointer.block;
	        		closestVersion.transaction = currentLeafNode.leftPointer.transaction;
	        		closestVersionNumber = currentLeafNode.version[0];
	        	}else if (currentLeafNode.version[1] == searchVersion) {
	        		closestVersion.block = currentLeafNode.equalPointer.block;
	        		closestVersion.transaction = currentLeafNode.equalPointer.transaction;
	        		closestVersionNumber = currentLeafNode.version[1];
	        	}else if (currentLeafNode.version[2] == searchVersion) {
	        		closestVersion.block = currentLeafNode.rightPointer.block;
	        		closestVersion.transaction = currentLeafNode.rightPointer.transaction;
	        		closestVersionNumber = currentLeafNode.version[2];
	        	}
	        }else if (searchVersion > currentNode.version[0] && currentNode.rightPointer != null) {
	        	// leaf node to right
	        	currentLeafNode = (teaslseLeafNode) currentNode.rightPointer;
	        	if (currentLeafNode.version[0] == searchVersion) {
	        		closestVersion.block = currentLeafNode.leftPointer.block;
	        		closestVersion.transaction = currentLeafNode.leftPointer.transaction;
	        		closestVersionNumber = currentLeafNode.version[0];
	        	}else if (currentLeafNode.version[1] == searchVersion) {
	        		closestVersion.block = currentLeafNode.equalPointer.block;
	        		closestVersion.transaction = currentLeafNode.equalPointer.transaction;
	        		closestVersionNumber = currentLeafNode.version[1];
	        	}else if (currentLeafNode.version[2] == searchVersion) {
	        		closestVersion.block = currentLeafNode.rightPointer.block;
	        		closestVersion.transaction = currentLeafNode.rightPointer.transaction;
	        		closestVersionNumber = currentLeafNode.version[2];
	        	}
	        }
        }
        
        if (closestVersionNumber < searchVersion) {
        	closestVersion.block = fallbackVersion.block;
    		closestVersion.transaction = fallbackVersion.transaction;
        }
                                        
        // return data initialise
        apdu.setOutgoing();
        apdu.setOutgoingLength((byte) 5);

        // moves the return data (closest_version) into the message buffer
        message [0] = (byte) (closestVersion.block[0]);
        message [1] = (byte) (closestVersion.block[1]);
        message [2] = (byte) (closestVersion.block[2]);
        message [3] = (byte) (closestVersion.transaction[0]);
        message [4] = (byte) (closestVersion.transaction[1]);

        		
        // send the 5-bytes data with an offset of 0
        apdu.sendBytes((short) 0, (short) 5);
    	
    }
    
    private void easl(APDU apdu) {
    	/**
    	 * Get version
    	 * Until version is equal
    	 * 		Get index data
    	 * 		Compare index
    	 * return (node data block height and transaction number)
    	 */
    	byte[] message = apdu.getBuffer();
    	
    	// encoding query version (8-bit), EASL node version (8-bit), 
    	// each skip list entry (<= 12) (56-bit) [version (8-bit), block height (24-bit), transaction number (16-bit)]
    	   	
    	// extract the version to be queried
        byte byteCount = message[ISO7816.OFFSET_LC];
        byte byteOffset = (byte) (apdu.setIncomingAndReceive());
        
        // loads the data into array
        byte[] data = null;
        if (data == null) {
        	data = new byte[byteCount]; // initialise
        }
        
        Util.arrayCopy(message, ISO7816.OFFSET_CDATA, data, (short) 0, byteCount);
        // 1 bytes query version
        byte queryVersion = data[0];
        // 1 bytes node version
        byte nodeVersion = data[1];
        
        // node skip list level processing - pre-nodes (6 bytes each)
        short nodeSize = (short) 6;
        // start from the highest skip list node
        int l = data.length;
        // currentOffsetCounter
        short k = (short) l;
        // node offset
        short i = (short) 2;
        
        // total number of pre-nodes
        short preNodeCount = (short) ((k - i) / nodeSize);

        // location of next or matching EASL node
        byte[] block = null;
        byte[] transaction = null;
        
        if (block == null || transaction == null){
        	block = new byte[3];
        	transaction = new byte[2];
        }
                
        // for each pre-node version define valid block
        for(short j=0; j < preNodeCount; j++){
        	
        	//set next node offset
        	k = (short) (k - nodeSize);
        	
            // 1 bytes pre-node version
        	byte preNodeVersion = data[k];
        	
	        if (preNodeVersion <= queryVersion) {
	        	// location of next or matching EASL node
		        // 3 bytes block height
                block[0] = data[(short) (k+1)];
                block[1] = data[(short) (k+2)];
                block[2] = data[(short) (k+3)];
		        // 2 bytes transaction number
                transaction[0] = data[(short) (k+4)];
                transaction[1] = data[(short) (k+5)];
	        }
        }
        
        // return data initialise
        apdu.setOutgoing();
        apdu.setOutgoingLength((byte) 5);
        
        
        // moves the return data (closest_version) into the message buffer
        message [0] = (byte) (block[0]);
        message [1] = (byte) (block[1]);
        message [2] = (byte) (block[2]);
        message [3] = (byte) (transaction[0]);
        message [4] = (byte) (transaction[1]);

        		
        // send the 5-bytes data with an offset of 0
        apdu.sendBytes((short) 0, (short) 5);
    	
    }
    
    private void teaslceSize(APDU apdu) {
    	// outputs the storage consumption of ternary tree
    	byte[] message = apdu.getBuffer();
    	byte byteOffset = (byte) (apdu.setIncomingAndReceive());
    	    	
    	// max reported on is 7FFF - 32767 - 0
        short T1 = JCSystem.getAvailableMemory(JCSystem.MEMORY_TYPE_PERSISTENT);
        // build tree
        teaslceNode currentNode = teaslceTree();
        short T2 = JCSystem.getAvailableMemory(JCSystem.MEMORY_TYPE_PERSISTENT);
        
        
    	// return data initialise
        apdu.setOutgoing();
        apdu.setOutgoingLength((byte) 4);
        
        byte b1 = (byte) (T1 >> 8);
        byte b2 = (byte) T1;
        byte b3 = (byte) (T2 >> 8); 
        byte b4 = (byte) T2;		
        
        message [0] = (byte) (b1);
        message [1] = (byte) (b2);
        message [2] = (byte) (b3);
        message [3] = (byte) (b4);
        
        // send the 4-bytes data with an offset of 0
        apdu.sendBytes((short) 0, (short) 4);	
    }
    
    private void teaslseSize(APDU apdu) {
    	// outputs the storage consumption of ternary tree
    	byte[] message = apdu.getBuffer();
    	byte byteOffset = (byte) (apdu.setIncomingAndReceive());
    	    	
        // max reported on is 7FFF - 32767 - 0  
        short T1 = JCSystem.getAvailableMemory(JCSystem.MEMORY_TYPE_PERSISTENT);
        // build tree
        //teaslseNode currentNode = teaslseTree();
        
        byte[] node123Version = {(byte) 0x01,(byte) 0x02};
        
        byte[] node1Block = {(byte) 0x34,(byte) 0x6C,(byte) 0x76};
        byte[] node1Transaction = {(byte) 0x09,(byte) 0x0E}; 
        leafEqualNode node123Left = new leafEqualNode(node1Block,node1Transaction);
        
        byte[] node2Block = {(byte) 0x34,(byte) 0x6C,(byte) 0x81};
        byte[] node2Transaction = {(byte) 0x07,(byte) 0x5B};
        leafEqualNode node123Equal = new leafEqualNode(node2Block,node2Transaction);
        
        byte[] node3Block = {(byte) 0x34,(byte) 0x6C,(byte) 0x83};
        byte[] node3Transaction = {(byte) 0x0C,(byte) 0xF7};
        leafEqualNode node123Right = new leafEqualNode(node3Block,node3Transaction);
        
        teaslseLeafNode node123 = new teaslseLeafNode(node123Version,node123Left,node123Equal,null);
        
        short T2 = JCSystem.getAvailableMemory(JCSystem.MEMORY_TYPE_PERSISTENT);
        
        
    	// return data initialise
        apdu.setOutgoing();
        apdu.setOutgoingLength((byte) 4);
        
        byte b1 = (byte) (T1 >> 8);
        byte b2 = (byte) T1;
        byte b3 = (byte) (T2 >> 8); 
        byte b4 = (byte) T2;		
        
        message [0] = (byte) (b1);
        message [1] = (byte) (b2);
        message [2] = (byte) (b3);
        message [3] = (byte) (b4);
        
        // send the 4-bytes data with an offset of 0
        apdu.sendBytes((short) 0, (short) 4);
    }
}
