package ru.savchenko.andrey.timeline.entities;

/**
 * Created by Andrey on 12.08.2017.
 */

public class Card {
    private int id;
    private int year;
    private String title;
    private String description;
    private String imagePath;
    private String category;

    public Card(int id, int year, String imagePath) {
        this.id = id;
        this.year = year;
        this.imagePath = imagePath;
    }

    public Card(int id, int year, String title, String description, String imagePath) {
        this.id = id;
        this.year = year;
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
    }

    public Card(int year, String title, String description, String imagePath) {
        this.year = year;
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", year=" + year +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
