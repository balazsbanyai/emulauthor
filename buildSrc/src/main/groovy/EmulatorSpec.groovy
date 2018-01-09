class EmulatorSpec {

    String abi
    String pkg

    void abi(final String abi) {
        this.abi = abi
    }

    void pkg(final String pkg) {
        this.pkg = pkg
    }

    String getAbi() {
        return abi
    }

    String getPkg() {
        return pkg
    }
}