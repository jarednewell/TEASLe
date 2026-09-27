import sys
import time
import gc

#
# MicroPython - executed on STM32 microcontroller 
# Ported from Java
#

class equalNode:
    # stores values for equal node
    block = None
    transaction = None
    
    # block height, transaction number
    def __init__(self, block, transaction):
        self.block = block
        self.transaction = transaction


class teaslceNode:
    # define the compute efficient index node
    version = None
    leftPointer = None
    rightPointer = None
    equalPointer = None
    
    # version, left, equal and right pointers
    def __init__(self, version, equal, left, right):
        self.version = version
        self.leftPointer = left
        self.rightPointer = right
        self.equalPointer = equal


class leafEqualNode:
    # stores values for equal node
    block = []
    transaction = []
        
    # block height, transaction number
    def __init__(self, block, transaction):
        self.block = block
        self.transaction = transaction


class teaslseNode():
    # define the storage efficient parent index node
    version = None
    equalPointer = None
    
    def __init__(self, version, equal):
        self.version = version
        self.equalPointer = equal
    

class teaslseIntNode(teaslseNode):
    # define the storage efficient intermediate index node
    leftPointer = None
    rightPointer = None
    
    # version, left, equal and right pointers
    def __init__(self, version, left, equal, right):
        super().__init__(version, equal)
        self.leftPointer = left
        self.rightPointer = right
        

class teaslseLeafNode(teaslseNode):
    # define the storage efficient leaf index node
    leftPointer = None
    rightPointer = None
    
    # version, left, equal and right pointers
    def __init__(self, version, left, equal, right):
        super().__init__(version, equal)
        self.leftPointer = left
        self.rightPointer = right


def teaslceTree():
    # build the compute efficient ternary tree
    
    isbuilt = False;
    rootNode = None;
    
    if isbuilt == False:
                
        node1Version = int.from_bytes(b'\x01','big')
        node1Block = int.from_bytes(b'\x34\x6C\x76','big')
        node1Transaction = int.from_bytes(b'\x09\x0E','big')
        node1Equal = equalNode(node1Block,node1Transaction)
        node1 = teaslceNode(node1Version,node1Equal,None,None)
    
        node3Version = int.from_bytes(b'\x03','big')
        node3Block = int.from_bytes(b'\x34\x6C\x83','big')
        node3Transaction = int.from_bytes(b'\x0C\xF7','big')
        node3Equal = equalNode(node3Block,node3Transaction)
        node3 = teaslceNode(node3Version,node3Equal,None,None)
            
        node2Version = int.from_bytes(b'\x02','big')
        node2Block = int.from_bytes(b'\x34\x6C\x81','big')
        node2Transaction = int.from_bytes(b'\x07\x5B','big')
        node2Equal = equalNode(node2Block,node2Transaction)
        node2 = teaslceNode(node2Version,node2Equal,node1,node3)
    
        node5Version =  int.from_bytes(b'\x05','big')
        node5Block = int.from_bytes(b'\x34\x6C\x93','big')
        node5Transaction = int.from_bytes(b'\x04\x07','big')
        node5Equal = equalNode(node5Block,node5Transaction)
        node5 = teaslceNode(node5Version,node5Equal,None,None)
    
        node7Version = int.from_bytes(b'\x07','big')
        node7Block =  int.from_bytes(b'\x34\x6C\xA1','big')
        node7Transaction = int.from_bytes(b'\x01\xB6','big')
        node7Equal = equalNode(node7Block,node7Transaction)
        node7 = teaslceNode(node7Version,node7Equal,None,None)
    
        node6Version = int.from_bytes(b'\x06','big')
        node6Block = int.from_bytes(b'\x34\x6C\x96','big')
        node6Transaction = int.from_bytes(b'\x04\xB4','big')
        node6Equal = equalNode(node6Block,node6Transaction)
        node6 = teaslceNode(node6Version,node6Equal,node5,node7)
    
        node4Version = int.from_bytes(b'\x04','big')
        node4Block = int.from_bytes(b'\x34\x6C\x8B','big')
        node4Transaction = int.from_bytes(b'\x06\x09','big')
        node4Equal = equalNode(node4Block,node4Transaction)
        node4 = teaslceNode(node4Version,node4Equal,node2,node6)
    
        node15Version = int.from_bytes(b'\x0F','big')
        node15Block = int.from_bytes(b'\x34\x6C\xE6','big')
        node15Transaction = int.from_bytes(b'\x07\x4A','big')
        node15Equal = equalNode(node15Block,node15Transaction)
        node15 = teaslceNode(node15Version,node15Equal,None,None)
    
        node13Version = int.from_bytes(b'\x0D','big')
        node13Block = int.from_bytes(b'\x34\x6C\xDA','big')
        node13Transaction = int.from_bytes(b'\x06\xEC','big')
        node13Equal = equalNode(node13Block,node13Transaction)
        node13 = teaslceNode(node13Version,node13Equal,None,None)
    
        node14Version = int.from_bytes(b'\x0E','big')
        node14Block = int.from_bytes(b'\x34\x6C\xDD','big')
        node14Transaction = int.from_bytes(b'\x0C\x96','big')
        node14Equal = equalNode(node14Block,node14Transaction)
        node14 = teaslceNode(node14Version,node14Equal,node13,node15)
    
        node9Version = int.from_bytes(b'\x09','big')
        node9Block = int.from_bytes(b'\x34\x6C\xB0','big')
        node9Transaction = int.from_bytes(b'\x0A\x94','big')
        node9Equal = equalNode(node9Block,node9Transaction)
        node9 = teaslceNode(node9Version,node9Equal,None,None)
    
        node11Version = int.from_bytes(b'\x0B','big')
        node11Block = int.from_bytes(b'\x34\x6C\xC9','big')
        node11Transaction = int.from_bytes(b'\x00\x8E','big')
        node11Equal = equalNode(node11Block,node11Transaction)
        node11 = teaslceNode(node11Version,node11Equal,None,None)
    
        node10Version = int.from_bytes(b'\x0A','big')
        node10Block = int.from_bytes(b'\x34\x6C\xB7','big')
        node10Transaction = int.from_bytes(b'\x0A\x29','big')
        node10Equal = equalNode(node10Block,node10Transaction)
        node10 = teaslceNode(node10Version,node10Equal,node9,node11)
    
        node12Version = int.from_bytes(b'\x0C','big')
        node12Block = int.from_bytes(b'\x34\x6C\xCE','big')
        node12Transaction = int.from_bytes(b'\x0A\xC4','big')
        node12Equal = equalNode(node12Block,node12Transaction)
        node12 = teaslceNode(node12Version,node12Equal,node10,node14)
    
        node8Version = int.from_bytes(b'\x08','big')
        node8Block = int.from_bytes(b'\x34\x6C\xA4','big')
        node8Transaction = int.from_bytes(b'\x09\x31','big')
        node8Equal = equalNode(node8Block,node8Transaction)
        node8 = teaslceNode(node8Version,node8Equal,node4,node12)
    
        node19Version = int.from_bytes(b'\x13','big')
        node19Block = int.from_bytes(b'\x34\x6C\xF8','big')
        node19Transaction = int.from_bytes(b'\x09\xD1','big')
        node19Equal = equalNode(node19Block,node19Transaction)
        node19 = teaslceNode(node19Version,node19Equal,None,None)
    
        node17Version = int.from_bytes(b'\x11','big')
        node17Block = int.from_bytes(b'\x34\x6C\xF2','big')
        node17Transaction = int.from_bytes(b'\x06\x85','big')
        node17Equal = equalNode(node17Block,node17Transaction)
        node17 = teaslceNode(node17Version,node17Equal,None,None)
    
        node18Version = int.from_bytes(b'\x12','big')
        node18Block = int.from_bytes(b'\x34\x6C\xF6','big')
        node18Transaction = int.from_bytes(b'\x04\x2D','big')
        node18Equal = equalNode(node18Block,node18Transaction)
        node18 = teaslceNode(node18Version,node18Equal,node17,node19)
    
        node20Version = int.from_bytes(b'\x14','big')
        node20Block = int.from_bytes(b'\x34\x6C\xFB','big')
        node20Transaction = int.from_bytes(b'\x0A\x71','big')
        node20Equal = equalNode(node20Block,node20Transaction)
        node20 = teaslceNode(node20Version,node20Equal,node18,None)
    
        node16Version = int.from_bytes(b'\x10','big')
        node16Block = int.from_bytes(b'\x34\x6C\xEC','big')
        node16Transaction = int.from_bytes(b'\x0C\x25','big')
        node16Equal = equalNode(node16Block,node16Transaction)
        node16 = teaslceNode(node16Version,node16Equal,node8,node20)

        rootNode = node16

        isbuilt = True
    
    return rootNode


def teaslseTree():
    # construct storage efficient ternary tree
    
    isbuilt = False
    version = []
    rootNode = None
    
    if (isbuilt == False):
        
        node123Version = [int.from_bytes(b'\x01','big'),int.from_bytes(b'\x02','big'),int.from_bytes(b'\x03','big')]
        
        node1Block = int.from_bytes(b'\x34\x6C\x76','big')
        node1Transaction = int.from_bytes(b'\x09\x0E','big') 
        node123Left = leafEqualNode(node1Block,node1Transaction)
            
        node2Block = int.from_bytes(b'\x34\x6C\x81','big')
        node2Transaction = int.from_bytes(b'\x07\x5B','big')
        node123Equal = leafEqualNode(node2Block,node2Transaction)
            
        node3Block = int.from_bytes(b'\x34\x6C\x83','big')
        node3Transaction = int.from_bytes(b'\x0C\xF7','big')
        node123Right = leafEqualNode(node3Block,node3Transaction)
        
        node123 = teaslseLeafNode(node123Version,node123Left,node123Equal,node123Right)
        
        node567Version = [int.from_bytes(b'\x05','big'),int.from_bytes(b'\x06','big'),int.from_bytes(b'\x07','big')]

        node5Block = int.from_bytes(b'\x34\x6C\x93','big')
        node5Transaction = int.from_bytes(b'\x04\x07','big')
        node567Left = leafEqualNode(node5Block,node5Transaction)
            
        node6Block = int.from_bytes(b'\x34\x6C\x96','big')
        node6Transaction = int.from_bytes(b'\x04\xB4','big')
        node567Equal = leafEqualNode(node6Block,node6Transaction)
        
        node7Block = int.from_bytes(b'\x34\x6C\xA1','big')
        node7Transaction = int.from_bytes(b'\x01\xB6','big')
        node567Right = leafEqualNode(node7Block,node7Transaction)
        
        node567 = teaslseLeafNode(node567Version,node567Left,node567Equal,node567Right)
            
        node4Version = [int.from_bytes(b'\x04','big')]

        node4Block = int.from_bytes(b'\x34\x6C\x8B','big')
        node4Transaction = int.from_bytes(b'\x06\x09','big')
        node4Equal = leafEqualNode(node4Block,node4Transaction)

        node4 = teaslseIntNode(node4Version,node123,node4Equal,node567)

        node91011Version = [int.from_bytes(b'\x09','big'),int.from_bytes(b'\x0A','big'),int.from_bytes(b'\x0B','big')]
        
        node9Block = int.from_bytes(b'\x34\x6C\xB0','big')
        node9Transaction = int.from_bytes(b'\x0A\x94','big')
        node91011Left = leafEqualNode(node9Block,node9Transaction)
        
        node10Block = int.from_bytes(b'\x34\x6C\xB7','big')
        node10Transaction = int.from_bytes(b'\x0A\x29','big')
        node91011Equal = leafEqualNode(node10Block,node10Transaction)

        node11Block = int.from_bytes(b'\x34\x6C\xC9','big')
        node11Transaction = int.from_bytes(b'\x00\x8E','big')
        node91011Right = leafEqualNode(node11Block,node11Transaction)
        
        node91011 = teaslseLeafNode(node91011Version,node91011Left,node91011Equal,node91011Right)
        
        node131415Version = [int.from_bytes(b'\x0D','big'),int.from_bytes(b'\x0E','big'),int.from_bytes(b'\x0F','big')]
        
        node13Block = int.from_bytes(b'\x34\x6C\xDA','big')
        node13Transaction = int.from_bytes(b'\x06\xEC','big')
        node131415Left = leafEqualNode(node13Block,node13Transaction)
        
        node14Block = int.from_bytes(b'\x34\x6C\xDD','big')
        node14Transaction = int.from_bytes(b'\x0C\x96','big')
        node131415Equal = leafEqualNode(node14Block,node14Transaction)

        node15Block = int.from_bytes(b'\x34\x6C\xE6','big')
        node15Transaction = int.from_bytes(b'\x07\x4A','big')
        node131415Right = leafEqualNode(node15Block,node15Transaction)
                
        node131415 = teaslseLeafNode(node131415Version,node131415Left,node131415Equal,node131415Right)
        
        node12Version = [int.from_bytes(b'\x0C','big')]
       
        node12Block = int.from_bytes(b'\x34\x6C\xCE','big')
        node12Transaction = int.from_bytes(b'\x0A\xC4','big')
        node12Equal = leafEqualNode(node12Block,node12Transaction)
        
        node12 = teaslseIntNode(node12Version,node91011,node12Equal,node131415)
           
        node8Version = [int.from_bytes(b'\x08','big')]

        node8Block = int.from_bytes(b'\x34\x6C\xA4','big')
        node8Transaction = int.from_bytes(b'\x09\x31','big')
        node8Equal = leafEqualNode(node8Block,node8Transaction)
        node8 = teaslseIntNode(node8Version,node4,node8Equal,node12)
            
        node171819Version = [int.from_bytes(b'\x11','big'),int.from_bytes(b'\x12','big'),int.from_bytes(b'\x13','big')]
        
        node17Block = int.from_bytes(b'\x34\x6C\xF2','big')
        node17Transaction = int.from_bytes(b'\x06\x85','big')
        node171819Left = leafEqualNode(node17Block,node17Transaction)

        node18Block = int.from_bytes(b'\x34\x6C\xF6','big')
        node18Transaction = int.from_bytes(b'\x04\x2D','big')
        node171819Equal = leafEqualNode(node18Block,node18Transaction)

        node19Block = int.from_bytes(b'\x34\x6C\xF8','big')
        node19Transaction = int.from_bytes(b'\x09\xD1','big')
        
        node171819Right = leafEqualNode(node19Block,node19Transaction)
        
        node171819 = teaslseLeafNode(node171819Version,node171819Left,node171819Equal,node171819Right)
        
        node20Version = [int.from_bytes(b'\x14','big')]
        
        node20Block = int.from_bytes(b'\x34\x6C\xFB','big')
        node20Transaction = int.from_bytes(b'\x0A\x71','big')
        
        node20Equal = leafEqualNode(node20Block,node20Transaction)
        
        node20 = teaslseIntNode(node20Version,node171819,node20Equal,None)
            
        node16Version = [int.from_bytes(b'\x10','big')]
        
        node16Block = int.from_bytes(b'\x34\x6C\xEC','big')
        node16Transaction = int.from_bytes(b'\x0C\x25','big')
        
        node16Equal = leafEqualNode(node16Block,node16Transaction)
        
        node16 = teaslseIntNode(node16Version,node8,node16Equal,node20)
        
        rootNode = node16
        
        isbuilt = True
              
    return rootNode


def teaslce(searchVersion, rootNode):
    # search for compute efficient version
    emptyBlock = []
    emptyTransaction = []
    closestVersionNumber = 0
    fallbackVersion = equalNode(emptyBlock,emptyTransaction)
    closestVersion = equalNode(emptyBlock,emptyTransaction)
    currentNode = rootNode
    #currentNode = teaslceTree() # begin at the root node    
    while True:
        if currentNode.version >= searchVersion:
            fallbackVersion.block = currentNode.equalPointer.block
            fallbackVersion.transaction = currentNode.equalPointer.transaction
        if currentNode.version == searchVersion or (currentNode.leftPointer == None and currentNode.rightPointer == None):
            closestVersion.block = currentNode.equalPointer.block
            closestVersion.transaction = currentNode.equalPointer.transaction
            closestVersionNumber = currentNode.version
            break
        elif currentNode.version > searchVersion:
            if currentNode.leftPointer != None:
                currentNode = currentNode.leftPointer
                continue
            else:
                closestVersion.block = currentNode.equalPointer.block
                closestVersion.transaction = currentNode.equalPointer.transaction
                closestVersionNumber = currentNode.version
                break
        elif currentNode.version < searchVersion:
            if currentNode.rightPointer != None:
                currentNode = currentNode.rightPointer
                continue
            else:
                closestVersion.block = currentNode.equalPointer.block
                closestVersion.transaction = currentNode.equalPointer.transaction
                closestVersionNumber = currentNode.version
                break
    if closestVersionNumber < searchVersion:
        closestVersion.block = fallbackVersion.block
        closestVersion.transaction = fallbackVersion.transaction
    return closestVersion


def teaslse(searchVersion,rootNode):
    # search for storage efficient version
    
    emptyBlock = []
    emptyTransaction = []
    closestVersionNumber = 0
    fallbackVersion = equalNode(emptyBlock,emptyTransaction)
    closestVersion = equalNode(emptyBlock,emptyTransaction)
    currentNode = rootNode
    
    #currentNode = teaslseTree() # begin at the root node
    currentLeafNode = equalNode(emptyBlock,emptyTransaction)    

    while True:
        # closest version if not built from level 0
        if currentNode.version[0] >= searchVersion:
            # fallback Version
            fallbackVersion.block = currentNode.equalPointer.block
            fallbackVersion.transaction = currentNode.equalPointer.transaction
            
        # intermediate node
        if currentNode.version[0] == searchVersion:
            closestVersion.block = currentNode.equalPointer.block
            closestVersion.transaction = currentNode.equalPointer.transaction
            closestVersionNumber = currentNode.version[0]
            break
        elif currentNode.version[0] > searchVersion:
            if isinstance(currentNode.leftPointer, teaslseIntNode):
                currentNode = currentNode.leftPointer
                continue
            break
        elif currentNode.version[0] < searchVersion:
            if isinstance(currentNode.rightPointer, teaslseIntNode):
                currentNode = currentNode.rightPointer
                continue
            break
                
    if isinstance(currentNode.leftPointer, teaslseLeafNode) or isinstance(currentNode.rightPointer, teaslseLeafNode):
        # process the leaf node
        if searchVersion < currentNode.version[0] and currentNode.leftPointer != None:
            # leaf node to left
            currentLeafNode = currentNode.leftPointer
            if currentLeafNode.version[0] == searchVersion:
                closestVersion.block = currentLeafNode.leftPointer.block
                closestVersion.transaction = currentLeafNode.leftPointer.transaction
                closestVersionNumber = currentLeafNode.version[0]
            elif currentLeafNode.version[1] == searchVersion:
                closestVersion.block = currentLeafNode.equalPointer.block
                closestVersion.transaction = currentLeafNode.equalPointer.transaction
                closestVersionNumber = currentLeafNode.version[1]
            elif currentLeafNode.version[2] == searchVersion:
                closestVersion.block = currentLeafNode.rightPointer.block
                closestVersion.transaction = currentLeafNode.rightPointer.transaction
                closestVersionNumber = currentLeafNode.version[2]
        elif searchVersion > currentNode.version[0] and currentNode.rightPointer != None:
            # leaf node to right
            currentLeafNode = currentNode.rightPointer
            if currentLeafNode.version[0] == searchVersion:
                closestVersion.block = currentLeafNode.leftPointer.block
                closestVersion.transaction = currentLeafNode.leftPointer.transaction
                closestVersionNumber = currentLeafNode.version[0]
            elif currentLeafNode.version[1] == searchVersion:
                closestVersion.block = currentLeafNode.equalPointer.block
                closestVersion.transaction = currentLeafNode.equalPointer.transaction
                closestVersionNumber = currentLeafNode.version[1]
            elif currentLeafNode.version[2] == searchVersion:
                closestVersion.block = currentLeafNode.rightPointer.block
                closestVersion.transaction = currentLeafNode.rightPointer.transaction
                closestVersionNumber = currentLeafNode.version[2]
        
    if closestVersionNumber < searchVersion:
        closestVersion.block = fallbackVersion.block
        closestVersion.transaction = fallbackVersion.transaction
                                    
    return closestVersion


def easl(data):
    
    # encoding query version (8-bit), EASL node version (8-bit),
    # each skip list entry (<= 12) (56-bit) [version (8-bit), block height (24-bit), transaction number (16-bit)]
        
    # 1 bytes query version
    queryVersion = data[0]
    # 1 bytes node version
    nodeVersion = data[1]
        
    # node skip list level processing - pre-nodes (6 bytes each)
    nodeSize = 3
    
    # start from the highest skip list node
    l = len(data)
    # currentOffsetCounter
    k = l
    # node offset
    i = 2
        
    # total number of pre-nodes
    preNodeCount = int((k - i) // nodeSize)

    # location of next or matching EASL node
    block = None
    transaction = None
               
    # for each pre-node version define valid block
    for j in range(preNodeCount):
        # set next node offset
        k = k - nodeSize
        
        # 1 bytes pre-node version
        preNodeVersion = data[k]

        if preNodeVersion <= queryVersion:
            # location of next or matching EASL node
            # 3 bytes block height
            block = data[k+1]
            # 2 bytes transaction number
            transaction = data[k+2]
                
    # moves the return data (closest_version) into the message buffer
    return [block,transaction]

def measure_size(function):
    gc.collect()
    b = gc.mem_alloc()
    obj = function()
    gc.collect()
    a = gc.mem_alloc()
    return a - b

if __name__ == "__main__":
    
    #versions = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]
    
    #for version in versions:
    #start = time.ticks_us()
    #output = teaslse(version,rootNode)
    #output = easl(data)
    #end = time.ticks_us()
    #execution_time = time.ticks_diff(end, start)
    #print(str(execution_time) + " --- " + str(output[0]) + ", " + str(output[1]))
    #gc.collect()
    teaslseroot = teaslseTree()
    print(measure_size(teaslseroot))
