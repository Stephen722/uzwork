Topic:
1. Topic sorted set: define the sorted set which can store 5000 elements.
2. Topic hash: Define the hash which can store 5000 elements
3. Define three lists to record the inserted/updated/deleted topic, the CUD changes of topic will be applied in Redis directly,
system applies these changed into database with batch execution in thread. 