#include <list>
#include <vector>
#include <memory>

#include "supporting_structures_TEASLe.cpp"

bool TEASLse_append(Node &version, std::vector<std::list<std::shared_ptr<TEASLse_Node>>> &levels){
    // builds TEASL-se index from the EASL index one version at a time

    int index_level = 3; // this is the base of the TEASL-ce index - user defined
    int highest_level;
    bool version_processed = false;

    // adds specifc new node to an existing level (index adjusted to 0)
    Node* cached_left_node;
    int build_time_cache = 0;

    if (version.node_level.size() > 0){
        // sets the highest level, only if the height is 1 or more
        highest_level = version.node_level.back();
    }

    // converts the level height to TEASL
    int relative_level = highest_level - index_level;

    // returns a count of the levels existing in the TEASL index
    int current_levels = levels.size();

    // is adjusted because the TEASL starts at level 1
    if (relative_level + 1 > current_levels) {        
        // adds the new level/s when required
        for (int k = 0; k < (relative_level - current_levels); k++){
            std::list<std::shared_ptr<TEASLse_Node>> new_list;
            levels.push_back(new_list);              
        }
    }

    if (relative_level == 0){
        // gets current teasl leaf node for comparision
        auto new_teasle_node = std::dynamic_pointer_cast<TEASLse_Leaf_Node>(levels.at(0).back());        
        if (current_levels >= 2){
            if (new_teasle_node->Version.size() == 3){
                // node has left and right pointers written and so no node exists writes to cache
                cached_left_node = &version;                  
            }

            // already has left pointer and confirms leaf version is smaller than right pointer.
            new_teasle_node->right_pointer = &version;
            // adds version
            new_teasle_node->Version.push_back(version.Version);

        }
        else if (current_levels == 1){

            if (levels.at(0).size() == 0){
                // no teasle node for comparison
                cached_left_node = &version;

            } else {                    
                // already has left pointer and confirms leaf version is smaller than right pointer.
                new_teasle_node->right_pointer = &version;
                // adds version
                new_teasle_node->Version.push_back(version.Version);
            }
        }  
    }
    else if (relative_level == 1){

        levels.at(relative_level - 1).push_back(std::make_shared<TEASLse_Leaf_Node>());
        auto new_teasle_node = std::dynamic_pointer_cast<TEASLse_Leaf_Node>(levels.at(relative_level - 1).back());

        new_teasle_node->equal_node_pointer = &version;
        new_teasle_node->Version.push_back(version.Version);

        // has no left or right pointer, so confirms to write left pointer.
        new_teasle_node->left_pointer = cached_left_node;
        // adds version to list
        new_teasle_node->Version.push_front(cached_left_node->Version);
                    
        if (relative_level < current_levels){
            // update the right pointers in the levels above
            auto relative_highest = std::dynamic_pointer_cast<TEASLse_Int_Node>(levels.back().back()); // start from the highest

            while(true){
            
                if (relative_highest->right_pointer != NULL) {
                    relative_highest = std::dynamic_pointer_cast<TEASLse_Int_Node>(relative_highest->right_pointer);
                    continue;
                }
                
                relative_highest->right_pointer = new_teasle_node;                     
                break;
            }
        }
    }
    else if (relative_level > 1){

        levels.at(relative_level - 1).push_back(std::make_shared<TEASLse_Int_Node>());
        auto new_teasle_node = std::dynamic_pointer_cast<TEASLse_Int_Node>(levels.at(relative_level - 1).back());
        new_teasle_node->equal_node_pointer = &version;
        new_teasle_node->Version.push_back(version.Version);
        
        // adjusted by 1 because TEASLe starts from level 1           
        if (relative_level + 1 > 1){
            // set left pointer to last node in level below
            new_teasle_node->left_pointer = levels.at(relative_level - 2).back();
                            
            if (relative_level < current_levels){
                // update the right pointers in the levels above
                auto relative_highest = std::dynamic_pointer_cast<TEASLse_Int_Node>(levels.back().back()); // start from the highest

                while(true){
                    
                    if (relative_highest->right_pointer->Version == new_teasle_node->left_pointer->Version){
                        // allows to re-write right pointer set to prevent orphaned node
                        relative_highest->right_pointer = new_teasle_node;                           
                        break;
                    }
                    relative_highest = std::dynamic_pointer_cast<TEASLse_Int_Node>(relative_highest->right_pointer);
                }
            }
        }
    }
    version_processed = true;
    return version_processed;
}
