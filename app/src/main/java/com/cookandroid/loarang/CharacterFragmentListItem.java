package com.cookandroid.loarang;

public class CharacterFragmentListItem {
    private String character_image;
    private String character_nickname;
    private String character_level;
    private String character_itemLevel;
    private String character_server;
    private String character_class;

    public String getCharacter_image() {
        return character_image;
    }

    public void setCharacter_image(String character_image) {
        this.character_image = character_image;
    }

    public String getCharacter_nickname() {
        return character_nickname;
    }

    public void setCharacter_nickname(String character_nickname) {
        this.character_nickname = character_nickname;
    }

    public String getCharacter_level() {
        return character_level;
    }

    public void setCharacter_level(String character_level) {
        this.character_level = character_level;
    }

    public String getCharacter_itemLevel() {
        return character_itemLevel;
    }

    public void setCharacter_itemLevel(String character_itemLevel) {
        this.character_itemLevel = character_itemLevel;
    }

    public String getCharacter_server() {
        return character_server;
    }

    public void setCharacter_server(String character_server) {
        this.character_server = character_server;
    }

    public String getCharacter_class() {
        return character_class;
    }

    public void setCharacter_class(String character_class) {
        this.character_class = character_class;
    }

    CharacterFragmentListItem(String character_image, String character_nickname, String character_level, String character_itemLevel, String character_server, String character_class) {
        this.character_image = character_image;
        this.character_nickname = character_nickname;
        this.character_level = character_level;
        this.character_itemLevel = character_itemLevel;
        this.character_server = character_server;
        this.character_class = character_class;
    }
}
