#include <vector>
#include "supporting_structures_EASL.cpp"

struct TEASLse_Node {
    /* node data container */
    std::list<int> Version; // maximum size 3
    Node* equal_node_pointer;
    virtual ~TEASLse_Node() = default; // virtual destructor
};

struct TEASLse_Int_Node: TEASLse_Node {
    /* intermediate variant node */
    std::shared_ptr<TEASLse_Node> left_pointer;
    std::shared_ptr<TEASLse_Node> right_pointer;
};

struct TEASLse_Leaf_Node: TEASLse_Node {
    /* leaf variant node */
    Node* left_pointer;
    Node* right_pointer;
};
