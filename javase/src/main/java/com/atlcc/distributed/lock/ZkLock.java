package com.atlcc.distributed.lock;

interface ZkLock {

    void lock();

    void unLock();
}
