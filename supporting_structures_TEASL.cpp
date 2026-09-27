#include <vector>
#include "supporting_structures_EASL.cpp"

struct TEASL_Node {
    /* TEASLce data container */
    int Version; // version of identifier
    TEASL_Node* left_pointer;
    Node* equal_node_pointer;
    TEASL_Node* right_pointer;
};
