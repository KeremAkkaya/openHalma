public enum LOGGER_LEVEL {

    ERROR(0, "ERROR"), WARNING(1, "WARNING"), GAMEINFO(5, "GAMEINFO");

    //TODO: more to come when needed

    private final int val;
    private final String name;

    LOGGER_LEVEL(int val, String name) {
        this.val = val;
        this.name = name;
    }

    public String toString() {
        return name;
    }

}