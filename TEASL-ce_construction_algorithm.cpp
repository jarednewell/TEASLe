#include <list>
#include <vector>
#include "supporting_structures_TEASL.cpp"

bool TEASLce_append(Node &version, std::vector<std::list<TEASL_Node>> &levels){
    // builds TEASL-ce index from the EASL index one version at a time

    int index_level = 3; // this is the base of the TEASL-ce index - user defined
    int highest_level;
    bool version_processed = false;

    if (version.node_level.size() > 0){
        // sets the highest level, only if the height is 1 or more
        highest_level = version.node_level.back();
    }
        
    if (highest_level >= index_level){
        // if the highest level is greater or equal to the TEASL starting level

        // converts the level height to TEASL
        int relative_level = highest_level - index_level;

        // is adjusted because the TEASL starts at level 1
        int teasl_level = relative_level + 1;
        // returns a count of the levels existing in the TEASL index
        int current_levels = levels.size();

        if (teasl_level > current_levels) {        
            // adds the new level/s when required
            for (int k = 0; k < (teasl_level - current_levels); k++){
                std::list<TEASL_Node> new_list;
                levels.push_back(new_list);
            }
        }

        // adds new node to an existing level (index adjusted to 0)
        TEASL_Node teasl_node;
        levels.at(relative_level).push_back(teasl_node);
        TEASL_Node* new_teasl_node = &(levels.at(relative_level).back());
        

        // if equal index level then no left pointer
        new_teasl_node->Version = version.Version;
        new_teasl_node->equal_node_pointer = &version;

        if (teasl_level > 1){
            // set left pointer to last node in level below
            new_teasl_node->left_pointer = &levels.at(relative_level - 1).back();
        }

        if (teasl_level < current_levels){
            // above removes out of bounds

            // new node is added as the first entry in the heighest level, so not orphaned and maintains the ternary tree constraints
            TEASL_Node* relative_highest = new TEASL_Node();
            relative_highest = &levels.back().back(); // start from the highest

            // locate the current highest node, to right pointer to the current highest level
            while(true){

                if (new_teasl_node->left_pointer == NULL) {
                    // Only for bottom level.
                    if (relative_highest->right_pointer != NULL) {
                        relative_highest = relative_highest->right_pointer;
                        continue;
                    }
                    else {
                        relative_highest->right_pointer = new_teasl_node;
                        break;
                    }
                }
                else {
                    if (relative_highest->right_pointer->Version == new_teasl_node->left_pointer->Version){
                        // allows to re-write right pointer set to prevent orphaned node
                        relative_highest->right_pointer = new_teasl_node;
                        break;
                    }
                    relative_highest = relative_highest->right_pointer;  
                }     
            }
        }
    }
    version_processed = true;
    return version_processed;
}
