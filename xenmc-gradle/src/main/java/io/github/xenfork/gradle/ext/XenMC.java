package io.github.xenfork.gradle.ext;

import java.util.concurrent.atomic.AtomicBoolean;

public class XenMC {
    public AtomicBoolean loader = new AtomicBoolean(false);

    public void isLoader() {
        loader.set(true);
    }
}
