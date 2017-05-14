package com.mail.impl;

public enum FolderMod {
    READ_ONLY(1),READ_WRITE(2);

    private int mod;

    FolderMod(int mod){
        this.mod = mod;
    }

    public int getMod() {
        return mod;
    }
}
