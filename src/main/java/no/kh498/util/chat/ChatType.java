package no.kh498.util.chat;

/**
 * Taken from
 * <a href="https://github.com/aadnk/ProtocolLib/blob/master/modules/API/src/main/java/com/comphenix/protocol/wrappers/EnumWrappers.java#L337">ProtocolLib</a>
 * to support older version (due to reload issues) as 4.3.0 cannot be used with reload
 *
 * @author Kristian
 */
public enum ChatType {

    CHAT(0),
    SYSTEM(1),
    GAME_INFO(2);

    private final byte id;

    ChatType(final int id) {
        this.id = (byte) id;
    }

    public byte getId() {
        return this.id;
    }

}
