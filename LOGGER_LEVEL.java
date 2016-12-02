public enum LOGGER_LEVEL {

    ERROR("ERROR"),
    WARNING("WARNING"),
    GAMEINFO("GAMEINFO"),
    AI_DEBUG("AI_DEBUG"),
    DEBUG("DEBUG"),
    TEMP_DEBUG("TEMP_DEBUG"),
    GRAPHICAL_DEBUG("GRAPHICAL_DEBUG"),
    GUI_DEBUG("GUI_DEBUG");

    //TODO: more to come when needed

    private final String name;

    LOGGER_LEVEL(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

}