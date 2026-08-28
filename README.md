# TEASLe: A Lightweight Blockchain Data Index for Smart Cards and Constrained IoT Devices

## Introduction
Blockchain stores data immutably across distributed network servers, creating replicated copies. Each block is linked in sequence, so data searches require inspection from the newest block back to the first. This linear search method becomes prohibitively expensive for any large blockchain.
Consequently, the Ternary Tree Enhanced Append-only Skip List (TEASL) indexing technique produced more consistent data retrieval times than EASL, and as a composite index, was now available on the blockchain client device. Thus, it introduces efficient access to blockchain data where maintaining a local blockchain copy is not feasible. 
However, smart cards and microcontroller-based Internet of Things (IoT) devices with limited bandwidth require extremely low compute and storage use. To address this, we propose TEASLe, a flexible index class optimised for such constraints. We empirically validate TEASLe’s efficiency on smart cards and constrained IoT devices, using a real-world public blockchain, demonstrating practical blockchain data integration.

### TEASLe composite index class with storage- (SE) and compute- (CE) efficient indexes.
![TEASLe composite blockchain index traversal](https://github.com/jarednewell/TEASLe/blob/main/TEASLe%20composite%20blockchain%20indexing%20technique.png)

### TEASLe composite index device application.
<img src="https://github.com/jarednewell/TEASLe/blob/main/TEASLe%20on%20smart%20card.png" alt="TEASLe indexing on smart card ACOSJ-G" width="400" height="600">
<img src="https://github.com/jarednewell/TEASLe/blob/main/TEASLe%20on%20Raspberry%20PI%20Pico.png" alt="TEASLe indexing on Raspberry Pi Pico WH" width="400" height="600">
<img src="https://github.com/jarednewell/TEASLe/blob/main/TEASLe%20on%20STM32L072CZ%20IoT%20over%20LoRaWAN.png" alt="TEASLe indexing on STM B-L072Z-LRWAN1" width="400" height="600">


## The Node Construction Algorithm
The algorithm and the supporting structure included in this repository is written in C++, all definitions and the implementation of TEASLe is included in the following article:

https://doi.org/10.1109/JIOT.2026.3703646

## Reference
If you intend on using TEASLe and this repository we request you reference using the following:

Harvard
```
Newell, J., Rehman, S. ur, Mamun, Q. and Islam, M.Z. (2026)
‘TEASLe: A Lightweight Blockchain Data Index for Smart Cards and Constrained IoT Devices’,
IEEE Internet of Things Journal, 13(17), p. 38592-38616.
doi: https://doi.org/10.1109/JIOT.2026.3703646.
```

APA
```
Newell, J., Rehman, S. u., Mamun, Q., & Islam, M. Z. (2026).
TEASLe: A Lightweight Blockchain Data Index for Smart Cards and Constrained IoT Devices
IEEE Internet of Things Journal, 13(17), 38592-38616.
https://doi.org/10.1109/JIOT.2026.3703646.
```

BibTeX
```
@article{11567998,
  author={Newell, Jared and Rehman, Sabih Ur and Mamun, Quazi and Islam, Md Zahidul},
  journal={IEEE Internet of Things Journal}, 
  title={TEASLe: A Lightweight Blockchain Data Index for Smart Cards and Constrained IoT Devices}, 
  year={2026},
  volume={13},
  number={17},
  pages={38592-38616},
  keywords={Indexes;Indexing;Internet of Things;Algorithms;Servers;Append-only skip list;blockchain;data index;data retrieval;data search;Internet of Things (IoT);smart card},
  doi={10.1109/JIOT.2026.3703646}}

```
